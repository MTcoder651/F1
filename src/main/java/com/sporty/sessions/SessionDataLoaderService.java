package com.sporty.sessions;

import com.sporty.bet.OddsService;
import com.sporty.driver.DriverMarket;
import com.sporty.externalData.DriverClientInterface;
import com.sporty.externalData.SessionClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(SessionDataLoaderService.class);
    private final SessionRepository sessionRepository;
    private final DriverClientInterface driverClient;
    private final SessionClientInterface sessionClient;
    private final OddsService oddsService;

    public SessionDataLoaderService(SessionRepository sessionRepository, DriverClientInterface driverClient, SessionClientInterface sessionClient, OddsService oddsService) {
        this.sessionRepository = sessionRepository;
        this.driverClient = driverClient;
        this.sessionClient = sessionClient;
        this.oddsService = oddsService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadSessionOnStartup() {
        loadSessionsAndSaveToDB();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void loadSessionEveryday() {
        loadSessionsAndSaveToDB();
    }

    private void loadSessionsAndSaveToDB() {
        logger.info("Loading Sessions on startup");
        List<Session> sessions = sessionClient.fetchAllSessions();
        List<DriverMarket> driverMarkets = driverClient.fetchAllDrivers();
        logger.info("Linking Drivers to Sessions");
        for (Session session : sessions) {
            List<DriverMarket> driverInThisSession = driverMarkets
                    .stream()
                    .filter(driverMarket -> session.getSessionId().equals(driverMarket.getSessionId()))
                    .map(driverMarket -> {
                        driverMarket.setOdds(oddsService.calculateOdds(driverMarket));
                        return driverMarket;
                    })
                    .toList();
            session.setDriverMarkets(driverInThisSession);
            sessionRepository.save(session);
        }
        logger.info("Saved all Sessions to Database {}", sessions.size());
    }


}
