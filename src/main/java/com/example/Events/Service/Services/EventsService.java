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
        // Save Event Metadata in MongoDB
        EventMetadata metadata = new EventMetadata();
        metadata.setName(name);
        metadata.setCreatedAt(LocalDateTime.now());
        metadata.setCreatedBy("testEventCreator");

        // Save and return the event
        EventMetadata savedEventMetadata = eventsRepository.save(metadata);
        return savedEventMetadata;
    }

    // Validate if event exists
    public boolean isEventMetadataValid(String eventId) {
        return eventsRepository.findById(eventId).isPresent();
    }

    // Link media to an event
    public boolean linkImageToEvent(String eventId, String imageId) {
        Optional<EventMetadata> optionalEventMetadata = eventsRepository.findById(eventId);
        if (optionalEventMetadata.isPresent()) {
            EventMetadata eventMetadata = optionalEventMetadata.get();

            // Append the imageId of a new image instead of replacing the list
            if (eventMetadata.getImgageIds() == null) {
                eventMetadata.setImageIds(Collections.singletonList(imageId));
            } else {
                eventMetadata.getImgageIds().add(imageId);
            }

            eventsRepository.save(eventMetadata);
            return true;
        }
        return false;
    }

    // Delete an event

    // End an event
}
