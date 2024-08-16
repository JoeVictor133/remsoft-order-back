package com.remsoft.orders.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true);

        registry.addMapping("/register").allowedOrigins("http://localhost:4200").allowedMethods("POST").allowedHeaders("*").allowCredentials(true);

        registry.addMapping("/authenticate").allowedOrigins("http://localhost:4200").allowedMethods("POST").allowedHeaders("*").allowCredentials(true);

        registry.addMapping("/v3/api-docs/**").allowedOrigins("http://localhost:4200").allowedMethods("GET").allowedHeaders("*").allowCredentials(true);
        registry.addMapping("/swagger-ui/**").allowedOrigins("http://localhost:4200").allowedMethods("GET").allowedHeaders("*").allowCredentials(true);
    }
}


