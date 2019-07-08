package com.yte.springreact.alertingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@SpringBootApplication
public class AlertingSystemApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlertingSystemApplication.class, args);
    }

}
