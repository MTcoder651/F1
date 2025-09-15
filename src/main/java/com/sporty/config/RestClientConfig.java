package com.sporty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient f1ApiRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.openf1.org/v1")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("User-Agent", "F1-Session-Client/1.0")
                .build();
    }
}