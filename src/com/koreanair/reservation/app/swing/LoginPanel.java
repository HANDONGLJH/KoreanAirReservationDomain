package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.koreanair.reservation.control.AuthService;
import com.koreanair.reservation.domain.user.Member;

/**
 * 1단계 로그인 화면 — 회원번호 + 이름(또는 비밀번호 stub) 입력을 받아 {@link AuthService#login}
 * 호출 결과에 따라 다음 화면으로 진행한다.
 *
 * <p>Iteration 1 AuthService 는 password 검증을 하지 않고 존재 여부만 확인하므로,
 * 이름 필드는 발표 시연에서 "내 회원번호/이름 입력" 맥락을 유지하기 위한 UX 장치로만 쓴다.
 */
public class LoginPanel extends JPanel {

    private final JTextField memberNumberField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JLabel messageLabel = new JLabel(" ");
    private final JButton loginButton = new JButton("로그인");
    private final JButton registerButton = new JButton("회원가입");

    private final MainFrame frame;
    private final AuthService authService;
    private final SwingReservationUI ui;

    public LoginPanel(MainFrame frame, AuthService authService, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.authService = authService;
        this.ui = ui;
        buildLayout();
        wireEvents();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("대한항공 예약 시스템 — 로그인", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(20f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(title, c);

        c.gridwidth = 1;
        c.gridy = 1; c.gridx = 0;
        form.add(new JLabel("회원번호 (Skypass)"), c);
        c.gridx = 1;
        form.add(memberNumberField, c);
        memberNumberField.setText("SKY-000-001");  // 발표 편의상 pre-fill

        c.gridy = 2; c.gridx = 0;
        form.add(new JLabel("비밀번호"), c);
        c.gridx = 1;
        form.add(passwordField, c);
        passwordField.setText("pw-stub");

        c.gridy = 3; c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        loginButton.setPreferredSize(new Dimension(120, 36));
        form.add(loginButton, c);

        c.gridy = 4;
        registerButton.setPreferredSize(new Dimension(120, 34));
        registerButton.setContentAreaFilled(false);
        form.add(registerButton, c);

        c.gridy = 5;
        messageLabel.setForeground(new java.awt.Color(0xD32F2F));
        form.add(messageLabel, c);

        add(form, BorderLayout.CENTER);

        JLabel hint = new JLabel(
                "샘플 회원번호: SKY-000-001 / 이름: 김정욱 (Iteration 1 walking skeleton)",
                SwingConstants.CENTER);
        hint.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        hint.setForeground(new java.awt.Color(0x757575));
        add(hint, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            RegistrationDialog dialog = new RegistrationDialog(null, authService);
            dialog.setVisible(true);
        });
    }

    private void wireEvents() {
        loginButton.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin()); // Enter 키
    }

    private void doLogin() {
        String skypass = memberNumberField.getText().trim();
        String pw = new String(passwordField.getPassword());
        if (skypass.isEmpty()) {
            messageLabel.setText("회원번호를 입력하세요.");
            return;
        }
        Member m = authService.login(skypass, pw);
        if (m == null) {
            messageLabel.setText("로그인 실패 — 등록된 회원이 아닙니다.");
            ui.displayError("등록되지 않은 회원번호입니다: " + skypass);
            return;
        }
        messageLabel.setText(" ");
        frame.onLoginSuccess(m);
    }

    /** MainFrame 이 최초 등장 시 focus 를 놓고 싶을 때 호출. */
    public void focusFirst() {
        memberNumberField.requestFocusInWindow();
    }
}
