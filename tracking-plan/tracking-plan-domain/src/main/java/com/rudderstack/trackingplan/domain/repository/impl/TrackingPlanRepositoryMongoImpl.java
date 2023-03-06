package com.rudderstack.trackingplan.domain.repository.impl;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;
import com.rudderstack.trackingplan.domain.repository.TrackingPlanRepository;
import com.rudderstack.trackingplan.domain.repository.model.TrackingPlanDbDocument;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrackingPlanRepositoryMongoImpl implements TrackingPlanRepository {
    private MongoOperations mongoOperations;
    private Clock clock;

    private final String NAME_KEY = "nameKey";

    public TrackingPlanRepositoryMongoImpl(
        MongoOperations mongoOperations,
        Clock clock
    ) {
        this.mongoOperations = mongoOperations;
        this.clock = clock;
    }

    @Override
    public TrackingPlan findTrackingPlanByName(String trackingPlanName) {
        TrackingPlanDbDocument dbDocument = findTrackingPlanDbDocByName(trackingPlanName);
        return dbDocument == null ? null : dbDocument.toTrackingPlan();
    }

    @Override
    public TrackingPlan upsertTrackingPlanByName(TrackingPlanCreationPayload trackingPlanCreationPayload) {
        TrackingPlanDbDocument existingDbDoc = findTrackingPlanDbDocByName(trackingPlanCreationPayload.getDisplayName());
        if (existingDbDoc == null) {
            return insert(TrackingPlanDbDocument.from(trackingPlanCreationPayload)).toTrackingPlan();
        } else {
            return update(getUpdatedTrackingPlanDbDoc(existingDbDoc, trackingPlanCreationPayload)).toTrackingPlan();
        }
    }

    @Override
    public TrackingPlan deleteTrackingPlanByName(String trackingPlanName) {
        TrackingPlanDbDocument trackingPlanDbDocument = this.mongoOperations
            .findAndRemove(
                getTrackingPlanByNameQuery(trackingPlanName),
                TrackingPlanDbDocument.class
            );
        if (trackingPlanDbDocument == null) {
            return null;
        }
        return trackingPlanDbDocument.toTrackingPlan();
    }

    @Override
    public List<TrackingPlan> printAndGetTrackingPlanData() {
        List<TrackingPlanDbDocument> trackingPlanDbDocuments = this.mongoOperations
            .find(
                new Query(),
                TrackingPlanDbDocument.class
            );
        trackingPlanDbDocuments.forEach(System.out::println);
        return trackingPlanDbDocuments.stream().map(TrackingPlanDbDocument::toTrackingPlan).collect(Collectors.toList());
    }

    private TrackingPlanDbDocument findTrackingPlanDbDocByName(String trackingName) {
        return this.mongoOperations.findOne(getTrackingPlanByNameQuery(trackingName), TrackingPlanDbDocument.class);
    }

    private TrackingPlanDbDocument insert(TrackingPlanDbDocument trackingPlanDbDocument) {
        return this.mongoOperations.insert(trackingPlanDbDocument);
    }

    private TrackingPlanDbDocument update(TrackingPlanDbDocument trackingPlanDbDocument) {
        return this.mongoOperations.findAndReplace(
            getTrackingPlanByNameQuery(trackingPlanDbDocument.getName()),
            trackingPlanDbDocument,
            new FindAndReplaceOptions().upsert().returnNew()
        );
    }

    private Query getTrackingPlanByNameQuery(String trackingPlanName) {
        Objects.requireNonNull(trackingPlanName, "Tracking plan can not be empty");
        return new Query(
            Criteria.where(NAME_KEY).is(TrackingPlanDbDocument.getEventNameKey(trackingPlanName))
        );
    }

    private TrackingPlanDbDocument getUpdatedTrackingPlanDbDoc(
        TrackingPlanDbDocument existingDbDocument,
        TrackingPlanCreationPayload trackingPlanCreationPayload
    ) {
        return new TrackingPlanDbDocument(
            existingDbDocument.getId(),
            trackingPlanCreationPayload.getDisplayName()
        );
    }
}
