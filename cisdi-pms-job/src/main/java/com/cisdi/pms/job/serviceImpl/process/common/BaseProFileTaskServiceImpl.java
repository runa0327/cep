package com.cisdi.pms.job.serviceImpl.process.common;

import com.cisdi.pms.job.domain.process.common.BaseProFileTask;
import com.cisdi.pms.job.domain.project.ProjectInventory;
import com.cisdi.pms.job.mapper.process.common.BaseProFileTaskMapper;
import com.cisdi.pms.job.mapper.project.ProjectInventoryMapper;
import com.cisdi.pms.job.service.process.common.BaseProFileTaskService;
import com.cisdi.pms.job.service.process.common.WfProcessService;
import com.cisdi.pms.job.service.project.ProjectInventoryService;
import com.cisdi.pms.job.serviceImpl.project.PmPrjServiceImpl;
import com.cisdi.pms.job.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class BaseProFileTaskServiceImpl implements BaseProFileTaskService {

    @Resource
    private BaseProFileTaskMapper baseProFileTaskMapper;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ProjectInventoryService projectInventoryService;

    @Resource
    private WfProcessService wfProcessService;


    /**
     * 流程文件同步资料库
     */
    @Override
    public void syncProFileToDatabase() {
        List<BaseProFileTask> list = baseProFileTaskMapper.queryUnEnd();
        if (!CollectionUtils.isEmpty(list)){
            String date = DateUtil.getNormalTimeStr(new Date());
            AtomicInteger atomicInteger = new AtomicInteger(0);
            for (BaseProFileTask tmp : list) {
                log.info("流程文件同步资料库文件--------------------当前进程第" + atomicInteger.getAndIncrement() + "个");
                tmp.setEndDateTime(date);
                taskExecutor.execute(()->{
                    String entityRecordId = tmp.getEntityRecordId();
                    String entCode = tmp.getEntCode();
                    String processInstanceId = tmp.getProcessInstanceId();
                    String processId = wfProcessService.getProcessIdByProcessId(tmp.getWfProcessId());
                    // 根据流程信息查询该流程对应的资料清单类型
                    List<ProjectInventory> procInventoryList = projectInventoryService.queryProInventory(processId);
                    if (CollectionUtils.isEmpty(procInventoryList)){
                        tmp.setTaskRemark("该流程没有配置资料清单类型，同步失败。流程实例id为："+processInstanceId);
                    } else {
                        List<Map<String,Object>> list1 = jdbcTemplate.queryForList("select * from " + entCode + " where id = ? and status = 'AP' ",entityRecordId);
                        if (!CollectionUtils.isEmpty(list1)){
                            Map<String,Object> map = list1.get(0);  // 该条流程表单信息
                            String projectId = PmPrjServiceImpl.getProjectId(map);
                            if (StringUtils.hasText(projectId)){
                                // 根据项目id找到项目资料清单 项目可能是多个项目
                                String[] projectArr = projectId.split(",");
                                for (String prjId : projectArr) {
                                    List<ProjectInventory> projectInventoryList = projectInventoryService.queryProjectInventory(prjId);
                                    // 创建更新项目清单
                                    String msg = projectInventoryService.createOrUpdateInventory(processInstanceId,processId,map,procInventoryList,projectInventoryList);
                                }
                            } else {
                                tmp.setTaskRemark("该流程记录项目id为空，无法同步！流程实例id："+processInstanceId);
                            }
                        } else {
                            tmp.setTaskRemark("未查询到该条流程详情信息！流程实例id："+processInstanceId);
                        }
                    }
                    tmp.setIsEnd(1);
                    baseProFileTaskMapper.updateById(tmp);
                });
            }

        }
    }
}
