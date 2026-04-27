package com.koreanair.reservation.boundary;

import java.util.List;

public interface GDSInterface {

    List<?> searchInterlineFlights(String origin, String destination);

    List<?> getPartnerAvailability(String flightNumber);
}
