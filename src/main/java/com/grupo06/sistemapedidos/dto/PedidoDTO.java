package com.grupo06.sistemapedidos.dto;

import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Usuario usuario;
    private Producto producto;
}