package com.abhilash.project.uber.uberApp.strategies;

import com.abhilash.project.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER=10;
    double calculateFare(RideRequest rideRequest);
}
