package com.grupo06.sistemapedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.grupo06.sistemapedidos.config.DotenvConfig;

@SpringBootApplication
public class SistemapedidosApplication {

	public static void main(String[] args) {
		// Carga las variables de entorno desde .env y las establece como propiedades del sistema
        new DotenvConfig();
		SpringApplication.run(SistemapedidosApplication.class, args);
	}

}
