package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;

import java.util.List;

public interface WfProcessInstanceWXService {

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    List<WfProcessInstanceWX> getAllUrgeList();
}
