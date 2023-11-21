package com.cisdi.pms.job.service.process.common;

public interface WfProcessService {

    /**
     * 根据流程id获取流程id 根据作废中流程id获取已批准流程id
     * @param wfProcessId 流程id
     * @return 流程id
     */
    String getProcessIdByProcessId(String wfProcessId);
}
