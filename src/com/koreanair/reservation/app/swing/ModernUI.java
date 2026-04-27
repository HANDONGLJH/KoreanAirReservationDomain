package com.koreanair.reservation.app.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

public final class ModernUI {

    public static final Color PRIMARY = new Color(0x00, 0x5F, 0xC7);
    public static final Color PRIMARY_HOVER = new Color(0x00, 0x4A, 0xA5);
    public static final Color SECONDARY = new Color(0x6C, 0x75, 0x80);
    public static final Color BACKGROUND = new Color(0xF7, 0xF9, 0xFC);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(0x1A, 0x1A, 0x2E);
    public static final Color TEXT_SECONDARY = new Color(0x6C, 0x75, 0x80);
    public static final Color BORDER = new Color(0xE0, 0xE4, 0xE9);
    public static final Color SUCCESS = new Color(0x00, 0xB8, 0x7A);
    public static final Color ERROR = new Color(0xE5, 0x3E, 0x3E);
    public static final Color WARNING = new Color(0xF5, 0xA6, 0x23);

    public static final Font FONT_TITLE = new Font("System", Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font("System", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("System", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("System", Font.PLAIN, 12);

    public static final Insets BUTTON_INSETS = new Insets(12, 24, 12, 24);
    public static final Insets FIELD_INSETS = new Insets(10, 14, 10, 14);
    public static final Insets CARD_INSETS = new Insets(24, 32, 24, 32);

    public static void apply() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // fallback to system look
        }

        Map<String, Object> overrides = new HashMap<>();
        overrides.put("Button.font", FONT_BODY);
        overrides.put("Label.font", FONT_BODY);
        overrides.put("TextField.font", FONT_BODY);
        overrides.put("Button.background", PRIMARY);
        overrides.put("Button.foreground", Color.WHITE);
        overrides.put("Label.foreground", TEXT_PRIMARY);

        for (Map.Entry<String, Object> e : overrides.entrySet()) {
            UIManager.put(e.getKey(), e.getValue());
        }
    }

    public static void styleButton(JButton btn) {
        btn.setFont(FONT_BODY);
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setUI(new BasicButtonUI() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                ((JButton) c).setOpaque(false);
            }
        });
    }

    public static void styleButtonSecondary(JButton btn) {
        btn.setFont(FONT_BODY);
        btn.setForeground(PRIMARY);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                BorderFactory.createEmptyBorder(12, 28, 12, 28)));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(FONT_BODY);
        label.setForeground(TEXT_SECONDARY);
    }

    public static void styleLabelPrimary(JLabel label) {
        label.setFont(FONT_HEADING);
        label.setForeground(TEXT_PRIMARY);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        field.setBackground(Color.WHITE);
        field.setSelectionColor(PRIMARY);
    }

    public static void styleCard(JComponent component) {
        component.setBackground(CARD_BG);
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)));
    }

    public static String airlineBlue() {
        return "#005FC7";
    }

    private ModernUI() {}
}