package com.grupo06.sistemapedidos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.service.ProductService;

@RestController // Indica que esta clase es un controlador REST que manejará solicitudes HTTP
@RequestMapping("/api/producto") // Define la ruta base para todos los endpoints de este controlador
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
     * @throws Error
     */
    @GetMapping("/{id}") // Define un endpoint GET con un parámetro en la URL
    public ProductDTO getProducto(@PathVariable Integer id) throws Error {
        // Busca el producto por ID y devuelve una respuesta HTTP adecuada
        return productoService.findById(id);
    }

    /**
     * Obtiene todos los productos
     * 
     * @return List<ProductDTO> DTO para la transferencia de productos
     * @throws Exception
     */
     @GetMapping("/all") // Define un endpoint GET para obtener todos los productos
     public List<ProductDTO> getAllProductos() throws Exception {
         // Devuelve una lista de todos los productos almacenados en la base de datos
         return productoService.findAll();
     }
 
     /**
      * Crea un nuevo producto, debe existir previamente el usuario

      * @param productDTO DTO para la transferencia de productos, se compone de el id del usuario y una lista de ids de productos
      * @return ProductDTO DTO para la transferencia de productos
      * @throws Exception
      */
     @PostMapping // Define un endpoint POST para crear un nuevo producto
     public ProductDTO postProducto(@RequestBody ProductDTO productDTO) throws Exception {
         // Guarda el producto recibido en el cuerpo de la solicitud y lo devuelve
         return productoService.postProducto(productDTO);
     }
 
    /**
    * Actualiza un producto existente, debe existir previamente el usuario
    
    * @param productDTO DTO para la transferencia de productos, se compone de el id del usuario y una lista de ids de productos
    * @return ProductDTO DTO para la transferencia de productos
    * @throws Exception
    */
     @PutMapping() // Define un endpoint PUT con un parámetro en la URL
     public ResponseEntity<ProductDTO> putProducto(@RequestBody ProductDTO productDTO) throws Exception {
         // Verifica si el producto con el ID especificado existe
         if (productoService.findById(productDTO.getId()) == null) {
             return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
         }
         // Establece el ID del producto y lo guarda
         productDTO.setId(productDTO.getId());
         ProductDTO updatedProducto = productoService.postProducto(productDTO);
         // Devuelve 200 OK con el producto actualizado
         return ResponseEntity.ok(updatedProducto);
     }

    
   /**
    * Elimina un producto por su ID
    * @param id ID del producto a eliminar
    * @return ResponseEntity<Void> Respuesta HTTP sin contenido
    * @throws Exception
    */
    @DeleteMapping("/del/{id}") // Define un endpoint DELETE con un parámetro en la URL
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) throws Exception {
        // Verifica si el producto con el ID especificado existe
        if (productoService.findById(id) == null) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        // Elimina el producto por su ID
        productoService.deleteById(id);
         
        return ResponseEntity.noContent().build();
    }
}
