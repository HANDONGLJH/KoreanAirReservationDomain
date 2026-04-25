package com.koreanair.reservation.domain.payment;

public enum PaymentStatus {
    READY,
    AUTHORIZED,
    PAID,
    FAILED,
    CANCELLED,
    PARTIALLY_REFUNDED,
    REFUNDED
}
