package com.pms.bid.job.service.processInstance;

import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.domain.processInstance.WfProcessInstance;

public interface WfProcessInstanceService {

    /**
     * 流程实例当前节点实例新增流程通知
     * @param wfProcessInstanceId 流程实例id
     * @param adUserId 被通知人id
     */
    void sendWfProcessNotice(String wfProcessInstanceId, String adUserId);

    /**
     * 发起施工方案计划流程
     * @param tmp 施工方案计划数据
     */
    String createWfProcessToSendNode(ConstructionPlan tmp);
}
