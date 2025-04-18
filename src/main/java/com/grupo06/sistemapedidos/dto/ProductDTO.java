package com.grupo06.sistemapedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para representar un producto.
 * Este DTO se utiliza para transferir información de producto entre la capa de servicio y la capa de presentación.
 * Contiene los campos necesarios para describir un producto, incluyendo su ID, nombre, stock, precio y descripción.
 * Se utilizan anotaciones de validación para asegurar que los datos sean correctos antes de ser procesados.
 */
@Data
@AllArgsConstructor
@Schema(description = "DTO para representar un producto")
public class ProductDTO {
    // Nombre del producto, no puede ser nulo ni estar vacío
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    @Schema(description = "Nombre del producto", example = "Arroz")
    private String name;

    // Stock del producto, no puede ser nulo y debe ser mayor o igual a 0
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad de producto disponible", example = "100")
    private Integer stock;

    // Precio del producto, no puede ser nulo y debe ser mayor o igual a 0
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Schema(description = "Precio del producto", example = "1500")
    private Integer price;

    // Descripción del producto, no puede ser nula ni estar vacía
    @NotNull(message = "La descripción no puede ser nula")
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Schema(description = "Descripción del producto", example = "Arroz de grano largo")
    private String description;
}