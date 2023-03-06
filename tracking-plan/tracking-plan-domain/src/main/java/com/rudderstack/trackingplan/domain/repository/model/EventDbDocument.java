package com.rudderstack.trackingplan.domain.repository.model;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;
import org.springframework.data.annotation.Id;

import java.util.Map;

@org.springframework.data.mongodb.core.mapping.Document("event")
public class EventDbDocument {
    @Id
    private String id;
    private String name;
    private String nameKey;
    private String description;
    private Map<String, Object> rules;

    public EventDbDocument(
        String id,
        String name,
        String description,
        Map<String, Object> rules
    ) {
        this.id = id;
        this.name = name;
        this.nameKey = getEventNameKey(name);
        this.description = description;
        this.rules = rules;
    }

    public static String getEventNameKey(String name) {
        return name.replace(" ", "-").toLowerCase();
    }

    public String getId() {
        return id;
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

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    // -------to and from-----------------
    public static EventDbDocument from(
        EventCreationPayload eventCreationPayload
    ) {
        return new EventDbDocument(
            null, // will be set by the mongo
            eventCreationPayload.getName(),
            eventCreationPayload.getDescription(),
            eventCreationPayload.getRules()
        );
    }

    public Event toEvent() {
        return new Event(
            this.name,
            this.description,
            this.rules
        );
    }
}
