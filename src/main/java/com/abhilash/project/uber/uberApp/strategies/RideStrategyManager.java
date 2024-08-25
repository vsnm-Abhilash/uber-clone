package com.abhilash.project.uber.uberApp.strategies;

import com.abhilash.project.uber.uberApp.strategies.Impl.DriverMatchingHighestRatedDriverStrategy;
import com.abhilash.project.uber.uberApp.strategies.Impl.DriverMatchingNearestDriverStrategy;
import com.abhilash.project.uber.uberApp.strategies.Impl.RideFareDefaultFareCalculationStrategy;
import com.abhilash.project.uber.uberApp.strategies.Impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
            if(riderRating>4.8){
                return highestRatedDriverStrategy;
            }
            else{
                return nearestDriverStrategy;
            }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        LocalTime surgeStartTime=LocalTime.of(20,0);// 20:00 (8 PM)
        LocalTime surgeEndTime=LocalTime.of(6,0);// (6 AM)
        LocalTime currentTime=LocalTime.now();
        log.info("currentTime: {} ",currentTime);
        log.info("surgeStartTime: {} ",surgeStartTime);
        log.info("surgeEndTime: {} ",surgeEndTime);
        log.info("currentTime.isAfter(surgeStartTime): {} ",currentTime.isAfter(surgeStartTime));
        log.info("currentTime.isBefore(surgeEndTime): {} ",currentTime.isBefore(surgeEndTime));


        boolean isSurgeTime=isCurrentTimeInRange(currentTime, surgeStartTime, surgeEndTime);


        if(isSurgeTime) return surgePricingFareCalculationStrategy;
        else return defaultFareCalculationStrategy;

    }

    public static boolean isCurrentTimeInRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            // Range does not span across midnight
            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        } else {
            // Range spans across midnight
            return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
        }
    }

}
