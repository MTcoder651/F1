package com.sporty.externalData;

import com.sporty.driver.DriverMarket;

import java.util.List;

public interface DriverClientInterface {

    List<DriverMarket> fetchAllDrivers();
}
