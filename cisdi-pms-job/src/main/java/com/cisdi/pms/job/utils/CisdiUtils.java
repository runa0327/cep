package com.cisdi.pms.job.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cisdi")
public class CisdiUtils {

    /*windows文件路径*/
    private String winProfile;

    /*linux文件路径*/
    private String linuxProfile;

    public String getWinProfile() {
        return winProfile;
    }

    public void setWinProfile(String winProfile) {
        this.winProfile = winProfile;
    }

    public String getLinuxProfile() {
        return linuxProfile;
    }

    public void setLinuxProfile(String linuxProfile) {
        this.linuxProfile = linuxProfile;
    }

    public String getDownLoadPath() {
        String sys = SystemUtil.getSystemType();
        if ("windows".equals(sys)){
            return getWinProfile() + "/download/";
        } else {
            return getLinuxProfile() + "/download/";
        }
    }
}
