package com.koreanair.reservation.domain.reservation.state;

/**
 * 취소 요청 접수 상태 — Iteration 1 스텁.
 *
 * <p>FareRule 확인과 cancellationPenalty 계산이라는 중간 단계를 표현한다.
 * <p>Iteration 2 에서 구현 예정 전이:
 *   - confirmCancellation() -> CancelledState
 */
public class CancellationRequestedState extends AbstractReservationState {

    @Override
    public String name() {
        return "CancellationRequested";
    }

    // TODO(iter2): confirmCancellation(Reservation ctx) override.
    //              내부에서 Seat: Booked -> Available 연동도 같이.
}
