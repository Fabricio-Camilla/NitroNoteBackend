package com.nitro_note.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // deshabilitamos CSRF para permitir POST/PUT desde Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,  "/mantenimiento/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/mantenimiento/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,  "/mantenimiento/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/mantenimiento/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/mantenimiento/**").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // opcional: habilita Basic Auth si después la querés
        return http.build();
    }
}
