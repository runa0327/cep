package com.cisdi.pms.job.domain.notice;

/**
 * 白名单
 */
public class SmsWhiteList {

    // id
    private String id;

    // 用户id
    private String userId;

    // 用户电话
    private String userCode;

    // 消息类型
    private String messageNotifyTypeId;

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

    public String getMessageNotifyTypeId() {
        return messageNotifyTypeId;
    }

    public void setMessageNotifyTypeId(String messageNotifyTypeId) {
        this.messageNotifyTypeId = messageNotifyTypeId;
    }
}
