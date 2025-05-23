package com.grupo06.sistemapedidos.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio productor de mensajes para Kafka.
 * Este servicio se encarga de enviar notificaciones a distintos tópicos de Kafka asociados a eventos
 * en el sistema de pedidos, tales como la creación, modificación y cancelación. Es utilizado para informar
 * a otros servicios o procesos cuando ocurre un evento relevante.
 * En Kafka, un producer es un componente que envía mensajes a un tópico específico en el clúster. Los producers
 * son responsables de serializar los mensajes y enviarlos a las particiones correspondientes, facilitando la distribución
 * y entrega de la información de forma efectiva.
 * 
 * {@link Service} indica que esta clase es un servicio de Spring, lo que permite la inyección de dependencias y
 * la gestión del ciclo de vida del bean.
 */
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param kafkaTemplate plantilla de Kafka para enviar mensajes.
     */
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envía una notificación de creación de pedido al tópico "pedido-creado".
     *
     * @param message el mensaje a enviar que indica la creación de un pedido.
     */
    public void sendOrderCreated(String message) {
        kafkaTemplate.send("pedido-creado", message);
    }

    /**
     * Envía una notificación de procesamiento de pedido al tópico "pedido-procesado".
     * 
     * @param message el mensaje a enviar que indica el procesamiento de un pedido.
     */
    public void sendOrderProcessed(String message) {
        kafkaTemplate.send("pedido-procesado", message);
    }
    
    /**
     * Envía una notificación de modificación de pedido al tópico "pedido-modificado".
     *
     * @param message el mensaje a enviar que indica la modificación de un pedido.
     */
    public void sendModificationNotification(String message) {
        kafkaTemplate.send("pedido-modificado", message);
    }
    
     /**
     * Envía una notificación de cancelación de pedido al tópico "pedido-cancelado".
     *
     * @param message el mensaje a enviar que indica la cancelación de un pedido.
     */
    public void sendCancellationNotification(String message) {
        kafkaTemplate.send("pedido-cancelado", message);
    }

    /**
     * Envía una notificación de error al tópico "pedido-dead-letter".
     * 
     * @param message el mensaje a enviar que indica un error en el procesamiento de un pedido.
     */
    public void sendDeadLetter(String message) {
        kafkaTemplate.send("pedido-dead-letter", message);
    }
}