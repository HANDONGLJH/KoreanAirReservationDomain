package com.koreanair.reservation.domain.flight;

import java.time.LocalDateTime;
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
        return new FlightSchedule();
    }

    public void updateStatus(FlightStatus newStatus) {
        this.status = newStatus;
    }
}
