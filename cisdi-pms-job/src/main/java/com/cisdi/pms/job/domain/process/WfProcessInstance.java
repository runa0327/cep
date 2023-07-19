package com.cisdi.pms.job.domain.process;

public class WfProcessInstance {

    // id
    public String id;

    // 用户id
    private String userId;

    // 用户编码/电话
    private String userCode;

    // 流程实例名称
    private String wfProcessInstanceName;

    // 流程实例id
    private String wfProcessInstanceId;

    // 任务id
    private String taskId;

    // 流程代办类型 NOTI-通知 TODO-代办
    private String taskType;

    // 流程代办对应业务表记录id
    private String entityRecordId;

    // 流程名称
    private String processName;

    // 流程id
    private String processId;

    // 当前实体视图id
    private String viewId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getWfProcessInstanceName() {
        return wfProcessInstanceName;
    }

    public void setWfProcessInstanceName(String wfProcessInstanceName) {
        this.wfProcessInstanceName = wfProcessInstanceName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getEntityRecordId() {
        return entityRecordId;
    }

    public void setEntityRecordId(String entityRecordId) {
        this.entityRecordId = entityRecordId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getWfProcessInstanceId() {
        return wfProcessInstanceId;
    }

    public void setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
