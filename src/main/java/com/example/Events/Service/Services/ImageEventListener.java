package com.example.Events.Service.Services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;
import com.example.Events.Service.Config.RabbitMQConfig;

@Service
public class ImageEventListener {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventsService eventsService;
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveImageEvent(EventMetadata eventMetadata) {
        System.out.println("Received Image Event: " + eventMetadata.getImageUrls() + "for Event: " + eventMetadata.getId());

        // Find event by ID
        Optional<EventMetadata> optionalEvent = eventsRepository.findById(eventMetadata.getId());

        if (optionalEvent.isPresent()) {
            EventMetadata existingEventMetadata = optionalEvent.get();

            // Append image URL to the list instead fo image ID
            existingEventMetadata.getImageUrls().addAll(existingEventMetadata.getImageUrls());
            eventsRepository.save(existingEventMetadata);
            System.out.println("✅ Image URL added to event: " + existingEventMetadata.getId());
        } else {
            System.out.println("❌ Event not found: " + eventMetadata.getId());
        }
    }
}
