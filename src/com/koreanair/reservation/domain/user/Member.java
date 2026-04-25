package com.koreanair.reservation.domain.user;

import java.util.ArrayList;
import java.util.List;

import com.koreanair.reservation.domain.reservation.Reservation;

public class Member extends User {

    private String memberNumber;
    private List<Reservation> reservations = new ArrayList<>();

    public String getMemberNumber() {
        return memberNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
