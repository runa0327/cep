package com.pms.bid.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.pms.bid.job.mapper"})
public class CisdiBidJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(CisdiBidJobApplication.class, args);
    }

}
