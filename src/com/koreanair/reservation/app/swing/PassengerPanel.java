package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingConstants;

import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.passenger.Passenger;
import com.koreanair.reservation.domain.passenger.PassengerType;
import com.koreanair.reservation.domain.reservation.Reservation;
import com.koreanair.reservation.domain.user.Member;

public class PassengerPanel extends JPanel {

    private final JTextField nameField = new JTextField(20);
    private final JTextField passportField = new JTextField(20);
    private final JTextField birthField = new JTextField(20);
    private final JLabel flightInfoLabel = new JLabel(" ");
    private final JButton nextButton = new JButton("다음 단계 →");
    private final JButton backButton = new JButton("← 뒤로");

    private final MainFrame frame;
    private final BookingController booking;
    private final SwingReservationUI ui;

    private FlightSchedule selected;
    private Reservation reservation;
    private Member member;

    public PassengerPanel(MainFrame frame, BookingController booking, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.booking = booking;
        this.ui = ui;
        setBackground(ModernUI.BACKGROUND);

        buildContent();
    }

    private void buildContent() {
        JPanel card = new JPanel(new GridBagLayout());
        ModernUI.styleCard(card);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        JLabel stepLabel = new JLabel("STEP 2");
        stepLabel.setFont(ModernUI.FONT_SMALL);
        stepLabel.setForeground(ModernUI.PRIMARY);
        stepLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        card.add(stepLabel, c);

        JLabel title = new JLabel("승객 정보 입력");
        title.setFont(ModernUI.FONT_HEADING);
        title.setForeground(ModernUI.TEXT_PRIMARY);
        c.gridy = 1;
        card.add(title, c);

        JLabel flightLabel = new JLabel("선택 항공편");
        flightLabel.setFont(ModernUI.FONT_SMALL);
        flightLabel.setForeground(ModernUI.TEXT_SECONDARY);
        c.gridy = 2; c.gridx = 0; c.gridwidth = 1;
        card.add(flightLabel, c);
        c.gridx = 1;
        flightInfoLabel.setFont(ModernUI.FONT_BODY);
        flightInfoLabel.setForeground(ModernUI.PRIMARY);
        card.add(flightInfoLabel, c);

        c.gridwidth = 1;
        c.gridy = 3; c.gridx = 0;
        card.add(new JLabel("이름 (Name)"), c);
        c.gridx = 1;
        ModernUI.styleTextField(nameField);
        card.add(nameField, c);

        c.gridy = 4; c.gridx = 0;
        card.add(new JLabel("여권번호 (Passport)"), c);
        c.gridx = 1;
        ModernUI.styleTextField(passportField);
        card.add(passportField, c);

        c.gridy = 5; c.gridx = 0;
        card.add(new JLabel("생년월일 (YYYY-MM-DD)"), c);
        c.gridx = 1;
        ModernUI.styleTextField(birthField);
        card.add(birthField, c);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(ModernUI.BACKGROUND);
        c.gridx = 0; c.gridy = 0;
        centerWrap.add(card, c);

        add(centerWrap, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(ModernUI.CARD_BG);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ModernUI.BORDER));

        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        rightBtns.setBackground(ModernUI.CARD_BG);

        ModernUI.styleButtonSecondary(backButton);
        rightBtns.add(backButton);

        ModernUI.styleButton(nextButton);
        rightBtns.add(nextButton);

        footer.add(rightBtns, BorderLayout.EAST);
        footer.setPreferredSize(new Dimension(0, 54));
        add(footer, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.showSearch());
        nextButton.addActionListener(e -> doNext());
    }

    public void prepare(FlightSchedule selected, Member me) {
        this.selected = selected;
        this.member = me;
        Object[] row = SwingReservationUI.toTableRow(1, selected);
        flightInfoLabel.setText(String.format("%s (%s → %s)", row[1], row[3], row[4]));

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
            ui.displayError("예약이 생성되지 않았습니다.");
            return;
        }
        if (nameField.getText().trim().isEmpty()) {
            ui.displayError("승객 이름을 입력하세요.");
            return;
        }
        try {
            java.time.LocalDate birthDate = java.time.LocalDate.parse(birthField.getText().trim());
            Passenger passenger = Passenger.create(
                    nameField.getText().trim(),
                    member != null ? member.getEmail() : null,
                    passportField.getText().trim(),
                    birthDate,
                    PassengerType.ADULT);
            booking.setPassengerInfo(reservation, passenger);
            frame.onPassengerInfoEntered(reservation);
        } catch (java.time.format.DateTimeParseException ex) {
            ui.displayError("생년월일 형식이 올바르지 않습니다. 예: 1999-01-31");
        } catch (IllegalArgumentException ex) {
            ui.displayError(ex.getMessage());
        }
    }
}