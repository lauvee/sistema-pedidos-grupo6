package com.grupo06.sistemapedidos.model;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un pedido en el sistema en la base de datos, osea la entidad.
 * Un pedido está asociado a un usuario y puede contener múltiples productos.
 * Esta clase es parte del modelo de datos y se utiliza para mapear la tabla "PEDIDO" en la base de datos.
 * Contiene anotaciones de JPA para la persistencia y validaciones de datos.
 * 
 * @Hidden es una anotación de Swagger que oculta la clase de la documentación
 * @Entity indica que esta clase es una entidad JPA
 * @Table especifica el nombre de la tabla en la base de datos
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Hidden
@Entity
@Table(name = "PEDIDO", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "El usuario no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "usuarioFK", referencedColumnName = "idUser", nullable = false)
    private Usuario usuario;
    
    @NotNull(message = "El producto no puede ser nulo")
    @ManyToMany
    @JoinTable(
        name = "pedido_producto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos = new ArrayList<>();
}