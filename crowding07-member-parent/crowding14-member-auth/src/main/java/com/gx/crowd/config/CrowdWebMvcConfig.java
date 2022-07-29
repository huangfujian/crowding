package com.gx.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 14:36
 */
@Component
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login/page").setViewName("member-login");
        registry.addViewController("/auth/reg/page").setViewName("member-reg");
        registry.addViewController("/auth/center/page").setViewName("member-center");
        registry.addViewController("/auth/mycrowd/page").setViewName("member-mycrowd");
        registry.addViewController("/auth/launch/page").setViewName("member-launch");
    }
}
