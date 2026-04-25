package com.koreanair.reservation.domain.reservation.state;

/**
 * 발권 완료 상태 — Iteration 1 스텁.
 *
 * <p>Iteration 1 에서는 Happy Path 가 ConfirmedState 에서 멈추므로 이 상태로는 진입하지 않는다.
 * <p>Iteration 2 에서 ConfirmedState.issueTicket() 이 실제 전이를 수행하면 여기로 진입한다.
 * <p>그 시점에 구현해야 할 것:
 *   - requestCancellation() -> CancellationRequestedState (발권 수수료 적용)
 */
public class TicketedState extends AbstractReservationState {

    @Override
    public String name() {
        return "Ticketed";
    }

    // TODO(iter2): requestCancellation(Reservation ctx) override 구현.
    //              AbstractReservationState 의 디폴트가 InvalidStateTransitionException 을 던진다.
}
