package com.example.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** yolunu /var/www/uploads/ dizinine yönlendir
        registry.addResourceHandler("/api/upload/**")
                .addResourceLocations("file:/var/www/upload/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }
}
