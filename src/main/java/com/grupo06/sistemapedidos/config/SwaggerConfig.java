package com.grupo06.sistemapedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

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
                        .description("Documentacion de API para sistema de pedidos "));
    }

    /**
     * Configura un grupo de API para los endpoints relacionados con los pedidos.
     *
     * @return una instancia de {@link GroupedOpenApi} con la configuración del grupo "task".
     */
   /* @Bean
    public GroupedOpenApi taskApi() {
        return GroupedOpenApi.builder()
                .group("task")
                .pathsToMatch("/api/tasks/**")
                .build();
    }

    */
}
