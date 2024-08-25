package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.RideRequestDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.dto.RiderRideDTO;
import com.abhilash.project.uber.uberApp.entities.*;
import com.abhilash.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.abhilash.project.uber.uberApp.entities.enums.RideStatus;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.repositories.RideRequestRepository;
import com.abhilash.project.uber.uberApp.repositories.RiderRepository;
import com.abhilash.project.uber.uberApp.services.DriverService;
import com.abhilash.project.uber.uberApp.services.RatingService;
import com.abhilash.project.uber.uberApp.services.RideService;
import com.abhilash.project.uber.uberApp.services.RiderService;
import com.abhilash.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;
    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        Rider rider=getCurrrentRider();
        RideRequest rideRequest=modelMapper.map(rideRequestDTO,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare=rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest=rideRequestRepository.save(rideRequest);

        List<Driver> drivers=rideStrategyManager
                .driverMatchingStrategy(rider.getRating()).findMatchingDrivers(rideRequest);

        return modelMapper.map(savedRideRequest,RideRequestDTO.class);
    }

    @Override
    public RiderRideDTO cancelRide(Long rideId) {
        Rider rider=getCurrrentRider();
        Ride ride=rideService.getRideById(rideId);
        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with rideId "+rideId);
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Cannot be cancelled, invalid status "+ride.getRideStatus());
        }
        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);
        return modelMapper.map(savedRide,RiderRideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        Ride ride=rideService.getRideById(rideId);
        Rider rider=getCurrrentRider();
        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with rideId "+rideId);
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended, hence cannot start rating, status: "+ride.getRideStatus());
        }
        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        Rider rider=getCurrrentRider();
        return modelMapper.map(rider,RiderDTO.class);

    }

    @Override
    public Page<RiderRideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider rider=getCurrrentRider();
        return rideService.getAllRidesOfRider(rider,pageRequest)
                .map(ride -> modelMapper.map(ride, RiderRideDTO.class));

    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider=Rider.builder().user(user).rating(0.0).build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrrentRider() {
        //TODO
        return riderRepository.findById(1L).orElseThrow(()->new ResourceNotFoundException("Rider not found with id 1"));
    }
}
