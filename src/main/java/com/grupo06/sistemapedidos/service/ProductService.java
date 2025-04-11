package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.mapper.ProductMapper;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.repository.ProductRepository;

@Service
public class ProductService {
  
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() throws Exception  {
        List<Producto> listProductos = productRepository.findAll();
        return listProductos.stream()
                .map(productMapper::toDTO).toList();
    }

    /**
     * Obtiene un producto por ID
     * 
     * @param id ID del producto a obtener
     * @return Optional<Producto> Producto encontrado
     */
    public ProductDTO findById(Integer id) throws Error {
        Optional<Producto> newProducto = productRepository.findById(id);
        if(!newProducto.isPresent()){
            throw new Error();
        }
        return productMapper.toDTO(newProducto.get());
    }

    /**
     * Guarda y actualiza el producto
     * 
     * @param producto
     * @return Producto guardado o actualizado
     */
    public ProductDTO postProducto(ProductDTO producto) throws Exception {
        Producto newProducto = productMapper.toEntity(producto);
        Producto savedProduct =  productRepository.save(newProducto);
        return productMapper.toDTO(savedProduct);
    }

    /**
     * Elimina un producto por ID
     * 
     * @param id ID del producto a eliminar
     */
    public void deleteById(Integer id) throws Exception {
        productRepository.deleteById(id);
    }
}