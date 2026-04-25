package com.koreanair.reservation.domain.flight;

public class SeatInventory {

    private Long inventoryId;
    private BookingClass bookingClass;
    private int totalSeats;
    private int availableSeats;

    public BookingClass getBookingClass() {
        return bookingClass;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean canReserve(int seatCount) {
        return availableSeats >= seatCount;
    }

    public void reserve(int seatCount) {
    }

    public void release(int seatCount) {
    }

    public void adjustTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
