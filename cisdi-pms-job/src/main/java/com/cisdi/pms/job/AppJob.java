package com.cisdi.pms.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AppJob {
    private static final Logger log = LoggerFactory.getLogger(AppJob.class);

    public AppJob() {
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class[]{AppJob.class});
        app.run(args);
    }
}