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
  
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Obtiene todos los productos
     * 
     * @return List<ProductDTO> Lista de productos encontrados
     */
    public List<ProductDTO> getAllProductos() {
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
    public ProductDTO getProductoById(Integer id) {
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
     * Obtiene un producto por nombre
     * 
     * @param name Nombre del producto a obtener
     * @return Optional<Producto> Producto encontrado
     */
    public ProductDTO getProductoByName(String name) {
        try {
            Optional<Producto> newProducto = productRepository.findByName(name);
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
    public ProductDTO postProduct(ProductDTO productoDTO) {
        try {
            // Verificamos que el producto no existe, si existe lanzamos una excepción
            Optional<Producto> newProductoOptional = productRepository.findByName(productoDTO.getName());
            if(newProductoOptional.isPresent())
                throw new RequestException(ApiError.PRODUCT_ALREADY_EXISTS);

            Producto newProducto = productMapper.toEntity(productoDTO);
            Producto savedProduct =  productRepository.save(newProducto);
            return productMapper.toDTO(savedProduct);
        }catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

     /**
     * Actualiza un producto por ID
     * 
     * @param id ID del producto a actualizar
     * @param productoDTO DTO del producto a actualizar
     * @return Producto actualizado
     */
    public ProductDTO updateProductById(Integer id, ProductDTO productoDTO) {
        try {
            Optional<Producto> newProducto = productRepository.findById(id);
            // Verificamos que el producto existe, si no existe lanzamos una excepción
            if(!newProducto.isPresent())
                throw new RequestException(ApiError.RECORD_NOT_FOUND);
            
            Producto producto = productMapper.toEntity(productoDTO);
            producto.setId(newProducto.get().getId());
            Producto updatedProduct = productRepository.save(producto);
            return productMapper.toDTO(updatedProduct);
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un producto por nombre
     * 
     * @param nombre Nombre del producto a actualizar
     * @param productoDTO DTO del producto a actualizar
     * @return Producto actualizado
     */
    public ProductDTO updateProductByName(String name, ProductDTO productoDTO) {
        try {
            Optional<Producto> newProducto = productRepository.findByName(name);
            // Verificamos que el producto existe, si no existe lanzamos una excepción
            if(!newProducto.isPresent())
                throw new RequestException(ApiError.RECORD_NOT_FOUND);
            
            Producto producto = productMapper.toEntity(productoDTO);
            producto.setId(newProducto.get().getId());
            Producto updatedProduct = productRepository.save(producto);
            return productMapper.toDTO(updatedProduct);
        } catch (RequestException e) {
            throw e;
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
            // Verificamos que el producto existe, si no existe lanzamos una excepción
            Optional<Producto> newProducto = productRepository.findById(id);
            if(!newProducto.isPresent())
                throw new RequestException(ApiError.RECORD_NOT_FOUND);
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina todos los productos que coincidan con el nombre especificado
     * 
     * @param name Nombre de los productos a eliminar
     * @return int Número de productos eliminados
     */
    public int deleteByName(String name) {
        try {
            // Obtenemos todos los productos con ese nombre
            List<Producto> productos = productRepository.findAllByName(name);
            
            // Verificamos que exista al menos un producto, si no existe lanzamos una excepción
            if (productos.isEmpty())
                throw new RequestException(ApiError.RECORD_NOT_FOUND);
            
                // Para cada producto, primero eliminamos sus referencias en pedido_producto
            for (Producto producto : productos) 
                productRepository.deletePedidoProductoByProductoId(producto.getId());
        
            // Eliminamos todos los productos encontrados
            productRepository.deleteAll(productos);
            
            // Devolvemos la cantidad de productos eliminados
            return productos.size();
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
}