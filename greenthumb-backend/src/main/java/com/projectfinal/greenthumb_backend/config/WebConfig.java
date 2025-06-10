package com.projectfinal.greenthumb_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permite CORS en todas las rutas bajo/api
                .allowedOrigins("http://localhost:3000") // Permite solicitudes desde tu frontend React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(true); // Permite el envío de cookies y credenciales
    }
}