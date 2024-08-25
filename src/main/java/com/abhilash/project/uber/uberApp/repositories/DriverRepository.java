package com.abhilash.project.uber.uberApp.repositories;

import com.abhilash.project.uber.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//ST_Distance(Point 1,Point 2)
//ST_DWithin(Point 1,10000)

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Query(value = "Select d.*,ST_Distance(d.current_location, :pickupLocation) distance " +
            "from driver d " +
            "where d.available=true and ST_DWithin(d.current_location,:pickupLocation,10000 ) " +
            "ORDER BY distance " +
            "LIMIT 10",nativeQuery = true)
    List<Driver> findTenNearestDrivers(Point pickupLocation);


    @Query(value = "Select d.* " +
            "from driver d " +
            "where d.available=true and ST_DWithin(d.current_location,:pickupLocation,15000 ) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10",nativeQuery = true)
    List<Driver> findTopRatedDrivers(Point pickupLocation);

    Driver findByVehicleId(String vehicleId);
}
