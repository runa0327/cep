package com.pms.bid.job.controller.consumer;

import com.pms.bid.job.domain.rocketmq.MQEngineeringMetricsResult;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 工程量填报mq
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "qc-construction-fill-output", consumerGroup = "consumer-qc-construction-fill-output")
public class QcConstructionFillOutputConsumerService implements RocketMQListener<String> {

    @Resource
    private CcEngineeringQuantityService ccEngineeringQuantityService;

    /**
     * 工程量对比消息接收
     * @param message rocketmq消息
     */
    @Override
    public void onMessage(String message) {
        System.out.printf("------- qc-construction-fill-output StringConsumer received: %s \n", message);
        if (StringUtils.hasText(message)) {
            String now = DateUtil.getNormalTimeStr(new Date());
            MQEngineeringMetricsResult mqEngineeringMetricsResult = JsonUtil.fromJson(message, MQEngineeringMetricsResult.class);
            if ("success".equals(mqEngineeringMetricsResult.getCode())){
                if (mqEngineeringMetricsResult.getData() != null) {
                    ccEngineeringQuantityService.dealRocketMQData(mqEngineeringMetricsResult.getData(),message,now);
                } else {
                    log.error("[qc-construction-fill-output]获取到的data数据为空，不进行消费，mq信息为：{}",message);
                }
            } else {
                log.error("状态不为success，不进行消费");
            }
        }
    }
}
