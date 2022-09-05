package com.cisdi.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppDataTransfer {
    private static final Logger log = LoggerFactory.getLogger(AppDataTransfer.class);

    public AppDataTransfer() {
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{AppDataTransfer.class});
        app.run(args);
    }
}