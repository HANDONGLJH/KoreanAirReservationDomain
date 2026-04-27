package com.koreanair.reservation.domain.reservation.state;

import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.reservation.ReservationStatus;

/**
 * 환불 요청 접수 상태 — Iteration 1 스텁.
 *
 * <p>RefundHandler 가 RefundPolicy (Strategy) 를 통해 환불 금액을 산정한 뒤 승인 / 거부를 결정.
 * <p>Iteration 2 에서 구현 예정 전이:
 *   - processRefundDecision(approved=true)  -> RefundedState
 *   - processRefundDecision(approved=false) -> CancelledState
 */
public class RefundRequestedState extends AbstractReservationState {

    @Override
    public String name() {
        return "RefundRequested";
    }

    @Override
    public void processRefundDecision(Reservation ctx, boolean approved) {
        if (approved) {
            ctx.setState(new RefundedState());
            ctx.updateStatus(ReservationStatus.REFUNDED);
        } else {
            ctx.setState(new CancelledState());
            ctx.updateStatus(ReservationStatus.CANCELLED);
        }
    }
}
