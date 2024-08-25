package com.abhilash.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name="idx_rating_rider",columnList = "rider_id"),
        @Index(name="idx_rating_driver",columnList = "driver_id")
})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private Integer driverRating;
    private Integer riderRating;
}
