package com.example.Events.Service.Services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import com.example.Events.Service.Models.EventMetadata;
import com.example.Events.Service.Repositories.EventsRepository;
import com.example.Events.Service.Config.RabbitMQConfig;
import com.example.Events.Service.DTO.ImageEventDTO;

@Service
public class ImageEventListener {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventsService eventsService;
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void receiveImageEvent(ImageEventDTO imageEventDTO) {
        // Log the received event metadata (immediately after deserialization)
        if (imageEventDTO == null) {
            System.out.println("❌ Deserialization failed: imageEventDTO is null");
        } else {
            System.out.println("📩 Received Image Event: " + imageEventDTO.getImageUrl() + " for Event: " + imageEventDTO.getEventId() + " for User: " + imageEventDTO.getUserId());
        }

        // Make sure that userId is a String (UUID)
        String userId = imageEventDTO.getUserId();

        // Make sure that eventId is a String (UUID)
        String eventId = imageEventDTO.getEventId();

        // Find event by ID in the database
        Optional<EventMetadata> optionalEvent = eventsRepository.findById(eventId);
        System.out.println("Looking for Event with ID: " + eventId);

        // Process if event is found
        if (optionalEvent.isPresent()) {
            EventMetadata eventMetadata = optionalEvent.get();
            List<String> existingImageUrls = eventMetadata.getImageUrls();

            boolean isFirstImage = existingImageUrls.isEmpty(); // Determine if it's first image being added

            // Ensure guestsUserIds list is initialized
            if (eventMetadata.getGuestsUserIds() == null) {
                eventMetadata.setGuestUserIds(new ArrayList<>());
            }
            // Add the new guestsUserId only if it's not already in the list
            if (!eventMetadata.getGuestsUserIds().contains(userId)) {
                eventMetadata.getGuestsUserIds().add(userId);
            }

            // Ensure imageUrls list is initialized
            if (eventMetadata.getImageUrls() == null) {
                eventMetadata.setImageUrls(new ArrayList<>());
            }

            // Add the new image URL
            eventMetadata.getImageUrls().add(imageEventDTO.getImageUrl());

            // Save updated event metadata with image URL(s)
            eventsRepository.save(eventMetadata);

            // Log whether it's the first image or not
            if (isFirstImage) {
                System.out.println("✅ First image URL added for event: " + eventMetadata.getId());
            } else {
                System.out.println("✅ Additional image URL(s) added for event: " + eventMetadata.getId());
            }
        } else {
            System.out.println("❌ Event not found: " + eventId);
        }
    }
}
