package com.grupo06.sistemapedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class SistemapedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemapedidosApplication.class, args);
	}

	@Bean
	CommandLineRunner checkConnection(DataSource dataSource) {
		return args -> {
			try {
				dataSource.getConnection();
				System.out.println("✅ Conexión exitosa a la base de datos PostgreSQL");
			} catch (Exception e) {
				System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
			}
		};
	}

}
