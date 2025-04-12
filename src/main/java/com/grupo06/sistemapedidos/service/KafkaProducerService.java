package com.grupo06.sistemapedidos.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreationNotification(String message) {
        kafkaTemplate.send("pedido-creado", message);
    }
    
    public void sendModificationNotification(String message) {
        kafkaTemplate.send("pedido-modificado", message);
    }
    
    public void sendCancellationNotification(String message) {
        kafkaTemplate.send("pedido-cancelado", message);
    }
}