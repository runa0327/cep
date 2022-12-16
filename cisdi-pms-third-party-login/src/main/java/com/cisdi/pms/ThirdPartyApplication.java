package com.cisdi.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ThirdPartyAppllcation
 * @package PACKAGE_NAME
 * @description
 * @date 2022/12/16
 */
@EnableFeignClients(basePackages = {"com.qygly.ext.rest.helper"})
@SpringBootApplication
public class ThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }
}
