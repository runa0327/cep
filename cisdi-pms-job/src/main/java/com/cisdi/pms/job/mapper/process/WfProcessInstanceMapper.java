package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;

public interface WfProcessInstanceMapper {

    /**
     * 新增流程实例
     * @param wfProcessInstance 实体信息
     */
    void insert(WfProcessInstance wfProcessInstance);

    /**
     * 新增节点实例
     * @param wfProcessInstance 实体信息
     */
    void insertNodeInstance(WfProcessInstance wfProcessInstance);

    /**
     * 新增用户流程代办任务
     * @param wfProcessInstance 实体信息
     */
    void insertUserTask(WfProcessInstance wfProcessInstance);

    /**
     * 根据id动态修改数据
     * @param wfProcessInstance 实体信息
     */
    void updateProcessInstanceById(WfProcessInstance wfProcessInstance);

    /**
     * 根据id动态修改节点实例数据
     * @param wfProcessInstance 实体数据
     */
    void updateNodeInstanceById(WfProcessInstance wfProcessInstance);
}
