package com.example.Events.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import java.util.Collections;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;

@Service
public class EventsService {
    
    @Autowired
    private EventsRepository eventsRepository;

    // Create a new event
    public EventMetadata createEvent(String name, String createdBy) {
        // Generate a unique event ID (UUID)
        String eventId = UUID.randomUUID().toString();

        // Save Event Metadata in MongoDB
        EventMetadata metadata = new EventMetadata();
        metadata.setName(name);
        metadata.setCreatedAt(LocalDateTime.now());
        metadata.setCreatedBy("testEventCreator");

        return eventsRepository.save(metadata);
    }

    // Validate if event exists
    public boolean isEventMetadataValid(String eventId) {
        return eventsRepository.getEventById(eventId).isPresent();
    }

    // Link media to an event
    public boolean linkImageToEvent(String eventId, String imageId) {
        Optional<EventMetadata> optionalEventMetadata = eventsRepository.findById(eventId);
        if (optionalEventMetadata.isPresent()) {
            EventMetadata eventMetadata = optionalEventMetadata.get();
            eventMetadata.setImageIds(Collections.singletonList(imageId));
            eventsRepository.save(eventMetadata);
            return true;
        }
        return false;
    }

    // Delete an event

    // End an event
}
