package com.pms.bid.job.service.zhanJiang;

import com.pms.bid.job.domain.rocketmq.DeviceMsg;

import java.util.List;

public interface CcDevicePurchaseDataService {

    /**
     * 消费设备信息
     * @param data mq数据详情
     * @param message mq原始json数据
     * @param now 当前时间
     */
    void dealRocketMQData(List<DeviceMsg> data, String message, String now);
}
