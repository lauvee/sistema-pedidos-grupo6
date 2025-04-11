package com.grupo06.sistemapedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

     // Identificador único del producto
    private Integer id;

    // Nombre del producto, no puede ser nulo ni estar vacío
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    private String name;

    // Stock del producto, no puede ser nulo y debe ser mayor o igual a 0
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // Precio del producto, no puede ser nulo y debe ser mayor o igual a 0
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Integer price;

    // Descripción del producto, no puede ser nula ni estar vacía
    @NotNull(message = "La descripción no puede ser nula")
    @NotEmpty(message = "La descripción no puede estar vacía")
    private String description;
}