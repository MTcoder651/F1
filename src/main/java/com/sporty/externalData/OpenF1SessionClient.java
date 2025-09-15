package com.sporty.externalData;

import com.sporty.driver.DriverMarketDto;
import com.sporty.sessions.Session;
import com.sporty.sessions.SessionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OpenF1SessionClient implements SessionClientInterface {
    private final RestClient client;
    private static final Logger logger = LoggerFactory.getLogger(OpenF1SessionClient.class);

    public OpenF1SessionClient(RestClient client) {
        this.client = client;
    }


    public List<Session> fetchAllSessions() {
        logger.info("Fetching all sessions from Open F1");
        List<SessionDto> sessionDtos = client.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/sessions");
                    return builder.build();
                })
                .retrieve()
                .body(new ParameterizedTypeReference<List<SessionDto>>() {
                });
        if (sessionDtos == null || sessionDtos.isEmpty()) {
            logger.error("No sessions found on Open F1 client");
        }
        return sessionDtos.stream()
                .map(SessionDto::convertToEntity)
                .toList();
    }

}
