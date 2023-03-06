package com.rudderstack.trackingplan.api.model;

import java.util.Map;

public class Event {
    private String name;
    private String description;
    private Map<String, Object> rules; // to change to json
    // may add other field like audit;

    public Event(String name, String description, Map<String, Object> rules) {
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
