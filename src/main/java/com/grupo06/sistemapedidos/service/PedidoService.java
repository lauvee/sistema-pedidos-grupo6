package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.mapper.PedidoMapper;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.repository.PedidoRepository;

@Service
public class PedidoService {
    PedidoMapper pedidoMapper; 
    PedidoRepository pedidoRepository;

    public PedidoService (PedidoMapper pedidoMapper){
        this.pedidoMapper = pedidoMapper;
    }

    public PedidoDTO getPedido(int id) throws Exception {
        Optional<Pedido> newPedidoOptional = pedidoRepository.findById(id);
        PedidoDTO newPedidoDTO = null;
        if(newPedidoOptional.isPresent()){
            Pedido newPedido = newPedidoOptional.get();
            newPedidoDTO = pedidoMapper.toDTO(newPedido);
        }
        return newPedidoDTO;
    }
    
    public List<PedidoDTO> getAllPedidos() throws Exception {
        List<Pedido> listaPedidos = pedidoRepository.findAll();
        List<PedidoDTO> listaPedidosDTO = listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .toList();

        return listaPedidosDTO;
    }

    public PedidoDTO postpedido(PedidoDTO pedidoDTO) throws Exception {
        Pedido newPedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoSave = pedidoRepository.save(newPedido);
        return pedidoMapper.toDTO(pedidoSave);
    }
    
    public void deletePedido(Integer id){
        pedidoRepository.deleteById(id);
    }
}
