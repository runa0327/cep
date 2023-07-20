package com.cisdi.pms.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title SendMessageFactory
 * @package com.cisdi.pms.service
 * @description
 * @date 2023/7/17
 */
@Service
public class SendMessageFactory {
    private final ApplicationContext context;

    public SendMessageFactory(ApplicationContext context) {
        this.context = context;
    }

    public WxSendMessageService getService(SendMessageAttribute attribute) {
        Map<String, WxSendMessageService> beansOfType = context.getBeansOfType(WxSendMessageService.class);
        return beansOfType.get(attribute.getAnEnum().getInstanceName());
    }
}
