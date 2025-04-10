package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.mapper.PedidoMapper;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.repository.PedidoRepository;


/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los pedidos.
 * Proporciona métodos para obtener, crear y eliminar pedidos.
 * 
 * @Service indica que esta clase es un servicio de Spring y permite la inyección de dependencias.
 */
@Service
public class PedidoService {
     /**
     * PedidoMapper es un objeto que se encarga de convertir entre entidades y DTOs.
     * PedidoRepository es un objeto que se encarga de interactuar con la base de datos.
     */
    PedidoMapper pedidoMapper; 
    PedidoRepository pedidoRepository;

    public PedidoService (PedidoMapper pedidoMapper){
        this.pedidoMapper = pedidoMapper;
    }

    /**
     * Obtiene un pedido por su id.
     * 
     * @param id ID del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     * @throws Exception
     */
    public PedidoDTO getPedido(int id) throws Exception {
        Optional<Pedido> newPedidoOptional = pedidoRepository.findById(id);
        PedidoDTO newPedidoDTO = null;
        if(newPedidoOptional.isPresent()){
            Pedido newPedido = newPedidoOptional.get();
            newPedidoDTO = pedidoMapper.toDTO(newPedido);
        }
        return newPedidoDTO;
    }
    
     /**
     * Obtiene todos los pedidos.
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     * @throws Exception
     */
    public List<PedidoDTO> getAllPedidos() throws Exception {
        List<Pedido> listaPedidos = pedidoRepository.findAll();
        List<PedidoDTO> listaPedidosDTO = listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .toList();

        return listaPedidosDTO;
    }

      /**
     * Crea un nuevo pedido.
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos
     * @return PedidoDTO DTO para la transferencia de pedidos
     * @throws Exception
     */
    public PedidoDTO postpedido(PedidoDTO pedidoDTO) throws Exception {
        Pedido newPedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoSave = pedidoRepository.save(newPedido);
        return pedidoMapper.toDTO(pedidoSave);
    }
    
    /**
     * Elimina un pedido por su id.
     * 
     * @param id ID del pedido a eliminar
     */
    public void deletePedido(Integer id){
        pedidoRepository.deleteById(id);
    }
}
