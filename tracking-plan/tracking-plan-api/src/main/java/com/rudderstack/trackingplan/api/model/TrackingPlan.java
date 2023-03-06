package com.rudderstack.trackingplan.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingPlan {
    @JsonProperty("display_name")
    private String displayName;
    //private TrackingPlanRule rules;

    public TrackingPlan(String displayName/*, TrackingPlanRule rules*/) {
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
