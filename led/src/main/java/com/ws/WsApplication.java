package com.ws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.ws.mapper")
@EnableScheduling
@ServletComponentScan
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }

}
