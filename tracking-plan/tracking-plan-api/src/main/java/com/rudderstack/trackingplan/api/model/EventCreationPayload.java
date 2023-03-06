package com.rudderstack.trackingplan.api.model;

import java.util.Map;

public class EventCreationPayload {
    private String name;
    private String description;
    private Map<String, Object> rules; // to change to json

    public EventCreationPayload(String name, String description, Map<String, Object> rules) {
        this.name = name;
        this.description = description;
        this.rules = rules;
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
