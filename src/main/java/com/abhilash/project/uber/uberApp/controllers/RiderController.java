package com.abhilash.project.uber.uberApp.controllers;

import com.abhilash.project.uber.uberApp.dto.*;
import com.abhilash.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDTO> requestRide(@RequestBody RideRequestDTO rideRequestDTO){
        return ResponseEntity.ok(riderService.requestRide(rideRequestDTO));
    }


    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RiderRideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }


    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDTO> rateDriver(@RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(riderService.rateDriver(ratingDTO.getRideId(),ratingDTO.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDTO> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RiderRideDTO>> getMyRides(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                     @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageNumber,pageSize,
                Sort.by(Sort.Direction.DESC,"created_time","id"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }



}
