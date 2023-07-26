package com.pms.cisdipmswordtopdf.model;

public class BaseProcessMessageBak extends BaseEntity{

    // id
    private String id;

    // 流程id
    private String processId;

    // 流程实例id
    private String wfProcessInstanceId;

    // 属性字段id
    private String adAttId;

    // 属性值
    private String attValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getWfProcessInstanceId() {
        return wfProcessInstanceId;
    }

    public void setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
    }

    public String getAdAttId() {
        return adAttId;
    }

    public void setAdAttId(String adAttId) {
        this.adAttId = adAttId;
    }

    public String getAttValue() {
        return attValue;
    }

    public void setAttValue(String attValue) {
        this.attValue = attValue;
    }
}
