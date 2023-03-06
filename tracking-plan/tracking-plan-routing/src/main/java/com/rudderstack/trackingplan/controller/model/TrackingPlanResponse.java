package com.rudderstack.trackingplan.controller.model;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanRule;

public class TrackingPlanResponse {
    private String displayName;
   // private TrackingPlanRule rules;

    public static TrackingPlanResponse from(
        TrackingPlan trackingPlan
    ) {
        return new TrackingPlanResponse(
            trackingPlan.getDisplayName()/*,
            trackingPlan.getRules()*/
        );
    }

    public TrackingPlanResponse(String displayName/*, TrackingPlanRule rules*/) {
        this.displayName = displayName;
       // this.rules = rules;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

//    public TrackingPlanRule getRules() {
//        return rules;
//    }
//
//    public void setRules(TrackingPlanRule rules) {
//        this.rules = rules;
//    }
}
