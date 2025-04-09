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

    @GetMapping("/pedido/{id}")
    public PedidoDTO getPedido(@RequestParam Integer id) throws Exception{
        return pedidoService.getPedido(id);
    }

    @GetMapping("/all/pedidos")
    public List<PedidoDTO> getAllPedidos() throws Exception {
        return pedidoService.getAllPedidos();
    }

    @PostMapping("/pedido")
    public PedidoDTO postMethodName(@RequestBody PedidoDTO pedidoDTO) throws Exception {
        return pedidoService.postpedido(pedidoDTO);
    }
    
    @DeleteMapping("/del/pedido/{id}")
    public void deletePedido(@RequestParam Integer id){
        pedidoService.deletePedido(id);
    }
}
