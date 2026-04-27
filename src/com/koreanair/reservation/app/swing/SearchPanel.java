package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Font;

import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.domain.flight.FlightSchedule;

public class SearchPanel extends JPanel {

    private final JTextField fromField = new JTextField(6);
    private final JTextField toField = new JTextField(6);
    private final JTextField dateField = new JTextField(10);
    private final JButton searchButton = new JButton("검색");
    private final JButton nextButton = new JButton("다음 단계 →");

    private DefaultTableModel tableModel;
    private JTable resultTable;

    private final MainFrame frame;
    private final BookingController booking;
    private final SwingReservationUI ui;
    private List<FlightSchedule> currentResults = new ArrayList<>();

    public SearchPanel(MainFrame frame, BookingController booking, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.booking = booking;
        this.ui = ui;
        setBackground(ModernUI.BACKGROUND);

        buildSearchBar();
        buildTable();
        buildFooter();
        loadAllSchedules();
    }

    private void buildSearchBar() {
        JPanel searchBar = new JPanel(new GridBagLayout());
        searchBar.setBackground(ModernUI.CARD_BG);
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernUI.BORDER, 1),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 8, 4, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel logo = new JLabel("🔍", SwingConstants.CENTER);
        logo.setFont(new Font("System", Font.PLAIN, 22));
        c.gridx = 0;
        searchBar.add(logo, c);

        c.gridx = 1;
        c.weightx = 0.3;
        fromField.setText("ICN");
        ModernUI.styleSearchField(fromField);
        JPanel fromWrap = wrapField(fromField, "출발 공항");
        searchBar.add(fromWrap, c);

        JLabel arrow = new JLabel("→", SwingConstants.CENTER);
        arrow.setFont(new Font("System", Font.BOLD, 18));
        arrow.setForeground(ModernUI.PRIMARY);
        c.gridx = 2;
        searchBar.add(arrow, c);

        c.gridx = 3;
        c.weightx = 0.3;
        toField.setText("NRT");
        ModernUI.styleSearchField(toField);
        JPanel toWrap = wrapField(toField, "도착 공항");
        searchBar.add(toWrap, c);

        JLabel divider = new JLabel("|", SwingConstants.CENTER);
        divider.setFont(new Font("System", Font.PLAIN, 18));
        divider.setForeground(ModernUI.BORDER);
        c.gridx = 4;
        searchBar.add(divider, c);

        c.gridx = 5;
        c.weightx = 0.25;
        dateField.setText("2026-05-01");
        ModernUI.styleSearchField(dateField);
        JPanel dateWrap = wrapField(dateField, "일자 (YYYY-MM-DD)");
        searchBar.add(dateWrap, c);

        c.gridx = 6;
        c.weightx = 0.15;
        ModernUI.styleButton(searchButton);
        searchButton.setPreferredSize(new Dimension(100, 42));
        searchBar.add(searchButton, c);

        add(searchBar, BorderLayout.NORTH);
    }

    private JPanel wrapField(JTextField field, String placeholder) {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(ModernUI.CARD_BG);
        wrap.add(field, BorderLayout.CENTER);
        JLabel ph = new JLabel(placeholder, SwingConstants.CENTER);
        ph.setFont(ModernUI.FONT_SMALL);
        ph.setForeground(ModernUI.TEXT_SECONDARY);
        ph.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return wrap;
    }

    private void buildTable() {
        tableModel = new DefaultTableModel(
                new Object[] { "#", "항공편명", "노선", "출발", "도착", "상태" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setFont(ModernUI.FONT_BODY);
        resultTable.setRowHeight(40);
        resultTable.setGridColor(ModernUI.BORDER);
        resultTable.setSelectionBackground(ModernUI.PRIMARY_LIGHT);
        resultTable.setSelectionForeground(ModernUI.PRIMARY);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setShowGrid(true);
        resultTable.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) proceedWithSelection();
            }
        });

        JTableHeader header = resultTable.getTableHeader();
        header.setFont(ModernUI.FONT_SMALL);
        header.setForeground(ModernUI.TEXT_SECONDARY);
        header.setBackground(ModernUI.BACKGROUND);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ModernUI.BORDER));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 6; i++) {
            resultTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(resultTable);
        scroll.setBackground(ModernUI.CARD_BG);
        scroll.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, ModernUI.BORDER));
        scroll.getViewport().setBackground(ModernUI.CARD_BG);
        add(scroll, BorderLayout.CENTER);

        searchButton.addActionListener(e -> doSearch());
    }

    private void buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(ModernUI.CARD_BG);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ModernUI.BORDER));

        JPanel leftHint = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        leftHint.setBackground(ModernUI.CARD_BG);
        JLabel hint = new JLabel("날짜를 비워두면 전체 항공편이 표시됩니다");
        hint.setFont(ModernUI.FONT_SMALL);
        hint.setForeground(ModernUI.TEXT_SECONDARY);
        leftHint.add(hint);

        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        rightBtns.setBackground(ModernUI.CARD_BG);

        JButton detailBtn = new JButton("상세 보기");
        ModernUI.styleButtonSecondary(detailBtn);
        detailBtn.addActionListener(e -> showSelectedDetail());
        rightBtns.add(detailBtn);

        ModernUI.styleButton(nextButton);
        nextButton.addActionListener(e -> proceedWithSelection());
        rightBtns.add(nextButton);

        footer.add(leftHint, BorderLayout.WEST);
        footer.add(rightBtns, BorderLayout.EAST);
        footer.setPreferredSize(new Dimension(0, 54));
        add(footer, BorderLayout.SOUTH);
    }

    private void loadAllSchedules() {
        currentResults = booking.getAllSchedules();
        refreshTable();
    }

    private void doSearch() {
        String dateText = dateField.getText().trim();
        if (dateText.isEmpty()) {
            loadAllSchedules();
            return;
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateText);
        } catch (DateTimeParseException ex) {
            ui.displayError("일자 형식이 올바르지 않습니다. 예: 2026-05-01");
            return;
        }
        String from = fromField.getText().trim().toUpperCase();
        String to = toField.getText().trim().toUpperCase();
        List<FlightSchedule> results = booking.processSearch(from, to, date);
        currentResults = results != null ? results : new ArrayList<>();
        refreshTable();
        if (currentResults.isEmpty()) {
            ui.displayError("검색 결과가 없습니다.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int i = 1;
        for (FlightSchedule s : currentResults) {
            tableModel.addRow(SwingReservationUI.toTableRow(i, s));
            i++;
        }
    }

    private void proceedWithSelection() {
        FlightSchedule selected = selectedSchedule();
        if (selected == null) return;
        ui.displayItineraryDetail(selected);
        frame.onFlightSelected(selected);
    }

    private void showSelectedDetail() {
        FlightSchedule selected = selectedSchedule();
        if (selected != null) {
            ui.displayItineraryDetail(selected);
        }
    }

    private FlightSchedule selectedSchedule() {
        int row = resultTable.getSelectedRow();
        if (row < 0) {
            if (!currentResults.isEmpty()) {
                row = 0;
                resultTable.setRowSelectionInterval(0, 0);
            } else {
                ui.displayError("항공편을 선택하세요.");
                return null;
            }
        }
        return currentResults.get(row);
    }
}