package com.rudderstack.trackingplan.domain.service.impl;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;
import com.rudderstack.trackingplan.domain.repository.TrackingPlanRepository;
import com.rudderstack.trackingplan.domain.service.TrackingPlanService;

import java.util.List;

public class TrackingPlanServiceImpl implements TrackingPlanService {
    private TrackingPlanRepository trackingPlanRepository;

    public TrackingPlanServiceImpl(
        TrackingPlanRepository trackingPlanRepository
    ) {
        this.trackingPlanRepository = trackingPlanRepository;
    }

    @Override
    public TrackingPlan getTrackingPlanByName(
        String trackingPlanName
    ) {
        return trackingPlanRepository.findTrackingPlanByName(trackingPlanName);
    }

    @Override
    public void upsertTrackingPlanByName(TrackingPlanCreationPayload trackingPlanCreationPayload) {
        trackingPlanRepository.upsertTrackingPlanByName(trackingPlanCreationPayload);
    }

    @Override
    public void deleteTrackingPlanByName(String trackingPlanName) {
        trackingPlanRepository.deleteTrackingPlanByName(trackingPlanName);
    }

    @Override
    public List<TrackingPlan> printAndGetTrackingPlan() {
        return trackingPlanRepository.printAndGetTrackingPlanData();
    }
}
