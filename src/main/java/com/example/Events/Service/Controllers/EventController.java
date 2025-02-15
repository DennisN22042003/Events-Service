package com.example.Events.Service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Events.Service.Services.EventsService;
import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventsRepository eventsRepository;
    
    @Autowired
    private EventsService eventService;

    // Create an event
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createEvent(@RequestParam String name, @RequestParam String createdBy) {
        EventMetadata eventMetadata = eventService.createEvent(name, createdBy);

        // Construct a response with eventId (to allow the media service know which event an image belongs to)
        Map<String, String> response = new HashMap<>();
        response.put("eventId", eventMetadata.getId());
        response.put("message", "Event created successfully");

        return ResponseEntity.ok(response);
    }

    // Validate if event exists
    @GetMapping("/validate/{eventId}")
    public ResponseEntity<Boolean> validateEventMetadata(@PathVariable String eventId) {
        boolean exists = eventService.isEventMetadataValid(eventId);
        return ResponseEntity.ok(exists);
    }

    // Fetch an Event
    @GetMapping("/{eventId}")
    public ResponseEntity<EventMetadata> getEventById(@PathVariable String eventId) {
        return eventsRepository.getEventById(eventId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Future work: Delete an Event

    // Future work: Update an Event (specifically event information)
}