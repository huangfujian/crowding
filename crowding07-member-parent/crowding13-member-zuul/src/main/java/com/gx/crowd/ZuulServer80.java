package com.gx.crowd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 11:25
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulServer80 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer80.class, args);
    }
}