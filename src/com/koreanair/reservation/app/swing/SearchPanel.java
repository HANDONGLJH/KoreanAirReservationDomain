package com.koreanair.reservation.app.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.table.DefaultTableModel;

import com.koreanair.reservation.control.BookingController;
import com.koreanair.reservation.domain.flight.FlightSchedule;

/**
 * 2단계 항공편 검색 화면. 출발지·도착지·일자 입력 후 검색 버튼을 누르면
 * {@link BookingController#processSearch(String, String, LocalDate)} 호출 결과를 JTable 로 표시한다.
 *
 * <p>Iteration 1 FlightSearchService 는 "등록된 전체 catalog 반환" 으로 단순화되어 있어
 * 필드 입력과 실제 결과가 일치하지 않을 수 있다. 발표 시연에서는 검색 버튼이 실제로
 * Control 계층을 타고 결과가 JTable 에 렌더링되는 흐름 자체를 보여주면 충분하다.
 */
public class SearchPanel extends JPanel {

    private final JTextField fromField = new JTextField("ICN", 6);
    private final JTextField toField = new JTextField("NRT", 6);
    private final JTextField dateField = new JTextField("2026-05-01", 10);
    private final JButton searchButton = new JButton("검색");
    private final JButton detailButton = new JButton("상세 보기");
    private final JButton nextButton = new JButton("다음");

    private final DefaultTableModel tableModel;
    private final JTable resultTable;

    private final MainFrame frame;
    private final BookingController booking;
    private final SwingReservationUI ui;

    /** 현재 화면에 표시된 검색 결과 (선택 시 index 로 참조). */
    private List<FlightSchedule> currentResults = new ArrayList<>();

    public SearchPanel(MainFrame frame, BookingController booking, SwingReservationUI ui) {
        super(new BorderLayout());
        this.frame = frame;
        this.booking = booking;
        this.ui = ui;

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 4, 10));
        header.add(new JLabel("출발"));
        header.add(fromField);
        header.add(new JLabel("도착"));
        header.add(toField);
        header.add(new JLabel("일자 (YYYY-MM-DD)"));
        header.add(dateField);
        header.add(searchButton);
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[] { "#", "라벨", "항공편", "출발지", "도착지", "상태" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setRowHeight(26);
        resultTable.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) proceedWithSelection();
            }
        });

        JScrollPane scroll = new JScrollPane(resultTable);
        scroll.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        scroll.setPreferredSize(new Dimension(600, 260));
        add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(4, 10, 10, 10));

        footer.add(detailButton);

        // 비활성 플레이스홀더 — "이 기능은 Iteration 2 예정" 을 가시화
        JButton iter2SeatMap = new JButton("좌석맵 선택 (Iteration 2 예정)");
        iter2SeatMap.setEnabled(false);
        footer.add(iter2SeatMap);

        footer.add(nextButton);
        add(footer, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> doSearch());
        detailButton.addActionListener(e -> showSelectedDetail());
        nextButton.addActionListener(e -> proceedWithSelection());

        loadAllSchedules();
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
            ui.displayError("검색 결과가 없습니다. SampleData seed 를 확인하세요.");
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
                ui.displayError("항공편을 먼저 검색하고 선택하세요.");
                return null;
            }
        }
        return currentResults.get(row);
    }
}
