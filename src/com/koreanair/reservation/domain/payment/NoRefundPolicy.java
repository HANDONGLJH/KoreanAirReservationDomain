package com.koreanair.reservation.domain.payment;

public class NoRefundPolicy implements RefundPolicy {

    @Override
    public int calculateRefundAmount(int baseAmount) {
        return 0;
    }

    @Override
    public String getRefundType() {
        return "NONE";
    }
}
