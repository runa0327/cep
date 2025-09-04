package com.ccis.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CcisAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcisAuthApplication.class, args);
    }

}
