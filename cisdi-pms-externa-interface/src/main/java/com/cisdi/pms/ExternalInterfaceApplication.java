package com.cisdi.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title com.cisdi.pms.ExternalInterfaceApplication
 * @package PACKAGE_NAME
 * @description
 * @date 2023/1/13
 */
//@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.cisdi.pms.mapper"})
public class ExternalInterfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExternalInterfaceApplication.class, args);
    }
}
