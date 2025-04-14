package com.grupo06.sistemapedidos.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Servicio que consume mensajes de Kafka para diversos eventos relacionados con los pedidos.
 * <p>
 * Este servicio implementa listeners para tres tópicos:
 * <ul>
 *   <li><strong>pedido-creado</strong>: Notifica la creación de un pedido.</li>
 *   <li><strong>pedido-modificado</strong>: Notifica la modificación de un pedido.</li>
 *   <li><strong>pedido-cancelado</strong>: Notifica la cancelación de un pedido.</li>
 * </ul>
 * </p>
 * Un consumer de Kafka es un componente que se suscribe a un tópico y procesa los mensajes que recibe en una cola.
 * Los consumers son responsables de recibir y procesar los mensajes enviados por los producers.
 * Procesa los mensajes de forma asíncrona. 
 * En un sistema Kafka los mensajes se almacenan en tópicos (que actúan de forma similar a una cola asíncrona) y
 * los consumers se suscriben a esas particiones para procesar los mensajes de forma paralela y distribuida.
 * 
 * @Service indica que esta clase es un servicio de Spring, lo que permite la inyección de dependencias y la gestión del ciclo de vida del bean.
 */
@Service
public class KafkaConsumerService {

     /**
     * Escucha y procesa los mensajes del tópico "pedido-creado".
     *
     * @param message el mensaje recibido que indica la creación de un pedido.
     */
    @KafkaListener(topics = "pedido-creado", groupId = "pedido_creado_group")
    public void listenCreado(String message) {
        System.out.println("Pedido creado recibido: " + message);
    }
    
    /**
     * Escucha y procesa los mensajes del tópico "pedido-modificado".
     *
     * @param message el mensaje recibido que indica la modificación de un pedido.
     */
    @KafkaListener(topics = "pedido-modificado", groupId = "pedido_modificado_group")
    public void listenModificado(String message) {
        System.out.println("Pedido modificado recibido: " + message);
    }
    
    /**
     * Escucha y procesa los mensajes del tópico "pedido-cancelado".
     *
     * @param message el mensaje recibido que indica la cancelación de un pedido.
     */
    @KafkaListener(topics = "pedido-cancelado", groupId = "pedido_cancelado_group")
    public void listenCancelado(String message) {
        System.out.println("Pedido cancelado recibido: " + message);
    }

}