package com.koreanair.reservation.domain.flight;

public class Seat {

    private String seatNumber;
    private CabinClass cabinClass;
    private SeatStatus status;
    private boolean windowSeat;
    private boolean aisleSeat;
    private boolean extraLegroom;

    public String getSeatNumber() {
        return seatNumber;
    }

    public CabinClass getCabinClass() {
        return cabinClass;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void hold() {
        this.status = SeatStatus.HELD;
    }

    public void updateStatus(SeatStatus newStatus) {
        this.status = newStatus;
    }

    public void release() {
        this.status = SeatStatus.AVAILABLE;
    }
}
