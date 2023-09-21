package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.WfProcess;

import java.util.List;

public interface WfProcessMapper {

    /**
     * 查询非核心流程id
     * @return 查询结果id
     */
    List<String> getNotCoreProId();

    /**
     * 根据流程名称精准查询流程信息
     * @param processName 流程名称
     * @return 查询结果
     */
    WfProcess queryByName(String processName);
}
