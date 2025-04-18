package com.grupo06.sistemapedidos.model;


import java.util.List;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase que representa un producto en el sistema en la base de datos, osea la entidad.
 * Un producto tiene un nombre, stock, precio y descripción.
 * Esta clase es parte del modelo de datos y se utiliza para mapear la tabla "PRODUCTO" en la base de datos.
 * Contiene anotaciones de JPA para la persistencia y validaciones de datos.
 * 
 * @Hidden es una anotación de Swagger que oculta la clase de la documentación
 * @Entity indica que esta clase es una entidad JPA
 * @Table especifica el nombre de la tabla en la base de datos
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Hidden
@Entity
@Table(name = "PRODUCTO", schema = "public")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "El nombre del producto no puede ser nulo")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull(message = "La descripción no puede ser nula")
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "productos")
    private List<Pedido> pedidos;
}