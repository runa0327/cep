package com.cisdi.pms.job.utils;

public class SystemUtil {

    /**
     * 获取当前操作系统类型
     * @return 系统类型
     */
    public static String getSystemType() {
        String system = System.getProperty("os.name");
        if (system.contains("windows") || system.contains("Windows")){
            return "windows";
        } else {
            return "linux";
        }
    }
}
