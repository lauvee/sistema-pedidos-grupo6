package com.grupo06.sistemapedidos.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;

@Component
public class PedidoMapper {
    public PedidoDTO toDTO(Pedido usuario){
        return new PedidoDTO(
            usuario.getUsuario().getId(), // Usuario
            usuario.getProductos().stream()
            .map(Producto::getId)
            .toList() // Productos
        );
    }
    
    public Pedido toEntity(Usuario usuarioDTO, List<Producto> productosId) {
        return new Pedido(
            null, // Id
            usuarioDTO, // Usuario
            productosId // Producto
        );
    }
}
