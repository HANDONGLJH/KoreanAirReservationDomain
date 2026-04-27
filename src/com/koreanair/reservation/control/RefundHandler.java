package com.koreanair.reservation.control;

import java.math.BigDecimal;
import java.util.List;

import com.koreanair.reservation.domain.payment.RefundRequest;
import com.koreanair.reservation.domain.flight.FareRule;
import com.koreanair.reservation.domain.payment.FullRefundPolicy;
import com.koreanair.reservation.domain.payment.NoRefundPolicy;
import com.koreanair.reservation.domain.payment.PartialRefundPolicy;
import com.koreanair.reservation.domain.payment.RefundPolicy;

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

    private RefundPolicy resolvePolicy(FareRule fareRule) {
        if (fareRule == null || !fareRule.isRefundable()) {
            return new NoRefundPolicy();
        }
        String fareClass = fareRule.getFareClass();
        if ("Y".equals(fareClass) || "B".equals(fareClass)) {
            return new FullRefundPolicy();
        }
        return new PartialRefundPolicy();
    }
}
