package com.sporty.user;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {
    @Id
    private Integer userId;
    private BigDecimal balance;
    private List<String> betsIds;

    public User(Integer userId) {
        this.userId = userId;
        this.balance = BigDecimal.valueOf(100);
        this.betsIds = new ArrayList<>();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<String> getBetsIds() {
        return betsIds;
    }

    public void setBets(List<String> bets) {
        this.betsIds = betsIds;
    }
}
