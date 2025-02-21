package com.example.Events.Service.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class UserJoinedRabbitMQConfig {
    
    public static final String EXCHANGE_NAME = "user.joined.exchange";
    public static final String QUEUE_NAME = "user.joined.queue";
    public static final String ROUTING_KEY = "user.joined.routingKey";

    @Bean
    public DirectExchange userJoinedExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue userJoinedQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding userJoinedBinding(Queue userJoinedQueue, DirectExchange userJoinedExchange) {
        return BindingBuilder.bind(userJoinedQueue).to(userJoinedExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(JsonMessageConverter()); // Ensures JSON converter is used globally
        return rabbitTemplate;
    }
}
