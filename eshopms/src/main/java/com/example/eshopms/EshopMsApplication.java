package com.example.eshopms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.eshopms.dao")
public class EshopMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopMsApplication.class, args);
    }

}
