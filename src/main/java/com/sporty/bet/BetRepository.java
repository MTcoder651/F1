package com.sporty.bet;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface BetRepository extends MongoRepository<Bet, String> {
    List<Bet> findBetsBySessionId(Integer sessionId);
}
