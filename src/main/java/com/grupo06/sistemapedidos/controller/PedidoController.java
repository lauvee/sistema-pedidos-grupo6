package com.grupo06.sistemapedidos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController // Indica que esta clase es un controlador REST que manejará solicitudes HTTP
@RequestMapping("/api/pedido") // Define la ruta base para todos los endpoints de este controlador
@Tag(name = "Pedido", description = "Controlador para gestionar pedidos") // Documentación de OpenAPI para este controlador
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
     * @throws Exception
     */
    @GetMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un pedido por su ID", description = "Devuelve un pedido específico basado en su ID.")
    public PedidoDTO getPedido(@PathVariable Integer id) {
        return pedidoService.getPedido(id);
    }

     /**
     * Obtener todos los pedidos
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     */
    @GetMapping("/all")
    @SwaggerApiResponses
    public List<PedidoDTO> getAllPedidos()  {
        return pedidoService.getAllPedidos();
    }

    /**
     * Crear un nuevo pedido, deven existir previamente los productos y el usuario
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return PedidoDTO DTO para la transferencia de pedidos
     * @throws Exception
     */
    @PostMapping()
    @SwaggerApiResponses
    public PedidoDTO postPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.postPedido(pedidoDTO);
    }
    
    /**
     * Actualizar un pedido existente, deven existir previamente los productos y el usuario
     * 
     * @param entity DTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return PedidoDTO DTO para la transferencia de pedidos
     */
    @PutMapping()
    @SwaggerApiResponses
    public PedidoDTO putPedido(@RequestBody PedidoDTO entity) {
        return pedidoService.putPedido(entity);
    }

     /**
     * Eliminar un pedido por su id
     * 
     * @param id ID del pedido a eliminar
     * @throws Exception
     */
    @DeleteMapping("/del/{id}")
    @SwaggerApiResponses
    public void deletePedido(@RequestParam Integer id)  {
        pedidoService.deletePedido(id);
    }
}
