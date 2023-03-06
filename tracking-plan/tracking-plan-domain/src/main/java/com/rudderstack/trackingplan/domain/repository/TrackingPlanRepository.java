package com.rudderstack.trackingplan.domain.repository;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;
import com.rudderstack.trackingplan.domain.repository.model.TrackingPlanDbDocument;

import java.util.List;

public interface TrackingPlanRepository {
    TrackingPlan findTrackingPlanByName(String trackingPlanName);
    TrackingPlan upsertTrackingPlanByName(TrackingPlanCreationPayload trackingPlanCreationPayload);
    TrackingPlan deleteTrackingPlanByName(String trackingPlanName);
    List<TrackingPlan> printAndGetTrackingPlanData();
}
