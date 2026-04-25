package com.koreanair.reservation.domain.flight;

import java.math.BigDecimal;

public class FareRule {

    private Long fareRuleId;
    private String fareClass;
    private boolean refundable;
    private BigDecimal changeFee;
    private BigDecimal cancellationPenalty;

    public String getFareClass() {
        return fareClass;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public BigDecimal getChangeFee() {
        return changeFee;
    }

    public BigDecimal getCancellationPenalty() {
        return cancellationPenalty;
    }

    public FareRule checkRefundPolicy(String fareClass) {
        return null;
    }
}
