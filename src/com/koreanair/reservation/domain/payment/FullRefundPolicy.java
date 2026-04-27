package com.koreanair.reservation.domain.payment;

public class FullRefundPolicy implements RefundPolicy {

    @Override
    public int calculateRefundAmount(int baseAmount) {
        return baseAmount;
    }

    @Override
    public String getRefundType() {
        return "FULL";
    }
}
