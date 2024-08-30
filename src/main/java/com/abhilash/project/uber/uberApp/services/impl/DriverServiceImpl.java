package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.DriverRideDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.dto.RiderRideDTO;
import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.Ride;
import com.abhilash.project.uber.uberApp.entities.RideRequest;
import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.abhilash.project.uber.uberApp.entities.enums.RideStatus;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.repositories.DriverRepository;
import com.abhilash.project.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper mapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    public DriverDTO createNewDriver(Driver driver) {
        return mapper.map(driverRepository.save(driver),DriverDTO.class);
    }

    @Override
    public Driver findByVehicleId(String vehicleId) {
        return driverRepository.findByVehicleId(vehicleId);
    }


    @Override
    @Transactional
    public RiderRideDTO acceptRide(Long rideRequestId) {
        RideRequest rideRequest=rideRequestService.findRideRequestById(rideRequestId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest cannot be accepted, status is "+rideRequest.getRideRequestStatus());
        }
        Driver currentDriver=getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }
        Driver savedDriver=updateDriverAvailability(currentDriver,false);
        Ride ride=rideService.createNewRide(rideRequest,savedDriver);
        return mapper.map(ride, RiderRideDTO.class);
    }

    @Override
    public DriverRideDTO cancelRide(Long rideId) {
        Ride ride =rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a Ride as he has not accepted it earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status "+ride.getRideStatus());
        }
        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        updateDriverAvailability(driver,true);

        return mapper.map(ride, DriverRideDTO.class);
    }

    @Override
    public DriverRideDTO startRide(Long rideId, String otp) {
        Ride ride =rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a Ride as he has not accepted it earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not confirmed hence cannot be started "+ride.getRideStatus());
        }
        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("OTP is not valid "+otp);
        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide=rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        return mapper.map(savedRide, DriverRideDTO.class);
    }

    @Override
    @Transactional
    public DriverRideDTO endRide(Long rideId) {
        Ride ride =rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a Ride as he has not accepted it earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not ongoing, hence ride cannot be ended "+ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide=rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver,true);
        paymentService.processPayment(savedRide);
        return mapper.map(savedRide,DriverRideDTO.class);

    }



    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not the owner of this ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended, hence cannot start rating, status: "+ride.getRideStatus());
        }
        return ratingService.rateRider(ride,rating);
    }

    @Override
    public DriverDTO getMyProfile() {
        Driver currentDriver=getCurrentDriver();
        return mapper.map(currentDriver,DriverDTO.class);
    }

    @Override
    public Page<DriverRideDTO> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver=getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver,pageRequest)
                .map(ride -> mapper.map(ride,DriverRideDTO.class));
    }

    @Override
    public Driver getCurrentDriver() {

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Driver not associated with User with id "+user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }
}
