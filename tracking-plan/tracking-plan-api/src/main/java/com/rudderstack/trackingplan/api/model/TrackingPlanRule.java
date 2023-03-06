package com.rudderstack.trackingplan.api.model;

import java.util.List;

public class TrackingPlanRule {
    private List<Event> events;

    public TrackingPlanRule(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
