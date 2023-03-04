package com.rudderstack.trackingplan.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "v1/tracking-plan",
    produces = {MediaType.APPLICATION_JSON_VALUE }
)
public class TrackingPlanRoutingController {

    @GetMapping("/test")
    public String test() {
        return "Test API is up!!";
    }

}
