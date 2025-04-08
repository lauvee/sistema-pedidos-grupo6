package com.grupo06.sistemapedidos.config;

import com.grupo06.sistemapedidos.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura las reglas de seguridad de la aplicación.
     * Permite el acceso a la ruta /auth/token para la generación del token.
     * El filtro de autenticación JWT se aplica para todas las demás rutas.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")  // Establece un matcher para todas las rutas
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/token").permitAll()  // Permite el acceso sin autenticación a la ruta de generación del token
                        .anyRequest().authenticated()  // Requiere autenticación para cualquier otra ruta
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Se añade el filtro para validar el token JWT
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
