package com.abhilash.project.uber.uberApp.strategies;

import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
        List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
