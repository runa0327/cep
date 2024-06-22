package com.pms.bid.job.service.zhanJiang;

import com.pms.bid.job.domain.rocketmq.EngineeringMetrics;

import java.util.List;

public interface CcEngineeringQuantityService {

    /**
     * 通过rocketMQ获取消息写入工程量填报
     * @param data 数据详情
     */
    void dealRocketMQData(List<EngineeringMetrics> data);
}
