package com.koreanair.reservation.domain.user;

import com.koreanair.reservation.domain.flight.Fare;
import com.koreanair.reservation.domain.flight.Flight;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.flight.FlightStatus;
import com.koreanair.reservation.domain.flight.Route;
import com.koreanair.reservation.domain.flight.SeatInventory;

public class Admin extends User {

    private String employeeId;
    private String department;

    public Flight registerFlight(String flightNumber, Route route) {
        return null;
    }

    public FlightSchedule createSchedule(Flight flight) {
        return null;
    }

    public void changeFlightStatus(FlightSchedule schedule, FlightStatus status) {
    }

    public void registerFare(Flight flight, Fare fare) {
    }

    public void adjustSeatInventory(SeatInventory seatInventory, int totalSeats) {
    }
}
