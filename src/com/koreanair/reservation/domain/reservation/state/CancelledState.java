package com.koreanair.reservation.domain.reservation.state;

import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.reservation.ReservationStatus;

/**
 * 취소 확정 상태 — Iteration 1 스텁.
 *
 * <p>환불 불가 운임이면 이 상태가 최종 상태 (진입 후 종료).
 * <p>환불 가능 운임이면 requestRefund() 로 RefundRequestedState 전이.
 *
 * <p>Iteration 2 에서 구현 예정 전이:
 *   - requestRefund() -> RefundRequestedState  (Strategy 패턴과 연동: FareRule.isRefundable)
 */
public class CancelledState extends AbstractReservationState {

    @Override
    public String name() {
        return "Cancelled";
    }

    @Override
    public void requestRefund(Reservation ctx) {
        ctx.setState(new RefundRequestedState());
        ctx.updateStatus(ReservationStatus.REFUND_REQUESTED);
    }
}
