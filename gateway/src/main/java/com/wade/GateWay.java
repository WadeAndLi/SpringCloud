package com.wade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class GateWay {
    public static void main(String[] args) {
        SpringApplication.run(GateWay.class, args);
    }
}
