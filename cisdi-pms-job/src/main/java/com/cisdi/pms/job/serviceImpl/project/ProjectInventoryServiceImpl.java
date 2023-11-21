package com.cisdi.pms.job.serviceImpl.project;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.project.ProjectInventory;
import com.cisdi.pms.job.mapper.project.ProjectInventoryMapper;
import com.cisdi.pms.job.service.project.ProjectInventoryService;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProjectInventoryServiceImpl implements ProjectInventoryService {

    @Resource
    private ProjectInventoryMapper projectInventoryMapper;

    /**
     * 根据流程信息查询该流程对应的资料清单类型
     *
     * @param processId 流程id
     * @return 流程资料清单
     */
    @Override
    public List<ProjectInventory> queryProInventory(String processId) {
        return projectInventoryMapper.queryProInventory(processId);
    }

    /**
     * 根据项目id找到项目资料清单
     *
     * @param prjId 项目id
     * @return 项目资料清单
     */
    @Override
    public List<ProjectInventory> queryProjectInventory(String prjId) {
        return projectInventoryMapper.queryProjectInventory(prjId);
    }

    /**
     * 创建或更新项目清单及清单文件明细
     *
     * @param projectId            项目id
     * @param processInstanceId    流程实例id
     * @param processId            流程id
     * @param map                  对应流程详情
     * @param procInventoryList    流程资料清单
     * @param projectInventoryList 项目清单
     */
    @Override
    public String createOrUpdateInventory(String projectId, String processInstanceId, String processId, Map<String, Object> map, List<ProjectInventory> procInventoryList, List<ProjectInventory> projectInventoryList) {
        String msg = "";
        for (ProjectInventory tmp : procInventoryList) {
            String inventoryId = tmp.getId(); // 资料清单类型id
            String adAttId = tmp.getAdAttId(); // 属性字段id
            String buyMatterId = tmp.getBuyMatterId(); // 采购事项
            String fileMasterId = tmp.getFileMasterInventoryTypeId(); // 主清单类型
            String adAttCode = tmp.getAdAttCode(); // 字段编码
            boolean mapCheckResult = false; // 和流程表单做匹配
            if ("0099952822476409136".equals(processId) || "1675746857843830784".equals(fileMasterId)){ // 合同签订需要额外根据采购事项判断
                String buyMatterStr = JdbcMapUtil.getString(map,"BUY_MATTER_ID");
                if (StringUtils.hasText(buyMatterStr)){
                    if (StringUtils.hasText(buyMatterId) && buyMatterId.equals(buyMatterStr)){
                        String fileId = JdbcMapUtil.getString(map,"adAttCode");
                        if (StringUtils.hasText(fileId)){
                            // 根据项目id+合同事项+资料清单类型判断是否存在项目清单，存在则修改/新增清单明细，不存在则新增项目清单并新增清单明细
                            if (CollectionUtils.isEmpty(projectInventoryList)){
                                String id = IdUtil.getSnowflakeNextIdStr();
                            } else {
                                boolean check = projectInventoryList.stream().map(p->projectId.equals(p.getProjectId()) && )
                            }


                        } else {
                            msg = "该流程没有对应文件信息，同步失败！流程实例id："+processInstanceId + " ; 合同事项：" + buyMatterId;
                        }
                    }
                } else {
                    msg = "该流程没有采购事项，同步失败！流程实例id："+processInstanceId;
                }
            } else {

            }
        }
        return msg;
    }
}
