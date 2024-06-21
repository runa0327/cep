package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfProcess;

public interface WfProcessMapper {

    /**
     * 通过流程id获取流程名称
     * @param wfProcessId 流程id
     * @return 流程名称
     */
    WfProcess queryNameById(String wfProcessId);
}
