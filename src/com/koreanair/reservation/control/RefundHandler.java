package com.koreanair.reservation.control;

import java.math.BigDecimal;
import java.util.List;

import com.koreanair.reservation.domain.payment.RefundRequest;

public class RefundHandler {

    public Object evaluateRefund(String pnr, String fareClass) {
        return null;
    }

    public RefundRequest getRefundDetail(String requestId) {
        return null;
    }

    public void processRefund(String requestId, BigDecimal approvedAmount) {
    }

    public void denyRefund(String requestId, String reason) {
    }

    public List<RefundRequest> getPendingRequests() {
        return null;
    }
}
