package com.grupo06.sistemapedidos.config;

import com.grupo06.sistemapedidos.dto.ExceptionDTO;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.RoleRepository;
import com.grupo06.sistemapedidos.repository.UserRepository;
import com.grupo06.sistemapedidos.security.JwtAuthenticationFilter;
import com.grupo06.sistemapedidos.utilities.ColorUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Clase de configuración de seguridad que configura los filtros de autenticación
 * y autorización para las rutas de la aplicación.
 * Configura la autenticación basada en JWT y define los endpoints permitidos y protegidos.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web en Spring Security
public class SecurityConfig {

    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     * Se inyecta desde el archivo de propiedades de la aplicación (application.propirties).
     */
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
                                "/api/user/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/auth/register"
                        ).permitAll()  // Permite acceso sin autenticación a las rutas anteriores
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                ).exceptionHandling(exception -> exception
                .authenticationEntryPoint(customAuthenticationEntryPoint())
                .accessDeniedHandler(customAccessDeniedHandler())
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Crea un CommandLineRunner que inicializa los roles por defecto en la base de datos.
     * 
     * @param roleRepository Repositorio de roles para acceder a la base de datos.
     * @return Un CommandLineRunner que se ejecuta al iniciar la aplicación.
     */
    @Bean
    @Order(1)
    public CommandLineRunner initDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            if (!roleRepository.findByName(RoleEnum.ADMIN).isPresent()) {
                Roles adminRole = new Roles(RoleEnum.ADMIN, "Administrator de la aplicación sistemas pedidos");
                roleRepository.save(adminRole);
                System.out.println(ColorUtils.pintarVerde("Rol ADMIN creado por defecto."));
            } 
        };
    }

    /**
     * Crea un CommandLineRunner que inicializa un usuario por defecto en la base de datos.
     * 
     * @param userRepository Repositorio de usuarios para acceder a la base de datos.
     * @param roleRepository Repositorio de roles para acceder a la base de datos. 
     * @param passwordEncoder Codificador de contraseñas para encriptar la contraseña del usuario.
     * @return Un CommandLineRunner que se ejecuta al iniciar la aplicación.
     */
    @Bean
    @Order(2)
    public CommandLineRunner initDefaultUser(UserRepository userRepository,
                                               RoleRepository roleRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultEmail = "admin@pedidos.com";
            String defaultName = "admin";
            if (!userRepository.findByEmailAndName(defaultEmail, defaultName).isPresent()) {
                try {
                         // Obtiene o crea el rol ADMIN
                        Roles adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                        .orElseGet(() -> roleRepository.save(new Roles(RoleEnum.ADMIN, "Administrator de la aplicación sitemas pedidos")));
                
                        Usuario defaultUser = new Usuario();
                        defaultUser.setName(defaultName);
                        defaultUser.setEmail(defaultEmail);
                        // Establece la fecha de registro (signUpDate) al día actual
                        defaultUser.setSignUpDate(LocalDate.now());
                        // Codifica la contraseña predeterminada
                        defaultUser.setPassword(passwordEncoder.encode("admin123"));
                        defaultUser.setRole(adminRole);
                        
                        userRepository.save(defaultUser);
                        System.out.println(ColorUtils.pintarVerde("Usuario por defecto creado: " + defaultEmail));
                } catch (Exception e) {
                        System.err.println(ColorUtils.pintarRojo("Error al crear el usuario por defecto: " + e.getMessage()));
                       throw new RuntimeException();
                }
               
            }
        };
    }

    /**
     * Crea un PasswordEncoder que utiliza BCrypt para encriptar contraseñas.
     * 
     * @return Un PasswordEncoder que utiliza BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }
    
    /**
     * Retorna una implementación de AccessDeniedHandler para gestionar peticiones 
     * en las que el usuario autenticado no tiene permisos suficientes.
     * Este método se invoca cuando un usuario intenta acceder a un recurso para el 
     * cual no está autorizado, enviando como respuesta un error con estado HTTP 403 (Forbidden)
     * junto con un mensaje de error estructurado en formato JSON.
     *
     * @return una instancia de AccessDeniedHandler configurada para enviar una respuesta de acceso denegado.
     */
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
                    throws StreamWriteException, DatabindException, IOException {
                ExceptionDTO errorResponse = new ExceptionDTO(
                    "Access Denied",
                    "Your role does not have permission to access this resource",
                    HttpStatus.FORBIDDEN.value(),
                    null,
                    ZonedDateTime.now().toLocalDateTime()
                );
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                // Configurar el ObjectMapper con soporte para Java 8 Date/Time API
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                mapper.writeValue(response.getOutputStream(), errorResponse);
                response.getOutputStream().flush();
            }
        };
    }

    /**
     * Retorna una implementación de AuthenticationEntryPoint que gestiona los casos de autenticación fallida.
     * Este método se utiliza cuando una petición requiere autenticación y no se suministra un token válido,
     * enviando una respuesta con estado HTTP 401 (Unauthorized) y un mensaje de error en formato JSON.
     *
     * @return una instancia de AuthenticationEntryPoint configurada para enviar una respuesta de autenticación no autorizada.
     */
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        /**
         * {@link HttpServletRequest} y {@link HttpServletResponse} son interfaces de la
         * especificación Servlet que representan la solicitud HTTP y la respuesta HTTP, respectivamente.
         * {@link AuthenticationException} es una excepción que se lanza cuando la autenticación falla.
         * {@link ExceptionDTO} es una clase personalizada que representa un objeto de error estructurado.
         */
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
            ExceptionDTO errorResponse = new ExceptionDTO(
                "Unauthorized",
                "No authentication token provided",
                HttpStatus.UNAUTHORIZED.value(),
                null,
                ZonedDateTime.now().toLocalDateTime()
            );
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Configurar el ObjectMapper con soporte para Java 8 Date/Time API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            mapper.writeValue(response.getOutputStream(), errorResponse);
            response.getOutputStream().flush();
        };
    }
}
