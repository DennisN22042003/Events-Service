package com.example.Events.Service.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Document(collection = "event_metadata")
public class EventMetadata {
    @Id
    private String id; // Ensure this is a String, not an ObjectId
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<String> guestsUserIds = new ArrayList<>(); // List to hold event guests
    private List<String> imageUrls = new ArrayList<>(); // List to hold image URLs

    // Constructors
    public EventMetadata() {
        this.createdAt = LocalDateTime.now();
    }

    public EventMetadata(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getGuestsUserIds() {
        return guestsUserIds;
    }
    public void setGuestUserIds(List<String> guestsUserIds) {
        this.guestsUserIds = guestsUserIds;
    }
}
