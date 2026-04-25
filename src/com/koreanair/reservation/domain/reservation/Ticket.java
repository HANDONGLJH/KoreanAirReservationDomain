package com.koreanair.reservation.domain.reservation;

import java.time.LocalDateTime;

import com.koreanair.reservation.domain.passenger.Passenger;

public class Ticket {

    private Long ticketId;
    private String ticketNumber;
    private Passenger passenger;
    private TicketStatus status;
    private LocalDateTime issuedAt;
    private SeatAssignment seatAssignment;

    public Passenger getPassenger() {
        return passenger;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public SeatAssignment getSeatAssignment() {
        return seatAssignment;
    }

    public void assignSeat(SeatAssignment seatAssignment) {
        this.seatAssignment = seatAssignment;
    }

    public void issue() {
        this.status = TicketStatus.ISSUED;
        this.issuedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = TicketStatus.CANCELLED;
    }

    public static Ticket getByReservation(String pnr) {
        return null;
    }
}
