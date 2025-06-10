package com.projectfinal.greenthumb_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev") // Se usa solo si el perfil "dev" está activo.
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas las solicitudes sin autenticación
                )
                .csrf(csrf -> csrf.disable()); // Deshabilitamos CSRF como en la otra configuración

        System.out.println("************************************************************");
        System.out.println("********** PERFIL 'DEV' CARGADO: SEGURIDAD DESACTIVADA **********");
        System.out.println("************************************************************");

        return http.build();
    }
}