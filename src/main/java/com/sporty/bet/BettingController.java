package com.sporty.bet;

import com.sporty.event.EventService;
import com.sporty.sessions.Session;
import com.sporty.sessions.SessionService;
import com.sporty.sessions.SessionsInterface;
import com.sporty.user.User;
import com.sporty.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/f1/bet")
@Tag(name = "Bets", description = "Bet placement operations")
public class BettingController {

    private final UserService userService;
    private final BettingService bettingService;
    private final SessionsInterface sessionsInterface;
    private static final Logger logger = LoggerFactory.getLogger(BettingController.class);

    public BettingController(UserService userService, BettingService bettingService, SessionsInterface sessionsInterface) {
        this.userService = userService;
        this.bettingService = bettingService;
        this.sessionsInterface = sessionsInterface;
    }

    @PostMapping
    @Operation(
            summary = "Place a Bet",
            description = "Places a bet for a user on a specific driver within a session after validating balance and session membership",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user placing the bet", required = true, schema = @Schema(type = "integer", example = "42")),
                    @Parameter(name = "sessionId", description = "ID of the session to place the bet in", required = true, schema = @Schema(type = "integer", example = "1001")),
                    @Parameter(name = "driverId", description = "ID of the driver being bet on", required = true, schema = @Schema(type = "integer", example = "7")),
                    @Parameter(name = "amount", description = "Amount to bet", required = true, schema = @Schema(type = "number", format = "decimal", example = "25.50"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bet placed successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "You don't have enough balance")
                    )),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            }
    )
    public ResponseEntity<String> placeBet(Integer userId, Integer sessionId, Integer driverId, BigDecimal amount) {
        logger.info("User {} placing bet for session {}, driver {} for {} euro", userId, sessionId, driverId, amount);
        Optional<User> optionalUser = userService.findUserById(userId);
        Optional<Session> optionalSession = sessionsInterface.fetchSession(sessionId);
        if (optionalSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // TODO To be replaced by proper user registration
        User user = optionalUser.orElse(new User(userId));
        Session session = optionalSession.get();
        if (user.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.badRequest().body("You don't have enough balance");
        }
        if (session.getDriverMarkets().stream().noneMatch(d -> d.getDriverId().equals(driverId))) {
            return ResponseEntity.badRequest().body("Driver is not in session");
        }
        bettingService.placeBet(user, new Bet(sessionId, driverId, userId, amount));
        return ResponseEntity.ok().build();
    }
}
