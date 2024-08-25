package com.abhilash.project.uber.uberApp.dto;

import com.abhilash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.abhilash.project.uber.uberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderRideDTO {
    private Long id;
    private PointDTO pickupLocation;
    private PointDTO dropOffLocation;
    private LocalDateTime createdTime;
    private RiderDTO rider;
    private DriverDTO driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;
    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String otp;

}
