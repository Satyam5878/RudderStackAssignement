package com.rudderstack.trackingplan.domain.service.impl;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;
import com.rudderstack.trackingplan.domain.repository.EventRepository;
import com.rudderstack.trackingplan.domain.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEventByNames(List<String> names) {
        return eventRepository.findAllByNames(names);
    }

    @Override
    public List<Event> upsertAllEventByNames(List<EventCreationPayload> eventCreationPayloads) {
        return eventRepository.upsertAllByName(eventCreationPayloads);
    }

    @Override
    public List<Event> deleteAllEventByNames(List<String> names) {
        return eventRepository.deleteAllByName(names);
    }
}
