package com.example.devicesapi.config;

import com.example.devicesapi.logging.TracingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TracingInterceptor())
                .excludePathPatterns("/actuator/**")
                .addPathPatterns("/api/devices/**")
                .addPathPatterns("/graphql");
    }

}