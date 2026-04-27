package com.koreanair.reservation.domain.payment;

public class PartialRefundPolicy implements RefundPolicy {

    @Override
    public int calculateRefundAmount(int baseAmount) {
        return baseAmount / 2;
    }

    @Override
    public String getRefundType() {
        return "PARTIAL";
    }
}
