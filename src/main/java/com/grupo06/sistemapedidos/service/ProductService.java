package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.repository.ProductRepository;

@Service
public class ProductService {
  
    @Autowired
    private ProductRepository productRepository;

    public List<Producto> findAll(){
        return productRepository.findAll();
    }

    // Obtiene un producto por ID
    public Optional<Producto> findById(Integer id) {
        return productRepository.findById(id);
    }

    // Guarda y actualiza el producto
    public Producto save(Producto producto) {
        return productRepository.save(producto);
    }

    // Elimina un producto por ID
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}