package com.grupo06.sistemapedidos.model;


import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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