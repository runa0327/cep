package com.pms.cisdipmswordtopdf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.pms.cisdipmswordtopdf.mapper"})
public class CisdiPmsWordToPdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(CisdiPmsWordToPdfApplication.class, args);
    }

}
