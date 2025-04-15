package com.grupo06.sistemapedidos.service;

import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.model.PedidoEvento;
import com.grupo06.sistemapedidos.repository.PedidoEventoRepository;

@Service
public class PedidoEventoService {
    private final PedidoEventoRepository pedidoEventoRepository;

    public PedidoEventoService(PedidoEventoRepository pedidoEventoRepository) {
        this.pedidoEventoRepository = pedidoEventoRepository;
    }

    public void saveEvent(PedidoEvento pedidoEvento) throws Exception {
        pedidoEventoRepository.save(pedidoEvento);
    }

    public void deleteEvent(Integer id) throws Exception {
        pedidoEventoRepository.deleteById(id);
    }
}
