package com.example.Events.Service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.Events.Service.Services.EventsService;
import com.example.Events.Service.Models.EventMetadata;


@RestController
@RequestMapping("/api/events")
public class EventController {
    
    @Autowired
    private EventsService eventService;

    // Create an event
    @PostMapping("/create")
    public ResponseEntity<EventMetadata> createEvent(@RequestParam String name, @RequestParam String createdBy) {
        EventMetadata eventMetadata = eventService.createEvent(name, createdBy);
        return ResponseEntity.ok(eventMetadata);
    }

    // Validate if event exists
    @GetMapping("/validate/{eventId}")
    public ResponseEntity<Boolean> validateEventMetadata(@PathVariable String eventId) {
        boolean exists = eventService.isEventMetadataValid(eventId);
        return ResponseEntity.ok(exists);
    }
}