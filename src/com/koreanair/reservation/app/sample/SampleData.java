package com.koreanair.reservation.app.sample;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.koreanair.reservation.control.AuthService;
import com.koreanair.reservation.control.FlightSearchService;
import com.koreanair.reservation.domain.flight.Airport;
import com.koreanair.reservation.domain.flight.BookingClass;
import com.koreanair.reservation.domain.flight.Fare;
import com.koreanair.reservation.domain.flight.FareRule;
import com.koreanair.reservation.domain.flight.Flight;
import com.koreanair.reservation.domain.flight.FlightSchedule;
import com.koreanair.reservation.domain.flight.Route;
import com.koreanair.reservation.domain.user.Member;

/**
 * Iteration 1 Walking Skeleton 용 in-memory 샘플 데이터 빌더.
 *
 * <p>기존 도메인 클래스들(Airport, Flight, Route 등) 은 public setter 가 없는 필드가 많다.
 * Walking Skeleton 단계에서 도메인 계약을 깨지 않으려면 reflection 으로 최소 속성만 주입.
 * TODO(iter2): 도메인 측에서 정식 factory / setter 제공 후 reflection 제거.
 */
public final class SampleData {

    private SampleData() {}

    // --- Airport ---

    public static Airport airport(String code, String name, String city, String country) {
        Airport a = new Airport();
        setField(a, "airportCode", code);
        setField(a, "airportName", name);
        setField(a, "city", city);
        setField(a, "country", country);
        return a;
    }

    // --- FareRule ---

    public static FareRule fareRule(String fareClass, boolean refundable, long changeFee, long cancellationPenalty) {
        FareRule r = new FareRule();
        setField(r, "fareClass", fareClass);
        setField(r, "refundable", refundable);
        setField(r, "changeFee", BigDecimal.valueOf(changeFee));
        setField(r, "cancellationPenalty", BigDecimal.valueOf(cancellationPenalty));
        return r;
    }

    // --- Fare ---

    public static Fare fare(BookingClass bc, long basePrice, boolean refundable) {
        Fare f = new Fare();
        setField(f, "bookingClass", bc);
        setField(f, "basePrice", BigDecimal.valueOf(basePrice));
        setField(f, "refundable", refundable);
        return f;
    }

    // --- Route + Flight ---

    public static Flight flight(String flightNumber, Airport origin, Airport dest, Fare... fares) {
        Route route = new Route();
        setField(route, "origin", origin);
        setField(route, "destination", dest);
        setField(route, "routeCode", origin != null ? origin.getAirportCode() + "-" + dest.getAirportCode() : null);

        Flight f = new Flight();
        setField(f, "flightNumber", flightNumber);
        setField(f, "route", route);
        for (Fare fare : fares) {
            f.addFare(fare);
        }
        return f;
    }

    // --- FlightSchedule ---

    public static FlightSchedule schedule(Flight flight) {
        FlightSchedule s = new FlightSchedule();
        setField(s, "flight", flight);
        return s;
    }

    // --- Member ---

    public static Member member(String name, String email, String memberNumber) {
        Member m = new Member();
        setField(m, "name", name);
        setField(m, "email", email);
        setField(m, "memberNumber", memberNumber);
        return m;
    }

    // --- 편의: Iteration 1 시연용 전체 seed ---

    public static SeedResult seedAll(AuthService auth, FlightSearchService search) {
        // Airports
        Airport icn = airport("ICN", "Incheon Intl", "Seoul", "KR");
        Airport nrt = airport("NRT", "Narita Intl", "Tokyo", "JP");
        Airport lax = airport("LAX", "Los Angeles Intl", "Los Angeles", "US");

        // Fares
        Fare yFare = fare(BookingClass.Y, 450_000L, true);
        Fare qFare = fare(BookingClass.Y, 380_000L, false);

        // Flights
        Flight ke001 = flight("KE001", icn, nrt, yFare);
        Flight ke017 = flight("KE017", icn, lax, qFare);
        Flight ke123 = flight("KE123", nrt, lax, yFare);

        // Schedules
        FlightSchedule sch001 = schedule(ke001);
        FlightSchedule sch017 = schedule(ke017);
        FlightSchedule sch123 = schedule(ke123);

        search.addSchedule(sch001);
        search.addSchedule(sch017);
        search.addSchedule(sch123);

        // FareRule (Iteration 1 결제 검증용)
        FareRule yRule = fareRule("Y", true, 30_000L, 100_000L);

        // Member (샘플 회원 1명)
        Member me = member("김정욱", "venturers.team@gmail.com", "SKY-000-001");
        auth.registerSample(me, "SKY-000-001");

        return new SeedResult(me, sch001, yRule);
    }

    /** seedAll 결과 묶음 — App.main 에서 Happy Path 진입점으로 사용. */
    public static final class SeedResult {
        public final Member member;
        public final FlightSchedule firstSchedule;
        public final FareRule defaultFareRule;

        public SeedResult(Member member, FlightSchedule firstSchedule, FareRule defaultFareRule) {
            this.member = member;
            this.firstSchedule = firstSchedule;
            this.defaultFareRule = defaultFareRule;
        }
    }

    // --- Reflection helper ---

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field f = findField(target.getClass(), fieldName);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("샘플 데이터 주입 실패: " + target.getClass().getSimpleName() + "." + fieldName, e);
        }
    }

    private static Field findField(Class<?> clz, String name) throws NoSuchFieldException {
        Class<?> c = clz;
        while (c != null) {
            try {
                return c.getDeclaredField(name);
            } catch (NoSuchFieldException ignore) {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }
}
