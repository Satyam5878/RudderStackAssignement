package com.rudderstack.trackingplan.controller;

import com.rudderstack.trackingplan.api.model.TrackingPlan;
import com.rudderstack.trackingplan.controller.model.TrackingPlanResponse;
import com.rudderstack.trackingplan.controller.model.TrackingPlanUpsertRequest;
import com.rudderstack.trackingplan.domain.service.TrackingPlanService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(
    path = "v1/tracking-plan",
    produces = {MediaType.APPLICATION_JSON_VALUE }
)
public class TrackingPlanRoutingController {
    private final TrackingPlanService trackingPlanService;

    public TrackingPlanRoutingController(
        TrackingPlanService trackingPlanService
    ) {
        this.trackingPlanService = trackingPlanService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test API is up!!";
    }

    //@Get
    @GetMapping("/{tracking-plan-name}")
    public TrackingPlanResponse getTrackingPlanByName(
        @PathParam("tracking-plan-name") String trackingPlanName
    ) {
        TrackingPlan trackingPlan = trackingPlanService.getTrackingPlanByName(trackingPlanName);
        if (trackingPlan == null) {
            return null;
        }
        // todo add logic fpr pulling events of tracking plan
        return TrackingPlanResponse.from(trackingPlan);
    }

    @PatchMapping
    public void upsertTrackingPlanByName(
        @RequestBody TrackingPlanUpsertRequest trackingPlanUpsertRequest
    ) {
        // do the validation for the tracking plan here
        trackingPlanService.upsertTrackingPlanByName(trackingPlanUpsertRequest.toTrackingPlanCreationPayload());
    }

    @DeleteMapping("/{tracking-plan-name}")
    public void deleteTrackingPlanByName(
        @PathParam("tracking-plan-name")  String trackingPlanName
    ) {
        // do the validation for the tracking plan here
        trackingPlanService.deleteTrackingPlanByName(trackingPlanName);
    }

    @GetMapping("/print-data")
    public List<TrackingPlan> printAndGetTrackingPlanData() {
        return trackingPlanService.printAndGetTrackingPlan();
    }

}
