package com.sporty.bet;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

public class Bet {

    @Id
    private String betId;
    private final Integer sessionId;
    private final Integer driverId;
    private final Integer userId;
    private final BigDecimal amount;
    private BetStatus status;

    public Bet(Integer sessionId, Integer driverId, Integer userId, BigDecimal amount) {
        this.betId = UUID.randomUUID().toString();
        this.sessionId = sessionId;
        this.driverId = driverId;
        this.userId = userId;
        this.amount = amount;
        this.status = BetStatus.PENDING;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getBetId() {
        return betId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BetStatus getStatus() {
        return status;
    }

    public void setStatus(BetStatus status) {
        this.status = status;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }
}
