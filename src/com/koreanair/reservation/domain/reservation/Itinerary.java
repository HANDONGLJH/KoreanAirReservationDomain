package com.koreanair.reservation.domain.reservation;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    private String tripType;
    private List<Segment> segments = new ArrayList<>();

    public String getTripType() {
        return tripType;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }
}
