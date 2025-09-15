package com.sporty.sessions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/f1/sessions")
@Tag(name = "Sessions", description = "Session retrieval operations")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    private final SessionsInterface sessionsInterface;

    public SessionController(SessionsInterface sessionsInterface) {
        this.sessionsInterface = sessionsInterface;
    }

    @GetMapping
    @Operation(
            summary = "Get Sessions",
            description = "Retrieves a list of sessions filtered optionally by type, year, and country",
            parameters = {
                    @Parameter(
                            name = "sessionType",
                            description = "Filter by session type",
                            required = false,
                            schema = @Schema(type = "string", example = "QUALIFYING")
                    ),
                    @Parameter(
                            name = "year",
                            description = "Filter by year",
                            required = false,
                            schema = @Schema(type = "integer", example = "2025")
                    ),
                    @Parameter(
                            name = "countryName",
                            description = "Filter by country name",
                            required = false,
                            schema = @Schema(type = "string", example = "Italy")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of matching sessions",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Session.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No sessions found for the given filters"
                    )
            }
    )
    public ResponseEntity<List<Session>> getSessions(
            @RequestParam(required = false) String sessionType,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String countryName) {
        logger.info("getSessions called with parameters sessionType={}, year={}, countryName={}", sessionType, year, countryName);
        List<Session> sessions = sessionsInterface.fetchSessions(sessionType, year, countryName);

        if (sessions.isEmpty()) {
            logger.info("No session found with parameters sessionType={}, year={}, countryName={}", sessionType, year, countryName);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(sessions);
    }
}
