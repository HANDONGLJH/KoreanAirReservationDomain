package com.koreanair.reservation.domain.user;

import java.time.LocalDateTime;

import com.koreanair.reservation.domain.flight.Route;
import com.koreanair.reservation.domain.reservation.Reservation;

public abstract class User {

    protected Long userId;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected LocalDateTime registeredAt;

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Reservation createReservation() {
        return null;
    }

    public void viewReservation(String reservationNumber) {
    }

    public void searchFlights(Route route) {
    }
}
