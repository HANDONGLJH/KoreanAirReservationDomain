package com.koreanair.reservation.app;

import java.time.LocalDate;
import java.util.List;

import com.koreanair.reservation.app.sample.SampleData;
import com.koreanair.reservation.app.sample.SampleData.SeedResult;
import com.koreanair.reservation.boundary.PaymentGatewayInterface;
import com.koreanair.reservation.boundary.ReservationUI;
import com.koreanair.reservation.control.AuthService;
import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.control.FlightSearchService;
import com.koreanair.reservation.control.PaymentProcessor;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.user.Member;

/**
 * Iteration 1 Walking Skeleton 실행 드라이버.
 *
 * <p>Happy Path 시연: 로그인 → 검색 → 선택 → 승객 정보 → 결제 → 확정.
 * <p>콘솔에 State 전이 2건 (Initiated → PendingPayment, PendingPayment → Confirmed) 이
 * {@code [STATE] X -> Y} 로 출력되는 것을 확인한다.
 *
 * <p>TODO(iter1 발표 준비): 실패 경로 시연 1건 추가 — gateway 를 fail 로 바꿔
 * {@code PendingPayment → Cancelled} 전이도 보여주면 State 패턴 설명 서사가 완성된다.
 */
public final class App {

    private App() {}

    public static void main(String[] args) {
        // --- 1) 의존성 주입 ---
        AuthService auth = new AuthService();
        FlightSearchService search = new FlightSearchService();
        PaymentGatewayInterface gateway = new MockPaymentGateway();
        PaymentProcessor paymentProcessor = new PaymentProcessor(gateway);
        BookingController booking = new BookingController(auth, search, paymentProcessor);
        ReservationUI ui = new ConsoleReservationUI();

        // --- 2) 샘플 데이터 seed (회원·공항·항공편 3건·운임 규칙) ---
        SeedResult seed = SampleData.seedAll(auth, search);

        // --- 3) 로그인 ---
        Member me = auth.login("SKY-000-001", "pw-stub");
        if (me == null) {
            ui.displayError("로그인 실패");
            return;
        }
        System.out.println("[LOGIN] " + me.getName());

        // --- 4) 검색 ---
        List<FlightSchedule> results = booking.processSearch("ICN", "NRT", LocalDate.of(2026, 5, 1));
        ui.displaySearchResults(results);
        if (results.isEmpty()) return;

        // --- 5) 선택 (첫 번째 결과) ---
        FlightSchedule selected = seed.firstSchedule;   // Iteration 2 에서 검색 결과 index 로 교체.
        Reservation reservation = booking.initiateBooking(selected);
        reservation.setRequester(me);
        System.out.println("[BOOK] 예약 개시: PNR=" + reservation.getPnrNumber()
                + " state=" + reservation.getStateName());

        // --- 6) 승객 정보 입력 ---     (State: Initiated → PendingPayment)
        //     Iteration 1: Passenger 엔티티는 아직 생성하지 않고 null 로 호출 — State 전이만 트리거.
        //     TODO(iter2): Member 정보 → Passenger 변환 로직 추가.
        booking.setPassengerInfo(reservation, null);

        // --- 7) 결제 ---               (State: PendingPayment → Confirmed)
        Payment payment = booking.confirmPayment(reservation, seed.defaultFareRule, 450_000L, 50_000L);

        // --- 8) 확정 화면 ---
        ui.displayBookingConfirmation(reservation, payment);
    }
}
