package com.sporty.event;

import com.sporty.bet.Bet;
import com.sporty.bet.BetStatus;
import com.sporty.bet.BettingService;
import com.sporty.driver.DriverMarket;
import com.sporty.sessions.Session;
import com.sporty.sessions.SessionService;
import com.sporty.user.User;
import com.sporty.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BettingService bettingService;

    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private EventService eventService;

    @Captor
    private ArgumentCaptor<Bet> betCaptor;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private Event event;
    private Session session;
    private DriverMarket winDriver;
    private DriverMarket loseDriver;
    private Bet winBet;
    private Bet loseBet;
    private User user;

    @BeforeEach
    void setUp() {
        event = new Event(1,42);

        winDriver = new DriverMarket();
        winDriver.setDriverId(42);
        winDriver.setOdds(2);

        loseDriver = new DriverMarket();
        loseDriver.setDriverId(99);
        loseDriver.setOdds(5);

        session = new Session();
        session.setSessionId(1);
        session.setDriverMarkets(List.of(winDriver, loseDriver));

        winBet = new Bet(1, 42, 10, BigDecimal.valueOf(20));
        loseBet = new Bet(1, 99, 11, BigDecimal.valueOf(30));

        user = new User(10);
        user.setBalance(BigDecimal.valueOf(100));
    }

    @Test
    void saveEvent_shouldProcessWinsAndLossesAndThenSave() {
        when(sessionService.fetchSession(1)).thenReturn(Optional.of(session));
        when(bettingService.getBetsBySession(1)).thenReturn(List.of(winBet, loseBet));
        when(userService.findUserById(10)).thenReturn(Optional.of(user));

        eventService.saveEvent(event);

        // Verify updateBet called for both bets
        verify(bettingService, times(2)).updateBet(betCaptor.capture());
        List<Bet> updated = betCaptor.getAllValues();
        assertThat(updated).hasSize(2);
        assertThat(updated.get(0).getStatus()).isEqualTo(BetStatus.WON);
        assertThat(updated.get(1).getStatus()).isEqualTo(BetStatus.LOST);

        // Verify user updated with winnings: 20 * odds(2) = 40, balance 100+40=140
        verify(userService).updateUser(userCaptor.capture());
        assertThat(userCaptor.getValue().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(140));

        // Verify repository save after processing
        verify(eventRepository).save(event);
    }

}
