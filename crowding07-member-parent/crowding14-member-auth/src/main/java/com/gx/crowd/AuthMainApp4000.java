package com.gx.crowd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 12:03
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AuthMainApp4000 {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainApp4000.class, args);
    }
}
