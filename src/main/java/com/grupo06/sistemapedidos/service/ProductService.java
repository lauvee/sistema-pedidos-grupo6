package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.repository.ProductRepository;

@Service
public class ProductService {
  
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Producto> findAll(){
        return productRepository.findAll();
    }

    /**
     * Obtiene un producto por ID
     * 
     * @param id ID del producto a obtener
     * @return Optional<Producto> Producto encontrado
     */
    public Optional<Producto> findById(Integer id) {
        return productRepository.findById(id);
    }

    /**
     * Guarda y actualiza el producto
     * 
     * @param producto
     * @return Producto guardado o actualizado
     */
    public Producto save(Producto producto) {
        return productRepository.save(producto);
    }

    /**
     * Elimina un producto por ID
     * 
     * @param id ID del producto a eliminar
     */
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}