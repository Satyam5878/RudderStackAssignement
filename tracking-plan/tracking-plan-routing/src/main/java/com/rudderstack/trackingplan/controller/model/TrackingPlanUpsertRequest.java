package com.rudderstack.trackingplan.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingPlanUpsertRequest {
    @JsonProperty("tracking_plan")
    private TrackingPlan trackingPlan; // though ideally TrackingPlan should not be used here. it should be independent

    public TrackingPlan getTrackingPlan() {
        return trackingPlan;
    }

    public void setTrackingPlan(TrackingPlan trackingPlan) {
        this.trackingPlan = trackingPlan;
    }

    public TrackingPlanCreationPayload toTrackingPlanCreationPayload() {
        return new TrackingPlanCreationPayload(trackingPlan.getDisplayName());
    }
}
