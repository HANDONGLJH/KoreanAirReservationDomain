package com.koreanair.reservation.domain.flight;

import java.util.ArrayList;
import java.util.List;

public class AircraftType {

    private String aircraftTypeCode;
    private String modelName;
    private String manufacturer;
    private List<Seat> seatLayout = new ArrayList<>();

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public List<Seat> getSeatLayout() {
        return seatLayout;
    }

    public Seat findSeat(String seatNumber) {
        return null;
    }

    public void addSeat(Seat seat) {
        seatLayout.add(seat);
    }
}
