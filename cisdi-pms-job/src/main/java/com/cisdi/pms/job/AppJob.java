package com.cisdi.pms.job;

import com.iflytek.fsp.flylog.sdk.java.core.FlylogTypeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.iflytek.fsp.flylog.sdk.java", "com.cisdi.pms.job", "com.qygly.ext.rest.helper"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FlylogTypeFilter.class})})
//@ComponentScan(basePackages = {"com.cisdi.pms.job", "com.qygly.ext.rest.helper"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.qygly.ext.rest.helper"})
@Slf4j
@EnableScheduling
@SpringBootApplication
public class AppJob {
    public AppJob() {
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{AppJob.class});
        app.run(args);
    }
}