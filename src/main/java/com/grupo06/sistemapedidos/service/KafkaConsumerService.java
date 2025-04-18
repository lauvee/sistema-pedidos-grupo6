package com.grupo06.sistemapedidos.service;

import java.time.LocalDate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.PedidoEvento;
import com.grupo06.sistemapedidos.utilities.ColorUtils;

/**
 * Servicio que consume mensajes de Kafka para diversos eventos relacionados con los pedidos.
 * Este servicio implementa listeners para tres tópicos:
 * - pedido-creado</strong>: Notifica la creación de un pedido.
 * - pedido-modificado</strong>: Notifica la modificación de un pedido.
 * - pedido-cancelado</strong>: Notifica la cancelación de un pedido.
*
 * Un consumer de Kafka es un componente que se suscribe a un tópico y procesa los mensajes que recibe en una cola.
 * Los consumers son responsables de recibir y procesar los mensajes enviados por los producers.
 * Procesa los mensajes de forma asíncrona. 
 * En un sistema Kafka los mensajes se almacenan en tópicos (que actúan de forma similar a una cola asíncrona) y
 * los consumers se suscriben a esas particiones para procesar los mensajes de forma paralela y distribuida.
 * 
 * {@link Service} indica que esta clase es un servicio de Spring, lo que permite la inyección de dependencias y 
 * la gestión del ciclo de vida del bean.
* {@link Retryable} indica que el método se reintentará en caso de que falle.
* value = { Exception.class, RequestException.class } indica qué excepciones se deben manejar.
* maxAttempts = 3 indica el número máximo de reintentos.
* backoff = @Backoff(delay = 2000) indica el tiempo de espera entre reintentos.
* En este caso, se espera 2000 ms (2 segundos) entre cada intento.
*/
@Service
public class KafkaConsumerService {
    private final KafkaProducerService kafkaProducerService;
    private final PedidoEventoService pedidoEventoService;

    public KafkaConsumerService(KafkaProducerService kafkaProducerService, PedidoEventoService pedidoEventoService) {
        this.kafkaProducerService = kafkaProducerService;
        this.pedidoEventoService = pedidoEventoService;
    }

    
     /**
      * Escucha y procesa los mensajes del tópico "pedido-creado".
      * 
      * @param message el mensaje recibido que indica la creación de un pedido. 
      * @param pedido el pedido que se está procesando.
      * @throws Exception si el pedido es nulo o si ocurre un error al procesar el mensaje, sera recivido por el método fallback.
      */
    @Retryable(
        value = { Exception.class, RequestException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @KafkaListener(topics = "pedido-creado", groupId = "pedido_creado_group")
    public void listenCreado(String message, Pedido pedido) throws Exception {
        if(pedido == null){
            throw new Exception();
        }

        // Guardamops el evento en la base de datos para generar persistencia
        String eventMessage = "Pedido con el id " + pedido.getId() + " del usuario" + pedido.getUsuario().getId() + " creado";
        PedidoEvento pedidoEvento = new PedidoEvento("pedido-creado", eventMessage , LocalDate.now());
        pedidoEventoService.saveEvent(pedidoEvento);

        System.out.println(ColorUtils.pintarVerde("Pedido creado recibido: " + message));
    }

    /**
     * Escucha y procesa los mensajes del tópico "pedido-creado".
     * 
     * @param message el mensaje recibido que indica la creación de un pedido.
     * @param pedido el pedido que se está procesando.
     * @throws Exception si el pedido es nulo o si ocurre un error al procesar el mensaje, sera recivido por el método fallback.
     */
    @Retryable(
        value = { Exception.class, RequestException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @KafkaListener(topics = "pedido-procesado", groupId = "pedido_procesado_group")
    public void listenProccesed(String message, Pedido pedido) throws Exception {
        if(pedido == null){
            throw new Exception();
        }

        // Guardamops el evento en la base de datos para generar persistencia
        String eventMessage = "Pedido con el id " + pedido.getId() + " del usuario" + pedido.getUsuario().getId() + " procesado";
        PedidoEvento pedidoEvento = new PedidoEvento("pedido-procesado", eventMessage , LocalDate.now());
        pedidoEventoService.saveEvent(pedidoEvento);

        System.out.println(ColorUtils.pintarVerde("Pedido procesado recibido: " + message));
    }
    
    /**
     * Escucha y procesa los mensajes del tópico "pedido-modificado".
     *
     * @param message el mensaje recibido que indica la modificación de un pedido.
     * @param pedido el pedido que se está procesando.
     * @throws Exception si el pedido es nulo o si ocurre un error al procesar el mensaje, sera recivido por el método fallback.
     */
    @Retryable(
        value = { Exception.class, RequestException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @KafkaListener(topics = "pedido-modificado", groupId = "pedido_modificado_group")
    public void listenModificado(String message, Pedido pedido) throws Exception {
        if(pedido == null){
            throw new Exception();
        }

        // Guardamops el evento en la base de datos para generar persistencia
        String eventMessage = "Pedido con el id " + pedido.getId() + " del usuario" + pedido.getUsuario().getId() + " modificado";
        PedidoEvento pedidoEvento = new PedidoEvento("pedido-modificado", eventMessage , LocalDate.now());
        pedidoEventoService.saveEvent(pedidoEvento);

        System.out.println(ColorUtils.pintarVerde("Pedido modificado recibido: " + message));
    }
    
    /**
     * Escucha y procesa los mensajes del tópico "pedido-cancelado".
     *
     * @param message el mensaje recibido que indica la cancelación de un pedido.
     * @param pedido el pedido que se está procesando.
    * @throws Exception si el pedido es nulo o si ocurre un error al procesar el mensaje, sera recivido por el método fallback.
    */
    @Retryable(
        value = { Exception.class, RequestException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @KafkaListener(topics = "pedido-cancelado", groupId = "pedido_cancelado_group")
    public void listenCancelado(String message, Pedido pedido) throws Exception {
        // Si el pedido sigue existiendo, lanzamos una excepción para que se reintente el mensaje.
        // En un caso real, aquí se podría enviar el mensaje a un dead-letter topic o manejarlo de otra manera.
        if(pedido == null){
            throw new Exception();
        }

        // Modificamos el estado del pedido a cancelado
        String eventMessage = "Pedido con el id " + pedido.getId() + " del usuario" + pedido.getUsuario().getId() + " cancelado";
        PedidoEvento pedidoEvento = new PedidoEvento("pedido-cancelado", eventMessage , LocalDate.now());
        pedidoEventoService.saveEvent(pedidoEvento);

        System.out.println(ColorUtils.pintarRojo("Pedido cancelado recibido: " + message));
    }

    /**
     * Escucha y procesa los mensajes del tópico "reserva-evento".
     * {@link Recover} indica el método que se ejecutará en caso de que falle el método original.
     * 
     * @param message el mensaje recibido que indica la creación de un pedido.
     * @param pedido el pedido que se está procesando.
     * @param e la excepción que se lanzó al procesar el mensaje.
     * @throws Exception si el pedido es nulo o si ocurre un error al procesar el mensaje.
     */
    @Recover
    public void fallback(Exception e, String message, Pedido pedido) throws Exception {
        // Lo añadimos a un dead-letter topic para que se procese posteriormente
        String deadLetterMessage = "Error al procesar pedido id: " + pedido.getId() + ", message: " + message;
        kafkaProducerService.sendDeadLetter(deadLetterMessage);

        // Guardamops el evento en la base de datos para generar persistencia
        String eventMessage = "Pedido con el id " + pedido.getId() + " del usuario" + pedido.getUsuario().getId() + " ocurrio un error al procesar el pedido";
        PedidoEvento pedidoEvento = new PedidoEvento("pedido-dead-letter", eventMessage , LocalDate.now());
        pedidoEventoService.saveEvent(pedidoEvento);

        System.out.println(ColorUtils.pintarRojo("Error persistente, enviando evento a dead letter: " + pedido.getId()));
    }
}