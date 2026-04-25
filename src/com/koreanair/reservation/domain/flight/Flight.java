package com.koreanair.reservation.domain.flight;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private Long flightId;
    private String flightNumber;
    private Route route;
    private List<Fare> fares = new ArrayList<>();

    public Long getFlightId() {
        return flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Route getRoute() {
        return route;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void addFare(Fare fare) {
        fares.add(fare);
    }

    public Fare findFare(BookingClass bookingClass) {
        return null;
    }
}
