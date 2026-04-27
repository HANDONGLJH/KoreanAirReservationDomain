package com.koreanair.reservation.domain.flight;

public class Route {

    private String routeCode;
    private Airport origin;
    private Airport destination;
    private boolean internationalRoute;

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public boolean isInternationalRoute() {
        return internationalRoute;
    }
}
