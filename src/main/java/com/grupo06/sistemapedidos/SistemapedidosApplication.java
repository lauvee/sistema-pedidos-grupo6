package com.grupo06.sistemapedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import com.grupo06.sistemapedidos.Utils.ColorUtils;

import java.net.Socket;

import javax.sql.DataSource;

@SpringBootApplication
public class SistemapedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemapedidosApplication.class, args);
	}

	// Verificamos la conexión con la base de datos PostgreSQL
	@Bean
	CommandLineRunner checkConnection(DataSource dataSource) {
		return args -> {
			try {
				dataSource.getConnection();
				System.out.println(ColorUtils.pintarVerde("Conexión exitosa a la base de datos PostgreSQL"));
			} catch (Exception e) {
				System.err.println(ColorUtils.pintarVerde("Error al conectar a la base de datos: " + e.getMessage()));
			}
		};
	}

	// VErificamos la conexión con Kafka
	@Bean
	CommandLineRunner checkKafka(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> {
			try {
				kafkaTemplate.send("test-topic", "Mensaje de prueba desde la aplicación").get();
				System.out.println(ColorUtils.pintarVerde("Kafka se inició correctamente y se pudo enviar mensaje a 'test-topic'"));
			} catch (Exception e) {
				System.err.println(ColorUtils.pintarRojo("Error al conectar con Kafka: " + e.getMessage()));
			}
		};
	}

	// Verificamos la conexión con Kafka UI
	@Bean
    CommandLineRunner checkKafkaUI() {
        return args -> {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8081"; // URL de Kafka UI
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println(ColorUtils.pintarVerde("Kafka UI está funcionando correctamente"));
                } else {
                    System.err.println(ColorUtils.pintarRojo("Kafka UI no respondió correctamente. Status: " + response.getStatusCode()));
                }
            } catch(Exception e) {
                System.err.println("Error al conectar con Kafka UI: " + e.getMessage());
            }
        };
    }

	// Verificamos la conexión con Zookeeper
	@Bean
	CommandLineRunner checkZookeeper() {
		return args -> {
			try (Socket socket = new Socket("localhost", 2181)) {
				System.out.println(ColorUtils.pintarVerde("Zookeeper está funcionando en localhost:2181"));
			} catch (Exception e) {
				System.err.println(ColorUtils.pintarRojo("No se pudo conectar a Zookeeper: " + e.getMessage()));
			}
		};
	}

}