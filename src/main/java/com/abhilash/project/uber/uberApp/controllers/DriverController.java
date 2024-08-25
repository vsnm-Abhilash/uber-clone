package com.abhilash.project.uber.uberApp.controllers;

import com.abhilash.project.uber.uberApp.dto.*;
import com.abhilash.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RiderRideDTO> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<DriverRideDTO> startRide(@PathVariable Long rideRequestId, @RequestBody RideStartDTO rideStartDTO){
        return ResponseEntity.ok(driverService.startRide(rideRequestId,rideStartDTO.getOtp()));
    }


    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<DriverRideDTO> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }


    @PostMapping("/rateRider")
    public ResponseEntity<RiderDTO> rateRider(@RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(driverService.rateRider(ratingDTO.getRideId(),ratingDTO.getRating()));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<DriverRideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDTO> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<DriverRideDTO>> getMyRides(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                         @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageNumber,pageSize,
                Sort.by(Sort.Direction.DESC,"created_time","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

}
