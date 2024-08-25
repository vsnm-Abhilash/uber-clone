package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.Ride;
import com.abhilash.project.uber.uberApp.entities.Rider;

public interface RatingService {

    DriverDTO rateDriver(Ride ride, Integer rating);
    RiderDTO rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
