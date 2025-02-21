package com.example.Events.Service.DTO;

import java.io.Serializable;

public class UserJoinedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String eventId;

    public UserJoinedDTO(String userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
