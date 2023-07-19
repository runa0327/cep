package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;

import java.util.List;

public interface WfProcessInstanceService {

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    List<WfProcessInstance> getAllUrgeList();
}
