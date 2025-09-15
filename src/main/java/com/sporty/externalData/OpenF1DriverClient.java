package com.sporty.externalData;

import com.sporty.driver.DriverMarket;
import com.sporty.driver.DriverMarketDto;
import com.sporty.sessions.SessionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OpenF1DriverClient implements DriverClientInterface {
    private final RestClient client;
    private static final Logger logger = LoggerFactory.getLogger(OpenF1DriverClient.class);

    public OpenF1DriverClient(RestClient client) {
        this.client = client;
    }

    @Override
    public List<DriverMarket> fetchAllDrivers() {
        logger.info("Fetching all drivers from Open F1");
        List<DriverMarketDto> driverMarketDtos = client.get()
                .uri(uriBuilder -> uriBuilder.path("/drivers")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<DriverMarketDto>>() {
                });
        if  (driverMarketDtos == null || driverMarketDtos.isEmpty()) {
            logger.error("No drivers found on Open F1 client");
            return List.of();
        }
        return driverMarketDtos.stream()
                .map(DriverMarketDto::convertToEntity)
                .toList();
    }


}
