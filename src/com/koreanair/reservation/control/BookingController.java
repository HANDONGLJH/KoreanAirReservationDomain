package com.koreanair.reservation.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.koreanair.reservation.domain.flight.Airport;
import com.koreanair.reservation.domain.flight.FareRule;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.flight.FlightStatus;
import com.koreanair.reservation.domain.passenger.Passenger;
import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.payment.PaymentStatus;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.user.Member;

/**
 * BookingController — Control 계층.
 *
 * <p>Iteration 1 Walking Skeleton: "Book Flight" Happy Path 를 오케스트레이션.
 * Search → Select → Enter Passenger Info → Validate Fare → Pay → Confirm.
 *
 * <p>기존 메서드 시그니처는 보존. 신규 Iteration 1 메서드는 별도 오버로드로 추가.
 *
 * <p>Iteration 2+ : Cancel, Refund 흐름을 여기에 추가 예정.
 */
public class BookingController {

    // --- Iteration 1 주입 의존성 (default = null, walking skeleton 에서만 사용) ---
    private AuthService authService;
    private FlightSearchService flightSearch;
    private PaymentProcessor paymentProcessor;

    public BookingController() {
    }

    /** Walking skeleton 전용 생성자. */
    public BookingController(AuthService authService,
                             FlightSearchService flightSearch,
                             PaymentProcessor paymentProcessor) {
        this.authService = authService;
        this.flightSearch = flightSearch;
        this.paymentProcessor = paymentProcessor;
    }

    // --- 기존 메서드 (시그니처 보존, 구현 미완 유지) ---

    public Object processSearch(Object searchCriteria) {
        return null;
    }

    public Reservation initiateBooking(Long flightId, String fareClass) {
        return null;
    }

    public Reservation initiateBooking(Long flightId, String fareClass, Long memberId) {
        return null;
    }

    public void setPassengerInfo(Long reservationId, Object passengerData) {
    }

    public void confirmInfo(Long reservationId) {
    }

    public void assignSeat(Long reservationId, String seatNumber) {
        // TODO(iter2): 좌석 배정 로직 — SeatInventory.reserve + SeatAssignment 생성.
    }

    public void processCancellation(String pnr) {
        // TODO(iter2): Reservation.findByPnr + requestCancellation → confirmCancellation 흐름.
    }

    public void changeFlightStatus(String flightNumber, FlightStatus newStatus) {
    }

    public FlightSchedule createSchedule(Object scheduleData) {
        return null;
    }

    public boolean authenticateAdmin(String adminId, String password) {
        return false;
    }

    public Object authenticateMember(String skypassNumber, String password) {
        return null;
    }

    public Object getBookingHistory(Long memberId) {
        return null;
    }

    public Object getTicketDetail(String pnr) {
        return null;
    }

    public boolean verifyGuestIdentity(String pnr, String name, String email) {
        return false;
    }

    public boolean reconfirmGuestIdentity(String pnr, String email) {
        return false;
    }

    // --- Iteration 1 Walking Skeleton 전용 메서드 (신규 시그니처) ---

    /** 1) 검색 — 공항 코드 + 일자로 직항편 조회. */
    public List<FlightSchedule> processSearch(String fromAirportCode,
                                              String toAirportCode,
                                              LocalDate date) {
        if (flightSearch == null) {
            return new ArrayList<>();
        }
        return flightSearch.search(fromAirportCode, toAirportCode, date);
    }

    /** 2) 선택된 flight 로 Reservation 생성 (Initiated 상태). */
    public Reservation initiateBooking(FlightSchedule selected) {
        if (selected == null || !selected.isAvailableForBooking()) {
            throw new IllegalArgumentException("예약 가능한 직항편을 선택해야 합니다.");
        }
        if (selected.getFlight() == null || selected.getFlight().getRoute() == null) {
            throw new IllegalArgumentException("항공편 경로 정보가 없습니다.");
        }
        Reservation r = new Reservation();
        r.setReservationNumber("PNR-" + System.currentTimeMillis());
        // Iteration 1: FlightSchedule 을 ReservationItem 으로 감싸지 않고 일단 보관 생략.
        // TODO(iter2): ReservationItem 생성 + 운임 선택 + SeatInventory.reserve 연동.
        return r;
    }

    /** 3) 승객 정보 입력 — State: Initiated → PendingPayment. */
    public void setPassengerInfo(Reservation reservation, Passenger passenger) {
        reservation.enterPassengerInfo(passenger);
    }

    /**
     * 4~5) 운임 검증 + 결제.
     *   - 운임 규칙 검증 실패 시 IllegalArgumentException.
     *   - 결제 실패 시 Reservation 을 handlePaymentFailure 로 전이.
     *   - 결제 성공 시 processPayment 호출로 Reservation: PendingPayment → Confirmed.
     *
     * @return 결제 완료된 Payment 객체 (성공 / 실패 여부는 payment.getStatus() 로 확인).
     */
    public Payment confirmPayment(Reservation reservation,
                                  FareRule fareRule,
                                  long baseFare,
                                  long tax) {
        if (!paymentProcessor.validateFareRule(fareRule)) {
            throw new IllegalArgumentException("운임 규칙 검증 실패: " + fareRule);
        }
        long total = paymentProcessor.calculateTotalAmount(baseFare, tax);
        Payment payment = paymentProcessor.processPaymentCharge(total);

        if (payment.getStatus() == PaymentStatus.PAID) {
            reservation.addPayment(payment);
            reservation.processPayment();      // State 전이
        } else {
            reservation.handlePaymentFailure(); // State 전이
        }
        return payment;
    }

    /** 현재 로그인된 회원. */
    public Member currentMember() {
        return authService != null ? authService.currentMember() : null;
    }

    // --- airport helper (walking skeleton 편의) ---

    public static Airport airport(String code, String name, String city) {
        // TODO(iter1): Airport 생성자가 비어 있어 factory 로 최소 속성만 세팅.
        //              정식 생성자/세터는 도메인 측에서 보강 필요.
        return new Airport();
    }
}
