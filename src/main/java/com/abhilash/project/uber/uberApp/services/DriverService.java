package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.DriverRideDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.dto.RiderRideDTO;
import com.abhilash.project.uber.uberApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {
    DriverRideDTO cancelRide(Long rideId); //driver id passed from Spring security context holder
    DriverRideDTO startRide(Long rideId, String otp);
    DriverRideDTO endRide(Long rideId);
    RiderRideDTO acceptRide(Long rideRequestId);
    RiderDTO rateRider(Long rideId,Integer rating);
    DriverDTO getMyProfile();
    Page<DriverRideDTO> getAllMyRides(PageRequest pageRequest);
    Driver getCurrentDriver();
    Driver updateDriverAvailability(Driver driver,boolean available);
    DriverDTO createNewDriver(Driver driver);
    Driver findByVehicleId(String vehicleId);
}
