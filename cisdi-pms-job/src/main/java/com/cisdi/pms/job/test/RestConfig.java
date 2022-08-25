package com.cisdi.pms.job.test;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title RestConfig
 * @package com.cisdi.pms.job
 * @description
 * @date 2022/8/25
 */
@Component
public class RestConfig {

    @Bean
    public String testRest(){
        return "sayYes";
    }
}
