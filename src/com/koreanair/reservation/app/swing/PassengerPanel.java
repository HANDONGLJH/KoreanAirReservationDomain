package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.user.Member;

/**
 * 3단계 승객 정보 입력 화면.
 *
 * <p>이 화면 진입 시점에 {@link BookingController#initiateBooking(FlightSchedule)} 로 Reservation 을
 * 생성(Initiated)하고, "다음" 버튼 클릭 시 {@code setPassengerInfo} 를 호출해
 * Initiated → PendingPayment 전이를 유도한다. 실제 Passenger 엔티티는 Iteration 2 에서 도입 예정이므로
 * 여기서는 입력값을 받기만 하고 null 로 넘겨 전이만 트리거한다.
 */
public class PassengerPanel extends JPanel {

    private final JTextField nameField = new JTextField(20);
    private final JTextField passportField = new JTextField(20);
    private final JTextField birthField = new JTextField(20);

    private final JLabel selectedFlightLabel = new JLabel(" ");
    private final JButton nextButton = new JButton("다음");
    private final JButton backButton = new JButton("뒤로");

    private final MainFrame frame;
    private final BookingController booking;
    private final SwingReservationUI ui;

    private FlightSchedule selected;
    private Reservation reservation;

    public PassengerPanel(MainFrame frame, BookingController booking, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.booking = booking;
        this.ui = ui;

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 60, 24, 60));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("승객 정보 입력");
        title.setFont(title.getFont().deriveFont(18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(title, c);

        c.gridwidth = 1;
        c.gridy = 1; c.gridx = 0; form.add(new JLabel("선택 항공편"), c);
        c.gridx = 1; form.add(selectedFlightLabel, c);

        c.gridy = 2; c.gridx = 0; form.add(new JLabel("이름"), c);
        c.gridx = 1; form.add(nameField, c);

        c.gridy = 3; c.gridx = 0; form.add(new JLabel("여권번호"), c);
        c.gridx = 1; form.add(passportField, c);

        c.gridy = 4; c.gridx = 0; form.add(new JLabel("생년월일 (YYYY-MM-DD)"), c);
        c.gridx = 1; form.add(birthField, c);

        add(form, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));
        backButton.setPreferredSize(new Dimension(120, 34));
        nextButton.setPreferredSize(new Dimension(120, 34));
        footer.add(backButton);
        footer.add(nextButton);
        add(footer, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.showSearch());
        nextButton.addActionListener(e -> doNext());
    }

    /**
     * 검색 화면에서 항공편 선택 시 호출. Reservation 을 즉시 생성하여 State=Initiated 로 보여준다.
     */
    public void prepare(FlightSchedule selected, Member me) {
        this.selected = selected;
        Object[] row = SwingReservationUI.toTableRow(1, selected);
        selectedFlightLabel.setText(String.format("%s (%s → %s)", row[1], row[3], row[4]));

        // Reservation 생성 — State: Initiated
        this.reservation = booking.initiateBooking(selected);
        if (reservation != null && me != null) {
            reservation.setRequester(me);
            if (nameField.getText().trim().isEmpty() && me.getName() != null) {
                nameField.setText(me.getName());
            }
        }
        frame.onReservationCreated(reservation);
    }

    private void doNext() {
        if (reservation == null) {
            ui.displayError("예약이 생성되지 않았습니다. 검색 화면으로 돌아가 재시도하세요.");
            return;
        }
        if (nameField.getText().trim().isEmpty()) {
            ui.displayError("승객 이름을 입력하세요.");
            return;
        }
        // Iteration 1: Passenger 엔티티는 아직 도입되지 않아 null 로 전달 — State 전이만 트리거.
        // TODO(iter2): Passenger(name, passport, birth) 엔티티 생성 후 전달.
        booking.setPassengerInfo(reservation, null);
        frame.onPassengerInfoEntered(reservation);
    }
}
