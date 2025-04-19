package com.pms.bid.job.service.processInstance;

import com.pms.bid.job.domain.designChaneges.CcChangeDesignDemonstrate;
import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.domain.processInstance.WfNode;
import com.pms.bid.job.domain.processInstance.WfProcess;
import com.pms.bid.job.domain.processInstance.WfProcessInstance;
import com.pms.bid.job.mapper.designChanges.CcChangeDesignDemonstrateMapper;

import java.util.Map;

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


    /**
     * 发起压力容器计划流程
     * @param tmp 施工方案计划数据
     */
    Map<String,String> createWfProcessToSendNode(SpecialEquipPreVe tmp, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId);

    /**
     * 创建一般压力容器待办任务
     * @param  processInstanceId
     */
    String  createUserTask(String  processInstanceId, String taskUser, Integer isCurrent, String now, String createBy);


    /**
     * 创建设计变更流程
     * @param tmp 设计变更实例
     * @param firstNodeId 根节点id
     * @param wfProcessId 流程id
     * @param entId 实体记录id
     * @param entCode 实体记录code
     * @param adUserId  发送用户id
     * @return
     */
    Map<String,String> createWfProcessToSendNode(CcChangeDesignDemonstrate tmp, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId);

}
