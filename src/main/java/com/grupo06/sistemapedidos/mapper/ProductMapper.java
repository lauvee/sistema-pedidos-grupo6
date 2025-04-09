package com.grupo06.sistemapedidos.mapper;


import org.springframework.stereotype.Component;

import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.model.Producto;

@Component
public class ProductMapper {

    // Convierte de Producto (entidad) a ProductDTO
    public ProductDTO toDTO(Producto producto) {
        ProductDTO dto = new ProductDTO();
        dto.setId(producto.getId());
        dto.setName(producto.getName());
        dto.setStock(producto.getStock());
        dto.setPrice(producto.getPrice());
        dto.setDescription(producto.getDescription());
        return dto;
    }

    // Convierte de ProductDTO a Producto (entidad)
    public Producto toEntity(ProductDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setName(dto.getName());
        producto.setStock(dto.getStock());
        producto.setPrice(dto.getPrice());
        producto.setDescription(dto.getDescription());
        return producto;
    }
}