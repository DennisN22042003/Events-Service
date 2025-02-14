package com.example.Events.Service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.Collections;
import org.springframework.stereotype.Service;

import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;

@Service
public class EventsService {
    
    @Autowired
    private EventsRepository eventRepository;

    // Create a new event
    public EventMetadata createEvent(String name, String createdBy) {
        EventMetadata eventMetadata = new EventMetadata(name, createdBy);
        return eventRepository.save(eventMetadata);
    }

    // Validate if event exists
    public boolean isEventMetadataValid(String eventId) {
        return eventRepository.findByEventId(eventId).isPresent();
    }

    // Link media to an event
    public boolean linkImageToEvent(String eventId, String imageId) {
        Optional<EventMetadata> optionalEventMetadata = eventRepository.findById(eventId);
        if (optionalEventMetadata.isPresent()) {
            EventMetadata eventMetadata = optionalEventMetadata.get();
            eventMetadata.setImageIds(Collections.singletonList(imageId));
            eventRepository.save(eventMetadata);
            return true;
        }
        return false;
    }

    // Delete an event

    // End an event
}
