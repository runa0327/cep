package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.commons.ProcessCommons;
import com.cisdi.pms.job.domain.base.AdUser;
import com.cisdi.pms.job.domain.process.WfProcess;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstance;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private AdUserMapper adUserMapper;

    /**
     * 创建流程实例，发起流程至下一步
     *
     * @param wfProcessInstanceId 流程实例id
     * @param entityRecordId      业务记录id
     * @param pmPrj               项目信息
     * @param wfProcess           流程相关信息
     * @param userId              发起人
     * @param now                 当前时间
     * @param urgent              是否紧急 1紧急 0不紧急
     */
    @Override
    public void createAllInstance(String wfProcessInstanceId, String entityRecordId, PmPrj pmPrj, WfProcess wfProcess, String userId, String now, int urgent) {
        String wfProcessId = wfProcess.getId();
        AdUser adUser = adUserMapper.queryById(userId);
        String wfProcessInstanceName = ProcessCommons.getProcessInstanceName(pmPrj.getProjectName(),wfProcess.getName(),entityRecordId,adUser.getAdUserName(),now,urgent);

        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        setCommonValue(wfProcessInstance,userId,now,wfProcessId);
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setWfProcessInstanceName(wfProcessInstanceName);
        wfProcessInstance.setStartUserId(userId);
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntId(wfProcess.getAdEntCode());
        wfProcessInstance.setEntityRecordId(entityRecordId);
        wfProcessInstance.setUrgent(urgent);

    }

    /**
     * 设置共用属性
     * @param wfProcessInstance 实体
     * @param userId 人员信息
     * @param now 时间信息
     * @param wfProcessId 流程id
     */
    public void setCommonValue(WfProcessInstance wfProcessInstance, String userId, String now, String wfProcessId) {
        wfProcessInstance.setStatus("AP");
        wfProcessInstance.setCreateBy(userId);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateBy(userId);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setTs(now);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setWfProcessId(wfProcessId);
    }
}
