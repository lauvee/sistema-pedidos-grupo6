package com.grupo06.sistemapedidos.config;

import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase de configuración para cargar las variables de entorno desde un archivo .env.
 * Utiliza la biblioteca dotenv-java para cargar las variables de entorno y establecerlas como propiedades del sistema.
 * Esta clase asegura que las variables de entorno estén disponibles para toda la aplicación.
 * 
 * @Configuration indica que esta clase es una clase de configuración de Spring.
 * Permite la inyección de dependencias y la configuración de beans.
 * Aunque no se carga lo suficientemente pronto por lo que lo instanciamos directamente en el main
 */

@Configuration
public class DotenvConfig {

    // Cargar las variables del archivo .env y establecerlas como propiedades del sistema
    public DotenvConfig() {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
