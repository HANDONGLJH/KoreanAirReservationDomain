package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.koreanair.reservation.control.AuthService;
import com.koreanair.reservation.domain.user.Member;

public class RegistrationDialog extends JDialog {

    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField memberNumberField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField confirmPasswordField = new JPasswordField(20);
    private final JLabel messageLabel = new JLabel(" ");

    private final AuthService authService;
    private boolean registered = false;

    public RegistrationDialog(JDialog parent, AuthService authService) {
        super(parent, "회원 가입", true);
        this.authService = authService;
        buildLayout();
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("회원 가입", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(title, c);
        c.gridwidth = 1;

        c.gridy = 1; c.gridx = 0; form.add(new JLabel("이름"), c);
        c.gridx = 1; form.add(nameField, c);

        c.gridy = 2; c.gridx = 0; form.add(new JLabel("이메일"), c);
        c.gridx = 1; form.add(emailField, c);

        c.gridy = 3; c.gridx = 0; form.add(new JLabel("회원번호 (Skypass)"), c);
        c.gridx = 1; form.add(memberNumberField, c);

        c.gridy = 4; c.gridx = 0; form.add(new JLabel("비밀번호"), c);
        c.gridx = 1; form.add(passwordField, c);

        c.gridy = 5; c.gridx = 0; form.add(new JLabel("비밀번호 확인"), c);
        c.gridx = 1; form.add(confirmPasswordField, c);

        c.gridy = 6; c.gridx = 0; c.gridwidth = 2;
        messageLabel.setForeground(new java.awt.Color(0xD32F2F));
        form.add(messageLabel, c);

        JPanel btnPanel = new JPanel(new GridBagLayout());
        c.gridy = 7; c.gridwidth = 1; c.anchor = GridBagConstraints.CENTER;
        JButton cancelBtn = new JButton("취소");
        JButton registerBtn = new JButton("회원가입");
        registerBtn.setPreferredSize(new Dimension(120, 34));
        cancelBtn.setPreferredSize(new Dimension(120, 34));
        c.gridx = 0; btnPanel.add(cancelBtn, c);
        c.gridx = 1; btnPanel.add(registerBtn, c);
        form.add(btnPanel, c);

        add(form, BorderLayout.CENTER);

        cancelBtn.addActionListener(e -> dispose());
        registerBtn.addActionListener(e -> doRegister());

        passwordField.setEchoChar('*');
        confirmPasswordField.setEchoChar('*');
    }

    private void doRegister() {
        messageLabel.setText(" ");

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String memberNumber = memberNumberField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty()) {
            messageLabel.setText("이름을 입력하세요.");
            return;
        }
        if (email.isEmpty()) {
            messageLabel.setText("이메일을 입력하세요.");
            return;
        }
        if (!email.contains("@")) {
            messageLabel.setText("이메일 형식이 올바르지 않습니다.");
            return;
        }
        if (memberNumber.isEmpty()) {
            messageLabel.setText("회원번호를 입력하세요.");
            return;
        }
        if (password.isEmpty()) {
            messageLabel.setText("비밀번호를 입력하세요.");
            return;
        }
        if (password.length() < 4) {
            messageLabel.setText("비밀번호는 4자 이상이어야 합니다.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("비밀번호가 일치하지 않습니다.");
            return;
        }

        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setMemberNumber(memberNumber);

        try {
            authService.registerMember(member, memberNumber, password);
            registered = true;
            JOptionPane.showMessageDialog(this,
                    "회원 가입이 완료되었습니다.\n로그인해 주세요.",
                    "회원 가입 완료", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException ex) {
            messageLabel.setText(ex.getMessage());
        }
    }

    public boolean isRegistered() {
        return registered;
    }
}