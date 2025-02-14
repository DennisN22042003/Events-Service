package com.example.Events.Service.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Document(collection = "event_metadata")
public class EventMetadata {
    @Id
    private String id;
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<String> imageIds = new ArrayList<>();

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

    public List<String> getImgageIds() {
        return imageIds;
    }
    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }
}
