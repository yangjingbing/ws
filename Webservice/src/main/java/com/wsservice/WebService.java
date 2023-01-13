package com.wsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsservice.mapper")
public class WebService {

    public static void main(String[] args) {

        SpringApplication.run(WebService.class,args);
    }
}
