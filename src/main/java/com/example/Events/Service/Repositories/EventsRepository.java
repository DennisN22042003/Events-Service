package com.example.Events.Service.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

import com.example.Events.Service.Models.EventMetadata;

@Repository
public interface EventsRepository extends MongoRepository<EventMetadata, String> {
    // Future work: Implement methods to query the database for Event Metadata
    // Future work: Implement methods to update Event Metadata(images, users, etc.)
    // Future work: Implement methods to delete Event Metadata

    @Query("{ 'eventId' : ?0 }")
    long deleteByEventId(String eventId);

    @Query("{ 'eventId' : ?0 }")
    Optional<EventMetadata> findByEventId(String eventId);
}
