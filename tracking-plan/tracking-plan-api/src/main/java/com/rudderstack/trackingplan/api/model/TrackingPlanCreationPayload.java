package com.rudderstack.trackingplan.api.model;

public class TrackingPlanCreationPayload {
    private String displayName;

    public TrackingPlanCreationPayload(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
