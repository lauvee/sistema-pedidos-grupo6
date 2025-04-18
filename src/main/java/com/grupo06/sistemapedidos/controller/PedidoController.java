package com.grupo06.sistemapedidos.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.exception.APIExceptionHandler;
import com.grupo06.sistemapedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controlador REST para gestionar pedidos en el sistema de pedidos.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar pedidos.
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
@RequestMapping("/api/pedido")
@Tag(name = "Pedido", description = "Controlador para gestionar pedidos, con eventos gestionados por Kafka")
public class PedidoController {
    private final PedidoService pedidoService; 

    public PedidoController (PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Obtener un pedido por su id
     * 
     * @param id ID del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     */
    @GetMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un pedido por su ID", description = "Devuelve un pedido específico basado en su ID.")
    public PedidoDTO getPedido(@PathVariable Integer id) {
        return pedidoService.getPedidoById(id);
    }

    /**
     * Obtener un pedido por su nombre
     * 
     * @param nombre Nombre del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     */
    @GetMapping("/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un pedido por su nombre", description = "Devuelve un pedido específico basado en su nombre.")
    public PedidoDTO getPedidoByNombre(@PathVariable String nombre) {
        return pedidoService.getPedidoByNombre(nombre);
    }

     /**
     * Obtener todos los pedidos
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     */
    @GetMapping("/all")
    @SwaggerApiResponses
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos.")
    public List<PedidoDTO> getAllPedidos()  {
        return pedidoService.getAllPedidos();
    }

    /**
     * Crear un nuevo pedido, deven existir previamente los productos y el usuario
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return PedidoDTO DTO para la transferencia de pedidos
     */
    @PostMapping()
    @SwaggerApiResponses
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido basado en el DTO proporcionado, los suuarios y los productos a los que hacen referencia deven de existir previamente.")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO postPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.postPedido(pedidoDTO);
    }
    
    /**
     * Actualizar un pedido existente por id, deven existir previamente los productos y el usuario
     * 
     * @param pedidoDTO DTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return PedidoDTO DTO para la transferencia de pedidos
     */
    @PutMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza un pedido existente basado en el DTO proporcionado.")
    public PedidoDTO putPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.putPedidoById(pedidoDTO);
    }

     /**
     * Eliminar un pedido por su id
     * 
     * @param id ID del pedido a eliminar
     */
    @DeleteMapping("/del/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un pedido por su ID", description = "Elimina un pedido específico basado en su ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void deletePedidoById(@PathVariable Integer id)  {
        pedidoService.deletePedidoById(id);
    }
}
