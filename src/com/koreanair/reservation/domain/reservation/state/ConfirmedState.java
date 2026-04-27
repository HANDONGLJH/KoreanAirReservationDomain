package com.koreanair.reservation.domain.reservation.state;

import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.reservation.ReservationStatus;

/**
 * 확정 상태 — PNR 발급 후 상태. Ticketed 또는 CancellationRequested 로 분기.
 *
 * <p>Iteration 1: Happy Path 의 종착점. issueTicket() / requestCancellation() 는 선언만.
 * <p>Iteration 2: requestCancellation() 경로 실제 구현 (Strategy 패턴 환불정책과 연동).
 */
public class ConfirmedState extends AbstractReservationState {

    @Override
    public String name() {
        return "Confirmed";
    }

    @Override
    public void issueTicket(Reservation ctx) {
        // TODO(iter2): Ticket 객체 생성 + e-Ticket 번호 발급 연동
        ctx.setState(new TicketedState());
        ctx.updateStatus(ReservationStatus.TICKETED);
    }

    @Override
    public void requestCancellation(Reservation ctx) {
        // TODO(iter2): FareRule.isRefundable 1차 확인 후 전이
        ctx.setState(new CancellationRequestedState());
        ctx.updateStatus(ReservationStatus.CANCELLATION_REQUESTED);
    }
}
