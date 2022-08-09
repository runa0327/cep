package com.cisdi.pms.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMq {
    private static final Logger log = LoggerFactory.getLogger(AppMq.class);

    public AppMq() {
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{AppMq.class});
        app.run(args);
    }
}