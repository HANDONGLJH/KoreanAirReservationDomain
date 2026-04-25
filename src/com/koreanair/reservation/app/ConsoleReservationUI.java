package com.koreanair.reservation.app;

import java.util.List;

import com.koreanair.reservation.boundary.ReservationUI;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.reservation.Reservation;

/**
 * ReservationUI 콘솔 구현 — Iteration 1 Walking Skeleton 전용.
 *
 * <p>여기서 GUI 를 도입하지 않는 이유는 Walking Skeleton 단계에서는 "비즈니스 로직이 끝에서 끝까지
 * 연결되는가" 가 검증 목표이기 때문. 시각화는 그 다음 단계의 근육에 해당한다.
 *
 * <p>TODO(iter2): Swing UI 로 교체 — displaySeatMap, promptGuestIdentity, displayCancellationPreview.
 */
public class ConsoleReservationUI implements ReservationUI {

    @Override
    public void displaySearchResults(Object flightList) {
        System.out.println("=== 검색 결과 ===");
        if (flightList instanceof List<?>) {
            List<?> list = (List<?>) flightList;
            if (list.isEmpty()) {
                System.out.println("(해당 조건의 항공편이 없습니다)");
                return;
            }
            int i = 1;
            for (Object o : list) {
                if (o instanceof FlightSchedule) {
                    FlightSchedule f = (FlightSchedule) o;
                    System.out.printf(" [%d] scheduleId=%s status=%s%n",
                            i++, f.getScheduleId(), f.getStatus());
                } else {
                    System.out.printf(" [%d] %s%n", i++, o);
                }
            }
        } else {
            System.out.println(" " + flightList);
        }
    }

    @Override
    public void displaySeatMap(Object aircraftType) {
        // TODO(iter2): 좌석맵 렌더링 — AircraftType.getSeatLayout() 기반.
        System.out.println("=== 좌석맵 (Iteration 2) ===");
    }

    @Override
    public void displayBookingConfirmation(String pnrNumber) {
        System.out.println("=== 예약 확정 ===");
        System.out.println(" PNR : " + pnrNumber);
    }

    @Override
    public void displayBookingConfirmation(Reservation reservation, Payment payment) {
        System.out.println("=== 예약 확정 ===");
        System.out.println(" PNR : " + reservation.getPnrNumber());
        System.out.println(" 상태: " + reservation.getStateName());
        if (payment != null) {
            System.out.println(" 결제: " + payment.getStatus() + " / " + payment.getAmount() + " KRW");
        }
    }
}
