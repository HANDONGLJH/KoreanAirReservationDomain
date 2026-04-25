package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.domain.flight.FareRule;
import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.payment.PaymentStatus;
import com.koreanair.reservation.domain.reservation.Reservation;

/**
 * 4단계 결제 화면 — 결제수단·금액 표시 후 "결제" 버튼 클릭으로
 * {@link BookingController#confirmPayment(Reservation, FareRule, long, long)} 호출.
 *
 * <p>결제 성공 시 Reservation 은 PendingPayment → Confirmed 로 전이되고
 * 확정 화면으로 이동한다. 실패 시(Iteration 1 MockPaymentGateway 는 항상 성공 반환)
 * 에러 다이얼로그 후 승객 정보 화면으로 돌아간다.
 *
 * <p>Iteration 1 에서는 PaymentMethod 선택을 받더라도 실제 전달은 하지 않는다
 * (PaymentProcessor 가 CREDIT_CARD 로 하드코딩되어 있음). 발표 시연에서 "결제수단 선택 UI 는
 * 있지만 실제 라우팅은 Iteration 2" 라는 구분을 육성으로 보여주기 위함.
 */
public class PaymentPanel extends JPanel {

    private static final long DEFAULT_BASE_FARE = 450_000L;
    private static final long DEFAULT_TAX = 50_000L;

    private final JLabel pnrLabel = new JLabel(" ");
    private final JLabel amountLabel = new JLabel(" ");
    private final JComboBox<String> methodCombo = new JComboBox<>(new String[] {
            "신용카드", "계좌이체 (Iteration 2 예정)", "마일리지 (Iteration 3 예정)"
    });
    private final JButton payButton = new JButton("결제");
    private final JButton backButton = new JButton("뒤로");

    private final MainFrame frame;
    private final BookingController booking;
    private final SwingReservationUI ui;

    private Reservation reservation;
    private FareRule fareRule;

    public PaymentPanel(MainFrame frame, BookingController booking, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.booking = booking;
        this.ui = ui;

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 60, 24, 60));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("결제");
        title.setFont(title.getFont().deriveFont(18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(title, c);
        c.gridwidth = 1;

        c.gridy = 1; c.gridx = 0; form.add(new JLabel("PNR"), c);
        c.gridx = 1; form.add(pnrLabel, c);

        c.gridy = 2; c.gridx = 0; form.add(new JLabel("결제 금액"), c);
        c.gridx = 1;
        amountLabel.setFont(amountLabel.getFont().deriveFont(16f));
        form.add(amountLabel, c);

        c.gridy = 3; c.gridx = 0; form.add(new JLabel("결제 수단"), c);
        c.gridx = 1;
        // 기본 선택: 신용카드 (Iteration 1 에서 유일하게 실제 라우팅되는 항목)
        methodCombo.setPreferredSize(new Dimension(280, 28));
        form.add(methodCombo, c);

        add(form, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));
        backButton.setPreferredSize(new Dimension(120, 34));
        payButton.setPreferredSize(new Dimension(160, 34));
        footer.add(backButton);
        footer.add(payButton);
        add(footer, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.showPassenger());
        payButton.addActionListener(e -> doPay());
    }

    public void prepare(Reservation reservation, FareRule fareRule) {
        this.reservation = reservation;
        this.fareRule = fareRule;
        pnrLabel.setText(reservation != null ? reservation.getPnrNumber() : "-");
        long total = DEFAULT_BASE_FARE + DEFAULT_TAX;
        amountLabel.setText(String.format("%,d KRW  (기본운임 %,d + 세금 %,d)",
                total, DEFAULT_BASE_FARE, DEFAULT_TAX));
    }

    private void doPay() {
        if (reservation == null || fareRule == null) {
            ui.displayError("결제 대상 예약이 없습니다.");
            return;
        }
        try {
            Payment payment = booking.confirmPayment(
                    reservation, fareRule, DEFAULT_BASE_FARE, DEFAULT_TAX);
            if (payment != null && payment.getStatus() == PaymentStatus.PAID) {
                frame.onPaymentConfirmed(reservation, payment);
            } else {
                ui.displayError("결제가 실패했습니다. 다시 시도하세요.");
            }
        } catch (Exception ex) {
            ui.displayError("결제 처리 중 오류: " + ex.getMessage());
        }
    }
}
