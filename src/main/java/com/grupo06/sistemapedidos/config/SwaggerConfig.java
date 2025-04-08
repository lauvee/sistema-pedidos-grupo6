package com.grupo06.sistemapedidos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para Swagger/OpenAPI en la aplicación.
 * Define la documentación de la API y agrupa los endpoints según su funcionalidad.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura la documentación general de la API utilizando OpenAPI.
     *
     * @return una instancia de {@link OpenAPI} con la configuración de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Pedidos")
                        .version("1.0")
                        .description("Documentación de la API para el sistema de pedidos. Proporciona endpoints para gestionar usuarios, autenticación y más."));
    }

    /**
     * Configura un grupo de API para los endpoints relacionados con usuarios.
     *
     * @return una instancia de {@link GroupedOpenApi} con la configuración del grupo "usuarios".
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("usuarios")
                .pathsToMatch("/api/users/**") // Especifica los endpoints relacionados con usuarios
                .build();
    }
}
