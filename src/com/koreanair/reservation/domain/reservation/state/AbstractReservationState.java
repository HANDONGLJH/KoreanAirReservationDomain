package com.koreanair.reservation.domain.reservation.state;

import com.koreanair.reservation.domain.reservation.Reservation;

/**
 * 공통 기본 클래스 — 모든 메서드의 디폴트 동작은 {@link InvalidStateTransitionException}.
 * 구체 상태는 자신이 허용하는 메서드만 override 한다.
 *
 * <p>왜 abstract class 를 끼우는가: 인터페이스만으로는 8개 구체 상태 각각에서
 * "거부" 로직을 중복 작성해야 한다. 디폴트 거부를 여기 한 곳에 두고, 허용만 override 하면
 * 각 구체 상태 클래스는 전이 허용 1~2건만 남아 읽기 쉬워진다.
 */
public abstract class AbstractReservationState implements ReservationState {

    @Override
    public void enterPassengerInfo(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "enterPassengerInfo");
    }

    @Override
    public void processPayment(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "processPayment");
    }

    @Override
    public void handlePaymentFailure(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "handlePaymentFailure");
    }

    @Override
    public void issueTicket(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "issueTicket");
    }

    @Override
    public void requestCancellation(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "requestCancellation");
    }

    @Override
    public void confirmCancellation(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "confirmCancellation");
    }

    @Override
    public void requestRefund(Reservation ctx) {
        throw new InvalidStateTransitionException(name(), "requestRefund");
    }

    @Override
    public void processRefundDecision(Reservation ctx, boolean approved) {
        throw new InvalidStateTransitionException(name(), "processRefundDecision");
    }
}
