package com.rudderstack.trackingplan.controller;

import com.rudderstack.trackingplan.api.model.Event;
import com.rudderstack.trackingplan.controller.model.EventResponse;
import com.rudderstack.trackingplan.controller.model.EventUpsertRequest;
import com.rudderstack.trackingplan.domain.service.EventService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
    path = "v1/event",
    produces = {MediaType.APPLICATION_JSON_VALUE }
)
public class EventRoutingController {

    private final EventService eventService;

    public EventRoutingController(
        EventService eventService
    ) {
        this.eventService = eventService;
    }
//    @GetMapping
//    public EventResponse getAllEvents() {
//        List<Event> events = eventService.getAllEventByNames(List.of(eventName));
//        if(events == null) {
//            return null;
//        }
//        return EventResponse.from(events.get(0));
//    }

    @GetMapping("/{event-name}")
    public EventResponse getEventByName(
        @PathParam("event-name") String eventName
    ) {
        List<Event> events = eventService.getAllEventByNames(List.of(eventName));
        if(events == null) {
            return null;
        }
        return EventResponse.from(events.get(0));
    }

    @PatchMapping
    public void upsertEventByName(@RequestBody EventUpsertRequest eventUpsertRequest) {
        eventService.upsertAllEventByNames(List.of(eventUpsertRequest.toEventCreationPayload()));
    }

    @DeleteMapping("/{event-name}")
    public void deleteEventByName(@PathParam("event-name") String eventName) {
        eventService.deleteAllEventByNames(List.of(eventName));
    }

}
