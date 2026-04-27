package com.koreanair.reservation.domain.payment;

public interface RefundPolicy {

    int calculateRefundAmount(int baseAmount);

    String getRefundType();
}
