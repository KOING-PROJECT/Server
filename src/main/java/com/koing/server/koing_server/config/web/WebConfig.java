package com.koing.server.koing_server.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://koing-project.github.io", "https://koing.store")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .maxAge(3600);
//    }

}
