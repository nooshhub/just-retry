package com.nooshhub.retry;

import com.nooshhub.retry.annotation.EnableRetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRetry
@SpringBootApplication
public class JustRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustRetryApplication.class, args);
    }

}