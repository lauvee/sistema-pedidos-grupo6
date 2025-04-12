package com.grupo06.sistemapedidos.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar un pedido.
 * Este DTO se utiliza para transferir información de pedido entre la capa de servicio y la capa de presentación.
 * Contiene el ID del usuario que realiza el pedido y una lista de IDs de productos incluidos en el pedido.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar un pedido")
public class PedidoDTO {
    @Schema(description = "ID del usuario que realiza el pedido", example = "1")
    private Integer usuario;
    @Schema(description = "Lista de IDs de productos incluidos en el pedido", example = "[1, 2, 3]")
    private List<Integer> productos;
}