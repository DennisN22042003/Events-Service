package com.example.Events.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;
import com.example.Events.Service.DTO.UserJoinedDTO;

@Service
public class EventsService {
    
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventsRabbitMQProducer eventsRabbitMQProducer;

    // Create a new event
    public EventMetadata createEvent(String name, String createdBy) {

        // Save Event Metadata in MongoDB
        EventMetadata metadata = new EventMetadata();
        metadata.setId(UUID.randomUUID().toString());
        metadata.setName(name);
        metadata.setCreatedAt(LocalDateTime.now());
        metadata.setCreatedBy("testEventCreator");
        metadata.setImageUrls(new ArrayList<>());

        // Save and return the event
        EventMetadata savedEventMetadata = eventsRepository.save(metadata);
        return savedEventMetadata;
    }

    // Validate if event exists
    public boolean isEventMetadataValid(String eventId) {
        return eventsRepository.findById(eventId).isPresent();
    }

    // Link media to an event
    public boolean linkImageToEvent(String eventId, String imageUrl) {
        Optional<EventMetadata> optionalEventMetadata = eventsRepository.findById(eventId);
        if (optionalEventMetadata.isPresent()) {
            EventMetadata eventMetadata = optionalEventMetadata.get();

            // Append the imageUrl of a new image instead of replacing the list
            eventMetadata.getImageUrls().add(imageUrl);

            eventsRepository.save(eventMetadata);
            return true;
        }
        return false;
    }

    // Link a user to an Event (when they join and/or create an Event)
    public boolean linkUserToEvent(String eventId, String userId) {
        Optional<EventMetadata> optionalEventMetadata = eventsRepository.findById(eventId);
        if (optionalEventMetadata.isPresent()) {
            EventMetadata eventMetadata = optionalEventMetadata.get();

            // Append the userId only if it's not already in the list
            if (!eventMetadata.getGuestsUserIds().contains(userId)) {
                eventMetadata.getGuestsUserIds().add(userId);
            }

            // Metadata to be sent to the Users Service via RabbitMQ(uses DTO to avoid exposing schemas)
            UserJoinedDTO DTOmetadata = new UserJoinedDTO(eventId, userId);
            DTOmetadata.setUserId(userId);
            DTOmetadata.setEventId(eventId);

            eventsRepository.save(eventMetadata);

            // Now send RabbitMQ message to Producer
            eventsRabbitMQProducer.sendUserJoinedEvent(eventId, userId);
            return true;
        }
        return false;
    }

    // Delete an event

    // End an event
}
