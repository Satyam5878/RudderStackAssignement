package com.rudderstack.trackingplan.domain.repository.impl;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.api.model.EventCreationPayload;
import com.rudderstack.trackingplan.domain.repository.EventRepository;
import com.rudderstack.trackingplan.domain.repository.model.EventDbDocument;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
public class EventRepositoryMongoImpl implements EventRepository {

    private MongoOperations mongoOperations;
    private Clock clock;

    private final String NAME_KEY = "nameKey";
    private final String ID = "id";

    public EventRepositoryMongoImpl(
        MongoOperations mongoOperations,
        Clock clock
    ) {
        this.mongoOperations = mongoOperations;
        this.clock = clock;
    }

    @Override
    public List<Event> findAllByNames(List<String> names) {
        return findAllDbDocByNames(names)
            .stream()
            .map(EventDbDocument::toEvent).collect(Collectors.toList());
    }

    @Override
    public List<Event> upsertAllByName(List<EventCreationPayload> eventCreationPayloads) {
        List<String> eventNameKeys = eventCreationPayloads.stream()
            .map(EventCreationPayload::getName)
            .collect(Collectors.toList());
            //.toList();

        List<EventDbDocument> existingDbDocuments = findAllDbDocByNames(eventNameKeys);

        Map<String, EventDbDocument> existingDbDocumentMap = existingDbDocuments.stream()
            .collect(Collectors.toMap(EventDbDocument::getNameKey, eventDbDocument -> eventDbDocument));

        Map<Boolean, List<EventCreationPayload>> partitionedEventCreationPayloads = eventCreationPayloads.stream()
            .collect(
                Collectors.partitioningBy(
                    eventCreationPayload -> {
                        String eventNameKey = EventDbDocument.getEventNameKey(eventCreationPayload.getName());
                        return existingDbDocumentMap.containsKey(eventNameKey);
                    }
                )
            );


        List<EventCreationPayload> eventsToInsert = partitionedEventCreationPayloads.get(false);
        List<EventCreationPayload> eventsToUpdate = partitionedEventCreationPayloads.get(true);

        List<EventDbDocument> touchedEventDocuments = new ArrayList<>(eventCreationPayloads.size());

        if (!eventsToInsert.isEmpty()) {
            touchedEventDocuments
                .addAll(
                    insert(
                        eventsToInsert.stream()
                            .map(EventDbDocument::from)
                            .collect(Collectors.toList())
                    )
                );
        }
        if (!eventsToUpdate.isEmpty()) {
            touchedEventDocuments.addAll(
                update(
                    eventsToUpdate.stream()
                        .map(eventCreationPayload ->
                            this.getUpdatedEventDbDocumentFromCreationPayload(
                                existingDbDocumentMap.get(EventDbDocument.getEventNameKey(eventCreationPayload.getName())),
                                eventCreationPayload)
                        )
                        .collect(Collectors.toList())
                )
            );
        }
        return touchedEventDocuments.stream().map(EventDbDocument::toEvent).collect(Collectors.toList());
    }

    @Override
    public List<Event> deleteAllByName(List<String> names) {
        return this.mongoOperations
            .findAllAndRemove(
                getEventsByNameQuery(names),
                EventDbDocument.class
            ).stream()
            .map(EventDbDocument::toEvent)
            .collect(Collectors.toList());
    }

    private List<EventDbDocument> findAllDbDocByNames(List<String> names) {
        return this.mongoOperations.find(getEventsByNameQuery(names), EventDbDocument.class);
    }

    private List<EventDbDocument> insert(List<EventDbDocument> events) {
        return this.mongoOperations
            .insertAll(
                events
            ).stream().collect(Collectors.toList());
        //.toList();
    }

    private List<EventDbDocument> update(List<EventDbDocument> events) {
         return events.stream().map(
                 (event) -> this.mongoOperations
                     .findAndReplace(
                         new Query(Criteria.where(ID).is(event.getId())),
                         event,
                         new FindAndReplaceOptions().upsert().returnNew()
                     )
             )
             .collect(Collectors.toList());
    }

    private Query getEventsByNameQuery(List<String> names) {
        List<String> nameKeys = names.stream().map(
            name -> {
                Objects.requireNonNull(name);
                return EventDbDocument.getEventNameKey(name);
            }
        ).collect(Collectors.toList());
            //.toList();
        return new Query(Criteria.where(NAME_KEY).in(nameKeys));
    }

    private EventDbDocument getUpdatedEventDbDocumentFromCreationPayload(
        EventDbDocument existingDbDocument,
        EventCreationPayload eventCreationPayload
    ) {
        return new EventDbDocument(
            existingDbDocument.getId(), // to not the existing id
            eventCreationPayload.getName(),
            eventCreationPayload.getDescription(),
            eventCreationPayload.getRules()
        );
    }
}
