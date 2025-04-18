package com.grupo06.sistemapedidos.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase que representa un evento de pedido en el sistema en la base de datos, osea la entidad.
 * Un evento de pedido tiene un topic, una descripción y una fecha.
 * Esta clase es parte del modelo de datos y se utiliza para mapear la tabla "PEDIDO_EVENTO" en la base de datos.
 * Representa un evento relacionado con un pedido en Kafka. {@link KafkaProducerService} y {@link KafkaConsumerService}
 * 
 * @Hidden es una anotación de Swagger que oculta la clase de la documentación
 * @Entity indica que esta clase es una entidad JPA
 * @Table especifica el nombre de la tabla en la base de datos
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Hidden
@Entity
@Table(name = "PEDIDO_EVENTO")
@Data
public class PedidoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedidoEvento", nullable = false, unique = true)
    public Integer idEvento;

    @NotNull(message = "El topic no puede ser nulo")
    @NotEmpty(message = "El topic no puede estar vacío")
    @Column(name = "topic", nullable = false)
    public String topic;

    @NotNull(message = "La descripcion del producto no puede ser nulo")
    @NotEmpty(message = "La descripcion del producto no puede estar vacío")
    @Column(name = "descripcion", nullable = false)
    public String descripcion;

    @NotNull(message = "La fecha no puede ser nulo")
    @Column(name = "fecha", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public LocalDate date;

    public PedidoEvento(String topic, String descripcion, LocalDate date) {
        this.topic = topic;
        this.descripcion = descripcion;
        this.date = date;
    }
}
