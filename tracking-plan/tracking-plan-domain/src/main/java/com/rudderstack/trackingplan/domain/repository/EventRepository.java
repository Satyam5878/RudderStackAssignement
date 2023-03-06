package com.rudderstack.trackingplan.domain.repository;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

public interface EventRepository {

    List<Event> findAllByNames(List<String> names);

    List<Event> upsertAllByName(List<EventCreationPayload> eventCreationPayloads);

    List<Event> deleteAllByName(List<String> names);
}
