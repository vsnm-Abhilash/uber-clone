package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.RideRequestDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.dto.RiderRideDTO;
import com.abhilash.project.uber.uberApp.entities.Rider;
import com.abhilash.project.uber.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDTO requestRide(RideRequestDTO rideRequestDTO); //driver id passed from Spring security context holder
    RiderRideDTO cancelRide(Long rideId);
    DriverDTO rateDriver(Long rideId, Integer rating);
    RiderDTO getMyProfile();
    Page<RiderRideDTO> getAllMyRides(PageRequest pageRequest);
    Rider createNewRider(User user);
    Rider getCurrrentRider();
}
