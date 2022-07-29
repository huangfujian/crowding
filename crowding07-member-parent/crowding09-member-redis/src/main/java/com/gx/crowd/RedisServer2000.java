package com.gx.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 10:43
 */
@SpringBootApplication
@EnableEurekaClient //开启eureka的客户端
public class RedisServer2000 {
    public static void main(String[] args) {
        SpringApplication.run(RedisServer2000.class, args);
    }
}
