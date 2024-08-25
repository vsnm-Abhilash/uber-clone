package com.abhilash.project.uber.uberApp.dto;


import com.abhilash.project.uber.uberApp.entities.enums.PaymentMethod;
import com.abhilash.project.uber.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDTO {
    private Long id;
    private PointDTO pickupLocation;
    private PointDTO dropOffLocation;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdTime;
    private RiderDTO rider;
    private Double fare;
    private RideRequestStatus rideStatus;

}
