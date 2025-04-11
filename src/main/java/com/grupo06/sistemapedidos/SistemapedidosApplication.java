package com.grupo06.sistemapedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.grupo06.sistemapedidos.config.DotenvConfig;

/**
 * Clase principal de la aplicación Spring Boot.
 * Esta clase contiene el método main que inicia la aplicación.
 * 
 * @SpringBootApplication es una anotación que indica que esta clase es la
 * configuración principal de la aplicación Spring Boot.
 * Realiza un escaneo de componentes y configura automáticamente la aplicación.
 */
@SpringBootApplication
public class SistemapedidosApplication {
	
	public static void main(String[] args) {
		// Carga las variables de entorno desde .env y las establece como propiedades del sistema
        new DotenvConfig();
		SpringApplication.run(SistemapedidosApplication.class, args);
	}

}
