package com.koreanair.reservation.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.koreanair.reservation.domain.flight.FlightSchedule;

/**
 * FlightSearchService — Control 계층. Iteration 1 Walking Skeleton 전용.
 *
 * <p>Iteration 1: in-memory sample dataset 기반 단순 필터링.
 *   SampleData 빌더가 주입한 in-memory catalog 에서 출발지/도착지/일자 매칭.
 * <p>TODO(iter2): DB / 외부 GDS 연동.
 * <p>TODO(iter3): Connecting flight 탐색 알고리즘 (Layover, MCT 검증).
 */
public class FlightSearchService {

    private final List<FlightSchedule> catalog = new ArrayList<>();

    public FlightSearchService() {
    }

    public void addSchedule(FlightSchedule schedule) {
        catalog.add(schedule);
    }

    /**
     * 출발지 / 도착지 / 일자로 직항편을 검색.
     *
     * <p>Iteration 1 에서는 기존 도메인 {@link FlightSchedule} 에 getter 가 비어 있는 속성이 많아
     * 매칭 로직이 "catalog 전체 반환" 에 가깝다. Iteration 2 에서 도메인 getter 를 채운 뒤 실제 필터로 교체.
     *
     * @return 조건에 맞는 FlightSchedule 리스트.
     */
    public List<FlightSchedule> search(String fromAirportCode, String toAirportCode, LocalDate date) {
        List<FlightSchedule> matches = new ArrayList<>();
        for (FlightSchedule schedule : catalog) {
            if (schedule.matchesDirect(fromAirportCode, toAirportCode, date)) {
                matches.add(schedule);
            }
        }
        return matches;
    }
}
