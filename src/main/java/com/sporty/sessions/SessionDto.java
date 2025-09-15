package com.sporty.sessions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sporty.Utils;

public class SessionDto {

    @JsonProperty("session_key")
    private Integer sessionKey;

    @JsonProperty("meeting_key")
    private Integer meetingKey;

    @JsonProperty("location")
    private String location;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("session_type")
    private String sessionType;

    @JsonProperty("session_name")
    private String sessionName;

    @JsonProperty("country_key")
    private Integer countryKey;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("circuit_key")
    private Integer circuitKey;

    @JsonProperty("circuit_short_name")
    private String circuitShortName;

    @JsonProperty("gmt_offset")
    private String gmtOffset;

    @JsonProperty("year")
    private Integer year;


    public Integer getSessionKey() {
        return sessionKey;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public String getLocation() {
        return location;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getSessionType() {
        return sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public Integer getCountryKey() {
        return countryKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public Integer getCircuitKey() {
        return circuitKey;
    }

    public String getCircuitShortName() {
        return circuitShortName;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public Integer getYear() {
        return year;
    }

    public static Session convertToEntity(SessionDto dto) {
        Session entity = new Session();
        entity.setSessionId(dto.getSessionKey());
        entity.setMeetingKey(dto.getMeetingKey());
        entity.setLocation(dto.getLocation());
        entity.setDateStart(Utils.parseDateTime(dto.getDateStart()));
        entity.setDateEnd(Utils.parseDateTime(dto.getDateEnd()));
        entity.setSessionType(dto.getSessionType());
        entity.setSessionName(dto.getSessionName());
        entity.setCountryKey(dto.getCountryKey());
        entity.setCountryCode(dto.getCountryCode());
        entity.setCountryName(dto.getCountryName());
        entity.setCircuitKey(dto.getCircuitKey());
        entity.setCircuitShortName(dto.getCircuitShortName());
        entity.setGmtOffset(dto.getGmtOffset());
        entity.setYear(dto.getYear());
        return entity;
    }

}