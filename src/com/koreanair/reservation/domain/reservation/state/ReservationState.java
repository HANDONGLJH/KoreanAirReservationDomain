package com.koreanair.reservation.domain.reservation.state;

import com.koreanair.reservation.domain.reservation.Reservation;

/**
 * State 패턴 — Reservation 의 생애주기를 상태 객체로 캡슐화한다.
 *
 * <p>모든 구체 상태는 8개 전이 이벤트를 선언하지만, 자신이 허용하는 전이만
 * 실제 로직을 구현하고 나머지는 {@link InvalidStateTransitionException} 을 던진다.
 *
 * <p>Iteration 1 실제 동작 경로:
 *   InitiatedState -> PendingPaymentState -> ConfirmedState
 * 나머지 상태는 declaration 만 제공하고 모든 메서드는 예외 (Iteration 2~4 에서 구현).
 */
public interface ReservationState {

    /** 현재 상태의 사람이 읽을 수 있는 이름 (예: "Confirmed"). */
    String name();

    // --- Reservation 생명주기 8개 이벤트 ---

    void enterPassengerInfo(Reservation ctx);

    void processPayment(Reservation ctx);

    void handlePaymentFailure(Reservation ctx);

    void issueTicket(Reservation ctx);

    void requestCancellation(Reservation ctx);

    void confirmCancellation(Reservation ctx);

    void requestRefund(Reservation ctx);

    void processRefundDecision(Reservation ctx, boolean approved);
}
