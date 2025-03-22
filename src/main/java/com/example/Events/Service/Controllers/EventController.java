package com.example.Events.Service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
        String eventId = eventMetadata.getId();

        // Generate a slug from event name (lowercase + hyphens, instead of spaces)
        String slug = name.toLowerCase().replace(" ", "-");
        String eventUrl = "http://localhost:8082/api/events/" + slug + "-" + eventId;
        
        // Construct a response with eventId and eventUrl
        Map<String, String> response = new HashMap<>();
        response.put("eventId", eventId);
        response.put("eventUrl", eventUrl);
        response.put("message", "Event created successfully");

        return ResponseEntity.ok(response);
    }

    // Validate if event exists
    @GetMapping("/validate/{eventId}")
    public ResponseEntity<Boolean> validateEventMetadata(@PathVariable String eventId) {
        boolean exists = eventService.isEventMetadataValid(eventId);
        return ResponseEntity.ok(exists);
    }

    // Fetch an Event by eventId
    @GetMapping("/{eventId}")
    public ResponseEntity<EventMetadata> getEventById(@PathVariable String eventId) {
        return eventsRepository.findById(eventId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Fetch an Event by eventUrl
    @GetMapping("/{slug}-{eventId}")
    public ResponseEntity<EventMetadata> getEventBySlug(@PathVariable String eventId) {
        return eventsRepository.findById(eventId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Join an Event(every other user apart from the Event Creator)
    @PostMapping("/{eventId}/join")
    public ResponseEntity<Map<String, String>> joinEvent(@PathVariable String eventId, @RequestParam String userId) {
        if (!eventService.isEventMetadataValid(eventId)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Cannot join Event, event not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        boolean joined = eventService.linkUserToEvent(eventId, userId);

        if (joined) {
            String nightOutName = eventService.getEventName(eventId);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "User: " + userId + " successfully joined event " + eventId);
            successResponse.put("nightOutName", nightOutName);
            return ResponseEntity.ok(successResponse);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to join event");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    } 

    // Future work: Delete an Event

    // Future work: Update an Event (specifically event information)
}