package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;

import java.util.List;

public interface WfProcessInstanceWXMapper {

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    List<WfProcessInstanceWX> getAllUrgeList();
}
