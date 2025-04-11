package com.grupo06.sistemapedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import com.grupo06.sistemapedidos.filter.JwtRequestFilter; TODO:

/**
 * Configuración de seguridad para la aplicación.
 * Esta clase define las políticas de seguridad, incluyendo las reglas de autorización,
 * configuración de filtros y encriptación de contraseñas.
 * 
 * La anotación @Configuration indica que esta clase es una fuente de definiciones de beans.
 * La anotación @EnableWebSecurity habilita la seguridad web de Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Define las reglas de acceso a distintos endpoints:
     * - Rutas públicas: login, registro, documentación Swagger
     * - Rutas para administradores: gestión de usuarios y operaciones de eliminación
     * - Resto de rutas: requieren autenticación
     * 
     * También se configura la desactivación de CSRF y la adición del filtro JWT.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad HTTP
     * @param jwtRequestFilter Filtro personalizado para procesar tokens JWT
     * @return La cadena de filtros de seguridad configurada
     * @throws Exception Si ocurre algún error durante la configuración
     */
    @Bean
    // TODO: Pasar por parametor JwtRequestFilter jwtRequestFilter
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
            ) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            "/users/login",
                                "/users/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**", "/js/**", "/static/**").permitAll()
                        .requestMatchers("/users/list", "/users/admin/register", "/users/resources/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
                // TODO .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Provee un codificador de contraseñas para la aplicación.
     * Se utiliza BCrypt, un algoritmo de hashing seguro para almacenar contraseñas.
     * 
     * @return Un objeto PasswordEncoder que utiliza el algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Crea y configura el filtro de peticiones JWT.
     * Este filtro intercepta las peticiones HTTP y verifica la presencia
     * y validez de tokens JWT en las cabeceras de autorización.
     * 
     * @param jwtUtil Utilidad para operaciones con JWT (generación, validación)
     * @param userDetailsService Servicio para cargar detalles de usuarios
     * @return Un filtro JwtRequestFilter configurado
     */
    // TODO: Implementar el filtro JwtRequestFilter
    // @Bean
    // public JwtRequestFilter jwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    //     return new JwtRequestFilter(jwtUtil, userDetailsService);
    // } 
}