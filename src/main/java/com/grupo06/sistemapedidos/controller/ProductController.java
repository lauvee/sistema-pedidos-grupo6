package com.grupo06.sistemapedidos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.exception.APIExceptionHandler;
import com.grupo06.sistemapedidos.service.ProductService;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador REST para gestionar productos en el sistema de pedidos.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar productos.
 * 
 * {@link RestController} Indica que esta clase es un controlador REST que manejará solicitudes HTTP.
 * {@link RequestMapping} Define la ruta base para todos los endpoints de este controlador.
 * {@link Tag} Documentación de OpenAPI para este controlador.
 * {@link ResponseStatus} Define el código de estado HTTP para las respuestas.
 * 
 * Swagger:
 * {@link SwaggerApiResponses} Anotación personalizada para definir respuestas de API.
 * {@link Operation} Documentación de OpenAPI para cada operación del controlador.
 * 
 * Errores:
 * {@link APIExceptionHandler} Manejo de excepciones personalizadas para errores de API.
 */
@RestController 
@RequestMapping("/api/producto") 
@Tag(name = "Producto", description = "Controlador para gestionar productos") 
public class ProductController {

    private ProductService productoService;

    public ProductController(ProductService productoService) {
        this.productoService = productoService;
    }

    /**
     * Obtiene un producto por su ID
     * 
     * @param id ID del producto a obtener
     * @return ProductDTO DTO para la transferencia de productos, producto encontrado
     */
    @GetMapping("/{id}") 
    @SwaggerApiResponses 
    @Operation(summary = "Obtener un producto por su ID", description = "Devuelve un producto específico basado en su ID.")
    public ProductDTO getProductoById(@PathVariable Integer id) {
        // Busca el producto por ID y devuelve una respuesta HTTP adecuada
        return productoService.getProductoById(id);
    }

    /**
     * Obtiene un producto por su nombre
     * 
     * @param nombre Nombre del producto a obtener
     * @return ProductDTO DTO para la transferencia de productos, producto encontrado
     */
    @GetMapping("/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un producto por su nombre", description = "Devuelve un producto específico basado en su nombre.")
    public ProductDTO getProductoByName(@PathVariable String name) {
        // Busca el producto por nombre y devuelve una respuesta HTTP adecuada
        return productoService.getProductoByName(name);
    }

     /**
     * Obtiene todos los productos
     * 
     * @return List<ProductDTO> DTO para la transferencia de productos
     */
     @GetMapping("/all") // Define un endpoint GET para obtener todos los productos
     @SwaggerApiResponses // Anotación personalizada para definir respuestas de API
     @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos.")
     public List<ProductDTO> getAllProductos() {
         // Devuelve una lista de todos los productos almacenados en la base de datos
         return productoService.getAllProductos();
     }
 
     /**
      * Crea un nuevo producto, debe existir previamente el usuario

      * @param productDTO DTO para la transferencia de productos, se compone de el id del usuario y una lista de ids de productos
      * @return ProductDTO DTO para la transferencia de productos
      */
     @PostMapping()
     @SwaggerApiResponses 
     @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema.")
     @ResponseStatus(HttpStatus.CREATED)
     public ProductDTO postProducto(@RequestBody ProductDTO productDTO) {
         // Guarda el producto recibido en el cuerpo de la solicitud y lo devuelve
         return productoService.postProduct(productDTO);
     }
 
    /**
    * Actualiza un producto existente, debe existir previamente el usuario
    
    * @param productDTO DTO para la transferencia de productos, se compone de el id del usuario y una lista de ids de productos
    * @return ProductDTO DTO para la transferencia de productos
    */
     @PutMapping("/{id}")
     @SwaggerApiResponses 
     @Operation(summary = "Actualizar un producto por su ID", description = "Actualiza un producto específico basado en su ID.")
     public ProductDTO updateProductById(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
         return productoService.updateProductById(id, productDTO);
     }

     @PutMapping("/name/{name}")
     @SwaggerApiResponses 
     @Operation(summary = "Actualizar un producto por su ID", description = "Actualiza un producto específico basado en su ID.")
     public ProductDTO updateProductByName(@PathVariable String name, @RequestBody ProductDTO productDTO) {
         return productoService.updateProductByName(name.toLowerCase().trim().replace(" ", "-"), productDTO);
     }
    
   /**
    * Elimina un producto por su ID
    * @param id ID del producto a eliminar
    * @return ResponseEntity<Void> Respuesta HTTP sin contenido
    */
    @DeleteMapping("/del/{id}") 
    @SwaggerApiResponses 
    @Operation(summary = "Eliminar un producto por su ID", description = "Elimina un producto específico basado en su ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        // Verifica si el producto con el ID especificado existe
        if (productoService.getProductoById(id) == null) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        // Elimina el producto por su ID
        productoService.deleteById(id);
         
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina un producto por su nombre, formato ejemplo: "nombre-producto"
     * 
     * @param name Nombre del producto a eliminar
     * @return ResponseEntity<Void> Respuesta HTTP sin contenido
     */
    @DeleteMapping("/del/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un producto por su nombre", description = "Elimina un producto específico basado en su nombre.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProductByName(@PathVariable String name) {
        productoService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
