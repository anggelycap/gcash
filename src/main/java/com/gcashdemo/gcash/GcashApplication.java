package com.gcashdemo.gcash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class GcashApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcashApplication.class, args);
    }

}