package com.bid.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.qygly.ext.jar.helper.debug")
public class DebugBidExt {
    private static final Logger log = LoggerFactory.getLogger(DebugBidExt.class);

    public DebugBidExt() {
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{DebugBidExt.class});
        app.run(args);
    }
}