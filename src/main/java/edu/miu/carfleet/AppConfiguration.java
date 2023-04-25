package edu.miu.carfleet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author bazz
 * Apr 25 2023
 * 10:14
 */
@Component
@ConfigurationProperties
        (prefix="app.carfleet")
public class AppConfiguration {
    private String fleetBaseUrl;
    private String reservationQueue;
    private Long maxRental;


    public String getFleetBaseUrl() {
        return fleetBaseUrl;
    }

    public void setFleetBaseUrl(String fleetBaseUrl) {
        this.fleetBaseUrl = fleetBaseUrl;
    }

    public Long getMaxRental() {
        return maxRental;
    }

    public void setMaxRental(Long maxRental) {
        this.maxRental = maxRental;
    }

    public String getReservationQueue() {
        return reservationQueue;
    }

    public void setReservationQueue(String reservationQueue) {
        this.reservationQueue = reservationQueue;
    }
}
