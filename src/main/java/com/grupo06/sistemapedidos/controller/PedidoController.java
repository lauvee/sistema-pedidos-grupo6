package com.grupo06.sistemapedidos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PedidoController {
    PedidoService pedidoService; 

    public PedidoController (PedidoService pedidoService) throws Exception {
        this.pedidoService = pedidoService;
    }

    /**
     * Obtener un pedido por su id
     * 
     * @param id ID del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     * @throws Exception
     */
    @GetMapping("/pedido/{id}")
    public PedidoDTO getPedido(@RequestParam Integer id) throws Exception{
        return pedidoService.getPedido(id);
    }

     /**
     * Obtener todos los pedidos
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     * @throws Exception 
     */
    @GetMapping("/all/pedidos")
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
    @PostMapping("/pedido")
    public PedidoDTO postMethodName(@RequestBody PedidoDTO pedidoDTO) throws Exception {
        return pedidoService.postpedido(pedidoDTO);
    }
    
     /**
     * Eliminar un pedido por su id
     * 
     * @param id ID del pedido a eliminar
     * @throws Exception
     */
    @DeleteMapping("/del/pedido/{id}")
    public void deletePedido(@RequestParam Integer id){
        pedidoService.deletePedido(id);
    }
}
