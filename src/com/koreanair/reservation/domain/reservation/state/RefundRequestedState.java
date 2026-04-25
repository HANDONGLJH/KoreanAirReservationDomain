package com.koreanair.reservation.domain.reservation.state;

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

    // TODO(iter2): processRefundDecision(Reservation ctx, boolean approved) override.
}
