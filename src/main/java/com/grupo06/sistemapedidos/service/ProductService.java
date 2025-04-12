package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.ProductMapper;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.repository.ProductRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los productos.
 * Proporciona métodos para obtener, crear y eliminar productos.
 * 
* @Service indica que esta clase es un servicio de Spring y permite la inyección de dependencias.
 */
@Service
public class ProductService {
  
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() {
        try {
            List<Producto> listProductos = productRepository.findAll();
            return listProductos.stream()
                    .map(productMapper::toDTO).toList();
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un producto por ID
     * 
     * @param id ID del producto a obtener
     * @return Optional<Producto> Producto encontrado
     */
    public ProductDTO findById(Integer id) {
        try {
            Optional<Producto> newProducto = productRepository.findById(id);
            if(!newProducto.isPresent()){
                throw new RequestException(ApiError.RECORD_NOT_FOUND);
            }
            return productMapper.toDTO(newProducto.get());
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Guarda y actualiza el producto
     * 
     * @param producto
     * @return Producto guardado o actualizado
     */
    public ProductDTO postProducto(ProductDTO producto) {
        try {
            Producto newProducto = productMapper.toEntity(producto);
            Producto savedProduct =  productRepository.save(newProducto);
            return productMapper.toDTO(savedProduct);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un producto por ID
     * 
     * @param id ID del producto a eliminar
     */
    public void deleteById(Integer id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
}