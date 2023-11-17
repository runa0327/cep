package com.cisdi.pms.job.mapper.process.common;

import com.cisdi.pms.job.domain.process.common.WfProcess;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据流程id获取流程id 根据作废中流程id获取已批准流程id
     * @param wfProcessId 流程id
     * @return 流程id
     */
    String getProcessIdByProcessId(@Param("wfProcessId") String wfProcessId);
}
