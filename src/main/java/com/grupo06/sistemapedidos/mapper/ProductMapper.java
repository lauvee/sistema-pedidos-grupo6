package com.grupo06.sistemapedidos.mapper;


import org.springframework.stereotype.Component;
import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.model.Producto;

/**
 * Clase de mapeo para convertir entre entidades de Producto y su representación DTO.
 * Esta clase se encarga de transformar los datos entre la capa de persistencia y la capa de presentación.
 * Utiliza el patrón Mapper para facilitar la conversión entre diferentes representaciones de datos.
 */
@Component
public class ProductMapper {

    // Convierte de Producto (entidad) a ProductDTO
    public ProductDTO toDTO(Producto producto) {
        ProductDTO dto = new ProductDTO(
                producto.getName().toLowerCase().trim().replace(" ", "-"), 
                producto.getStock(), 
                producto.getPrice(), 
                producto.getDescription()
        );
        return dto;
    }

    // Convierte de ProductDTO a Producto (entidad)
    public Producto toEntity(ProductDTO dto) {
        
        Producto producto = new Producto();
        producto.setName(dto.getName().toLowerCase().trim().replace(" ", "-"));
        producto.setStock(dto.getStock());
        producto.setPrice(dto.getPrice());
        producto.setDescription(dto.getDescription());
      
        return producto;
    }
}