package com.rudderstack.trackingplan.domain.repository.model;


import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.api.model.TrackingPlanCreationPayload;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document("trackingPlan")
public class TrackingPlanDbDocument {
    @Id
    private String id;
    private String name;
    private String nameKey;

    // Constructor, Getter and Setter
    public TrackingPlanDbDocument(
        String id,
        String name//,
        // TrackingPlanRuleDocument rules
    ) {
        this.id = id;
        this.name = name;
        //this.rules = rules;
        this.nameKey = getEventNameKey(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }
//private TrackingPlanRuleDocument rules;
    //private
    // TODO: Modify the attributes


    // to and from
    public static TrackingPlanDbDocument from(
        TrackingPlanCreationPayload trackingPlanCreationPayload
    ) {
        return new TrackingPlanDbDocument(
            null,
            trackingPlanCreationPayload.getDisplayName()
        );
    }

    public TrackingPlan toTrackingPlan() {
        return new TrackingPlan(
            this.name/*,
            this.rules.toTrackingPlanRule()*/
        );
    }

    public static String getEventNameKey(String displayName) {
        return displayName.replace(" ", "-").toLowerCase();
    }

    @Override
    public String toString() {
        return "TrackingPlanDbDocument{" +
            "id='" + id + '\'' +
            ", displayName='" + name + '\'' +
           // ", rules=" + rules +
            '}';
    }
}
