package com.grupo06.sistemapedidos.dto;

import java.util.List;

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
public class PedidoDTO {
    private Integer usuario;
    private List<Integer> productos;
}