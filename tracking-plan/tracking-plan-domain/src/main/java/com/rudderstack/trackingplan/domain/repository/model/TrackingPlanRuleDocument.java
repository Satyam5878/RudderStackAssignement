package com.rudderstack.trackingplan.domain.repository.model;

import com.rudderstack.trackingplan.api.model.TrackingPlanRule;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackingPlanRuleDocument {
    @DBRef
    private List<EventDbDocument> events;

    public TrackingPlanRuleDocument(List<EventDbDocument> events) {
        this.events = events;
    }

    public List<EventDbDocument> getEvents() {
        return events;
    }

    public void setEvents(List<EventDbDocument> events) {
        this.events = events;
    }

    public TrackingPlanRule toTrackingPlanRule() {
        return new TrackingPlanRule(
            events.stream().map(EventDbDocument::toEvent).collect(Collectors.toList())
        );
    }
}
