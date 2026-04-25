package com.koreanair.reservation.domain.reservation;

public enum ReservationStatus {
    CREATED,
    PENDING_PAYMENT,
    CONFIRMED,
    TICKETED,
    CANCELLED,
    PARTIALLY_CANCELLED
}
