package com.abhilash.project.uber.uberApp.strategies.Impl;

import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.RideRequest;
import com.abhilash.project.uber.uberApp.repositories.DriverRepository;
import com.abhilash.project.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}
