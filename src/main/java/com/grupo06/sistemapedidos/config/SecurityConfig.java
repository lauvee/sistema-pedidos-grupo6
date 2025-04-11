package com.grupo06.sistemapedidos.config;

import com.grupo06.sistemapedidos.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad que configura los filtros de autenticación
 * y autorización para las rutas de la aplicación.
 * Configura la autenticación basada en JWT y define los endpoints permitidos y protegidos.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web en Spring Security
public class SecurityConfig {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    /**
     * Configura la seguridad de las solicitudes HTTP.
     * Se establece un filtro personalizado para la autenticación JWT y se configuran
     * las rutas que deben estar protegidas o abiertas.
     *
     * @param http El objeto HttpSecurity que se configura para la seguridad web.
     * @return El filtro de seguridad configurado.
     * @throws Exception Si ocurre un error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita la protección CSRF (no necesario para API REST)
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas que no requieren autenticación
                        .requestMatchers(
                                "/auth/token",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/auth/register"
                        ).permitAll()  // Permite acceso sin autenticación a las rutas anteriores
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
