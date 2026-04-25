package com.koreanair.reservation.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RefundRequest {

    private String requestId;
    private LocalDateTime requestDate;
    private BigDecimal refundAmount;
    private RefundStatus status;
    private String reason;

    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public static List<RefundRequest> queryByStatus(RefundStatus status) {
        return null;
    }

    public static RefundRequest getDetail(String requestId) {
        return null;
    }

    public void updateStatus(RefundStatus newStatus) {
        this.status = newStatus;
    }

    public void updateStatus(RefundStatus newStatus, String reason) {
        this.status = newStatus;
        this.reason = reason;
    }
}
