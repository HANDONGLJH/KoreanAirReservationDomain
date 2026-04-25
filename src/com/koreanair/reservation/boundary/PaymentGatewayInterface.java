package com.koreanair.reservation.boundary;

import java.math.BigDecimal;

import com.koreanair.reservation.domain.payment.Payment;

public interface PaymentGatewayInterface {

    Object sendAuthorizationRequest(BigDecimal amount, Object paymentInfo);

    Object receiveTransactionResult();

    Object sendRefund(String originalPaymentId, BigDecimal amount);

    /**
     * Iteration 1 Walking Skeleton 전용 단순 승인 API.
     * TODO(iter3): 기존 sendAuthorizationRequest(BigDecimal, Object) + receiveTransactionResult() 페어로 통합.
     *              현재는 Walking Skeleton 의 in-memory Mock (MockPaymentGateway) 가 이 메서드만 사용.
     *
     * @return true = 승인, false = 거절.
     */
    boolean authorize(Payment payment);
}
