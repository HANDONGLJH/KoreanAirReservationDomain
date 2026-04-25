package com.koreanair.reservation.domain.flight;

import java.math.BigDecimal;

public class Fare {

    private Long fareId;
    private BookingClass bookingClass;
    private CabinClass cabinClass;
    private BigDecimal basePrice;
    private boolean refundable;
    private boolean changeable;

    public Long getFareId() {
        return fareId;
    }

    public BookingClass getBookingClass() {
        return bookingClass;
    }

    public CabinClass getCabinClass() {
        return cabinClass;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public boolean isRefundable() {
        return refundable;
    }
}
