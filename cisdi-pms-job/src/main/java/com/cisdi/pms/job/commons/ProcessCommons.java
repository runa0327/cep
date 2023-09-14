package com.cisdi.pms.job.commons;

import com.cisdi.pms.job.utils.StringUtil;

/**
 * 流程公用方法
 */
public class ProcessCommons {

    /**
     * 组装流程实例名称
     * @param projectName 项目名称
     * @param processName 流程名称
     * @param entityRecordId 业务记录id
     * @param userName 发起人
     * @param now 发起时间
     * @param urgent 是否紧急 1紧急 0不紧急
     * @return 流程实例名称
     */
    public static String getProcessInstanceName(String projectName, String processName, String entityRecordId, String userName, String now, int urgent) {
        if (1 == urgent){
            processName = "【紧急】" + processName;
        }
        return StringUtil.concat("-",processName,projectName,userName,now);
    }
}
