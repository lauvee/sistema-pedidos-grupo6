package com.grupo06.sistemapedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.service.ProductService;

@RestController // Indica que esta clase es un controlador REST que manejará solicitudes HTTP
@RequestMapping("/api/productos") // Define la ruta base para todos los endpoints de este controlador
public class ProductController {

    @Autowired // Inyección de dependencias para el servicio de productos
    private ProductService productoService;

    // Obtiene un producto por ID
    @GetMapping("/{id}") // Define un endpoint GET con un parámetro en la URL
    public ResponseEntity<Producto> getProducto(@PathVariable Integer id) {
        // Busca el producto por ID y devuelve una respuesta HTTP adecuada
        return productoService.findById(id)
                .map(producto -> ResponseEntity.ok(producto)) // Si se encuentra, devuelve 200 OK con el producto
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404 Not Found
    }

     // Obtiene todos los productos
     @GetMapping // Define un endpoint GET para obtener todos los productos
     public List<Producto> getAllProductos() {
         // Devuelve una lista de todos los productos almacenados en la base de datos
         return productoService.findAll();
     }
 
     // Crea un nuevo producto
     @PostMapping // Define un endpoint POST para crear un nuevo producto
     public Producto postProducto(@RequestBody Producto entity) {
         // Guarda el producto recibido en el cuerpo de la solicitud y lo devuelve
         return productoService.save(entity);
     }
 
     // Actualiza un producto existente
     @PutMapping("/{id}") // Define un endpoint PUT con un parámetro en la URL
     public ResponseEntity<Producto> putProducto(@PathVariable Integer id, @RequestBody Producto entity) {
         // Verifica si el producto con el ID especificado existe
         if (!productoService.findById(id).isPresent()) {
             return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
         }
         // Establece el ID del producto y lo guarda
         entity.setId(id);
         Producto updatedProducto = productoService.save(entity);
         // Devuelve 200 OK con el producto actualizado
         return ResponseEntity.ok(updatedProducto);
     }

    
    // Elimina un producto por ID
    @DeleteMapping("/{id}") // Define un endpoint DELETE con un parámetro en la URL
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        // Verifica si el producto con el ID especificado existe
        if (!productoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        // Elimina el producto por su ID
        productoService.deleteById(id);
         
        return ResponseEntity.noContent().build();
    }
}
