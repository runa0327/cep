package com.cisdi.pms.job.domain.base;

/**
 * 消息通知提醒日志-实体
 */
public class AdRemindLog extends BaseCommon {

    // id
    private String id;

    // 实体
    private String adEntId;

    // 实体代码
    private String entCode;

    // 实体记录id
    private String entityRecordId;

    // 提醒用户
    private String remindUserId;

    // 提醒方法
    private String remindMethod;

    // 提醒目标
    private String remindTarget;

    // 提醒时间
    private String remindTime;

    // 提醒内容
    private String remindText;

    // 消息推送日志类型
    private String messageNotifyLogType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdEntId() {
        return adEntId;
    }

    public void setAdEntId(String adEntId) {
        this.adEntId = adEntId;
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

    public String getRemindUserId() {
        return remindUserId;
    }

    public void setRemindUserId(String remindUserId) {
        this.remindUserId = remindUserId;
    }

    public String getRemindMethod() {
        return remindMethod;
    }

    public void setRemindMethod(String remindMethod) {
        this.remindMethod = remindMethod;
    }

    public String getRemindTarget() {
        return remindTarget;
    }

    public void setRemindTarget(String remindTarget) {
        this.remindTarget = remindTarget;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getMessageNotifyLogType() {
        return messageNotifyLogType;
    }

    public void setMessageNotifyLogType(String messageNotifyLogType) {
        this.messageNotifyLogType = messageNotifyLogType;
    }

    public String getRemindText() {
        return remindText;
    }

    public void setRemindText(String remindText) {
        this.remindText = remindText;
    }
}
