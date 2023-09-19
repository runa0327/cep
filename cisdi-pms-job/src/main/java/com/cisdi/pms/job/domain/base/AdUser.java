package com.cisdi.pms.job.domain.base;

public class AdUser {

    // id
    private String id;
    private String adUserId;

    // 名称
    private String name;
    private String adUserName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdUserName() {
        return adUserName;
    }

    public void setAdUserName(String adUserName) {
        this.adUserName = adUserName;
    }
}
