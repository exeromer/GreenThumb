package com.projectfinal.greenthumb_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si vas a usar APIs REST con tokens o para simplificar desarrollo inicial
                .authorizeHttpRequests(authz -> authz
                        // Aquí defines tus reglas de autorización.
                        // Por ahora, permitimos todo para facilitar la inicialización y pruebas.
                        // DEBES AJUSTAR ESTO PARA PRODUCCIÓN.
                        .requestMatchers("/**").permitAll() // Permite todas las solicitudes sin autenticación
                        // Ejemplo más restrictivo:
                        // .requestMatchers("/api/public/**").permitAll()
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN") // Asumiendo que tienes roles
                        // .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated() // Todas las demás requieren autenticación
                )
                .httpBasic(withDefaults()) // Habilita autenticación básica HTTP (puedes cambiarla por JWT, etc. más adelante)
                .formLogin(withDefaults()); // Habilita formulario de login por defecto de Spring Security

        return http.build();
    }
}
