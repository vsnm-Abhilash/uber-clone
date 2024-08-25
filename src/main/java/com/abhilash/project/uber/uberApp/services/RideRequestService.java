package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.entities.RideRequest;

public interface RideRequestService {
    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);

}
