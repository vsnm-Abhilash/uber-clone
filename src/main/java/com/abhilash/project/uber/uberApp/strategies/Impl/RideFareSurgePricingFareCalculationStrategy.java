package com.abhilash.project.uber.uberApp.strategies.Impl;

import com.abhilash.project.uber.uberApp.entities.RideRequest;
import com.abhilash.project.uber.uberApp.services.impl.DistanceServiceOSRMImpl;
import com.abhilash.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    private final DistanceServiceOSRMImpl distanceServiceOSRM;
    private static final double SURGE_FACTOR=1.2;
    @Override
    public double calculateFare(RideRequest rideRequest) {

        double distance=distanceServiceOSRM.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropOffLocation());
        log.info("distance: {}, RIDE_FARE_MULTIPLIER: {} ,SURGE_FACTOR : {}",distance,RIDE_FARE_MULTIPLIER,SURGE_FACTOR);
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
