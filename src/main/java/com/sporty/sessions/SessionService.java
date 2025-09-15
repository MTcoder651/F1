package com.sporty.sessions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService implements SessionsInterface {

    private final SessionRepository sessionRepository;
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> fetchSessions(String sessionType, Integer year, String countryName) {
        logger.info("Fetching sessions with sessionType {} and year {} and countryName {}", sessionType, year, countryName);
        return this.findAllBySessionTypeAndYearAndCountryName(sessionType, year, countryName);
    }

    @Override
    public Optional<Session> fetchSession(Integer sessionId) {
        return sessionRepository.findById(sessionId);
    }

    /// I am using the find by example way of getting data from the repository as it automatically ignores fields that
    /// are null in the probe object.
    public List<Session> findAllBySessionTypeAndYearAndCountryName(String sessionType, Integer year, String countryName) {
        Session probe = new Session();
        probe.setSessionType(sessionType);
        probe.setYear(year);
        probe.setCountryName(countryName);

        Example<Session> example = Example.of(probe);
        return sessionRepository.findAll(example);
    }


}

