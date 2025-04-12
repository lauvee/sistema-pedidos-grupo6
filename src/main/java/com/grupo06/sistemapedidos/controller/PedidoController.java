package com.grupo06.sistemapedidos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController // Indica que esta clase es un controlador REST que manejará solicitudes HTTP
@RequestMapping("/api/pedido") // Define la ruta base para todos los endpoints de este controlador
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
    public PedidoDTO getPedido(@RequestParam Integer id) throws Exception{
        return pedidoService.getPedido(id);
    }

     /**
     * Obtener todos los pedidos
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     * @throws Exception 
     */
    @GetMapping("/all")
    public List<PedidoDTO> getAllPedidos() throws Exception {
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
    public PedidoDTO postPedido(@RequestBody PedidoDTO pedidoDTO) throws Exception {
        return pedidoService.postPedido(pedidoDTO);
    }
    
    @PutMapping()
    public PedidoDTO putPedido(@RequestBody PedidoDTO entity) throws Exception {
        return pedidoService.putPedido(entity);
    }

     /**
     * Eliminar un pedido por su id
     * 
     * @param id ID del pedido a eliminar
     * @throws Exception
     */
    @DeleteMapping("/del/{id}")
    public void deletePedido(@RequestParam Integer id) throws Exception {
        pedidoService.deletePedido(id);
    }
}
