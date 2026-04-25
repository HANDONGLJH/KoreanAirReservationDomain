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

import com.koreanair.reservation.domain.payment.Payment;
import com.koreanair.reservation.domain.reservation.Reservation;

/**
 * 5단계 예약 확정 화면. PNR·상태·결제 금액을 표시한 뒤 "처음으로" 클릭 시 로그인 화면으로 복귀.
 *
 * <p>State 패턴 시연의 종착지 — 이 화면 진입 시점에 Reservation.currentState 가 Confirmed 여야 하며,
 * 상단 {@link StateBadge} 가 "Confirmed" 를 강조 표시하고 있어야 한다.
 */
public class ConfirmationPanel extends JPanel {

    private final JLabel pnrLabel = new JLabel(" ");
    private final JLabel stateLabel = new JLabel(" ");
    private final JLabel amountLabel = new JLabel(" ");
    private final JLabel paymentStatusLabel = new JLabel(" ");

    private final JButton homeButton = new JButton("처음으로");
    private final JButton ticketButton = new JButton("e-Ticket 발급 (Iteration 2 예정)");

    private final MainFrame frame;

    public ConfirmationPanel(MainFrame frame) {
        super(new BorderLayout());
        this.frame = frame;

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("예약 확정");
        title.setFont(title.getFont().deriveFont(22f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(title, c);
        c.gridwidth = 1;

        c.gridy = 1; c.gridx = 0; form.add(new JLabel("PNR"), c);
        pnrLabel.setFont(pnrLabel.getFont().deriveFont(16f));
        c.gridx = 1; form.add(pnrLabel, c);

        c.gridy = 2; c.gridx = 0; form.add(new JLabel("예약 상태"), c);
        stateLabel.setFont(stateLabel.getFont().deriveFont(16f));
        c.gridx = 1; form.add(stateLabel, c);

        c.gridy = 3; c.gridx = 0; form.add(new JLabel("결제 상태"), c);
        c.gridx = 1; form.add(paymentStatusLabel, c);

        c.gridy = 4; c.gridx = 0; form.add(new JLabel("결제 금액"), c);
        c.gridx = 1; form.add(amountLabel, c);

        add(form, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));
        ticketButton.setEnabled(false);
        homeButton.setPreferredSize(new Dimension(140, 36));
        footer.add(ticketButton);
        footer.add(homeButton);
        add(footer, BorderLayout.SOUTH);

        homeButton.addActionListener(e -> frame.reset());
    }

    public void prepare(Reservation reservation, Payment payment) {
        pnrLabel.setText(reservation != null ? reservation.getPnrNumber() : "-");
        stateLabel.setText(reservation != null ? reservation.getStateName() : "-");
        if (payment != null) {
            paymentStatusLabel.setText(String.valueOf(payment.getStatus()));
            amountLabel.setText(payment.getAmount() != null
                    ? String.format("%,d KRW", payment.getAmount().longValueExact())
                    : "-");
        } else {
            paymentStatusLabel.setText("-");
            amountLabel.setText("-");
        }
    }
}
