package com.grupo06.sistemapedidos.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "cart-topic", groupId = "cart_group")
    public void listen(String message) {
        System.out.println("Mensaje recibido de Kafka: " + message);
    }

}