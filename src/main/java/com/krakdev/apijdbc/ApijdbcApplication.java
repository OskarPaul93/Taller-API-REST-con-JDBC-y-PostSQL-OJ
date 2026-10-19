package com.krakdev.apijdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.krakdev")
public class ApijdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApijdbcApplication.class, args);
    }

}