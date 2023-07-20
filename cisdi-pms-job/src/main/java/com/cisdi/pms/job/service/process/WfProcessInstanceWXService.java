package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;

import java.util.List;

public interface WfProcessInstanceWXService {

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    List<WfProcessInstanceWX> getAllUrgeList();

    /**
     * 查询待发送政务微信的紧急消息代办
     * @return
     */
    List<WfProcessInstanceWX> getAllWaitUrgeList();
}
