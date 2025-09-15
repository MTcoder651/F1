package com.sporty.event;

import com.sporty.bet.Bet;
import com.sporty.bet.BetStatus;
import com.sporty.bet.BettingService;
import com.sporty.driver.DriverMarket;
import com.sporty.sessions.Session;
import com.sporty.sessions.SessionService;
import com.sporty.user.User;
import com.sporty.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class EventService {

    private final EventRepository eventRepository;
    private final BettingService bettingService;
    private final UserService userService;
    private final SessionService sessionService;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, BettingService bettingService, UserService userService, SessionService sessionService) {
        this.eventRepository = eventRepository;
        this.bettingService = bettingService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public void saveEvent(Event event) {
        // TODO add transactionality
        List<Bet> betsBySession = bettingService.getBetsBySession(event.getSessionId());
        Session session = getSession(event);
        for (Bet bet : betsBySession) {
            if (bet.getDriverId().equals(event.getDriverId())) {
                logger.info("user {} has won bet {} on session {} and driver {}", bet.getUserId(), bet.getBetId(), event.getSessionId(), event.getDriverId());
                userWinsBet(event, bet, session);
            } else {
                logger.info("user {} has lost bet {} on session {} and driver {}", bet.getUserId(), bet.getBetId(), event.getSessionId(), event.getDriverId());
                userLosesBet(bet);
            }
        }
        eventRepository.save(event);
    }

    private void userLosesBet(Bet bet) {
        bet.setStatus(BetStatus.LOST);
        bettingService.updateBet(bet);
    }

    private void userWinsBet(Event event, Bet bet, Session session) {
        bet.setStatus(BetStatus.WON);
        User user = getUser(bet);
        DriverMarket driverMarket = getDriverMarket(event, session);
        Integer odds = driverMarket.getOdds();
        BigDecimal userWinnings = bet.getAmount().multiply(BigDecimal.valueOf(odds));
        user.setBalance(user.getBalance().add(userWinnings));
        userService.updateUser(user);
        bettingService.updateBet(bet);
    }

    private static DriverMarket getDriverMarket(Event event, Session session) {
        Optional<DriverMarket> driverMarketOptional = session
                .getDriverMarkets()
                .stream()
                .filter(driverMarket -> driverMarket.getDriverId().equals(event.getDriverId()))
                .findFirst();
        if (driverMarketOptional.isEmpty()) {
            throw new RuntimeException("Driver in bet not found");
        }
        return driverMarketOptional.get();
    }

    private User getUser(Bet bet) {
        Optional<User> optionalUser = userService.findUserById(bet.getUserId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User in bet not found");
        }
        return optionalUser.get();
    }

    private Session getSession(Event event) {
        Optional<Session> optionalSession = sessionService.fetchSession(event.getSessionId());
        if (optionalSession.isEmpty()) {
            throw new RuntimeException("Session not found");
        }
        return optionalSession.get();
    }
}
