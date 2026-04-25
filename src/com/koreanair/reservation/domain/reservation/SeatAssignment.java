package com.koreanair.reservation.domain.reservation;

import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.flight.Seat;

public class SeatAssignment {

    private Long seatAssignmentId;
    private FlightSchedule flightSchedule;
    private Seat seat;
    private SeatAssignmentStatus status;

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public Seat getSeat() {
        return seat;
    }

    public SeatAssignmentStatus getStatus() {
        return status;
    }

    public void changeSeat(Seat newSeat) {
        this.seat = newSeat;
    }

    public void cancel() {
        this.status = SeatAssignmentStatus.CANCELLED;
    }
}
