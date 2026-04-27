package com.koreanair.reservation.domain.reservation;

public enum ReservationStatus {
    CREATED,
    PENDING_PAYMENT,
    CONFIRMED,
    TICKETED,
    CANCELLATION_REQUESTED,
    CANCELLED,
    PARTIALLY_CANCELLED,
    REFUND_REQUESTED,
    REFUNDED
}
