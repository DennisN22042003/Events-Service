package com.example.Events.Service.Services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.example.Events.Service.Config.UserJoinedRabbitMQConfig;
import com.example.Events.Service.DTO.UserJoinedDTO;

@Service
public class EventsRabbitMQProducer {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventsRabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Send User-Joined Event with eventId and userId as strings to RabbitMQ
    public void sendUserJoinedEvent(String eventId, String userId) {
        // Create a UserJoinedDTO object
        UserJoinedDTO metadata = new UserJoinedDTO(eventId, userId);
        //metadata.getEventId();
        //metadata.getUserId();
        // Log the UserJoinedDTO before serialization
        System.out.println("📤 Preparing to send User-Joined Event: " + metadata /*metadata.getEventId() + " for User: " + metadata.getUserId()*/);

        // Serialize and send the UserJoinedDTO object to RabbitMQ
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(UserJoinedRabbitMQConfig.EXCHANGE_NAME, UserJoinedRabbitMQConfig.ROUTING_KEY, metadata);

        // Log the serialized message sent to RabbitMQ
        System.out.println("📤 Sent User-Joined Event (Serialized): " + metadata /*metadata.getEventId() + " for User: " + metadata.getUserId()*/);
    }
}
