package com.rudderstack.trackingplan.domain.service;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;

import java.util.List;

public interface EventService {

    List<Event> getAllEventByNames(List<String> names);

    List<Event> upsertAllEventByNames(List<EventCreationPayload> eventCreationPayloads);

    List<Event> deleteAllEventByNames(List<String> names);
}
