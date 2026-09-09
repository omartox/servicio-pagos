package com.prueba.pagos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigRabbits {
    public static final String EXCHANGE = "pagos.exchange";
    public static final String QUEUE = "pagos.estatus.queue";
    public static final String ROUTING_KEY = "pago.estatus.cambiado";

    @Bean
    public TopicExchange pagosExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue pagosQueue() {
        return QueueBuilder
                .durable(QUEUE)
                .build();
    }

    @Bean
    public Binding pagosBinding(
            Queue pagosQueue,
            TopicExchange pagosExchange) {

        return BindingBuilder
                .bind(pagosQueue)
                .to(pagosExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
