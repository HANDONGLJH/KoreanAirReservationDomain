package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.koreanair.reservation.app.sample.SampleData;
import com.koreanair.reservation.app.sample.SampleData.SeedResult;
import com.koreanair.reservation.control.AuthService;
import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.control.FlightSearchService;
import com.koreanair.reservation.control.PaymentProcessor;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.user.Member;

/**
 * Swing GUI 의 최상위 컨테이너. CardLayout 으로 5개 패널을 교체하며,
 * 상단에는 현재 Reservation State 를 강조 표시하는 {@link StateBadge} 를 고정 배치한다.
 *
 * <p>패널 간 전이는 모두 이 클래스의 콜백(onXxx) 메서드를 통해 이루어진다.
 * 각 패널은 자신의 입력 검증과 Control 호출만 담당하고, 다음 화면 이동은
 * MainFrame 에 위임하여 결합도를 낮춘다.
 */
public class MainFrame extends JFrame {

    private static final String CARD_LOGIN = "login";
    private static final String CARD_SEARCH = "search";
    private static final String CARD_PASSENGER = "passenger";
    private static final String CARD_PAYMENT = "payment";
    private static final String CARD_CONFIRMATION = "confirmation";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final StateBadge stateBadge = new StateBadge();

    // --- Control / Boundary 의존성 (SwingApp 에서 주입) ---
    private final AuthService authService;
    private final FlightSearchService flightSearch;
    private final BookingController booking;
    private final SwingReservationUI ui;
    private final SeedResult seed;

    // --- 패널 ---
    private final LoginPanel loginPanel;
    private final SearchPanel searchPanel;
    private final PassengerPanel passengerPanel;
    private final PaymentPanel paymentPanel;
    private final ConfirmationPanel confirmationPanel;

    // --- 세션 상태 ---
    private Member loggedInMember;
    @SuppressWarnings("unused")
    private Reservation currentReservation;

    public MainFrame(AuthService authService,
                     FlightSearchService flightSearch,
                     PaymentProcessor paymentProcessor,
                     BookingController booking,
                     SwingReservationUI ui,
                     SeedResult seed) {
        super("대한항공 예약 시스템 — Iteration 1 (Swing)");
        this.authService = authService;
        this.flightSearch = flightSearch;
        this.booking = booking;
        this.ui = ui;
        this.seed = seed;

        // 레이아웃: 상단 StateBadge + 중앙 CardLayout
        setLayout(new BorderLayout());
        add(stateBadge, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);

        // 패널 초기화 — MainFrame 참조를 넘겨 콜백 연결.
        loginPanel = new LoginPanel(this, authService, ui);
        searchPanel = new SearchPanel(this, booking, ui);
        passengerPanel = new PassengerPanel(this, booking, ui);
        paymentPanel = new PaymentPanel(this, booking, ui);
        confirmationPanel = new ConfirmationPanel(this);

        cards.add(loginPanel, CARD_LOGIN);
        cards.add(searchPanel, CARD_SEARCH);
        cards.add(passengerPanel, CARD_PASSENGER);
        cards.add(paymentPanel, CARD_PAYMENT);
        cards.add(confirmationPanel, CARD_CONFIRMATION);

        stateBadge.reset();
        ui.setParent(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(760, 520));
        pack();
        setLocationRelativeTo(null);
    }

    // --- 화면 전환 헬퍼 ---

    public void showLogin() { cardLayout.show(cards, CARD_LOGIN); }
    public void showSearch() { cardLayout.show(cards, CARD_SEARCH); }
    public void showPassenger() { cardLayout.show(cards, CARD_PASSENGER); }
    public void showPayment() { cardLayout.show(cards, CARD_PAYMENT); }
    public void showConfirmation() { cardLayout.show(cards, CARD_CONFIRMATION); }

    // --- 콜백: 각 패널이 다음 단계 진입을 요청할 때 호출 ---

    public void onLoginSuccess(Member m) {
        this.loggedInMember = m;
        stateBadge.reset();   // 아직 Reservation 없음
        showSearch();
    }

    public void onFlightSelected(FlightSchedule selected) {
        passengerPanel.prepare(selected, loggedInMember);
        showPassenger();
    }

    /** PassengerPanel 에서 Reservation 이 새로 만들어진 직후 호출 (State: Initiated). */
    public void onReservationCreated(Reservation reservation) {
        this.currentReservation = reservation;
        if (reservation != null) stateBadge.setCurrentState(reservation.getStateName());
    }

    /** 승객 정보 입력 완료 — State: Initiated → PendingPayment. */
    public void onPassengerInfoEntered(Reservation reservation) {
        this.currentReservation = reservation;
        if (reservation != null) stateBadge.setCurrentState(reservation.getStateName());
        paymentPanel.prepare(reservation, seed.defaultFareRule);
        showPayment();
    }

    /** 결제 성공 — State: PendingPayment → Confirmed. */
    public void onPaymentConfirmed(Reservation reservation, Payment payment) {
        this.currentReservation = reservation;
        if (reservation != null) stateBadge.setCurrentState(reservation.getStateName());
        confirmationPanel.prepare(reservation, payment);
        showConfirmation();
    }

    /** 확정 화면의 "처음으로" 클릭 — 새 AuthService session 을 유지한 채 로그인 화면부터 재시작. */
    public void reset() {
        this.currentReservation = null;
        this.loggedInMember = null;
        authService.logout();
        stateBadge.reset();
        showLogin();
        loginPanel.focusFirst();
    }

    // --- Iteration 1 편의: seed / flightSearch 노출 (테스트·확장 시 참조) ---

    public SeedResult seed() { return seed; }
    public FlightSearchService flightSearch() { return flightSearch; }
    public BookingController booking() { return booking; }
}
