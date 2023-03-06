package com.rudderstack.trackingplan.domain.service;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;

import java.util.List;

public interface TrackingPlanService {

     TrackingPlan getTrackingPlanByName(String trackingPlanName);

     void upsertTrackingPlanByName(TrackingPlanCreationPayload trackingPlanCreationPayload);

     void deleteTrackingPlanByName(String trackingPlan);

     List<TrackingPlan> printAndGetTrackingPlan();
}
