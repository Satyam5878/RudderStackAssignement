package com.rudderstack.trackingplan.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventUpsertRequest {
    private String name;
    private String description;
    private Map<String, Object> rules;

    public EventCreationPayload toEventCreationPayload() {
        return new EventCreationPayload(
            this.name,
            this.description,
            this.rules
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getRules() {
        return rules;
    }

    public void setRules(Map<String, Object> rules) {
        this.rules = rules;
    }
}
