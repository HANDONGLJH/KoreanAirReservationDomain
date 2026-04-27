package com.koreanair.reservation.domain.reservation;

import java.time.Duration;
import java.time.LocalDateTime;

import com.koreanair.reservation.domain.flight.Airport;
import com.koreanair.reservation.domain.flight.FlightSchedule;

public class Segment {

    private int sequenceNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Duration connectionTime;
    private FlightSchedule flightSchedule;
    private Airport airport;

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Duration getConnectionTime() {
        return connectionTime;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public Airport getAirport() {
        return airport;
    }
}
