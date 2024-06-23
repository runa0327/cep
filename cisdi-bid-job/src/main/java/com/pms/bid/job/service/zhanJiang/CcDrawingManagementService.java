package com.pms.bid.job.service.zhanJiang;

import com.pms.bid.job.domain.rocketmq.ModelStatusUpdate;

public interface CcDrawingManagementService {

    /**
     * 消费模型解析状态
     * @param data mq数据详情
     * @param message mq原始json数据
     * @param now 当前时间
     */
    void dealRocketMQData(ModelStatusUpdate data, String message, String now);
}
