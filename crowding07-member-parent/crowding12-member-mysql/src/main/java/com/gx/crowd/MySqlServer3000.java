package com.gx.crowd;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 11:17
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.gx.crowd.mapper")
public class MySqlServer3000 {
    public static void main(String[] args) {
        SpringApplication.run(MySqlServer3000.class, args);
    }
}
