package com.cisdi.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ThirdPartyAppllcation
 * @package PACKAGE_NAME
 * @description 未使用数据库，通过exclude = {DataSourceAutoConfiguration.class}不自动装配，避免报错Failed to configure a DataSource
 * @date 2022/12/16
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }
}
