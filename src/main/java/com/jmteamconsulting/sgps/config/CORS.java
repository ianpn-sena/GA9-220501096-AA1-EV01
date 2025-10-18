package com.jmteamconsulting.sgps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Define la configuración de CORS del servidor.
 * Acepta conexiones HTTPS del frontend local.
 */
@Configuration
public class CORS {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("https://localhost:3000")
                    .allowedMethods("GET", "POST")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}