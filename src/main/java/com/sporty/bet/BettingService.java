package com.sporty.bet;

import com.sporty.user.User;
import com.sporty.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BettingService {

    UserService userService;
    BetRepository betRepository;

    public BettingService(UserService userService, BetRepository betRepository) {
        this.userService = userService;
        this.betRepository = betRepository;
    }

    public void placeBet(User user, Bet bet) {
        user.setBalance(user.getBalance().subtract(bet.getAmount()));
        user.getBetsIds().add(bet.getBetId());
        //TODO add transactionality
        userService.updateUser(user);
        betRepository.save(bet);
    }

    public List<Bet> getBetsBySession(Integer sessionId) {
        return betRepository.findBetsBySessionId(sessionId);
    }

    public void updateBet(Bet bet) {
        betRepository.save(bet);
    }
}
