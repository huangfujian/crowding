package com.gx.crowd.config;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 19:02
 */
@Component
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/launch/info/page").setViewName("project-launch");
        registry.addViewController("/create/confirm/page").setViewName("project-confirm");
        registry.addViewController("/launch/success").setViewName("project-success");
    }
}
