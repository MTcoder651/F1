package com.sporty.event;

import java.util.UUID;

public class Event {

    private final String eventId;
    private final Integer sessionId;
    private final Integer driverId;

    public Event(Integer sessionId, Integer driverId) {
        this.eventId = UUID.randomUUID().toString();
        this.sessionId = sessionId;
        this.driverId = driverId;
    }

    public String getEventId() {
        return eventId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getDriverId() {
        return driverId;
    }
}
