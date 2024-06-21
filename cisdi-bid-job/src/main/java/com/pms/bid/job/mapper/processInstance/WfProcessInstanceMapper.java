package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfProcessInstance;

public interface WfProcessInstanceMapper {

    /**
     * 根据流程实例id获取当前节点所处的流程节点实例id
     * @param wfProcessInstanceId 流程实例id
     * @return 流程节点实例id
     */
    WfProcessInstance queryWfNodeInstanceIdById(String wfProcessInstanceId);

    /**
     * 新增流程实例
     * @param wfProcessInstance 流程实例实体
     */
    void create(WfProcessInstance wfProcessInstance);
}
