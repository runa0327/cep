package com.pms.bid.job.controller.consumer;

import com.pms.bid.job.domain.rocketmq.MQEngineeringMetricsResult;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
@RocketMQMessageListener(topic = "qc-construction-fill-output", consumerGroup = "test")
public class QcConstructionFillOutputConsumerService implements RocketMQListener<String> {

    @Resource
    private CcEngineeringQuantityService ccEngineeringQuantityService;

    /**
     * 工程量对比消息接收
     * @param message rocketmq消息
     */
    @Override
    public void onMessage(String message) {
//        System.out.printf("------- qc-construction-fill-output StringConsumer received: %s \n", message);
        if (StringUtils.hasText(message)) {
            String now = DateUtil.getNormalTimeStr(new Date());
            MQEngineeringMetricsResult mqEngineeringMetricsResult = JsonUtil.fromJson(message, MQEngineeringMetricsResult.class);
            if ("success".equals(mqEngineeringMetricsResult.getCode())){
                if (mqEngineeringMetricsResult.getData() != null) {
                    ccEngineeringQuantityService.dealRocketMQData(mqEngineeringMetricsResult.getData(),message,now);
                }
            }
        }
    }
}
