package com.gx.crowd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 10:39
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer1000 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer1000.class, args);
    }
}
