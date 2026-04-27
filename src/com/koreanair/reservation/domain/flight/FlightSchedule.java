package com.koreanair.reservation.domain.flight;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FlightSchedule {

    private Long scheduleId;
    private Flight flight;
    private AircraftType aircraftType;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private FlightStatus status;
    private List<SeatInventory> seatInventories = new ArrayList<>();

    public Long getScheduleId() {
        return scheduleId;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getFlightNumber() {
        return flight != null ? flight.getFlightNumber() : null;
    }

    public Duration getDuration() {
        if (departureDateTime == null || arrivalDateTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(departureDateTime, arrivalDateTime);
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public List<SeatInventory> getSeatInventories() {
        return seatInventories;
    }

    public boolean isAvailableForBooking() {
        return status == FlightStatus.SCHEDULED || status == FlightStatus.DELAYED;
    }

    public SeatInventory findSeatInventory(BookingClass bookingClass) {
        return null;
    }

    public void changeStatus(FlightStatus status) {
        this.status = status;
    }

    public void addSeatInventory(SeatInventory seatInventory) {
        seatInventories.add(seatInventory);
    }

    public static FlightSchedule create(String flightNumber, Airport departure, Airport arrival, AircraftType aircraftType) {
        FlightSchedule schedule = new FlightSchedule();
        Route route = new Route();
        route.setOrigin(departure);
        route.setDestination(arrival);
        Flight createdFlight = new Flight();
        createdFlight.setFlightNumber(flightNumber);
        createdFlight.setRoute(route);
        schedule.flight = createdFlight;
        schedule.aircraftType = aircraftType;
        schedule.status = FlightStatus.SCHEDULED;
        return schedule;
    }

    public void updateStatus(FlightStatus newStatus) {
        this.status = newStatus;
    }
}
