package com.pms.bid.job.controller.consumer;

import com.pms.bid.job.domain.rocketmq.QCRocketMQResult;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityService;
import com.pms.bid.job.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(topic = "qc-construction-fill-output", consumerGroup = "test")
public class ConsumerService implements RocketMQListener<String> {

    @Resource
    private CcEngineeringQuantityService ccEngineeringQuantityService;

    /**
     * 工程量对比消息接收
     * @param message rocketmq消息
     */
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringConsumer received: %s \n", message);
        if (StringUtils.hasText(message)) {
            QCRocketMQResult qcRocketMQResult = JsonUtil.fromJson(message, QCRocketMQResult.class);
            if ("success".equals(qcRocketMQResult.getCode())){
                ccEngineeringQuantityService.dealRocketMQData(qcRocketMQResult.getData());
            }
        }
    }
}
