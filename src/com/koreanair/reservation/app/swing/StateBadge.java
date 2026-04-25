package com.koreanair.reservation.app.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Iteration 1 GUI 전용 — 현재 Reservation State 를 헤더 라벨로 시각화.
 *
 * <p>발표 평가 포인트인 State 전이 흐름을 GUI 에서도 바로 보이게 하는 것이 목적이다.
 * Reservation 이 아직 생성되지 않은 초기 화면(로그인·검색)에서는 "N/A" 로 표시하고,
 * Initiated / PendingPayment / Confirmed 로 진행될수록 해당 단계가 강조된다.
 *
 * <p>TODO(iter2): Observer 패턴 도입 시 Reservation 상태 변경 이벤트 구독으로 교체.
 */
public class StateBadge extends JPanel {

    private static final String[] STATES = { "Initiated", "PendingPayment", "Confirmed" };

    private final JLabel[] labels = new JLabel[STATES.length];

    public StateBadge() {
        super(new GridLayout(1, STATES.length, 6, 0));
        setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        for (int i = 0; i < STATES.length; i++) {
            JLabel l = new JLabel(STATES[i], SwingConstants.CENTER);
            l.setOpaque(true);
            l.setFont(l.getFont().deriveFont(Font.BOLD, 12f));
            l.setBorder(BorderFactory.createLineBorder(new Color(0xBDBDBD), 1));
            l.setBackground(new Color(0xF5F5F5));
            l.setForeground(new Color(0x616161));
            l.setPreferredSize(new Dimension(140, 28));
            labels[i] = l;
            add(l);
        }
    }

    /**
     * 현재 상태를 강조. {@code stateName} 은 {@link com.koreanair.reservation.domain.reservation.Reservation#getStateName()}
     * 결과를 그대로 받는다. null 또는 해당되지 않는 이름이면 전체 비강조.
     */
    public void setCurrentState(String stateName) {
        for (int i = 0; i < STATES.length; i++) {
            boolean active = STATES[i].equalsIgnoreCase(stateName);
            labels[i].setBackground(active ? new Color(0x1976D2) : new Color(0xF5F5F5));
            labels[i].setForeground(active ? Color.WHITE : new Color(0x616161));
        }
    }

    /** Reservation 이 아직 없는 초기 화면 전용 — 전체를 비활성 표시. */
    public void reset() {
        setCurrentState(null);
    }
}
