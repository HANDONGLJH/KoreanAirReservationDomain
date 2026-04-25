# 대한항공 Skypass 티켓 예약 시스템

> **ECE312 객체지향 설계패턴** (2026년 1학기, 한동대학교) — **설계프로젝트 #2** A팀의 참고 구현체.

설계프로젝트 #1에서 만든 UML 모델을 자바 데스크톱 애플리케이션으로 구현하고, 4번의 iteration을 거치며 점진적으로 정제해 나가는 프로젝트. 각 iteration은 하나의 주축 디자인 패턴을 중심으로 한다.

## 진행 상태

| Iteration | 패턴 | 상태 |
| --- | --- | --- |
| 1 | **State** — `ReservationState` family와 8개 구상 상태 클래스 | Walking skeleton 동작 (`Initiated → PendingPayment → Confirmed`) |
| 2 | **Strategy** — 환불 처리용 `RefundPolicy` family | 계획 |
| 3 | **Observer** — 스케줄 변경·결제 실패·환불 알림 등 비동기 이벤트 전파 | 계획 |
| 4 (Final) | **Singleton** (`AppConfig`) + 옵션 **Factory Method** (`ItineraryFactory`) | 계획 |

## 아키텍처

코드베이스는 **ECB(Entity / Boundary / Control)** 구조로 정리되어 있다.

```
src/com/koreanair/reservation/
├── app/                    # 진입점(App, SwingApp), 목 인프라, 샘플 데이터
│   └── swing/              # Swing UI 패널 (MainFrame, LoginPanel, SearchPanel, ...)
├── boundary/               # Boundary 인터페이스 (ReservationUI, PaymentGatewayInterface, SkypassInterface)
├── control/                # Control 서비스 (BookingController, AuthService, FlightSearchService, PaymentProcessor, RefundHandler)
├── domain/
│   ├── reservation/        # Reservation 애그리거트 (State 패턴의 Context)
│   │   └── state/          # 8개 ReservationState 클래스 + AbstractReservationState + InvalidStateTransitionException
│   ├── flight/             # Flight, FlightSchedule, FareRule, Seat, SeatInventory, ...
│   ├── passenger/          # Passenger, MileageAccount (iter3), PassengerType
│   ├── payment/            # Payment, PaymentMethod, PaymentStatus, Refund (iter2), RefundRequest (iter2)
│   └── user/               # User, Member, Admin, GuestBookingRequester
└── tools/                  # AmaterasUML 에미터 (Generate*Diagram.java)
```

## Walking Skeleton (iteration 1)

iteration 1의 happy path는 `App.main(...)`에서 끝까지 동작한다.

```
검색 → 선택 → 승객 정보 입력 → 운임 검증 → 결제 → 확정
```

State 전이 두 건이 실제로 실행된다.

```
Initiated --enterPassengerInfo--> PendingPayment --processPayment--> Confirmed
```

또 다른 `ReservationUI` 구현체가 `app.swing.SwingApp`에 있으며, Control과 Domain 코드를 그대로 사용하면서 동일한 시나리오를 구동한다 — Boundary 교체가 비파괴적임을 증명하는 셈이다.

## 빌드 및 실행

표준 Eclipse 자바 프로젝트 (build tool 미도입 — Maven / Gradle은 iteration 2 작업).

**A) Eclipse**

1. File → Import → Existing Projects into Workspace
2. clone한 디렉토리 선택
3. `com.koreanair.reservation.app.App`(콘솔) 또는 `com.koreanair.reservation.app.swing.SwingApp`(Swing UI) 실행

**B) 커맨드라인 (javac / java)**

```bash
cd src
find . -name "*.java" > sources.txt
javac -d ../bin @sources.txt
cd ..
java -cp bin com.koreanair.reservation.app.App
```

## 다이어그램

UML 다이어그램 4종(use case, class, sequence, state)은 `com.koreanair.reservation.tools` 패키지의 에미터 클래스가 소스 트리에서 자동 생성한다.

- `GenerateUseCaseDiagram`
- `GenerateClassDiagram`
- `GenerateSequenceDiagrams`
- `GenerateStateDiagrams`

각 에미터는 워크스페이스에 AmaterasUML XML 파일을 쓰며, Eclipse에서 AmaterasUML 플러그인으로 열어 PNG로 export하면 된다. 다이어그램을 손으로 그리지 않고 소스에서 자동 생성하도록 해두면 iteration이 진행되며 설계가 변해도 다이어그램이 코드와 항상 동기화 상태를 유지한다.

## 제출물

iteration별 제출물은 `docs/` 아래에 둔다.

- [`docs/proposal-0/proposal-0-feature-inventory.md`](docs/proposal-0/proposal-0-feature-inventory.md) — 영문 (제출본)
- [`docs/proposal-0/proposal-0-feature-inventory-ko.md`](docs/proposal-0/proposal-0-feature-inventory-ko.md) — 한국어 (검토본)

## A팀

| 팀원 | 담당 계층 | 구체 책임 |
| --- | --- | --- |
| 김정욱 (Jungwook Kim) | 도메인 & 패턴 | `Reservation` 애그리거트; State / Strategy / Observer / Singleton 패턴; AmaterasUML 에미터; 통합 |
| 이재호 (Jaeho Lee) | Boundary | Swing UI 패널과 콘솔 프런트엔드 |
| 김경동 (Gyungdong Kim) | Control & 어댑터 | `PaymentProcessor`, `RefundHandler`, `PaymentGatewayInterface` 목 구현, `AuthService`, JUnit 스위트 |

## 라이선스 및 학술 무결성

본 저장소는 학술·참고 목적으로 공개된다. 다른 학기·기관의 OODP 제출에 그대로 재사용하는 것은 학술적 부정행위에 해당하며 허용되지 않는다.
