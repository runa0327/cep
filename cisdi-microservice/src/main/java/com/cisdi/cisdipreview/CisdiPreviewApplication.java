package com.cisdi.cisdipreview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;



//@MapperScan(basePackages = {"com.cisdi.cisdischeduler.mapper"})
@EnableScheduling
@ComponentScan(basePackages = {"com.cisdi.cisdipreview", "com.qygly.ext.rest.helper"})
@EnableFeignClients(basePackages = {"com.qygly.ext.rest.helper"})
@SpringBootApplication
public class CisdiPreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CisdiPreviewApplication.class, args);
    }

}
