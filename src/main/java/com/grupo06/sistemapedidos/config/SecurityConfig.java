package com.grupo06.sistemapedidos.config;

import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.RoleRepository;
import com.grupo06.sistemapedidos.repository.UserRepository;
import com.grupo06.sistemapedidos.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.grupo06.sistemapedidos.Utils.ColorUtils;

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
                                "/api/user/auth/**",
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

     // Inicializa los roles por defecto, en este caso, el rol ADMIN
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

    @Bean
    @Order(2)
    public CommandLineRunner initDefaultUser(UserRepository userRepository,
                                               RoleRepository roleRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultEmail = "admin@pedidos.com";
            if (!userRepository.findByEmail(defaultEmail).isPresent()) {
                try {
                         // Obtiene o crea el rol ADMIN
                        Roles adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                        .orElseGet(() -> roleRepository.save(new Roles(RoleEnum.ADMIN, "Administrator de la aplicación sitemas pedidos")));
                
                        Usuario defaultUser = new Usuario();
                        defaultUser.setName("admin");
                        defaultUser.setEmail(defaultEmail);
                        // Establece la fecha de registro (signUpDate) al día actual
                        defaultUser.setSignUpDate(java.time.LocalDate.now());
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


        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
