package com.koreanair.reservation.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Refund {

    private Long refundId;
    private BigDecimal refundAmount;
    private RefundStatus status;
    private String reason;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public void approve() {
        this.status = RefundStatus.APPROVED;
    }

    public void reject() {
        this.status = RefundStatus.REJECTED;
    }

    public void complete() {
        this.status = RefundStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}
