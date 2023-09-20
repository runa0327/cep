package com.cisdi.pms.job.serviceImpl.process;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.notice.BaseThirdInterface;
import com.cisdi.pms.job.domain.process.PmPostAppoint;
import com.cisdi.pms.job.domain.process.WfProcess;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.project.PmRoster;
import com.cisdi.pms.job.domain.project.ProjectStart;
import com.cisdi.pms.job.mapper.base.HrDeptMapper;
import com.cisdi.pms.job.mapper.notice.BaseThirdInterfaceMapper;
import com.cisdi.pms.job.mapper.process.PmPostAppointMapper;
import com.cisdi.pms.job.mapper.process.WfProcessMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.mapper.project.PmRosterMapper;
import com.cisdi.pms.job.mapper.project.ProjectStartMapper;
import com.cisdi.pms.job.service.process.PmPostAppointService;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PmPostAppointServiceImpl implements PmPostAppointService {

    @Resource
    private PmPostAppointMapper pmPostAppointMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private BaseThirdInterfaceMapper baseThirdInterfaceMapper;

    @Resource
    private PmRosterMapper pmRosterMapper;

    @Resource
    private ProjectStartMapper projectStartMapper;

    @Resource
    private HrDeptMapper hrDeptMapper;

    @Resource
    private WfProcessMapper wfProcessMapper;

    @Resource
    private WfProcessInstanceService wfProcessInstanceService;

    /**
     * 根据项目id自动发起岗位指派流程
     *
     * @param pmPostAppoint 岗位指派流程实体
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void automaticPmPostAppoint(PmPostAppoint pmPostAppoint) {
        String projectId = pmPostAppoint.getProjectId();
        String interfaceId = pmPostAppoint.getInterfaceId();
        String now = DateUtil.getNormalTimeStr(new Date());
        String userId = pmPostAppoint.getCreateBy();

        List<PmPostAppoint> list = queryListByProjectNotVD(projectId);
        if (CollectionUtils.isEmpty(list)){ // 同一个项目岗位指派流程只能存在一个
            String hrDeptId = hrDeptMapper.queryIdByUserId(userId);
            if (!StringUtils.hasText(hrDeptId)){
                String msg = "该用户没有对应的部门信息，不允许发起流程";
                valueInterface(msg,now,interfaceId,1);
            } else {
                String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr(); // 流程实例id
                String id = IdUtil.getSnowflakeNextIdStr(); // 岗位指派表id

                WfProcessInstance wfProcessInstance = new WfProcessInstance();
                wfProcessInstance.setId(wfProcessInstanceId);
                wfProcessInstanceService.insert(wfProcessInstance);

                PmPostAppoint pmPostAppoint1 = new PmPostAppoint(); // 创建岗位指派表
                pmPostAppoint1.setId(id);
                pmPostAppoint1.setCreateBy(userId);
                pmPostAppoint1.setLastUpdateBy(userId);
                pmPostAppoint1.setStatus("DR");
                pmPostAppoint1.setDeptId(hrDeptId);
                pmPostAppoint1.setCreateDate(now);
                pmPostAppoint1.setLastUpdateDate(now);
                pmPostAppoint1.setTs(now);
                pmPostAppoint1.setId(id);
                pmPostAppoint1.setVer("1");
                pmPostAppointMapper.insert(pmPostAppoint1);

                pmPostAppoint1.setWfProcessInstanceId(wfProcessInstanceId);


                PmPrj pmPrj = pmPrjMapper.queryById(projectId); // 项目基础信息
                ProjectStart projectStart = new ProjectStart();
                List<PmRoster> pmRosterList = pmRosterMapper.queryByProjectId(projectId); // 项目花名册信息

                if (pmPrj == null){
                    projectStart = projectStartMapper.queryByProjectId(projectId);
                }

                // 岗位指派流程表创建数据
                getPmPostAppointByPrjOrPrjStart(pmPostAppoint1,pmPrj,projectStart,pmRosterList);
                pmPostAppointMapper.updateById(pmPostAppoint1);
                Map<String,Object> map = MapUtils.objectToMap(pmPostAppoint1);

                // 创建流程实例、节点实例、用户任务
                WfProcess wfProcess = wfProcessMapper.queryByName("岗位指派");
                wfProcessInstanceService.createAllInstance(wfProcessInstanceId,map,pmPrj,wfProcess,userId,now,0);

                String msg = "发起成功！";
                valueInterface(msg,now,interfaceId,1);

            }

        } else {
            String msg = "该项目已经发起过岗位指派流程，不需重复发起!";
            valueInterface(msg,now,interfaceId,1);
        }
    }

    /**
     * 接口调用结果信息封装
     * @param msg 消息体
     * @param now 时间
     * @param interfaceId 接口调用详情记录id
     * @param executeStatus 接口执行状态 1已执行/成功 0未执行/失败
     */
    public void valueInterface(String msg, String now, String interfaceId, int executeStatus) {
        BaseThirdInterface baseThirdInterface = new BaseThirdInterface();
        if (executeStatus == 0){
            Integer failNum = baseThirdInterfaceMapper.getFailNumsById(interfaceId);
            baseThirdInterface.setFailNum(failNum+1);
        }
        baseThirdInterface.setId(interfaceId);
        baseThirdInterface.setRemark(msg);
        baseThirdInterface.setIsSuccess(executeStatus);
        baseThirdInterface.setSuccessDate(now);
        baseThirdInterface.setTs(now);
        baseThirdInterface.setLastUpdateDate(now);
        baseThirdInterface.setLastUpdateBy("0099250247095871681");
        baseThirdInterfaceMapper.updateConditionById(baseThirdInterface);
    }

    /**
     * 根据项目库或项目启动的项目信息进行岗位指派流程实体数据封装
     * @param pmPostAppoint 被封装赋值的主体
     * @param pmPrj 项目库项目信息
     * @param projectStart 项目启动项目信息
     * @param pmRosterList 项目花名册信息
     */
    private void getPmPostAppointByPrjOrPrjStart(PmPostAppoint pmPostAppoint, PmPrj pmPrj, ProjectStart projectStart,List<PmRoster> pmRosterList) {
        if (pmPrj != null){
            getValueByPmPrj(pmPostAppoint, pmPrj);
        } else {
            getValueByPrjStart(pmPostAppoint,projectStart);
        }
        pmPostAppointValuePostUser(pmPostAppoint,pmRosterList);
    }

    /**
     * 岗位人员封装
     * @param pmPostAppoint 岗位指派实体信息
     * @param pmRosterList 项目花名册信息
     */
    private void pmPostAppointValuePostUser(PmPostAppoint pmPostAppoint, List<PmRoster> pmRosterList) {
        String projectTypeId = pmPostAppoint.getProjectTypeId();
        if ("1638731685728239616".equals(projectTypeId) || "0099799190825080994".equals(projectTypeId) || "0099799190825080740".equals(projectTypeId)){
            pmPostAppoint.setCollectAndRemovePostUserId("1641281525532323840"); // 赋值人员为 未涉及
        }
        if (!CollectionUtils.isEmpty(pmRosterList)){
            for (PmRoster tmp : pmRosterList) {
                String postName = tmp.getPostInfoName();
                String user = tmp.getAdUserId();
                if (StringUtils.hasText(user)){
                    switch (postName) {
                        case "前期报建岗" :
                            pmPostAppoint.setEarlyConstructionPostUserId(user);
                            break;
                        case "土地管理岗" :
                            pmPostAppoint.setLandManagePostUserId(user);
                            break;
                        case "管线迁改岗" :
                            pmPostAppoint.setPipeChangePostUserId(user);
                            break;
                        case "计划运营岗" :
                            pmPostAppoint.setPlanOperatePostUserId(user);
                            break;
                        case "前期设备岗" :
                            pmPostAppoint.setEarlyEquipPostUserId(user);
                            break;
                        case "成本管理岗" :
                            pmPostAppoint.setCostManagePostUserId(user);
                            break;
                        case "合约管理岗" :
                            pmPostAppoint.setContractManagePostUserId(user);
                            break;
                        case "设备成本岗" :
                            pmPostAppoint.setEquipCostPostUserId(user);
                            break;
                        case "采购管理岗" :
                            pmPostAppoint.setBuyManagePostUserId(user);
                            break;
                        case "设计管理岗" :
                            pmPostAppoint.setDesignManagePostUserId(user);
                            break;
                        case "工程管理岗" :
                            pmPostAppoint.setEngineerManagerPostUserId(user);
                            break;
                        case "征拆对接岗" :
                            pmPostAppoint.setCollectAndRemovePostUserId(user);
                            break;
                        case "财务管理岗" :
                            pmPostAppoint.setFinanceManagePostUserId(user);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * 实体信息从项目启动获取
     * @param pmPostAppoint 被赋值主体
     * @param projectStart 项目库项目信息
     */
    private void getValueByPrjStart(PmPostAppoint pmPostAppoint, ProjectStart projectStart) {
        pmPostAppoint.setProjectId(projectStart.getProjectId()); // 项目id
        pmPostAppoint.setInvestSourceId(projectStart.getInvestSourceId()); // 资金来源
        pmPostAppoint.setProjectTotalInvest(projectStart.getProjectTotalInvest()); // 资金总额
        pmPostAppoint.setProjectTypeId(projectStart.getProjectTypeId()); // 项目类型
        pmPostAppoint.setStartDate(projectStart.getStartDate()); // 启动日期
        pmPostAppoint.setCustomerUnitId(projectStart.getCustomerUnitId()); // 建设单位
        pmPostAppoint.setTenderModeId(projectStart.getTenderModeId()); // 招标模式
        pmPostAppoint.setProjectSituation(projectStart.getProjectSituation()); // 项目简介
        pmPostAppoint.setStartRemark(projectStart.getStartRemark()); // 启动说明
        pmPostAppoint.setStartFile(projectStart.getStartFile()); // 启动依据
    }

    /**
     * 实体信息从项目库获取
     * @param pmPostAppoint 被赋值主体
     * @param pmPrj 项目库项目信息
     */
    private void getValueByPmPrj(PmPostAppoint pmPostAppoint, PmPrj pmPrj) {
        pmPostAppoint.setProjectId(pmPrj.getProjectId()); // 项目id
        pmPostAppoint.setInvestSourceId(pmPrj.getInvestSourceId()); // 资金来源
        pmPostAppoint.setProjectTotalInvest(pmPrj.getProjectTotalInvest()); // 资金总额
        pmPostAppoint.setProjectTypeId(pmPrj.getProjectTypeId()); // 项目类型
        pmPostAppoint.setStartDate(pmPrj.getStartDate()); // 启动日期
        pmPostAppoint.setCustomerUnitId(pmPrj.getCustomerUnitId()); // 建设单位
        pmPostAppoint.setTenderModeId(pmPrj.getTenderModeId()); // 招标模式
        pmPostAppoint.setProjectSituation(pmPrj.getProjectSituation()); // 项目简介
        pmPostAppoint.setStartRemark(pmPrj.getStartRemark()); // 启动说明
        pmPostAppoint.setStartFile(pmPrj.getStartFile()); // 启动依据
    }

    /**
     * 根据项目id查询状态不是作废及作废中的数据
     * @param projectId 项目id
     * @return 查询结果
     */
    public List<PmPostAppoint> queryListByProjectNotVD(String projectId) {
        return pmPostAppointMapper.queryListByProjectNotVD(projectId);
    }
}
