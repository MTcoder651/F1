package com.sporty.driver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverMarketDto {

    @JsonProperty("session_key")
    private Integer sessionKey;

    @JsonProperty("meeting_key")
    private Integer meetingKey;

    @JsonProperty("driver_number")
    private Integer driverNumber;

    @JsonProperty("broadcast_name")
    private String broadcastName;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("name_acronym")
    private String nameAcronym;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_colour")
    private String teamColour;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("headshot_url")
    private String headshotUrl;

    @JsonProperty("country_code")
    private String countryCode;


    public Integer getSessionKey() {
        return sessionKey;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNameAcronym() {
        return nameAcronym;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public static DriverMarket convertToEntity(DriverMarketDto dto) {
        DriverMarket entity = new DriverMarket();
        entity.setDriverId(dto.driverNumber);
        entity.setDriverName(dto.firstName);
        entity.setSessionId(dto.sessionKey);
        return entity;
    }
}