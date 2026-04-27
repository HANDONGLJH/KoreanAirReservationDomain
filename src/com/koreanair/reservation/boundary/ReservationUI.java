package com.koreanair.reservation.boundary;

import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.flight.FlightSchedule;

public interface ReservationUI {

    void displaySearchResults(Object flightList);

    void displaySeatMap(Object aircraftType);

    default void displayItineraryDetail(FlightSchedule schedule) {
    }

    void displayBookingConfirmation(String pnrNumber);

    /**
     * Iteration 1 Walking Skeleton 전용 — Reservation + Payment 상세를 콘솔에 출력.
     * TODO(iter2): Swing UI 전환 시 이 default 구현을 override.
     */
    default void displayBookingConfirmation(Reservation reservation, Payment payment) {
        displayBookingConfirmation(reservation != null ? reservation.getPnrNumber() : null);
    }

    /**
     * Iteration 1 Walking Skeleton 전용 — 에러 메시지 출력.
     * TODO(iter2): 에러 유형별 색상/배경 구분은 GUI 단계에서 추가.
     */
    default void displayError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
