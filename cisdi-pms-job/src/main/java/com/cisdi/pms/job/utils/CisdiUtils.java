package com.cisdi.pms.job.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cisdi")
public class CisdiUtils {

    /*文件路径*/
    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDownLoadPath() {
        return getProfile() + "/download/";
    }
}
