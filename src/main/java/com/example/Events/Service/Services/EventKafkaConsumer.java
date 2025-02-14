package com.example.Events.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class EventKafkaConsumer {
    
    @Autowired
    private EventsService eventService;

    @KafkaListener(topics = "event-image-topic", groupId = "event-service-group")
    public void updateEventWithImage(String message) {
        String[] data = message.split(":");
        String eventId = data[0];
        String imageId = data[1];

        System.out.println("📥 Received Kafka event -> eventId: " + eventId + ", imageId: " + imageId);

        // Link image to event in database
        boolean updated = eventService.linkImageToEvent(eventId, imageId);
        if (updated) {
            System.out.println("✅ Event updated with media");
        } else {
            System.out.println("❌ Event not found");
        }
    }
}
