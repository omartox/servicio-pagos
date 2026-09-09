package com.prueba.pagos.service;

import com.prueba.pagos.config.ConfigRabbits;
import com.prueba.pagos.dto.StatusCambioEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publicaCambioStatus(StatusCambioEvent EvenMensaje) {
        rabbitTemplate.convertAndSend(ConfigRabbits.EXCHANGE, ConfigRabbits.ROUTING_KEY,  EvenMensaje);
    }
}
