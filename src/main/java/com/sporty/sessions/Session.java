package com.sporty.sessions;

import com.sporty.driver.DriverMarket;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Session {
    @Id
    private Integer sessionId;
    private Integer meetingKey;
    private String location;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String sessionType;
    private String sessionName;
    private Integer countryKey;
    private String countryCode;
    private String countryName;
    private Integer circuitKey;
    private String circuitShortName;
    private String gmtOffset;
    private Integer year;
    private List<DriverMarket> driverMarkets;


    public List<DriverMarket> getDriverMarkets() {
        return driverMarkets;
    }

    public void setDriverMarkets(List<DriverMarket> driverMarkets) {
        this.driverMarkets = driverMarkets;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
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

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setCountryKey(Integer countryKey) {
        this.countryKey = countryKey;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCircuitKey(Integer circuitKey) {
        this.circuitKey = circuitKey;
    }

    public void setCircuitShortName(String circuitShortName) {
        this.circuitShortName = circuitShortName;
    }
}
