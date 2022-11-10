package com.cisdi.pms.mq;

import com.qygly.shared.wf.callback.CallbackInfo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 *
 */
@Component
public class MqListener {

    @Resource
    MqProcessor processor;

    @RabbitListener(queues = "${mq-queue}")
    public void receiver0(CallbackInfo callbackInfo) throws ParseException {
        processor.process(callbackInfo);
    }

}

