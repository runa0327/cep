package com.pms.bid.job.service.processInstance;

import com.pms.bid.job.domain.processInstance.WfProcessInstance;
import com.pms.bid.job.domain.processInstance.WfTask;

public interface WfTaskService {

    /**
     * 根据流程实例节点创建任务通知
     * @param wfProcessInstance 流程实例信息
     * @param adUserId 被通知人
     */
    void createNewTaskNotice(WfProcessInstance wfProcessInstance, String adUserId);

    /**
     * 创建流程实例任务
     * @param wfTask 任务实体
     */
    void create(WfTask wfTask);


    /**
     * 关闭流程实例任务
     * @param taskId 任务id
     */
    void closeTask(String  taskId);

    /**
     * 获取流程实例任务关闭状态
     * @param taskId 任务id
     */
    int getTaskStatus(String  taskId);
}
