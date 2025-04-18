package com.grupo06.sistemapedidos.service;

import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.model.PedidoEvento;
import com.grupo06.sistemapedidos.repository.PedidoEventoRepository;

/**
 * Servicio para manejar eventos de pedidos.
 * 
 * Esta clase proporciona métodos para guardar y eliminar eventos de pedidos en la base de datos.
 * Los eventos de pedidos son representaciones de acciones o cambios que ocurren en el ciclo de vida de un pedido.
 */
@Service
public class PedidoEventoService {
    private final PedidoEventoRepository pedidoEventoRepository;

    public PedidoEventoService(PedidoEventoRepository pedidoEventoRepository) {
        this.pedidoEventoRepository = pedidoEventoRepository;
    }

    /**
     * Obtiene todos los eventos de pedidos.
     * 
     * @param pedidoEvento Evento de pedido a buscar.
     * @return Lista de eventos de pedidos.
     * @throws Exception Si ocurre un error al obtener los eventos lo lanzada al topico de Kafka "pedido-dead-letter".
     */
    public void saveEvent(PedidoEvento pedidoEvento) throws Exception {
        pedidoEventoRepository.save(pedidoEvento);
    }

    /**
     * Elimina un evento de pedido por su ID.
     * 
     * @param id ID del evento de pedido a eliminar.
     * @throws Exception Si ocurre un error al eliminar el evento. Lo lanzada al topico de Kafka "pedido-dead-letter".
     */
    public void deleteEvent(Integer id) throws Exception {
        pedidoEventoRepository.deleteById(id);
    }
}
