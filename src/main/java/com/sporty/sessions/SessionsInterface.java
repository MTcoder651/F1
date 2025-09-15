package com.sporty.sessions;

import java.util.List;
import java.util.Optional;

public interface SessionsInterface {

    List<Session> fetchSessions(String sessionType, Integer year, String countryName);

    Optional<Session> fetchSession(Integer sessionId);

}
