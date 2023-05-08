package com.cisdi.pms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WXCongig
 * @package com.cisdi.pms.config
 * @description
 * @date 2023/5/5
 */
@Data
@Component
@ConfigurationProperties(prefix = "gwx")
public class GovernmentWXConfig {

    private String domain;

    private String corpid;

    private String corpsecret;
}
