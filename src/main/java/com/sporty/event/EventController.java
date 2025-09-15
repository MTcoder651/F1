package com.sporty.event;

import com.sporty.sessions.Session;
import com.sporty.sessions.SessionController;
import com.sporty.sessions.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/f1/admin")
@Tag(name = "Events", description = "Event publishing operations")
public class EventController {

    private final SessionService sessionService;
    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    public EventController(SessionService sessionService, EventService eventService) {
        this.sessionService = sessionService;
        this.eventService = eventService;
    }

    @PostMapping
    @Operation(
            summary = "Publish Event",
            description = "Publishes an event in the system of a Formula 1 race after validating the session exists and the driver is part of the session",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = Event.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event published successfully",
                            content = @Content(schema = @Schema(type = "string"))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request - Driver is not in session",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "string", example = "Driver is not in session")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Session not found"
                    )
            }
    )
    public ResponseEntity<String> publishEvent(@RequestBody Event event){
        logger.info("Publishing Event {}", event);
        Optional<Session> optionalSession = sessionService.fetchSession(event.getSessionId());
        if (optionalSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Session session = optionalSession.get();
        if (session.getDriverMarkets().stream().noneMatch(d -> d.getDriverId().equals(event.getDriverId()))) {
            return ResponseEntity.badRequest().body("Driver is not in session");
        }
        eventService.saveEvent(event);
        return ResponseEntity.ok().build();
    }

}
