package com.sporty.bet;

import com.sporty.driver.DriverMarket;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OddsService {

    private static Random random;

    public OddsService() {
        random = new Random();
    }

    public int calculateOdds(DriverMarket driverMarket) {
        int[] choices = {2, 3, 4};
        return choices[random.nextInt(choices.length)];
    }
}
