package com.koreanair.reservation.app.sample;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

public final class SampleData {

    private SampleData() {}

    public static Airport airport(String code, String name, String city, String country) {
        Airport a = new Airport();
        setField(a, "airportCode", code);
        setField(a, "airportName", name);
        setField(a, "city", city);
        setField(a, "country", country);
        return a;
    }

    public static FareRule fareRule(String fareClass, boolean refundable, long changeFee, long cancellationPenalty) {
        FareRule r = new FareRule();
        setField(r, "fareClass", fareClass);
        setField(r, "refundable", refundable);
        setField(r, "changeFee", BigDecimal.valueOf(changeFee));
        setField(r, "cancellationPenalty", BigDecimal.valueOf(cancellationPenalty));
        return r;
    }

    public static Fare fare(BookingClass bc, long basePrice, boolean refundable) {
        Fare f = new Fare();
        setField(f, "bookingClass", bc);
        setField(f, "basePrice", BigDecimal.valueOf(basePrice));
        setField(f, "refundable", refundable);
        return f;
    }

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

    public static FlightSchedule schedule(Flight flight, long scheduleId, LocalDateTime departure, LocalDateTime arrival, FareRule fareRule) {
        FlightSchedule s = new FlightSchedule();
        setField(s, "scheduleId", scheduleId);
        setField(s, "flight", flight);
        setField(s, "departureDateTime", departure);
        setField(s, "arrivalDateTime", arrival);
        setField(s, "status", com.koreanair.reservation.domain.flight.FlightStatus.SCHEDULED);
        s.setFareRule(fareRule);
        return s;
    }

    public static Member member(String name, String email, String memberNumber) {
        Member m = new Member();
        setField(m, "name", name);
        setField(m, "email", email);
        setField(m, "memberNumber", memberNumber);
        return m;
    }

    public static SeedResult seedAll(AuthService auth, FlightSearchService search) {
        Airport icn = airport("ICN", "Incheon International Airport", "Seoul", "Korea");
        Airport nrt = airport("NRT", "Narita International Airport", "Tokyo", "Japan");
        Airport lax = airport("LAX", "Los Angeles International Airport", "Los Angeles", "USA");
        Airport ccd = airport("CCD", "Cameron International Airport", "Seoul", "Korea");
        Airport sin = airport("SIN", "Changi Airport", "Singapore", "Singapore");
        Airport hnd = airport("HND", "Haneda Airport", "Tokyo", "Japan");
        Airport fuk = airport("FUK", "Fukuoka Airport", "Fukuoka", "Japan");
        Airport cak = airport("CAK", "Cao Hien International Airport", "Vinh", "Vietnam");

        Fare yFare = fare(BookingClass.Y, 450_000L, true);
        Fare qFare = fare(BookingClass.B, 380_000L, false);
        Fare jFare = fare(BookingClass.J, 650_000L, true);
        Fare fFare = fare(BookingClass.F, 1_200_000L, true);

        FareRule yRule = fareRule("Y", true, 30_000L, 100_000L);
        FareRule qRule = fareRule("Q", false, 50_000L, 180_000L);
        FareRule jRule = fareRule("J", true, 20_000L, 80_000L);
        FareRule fRule = fareRule("F", true, 0L, 50_000L);

        Flight ke001 = flight("KE001", icn, nrt, yFare, jFare);
        Flight ke017 = flight("KE017", icn, lax, qFare, jFare, fFare);
        Flight ke123 = flight("KE123", nrt, lax, yFare, qFare);
        Flight ke501 = flight("KE501", icn, sin, yFare, jFare);
        Flight ke502 = flight("KE502", sin, icn, yFare, jFare);
        Flight ke021 = flight("KE021", icn, hnd, yFare, qFare);
        Flight ke022 = flight("KE022", hnd, icn, yFare, qFare);
        Flight ke041 = flight("KE041", icn, ccd, yFare, qFare);
        Flight ke042 = flight("KE042", ccd, icn, yFare, qFare);
        Flight ke101 = flight("KE101", ccd, nrt, yFare, qFare);
        Flight ke102 = flight("KE102", nrt, ccd, yFare, qFare);
        Flight ke551 = flight("KE551", sin, lax, yFare, qFare);
        Flight ke552 = flight("KE552", lax, sin, yFare, jFare);
        Flight ke601 = flight("KE601", fuk, icn, yFare, qFare);
        Flight ke602 = flight("KE602", icn, fuk, yFare, qFare);
        Flight ke701 = flight("KE701", cak, icn, yFare, qFare, jFare);
        Flight ke702 = flight("KE702", icn, cak, yFare, qFare, jFare);
        Flight ke301 = flight("KE301", icn, lax, yFare, qFare, jFare, fFare);
        Flight ke302 = flight("KE302", lax, icn, yFare, qFare, jFare, fFare);
        Flight ke002 = flight("KE002", icn, nrt, yFare, jFare);
        Flight ke022b = flight("KE022", hnd, icn, yFare, qFare);
        Flight ke003 = flight("KE003", icn, nrt, yFare, jFare);
        Flight ke023 = flight("KE023", icn, hnd, yFare, qFare);
        Flight ke303 = flight("KE303", icn, lax, yFare, qFare, jFare, fFare);
        Flight ke403 = flight("KE403", ccd, nrt, yFare, qFare);
        Flight ke503 = flight("KE503", icn, sin, yFare, jFare);
        Flight ke703 = flight("KE703", cak, icn, yFare, qFare, jFare);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dayAfter = LocalDate.now().plusDays(2);
        LocalDate nextWeek = LocalDate.now().plusDays(7);

        int tYear = tomorrow.getYear();
        int tMonth = tomorrow.getMonthValue();
        int tDay = tomorrow.getDayOfMonth();
        int dYear = dayAfter.getYear();
        int dMonth = dayAfter.getMonthValue();
        int dDay = dayAfter.getDayOfMonth();
        int nYear = nextWeek.getYear();
        int nMonth = nextWeek.getMonthValue();
        int nDay = nextWeek.getDayOfMonth();

        search.addSchedule(schedule(ke001, 1L, LocalDateTime.of(tYear, tMonth, tDay, 9, 30), LocalDateTime.of(tYear, tMonth, tDay, 11, 55), yRule));
        search.addSchedule(schedule(ke021, 2L, LocalDateTime.of(tYear, tMonth, tDay, 14, 30), LocalDateTime.of(tYear, tMonth, tDay, 16, 45), yRule));
        search.addSchedule(schedule(ke601, 3L, LocalDateTime.of(tYear, tMonth, tDay, 10, 0), LocalDateTime.of(tYear, tMonth, tDay, 11, 20), qRule));
        search.addSchedule(schedule(ke301, 4L, LocalDateTime.of(tYear, tMonth, tDay, 18, 0), LocalDateTime.of(tYear, tMonth, tDay, 11, 30), jRule));
        search.addSchedule(schedule(ke501, 5L, LocalDateTime.of(tYear, tMonth, tDay, 8, 0), LocalDateTime.of(tYear, tMonth, tDay, 14, 30), yRule));
        search.addSchedule(schedule(ke041, 6L, LocalDateTime.of(tYear, tMonth, tDay, 13, 0), LocalDateTime.of(tYear, tMonth, tDay, 14, 15), qRule));
        search.addSchedule(schedule(ke101, 7L, LocalDateTime.of(tYear, tMonth, tDay, 7, 30), LocalDateTime.of(tYear, tMonth, tDay, 10, 45), yRule));
        search.addSchedule(schedule(ke701, 8L, LocalDateTime.of(tYear, tMonth, tDay, 16, 30), LocalDateTime.of(tYear, tMonth, tDay, 20, 0), jRule));

        search.addSchedule(schedule(ke002, 9L, LocalDateTime.of(dYear, dMonth, dDay, 9, 30), LocalDateTime.of(dYear, dMonth, dDay, 11, 55), yRule));
        search.addSchedule(schedule(ke022b, 10L, LocalDateTime.of(dYear, dMonth, dDay, 14, 30), LocalDateTime.of(dYear, dMonth, dDay, 16, 45), yRule));
        search.addSchedule(schedule(ke017, 11L, LocalDateTime.of(dYear, dMonth, dDay, 18, 30), LocalDateTime.of(dYear, dMonth, dDay, 11, 0), qRule));
        search.addSchedule(schedule(ke502, 12L, LocalDateTime.of(dYear, dMonth, dDay, 15, 0), LocalDateTime.of(dYear, dMonth, dDay, 22, 0), yRule));
        search.addSchedule(schedule(ke102, 13L, LocalDateTime.of(dYear, dMonth, dDay, 11, 0), LocalDateTime.of(dYear, dMonth, dDay, 14, 15), qRule));
        search.addSchedule(schedule(ke602, 14L, LocalDateTime.of(dYear, dMonth, dDay, 19, 0), LocalDateTime.of(dYear, dMonth, dDay, 20, 20), yRule));
        search.addSchedule(schedule(ke702, 15L, LocalDateTime.of(dYear, dMonth, dDay, 17, 30), LocalDateTime.of(dYear, dMonth, dDay, 21, 0), jRule));
        search.addSchedule(schedule(ke302, 16L, LocalDateTime.of(dYear, dMonth, dDay, 20, 0), LocalDateTime.of(dYear, dMonth, dDay, 13, 30), fRule));

        search.addSchedule(schedule(ke003, 17L, LocalDateTime.of(nYear, nMonth, nDay, 9, 30), LocalDateTime.of(nYear, nMonth, nDay, 11, 55), yRule));
        search.addSchedule(schedule(ke023, 18L, LocalDateTime.of(nYear, nMonth, nDay, 14, 30), LocalDateTime.of(nYear, nMonth, nDay, 16, 45), yRule));
        search.addSchedule(schedule(ke303, 19L, LocalDateTime.of(nYear, nMonth, nDay, 18, 0), LocalDateTime.of(nYear, nMonth, nDay, 11, 30), jRule));
        search.addSchedule(schedule(ke503, 20L, LocalDateTime.of(nYear, nMonth, nDay, 8, 0), LocalDateTime.of(nYear, nMonth, nDay, 14, 30), yRule));
        search.addSchedule(schedule(ke703, 21L, LocalDateTime.of(nYear, nMonth, nDay, 16, 30), LocalDateTime.of(nYear, nMonth, nDay, 20, 0), jRule));
        search.addSchedule(schedule(ke403, 22L, LocalDateTime.of(nYear, nMonth, nDay, 22, 0), LocalDateTime.of(nYear, nMonth, nDay, 3, 30), qRule));

        Member me = member("김정욱", "venturers.team@gmail.com", "SKY-000-001");
        Member lee = member("이재호", "lee.jaeho@email.com", "SKY-000-002");
        Member kim = member("김경동", "kim.gyeongdong@email.com", "SKY-000-003");

        auth.registerMember(me, "SKY-000-001", "pw1234");
        auth.registerMember(lee, "SKY-000-002", "pw5678");
        auth.registerMember(kim, "SKY-000-003", "pw9012");

        List<FlightSchedule> catalog = search.getCatalog();
        FlightSchedule firstResult = catalog.isEmpty() ? null : catalog.get(0);
        return new SeedResult(me, firstResult, yRule);
    }

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

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field f = findField(target.getClass(), fieldName);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Sample data injection failed: " + target.getClass().getSimpleName() + "." + fieldName, e);
        }
    }

    private static Field findField(Class<?> clz, String name) throws NoSuchFieldException {
        while (clz != null) {
            try {
                return clz.getDeclaredField(name);
            } catch (NoSuchFieldException ignore) {
                clz = clz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }
}