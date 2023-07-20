package com.cisdi.pms.job.domain.notice;

import java.util.List;

public class MessageModel {

    /**
     * 消息接收人手机号
     */
    private List<String> toUser;

    /**
     * 部门ID--非必传
     */
    private List<String> toParty;

    /**
     * 标签ID--非必传
     */
    private List<String> toTag;

    /**
     * 消息类型：text,image,voice,video,file,textcard,news
     */
    private String type;

    /**
     * 消息体
     */
    private Object message;

    /**
     * 详情地址:list跳转列表也，
     * detail跳转详情页
     */
    private String pathSuffix;

    public List<String> getToUser() {
        return toUser;
    }

    public void setToUser(List<String> toUser) {
        this.toUser = toUser;
    }

    public List<String> getToParty() {
        return toParty;
    }

    public void setToParty(List<String> toParty) {
        this.toParty = toParty;
    }

    public List<String> getToTag() {
        return toTag;
    }

    public void setToTag(List<String> toTag) {
        this.toTag = toTag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getPathSuffix() {
        return pathSuffix;
    }

    public void setPathSuffix(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }
}
