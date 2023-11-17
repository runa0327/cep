package com.cisdi.pms.job.domain.process.common;

/**
 * 流程文件同步资料库中间表
 */
public class BaseProFileTask {

    private String id;
    private String processInstanceId;
    private int isEnd;
    private String endDateTime;
    private String taskRemark;
    private String entCode;
    private String entityRecordId;
    private String wfProcessId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getEntCode() {
        return entCode;
    }

    public void setEntCode(String entCode) {
        this.entCode = entCode;
    }

    public String getEntityRecordId() {
        return entityRecordId;
    }

    public void setEntityRecordId(String entityRecordId) {
        this.entityRecordId = entityRecordId;
    }

    public String getWfProcessId() {
        return wfProcessId;
    }

    public void setWfProcessId(String wfProcessId) {
        this.wfProcessId = wfProcessId;
    }
}
