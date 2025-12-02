package com.hw.hwjobbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HwjobBackendApplication {

    static void main(String[] args) {
        SpringApplication.run(HwjobBackendApplication.class, args);
    }

}
