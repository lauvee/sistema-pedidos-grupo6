package com.grupo06.sistemapedidos.mapper;

import org.springframework.stereotype.Component;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.model.Pedido;

@Component
public class PedidoMapper {
    public PedidoDTO toDTO(Pedido usuario){
        return new PedidoDTO(
            usuario.getUsuario(), // Usuario
            usuario.getProducto() // Producto
        );
    }
    
    public Pedido toEntity(PedidoDTO usuarioDTO){
        return new Pedido(
            null, // Id
            usuarioDTO.getUsuario(), // Usuario
            usuarioDTO.getProducto() // Producto
        );
    }
}
