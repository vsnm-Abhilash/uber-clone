package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.RiderDTO;
import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.Rating;
import com.abhilash.project.uber.uberApp.entities.Ride;
import com.abhilash.project.uber.uberApp.entities.Rider;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.abhilash.project.uber.uberApp.repositories.DriverRepository;
import com.abhilash.project.uber.uberApp.repositories.RatingRepository;
import com.abhilash.project.uber.uberApp.repositories.RiderRepository;
import com.abhilash.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {
        Driver driver=ride.getDriver();
        Rating ratingObj=ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Rating not found for ride with id "+ride.getId()));
        if(ratingObj.getDriverRating()!=null) throw new RuntimeConflictException("Driver has already been rated, cannot rate again!");
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);
        Double avgRating= ratingRepository.findByDriver(driver).stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);

        driver.setRating(avgRating);
        Driver savedDriver=driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(Ride ride, Integer rating) {
        Rider rider=ride.getRider();
        Rating ratingObj=ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Rating not found for ride with id "+ride.getId()));
        if(ratingObj.getRiderRating()!=null) throw new RuntimeConflictException("Rider has already been rated, cannot rate again!");
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);
        Double avgRating= ratingRepository.findByRider(rider).stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);

        rider.setRating(avgRating);
        Rider savedRider=riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDTO.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating=Rating.builder()
                .driver(ride.getDriver())
                .ride(ride)
                .rider(ride.getRider())
                .build();
        ratingRepository.save(rating);

    }
}
