package com.pms.bid.job.controller.consumer;

import com.pms.bid.job.domain.rocketmq.MQModelStatusResult;
import com.pms.bid.job.service.zhanJiang.CcDrawingManagementService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 图纸管理mq
 */
@Service
@RocketMQMessageListener(topic = "qc-model-status-update-output", consumerGroup = "consumer-qc-model-status-update-output")
public class QcModelStatusUpdateOutputConsumerService implements RocketMQListener<String> {

    @Resource
    private CcDrawingManagementService ccDrawingManagementService;

    /**
     * 对接mq获取图纸管理信息
     * @param message rocketmq消息
     */
    @Override
    public void onMessage(String message) {
        System.out.printf("------- qc-model-status-update-output StringConsumer received: %s \n", message);
        if (StringUtils.hasText(message)) {
            String now = DateUtil.getNormalTimeStr(new Date());
            MQModelStatusResult mqModelStatusResult = JsonUtil.fromJson(message, MQModelStatusResult.class);
            if ("success".equals(mqModelStatusResult.getCode())){
                if (mqModelStatusResult.getData() != null) {
                    ccDrawingManagementService.dealRocketMQData(mqModelStatusResult.getData(),message,now);
                }
            }
        }
    }
}
