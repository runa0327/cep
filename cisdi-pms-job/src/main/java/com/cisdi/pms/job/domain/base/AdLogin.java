package com.cisdi.pms.job.domain.base;

/**
 * 登录日志
 */
public class AdLogin {

    // id
    private String id;

    // 登录人
    private String adUserId;

    // 登录时间
    private String loginDate;

    // 次数
    private Integer num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdUserId() {
        return adUserId;
    }

    public void setAdUserId(String adUserId) {
        this.adUserId = adUserId;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
