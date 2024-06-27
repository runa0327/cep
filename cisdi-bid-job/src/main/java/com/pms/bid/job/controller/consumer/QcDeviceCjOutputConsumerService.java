package com.pms.bid.job.controller.consumer;

import com.pms.bid.job.domain.rocketmq.MQDeviceCjResult;
import com.pms.bid.job.domain.rocketmq.MQEngineeringMetricsResult;
import com.pms.bid.job.service.zhanJiang.CcDevicePurchaseDataService;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 设备采购mq
 */
@Service
@RocketMQMessageListener(topic = "qc-device-cj-output", consumerGroup = "consumer-qc-device-cj-output")
public class QcDeviceCjOutputConsumerService implements RocketMQListener<String> {

    @Resource
    private CcDevicePurchaseDataService ccDevicePurchaseDataService;

    /**
     * 工程量对比消息接收
     * @param message rocketmq消息
     */
    @Override
    public void onMessage(String message) {
//        System.out.printf("------- qc-device-cj-output StringConsumer received: %s \n", message);
        if (StringUtils.hasText(message)) {
            String now = DateUtil.getNormalTimeStr(new Date());
            MQDeviceCjResult mqDeviceCjResult = JsonUtil.fromJson(message, MQDeviceCjResult.class);
            if ("success".equals(mqDeviceCjResult.getCode())){
                if (mqDeviceCjResult.getData() != null) {
                    ccDevicePurchaseDataService.dealRocketMQData(mqDeviceCjResult.getData(),message,now);
                }
            }
        }
    }
}
