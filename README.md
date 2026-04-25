# Korean Air Skypass Ticket Reservation System

> Reference implementation for **ECE312 Object-Oriented Design Patterns** (Spring 2026, Handong Global University) — **Design Project #2**, Team A.

A Java desktop application that implements the UML model produced in Design Project #1 and progressively refines it across four iterations, each anchored on one major design pattern.

## Status

| Iteration | Pattern | Status |
| --- | --- | --- |
| 1 | **State** — `ReservationState` family with eight concrete states | Walking skeleton complete (`Initiated → PendingPayment → Confirmed`) |
| 2 | **Strategy** — `RefundPolicy` family for refund handling | Planned |
| 3 | **Observer** — async event propagation for schedule changes, payment failures, refund alerts | Planned |
| 4 (Final) | **Singleton** (`AppConfig`) + optional **Factory Method** (`ItineraryFactory`) | Planned |

## Architecture

The codebase follows the **ECB (Entity / Boundary / Control)** organisation:

```
src/com/koreanair/reservation/
├── app/                    # Entry points (App, SwingApp), mock infrastructure, sample data
│   └── swing/              # Swing UI panels (MainFrame, LoginPanel, SearchPanel, ...)
├── boundary/               # Boundary interfaces (ReservationUI, PaymentGatewayInterface, SkypassInterface)
├── control/                # Control services (BookingController, AuthService, FlightSearchService, PaymentProcessor, RefundHandler)
├── domain/
│   ├── reservation/        # Reservation aggregate (Context for State pattern)
│   │   └── state/          # Eight ReservationState classes + AbstractReservationState + InvalidStateTransitionException
│   ├── flight/             # Flight, FlightSchedule, FareRule, Seat, SeatInventory, ...
│   ├── passenger/          # Passenger, MileageAccount (iter3), PassengerType
│   ├── payment/            # Payment, PaymentMethod, PaymentStatus, Refund (iter2), RefundRequest (iter2)
│   └── user/               # User, Member, Admin, GuestBookingRequester
└── tools/                  # AmaterasUML emitters (Generate*Diagram.java)
```

## Walking Skeleton (iteration 1)

The iteration-1 happy path runs end to end via `App.main(...)`:

```
Search → Select → Enter Passenger Info → Validate Fare → Pay → Confirm
```

Two State transitions execute live:

```
Initiated --enterPassengerInfo--> PendingPayment --processPayment--> Confirmed
```

A second `ReservationUI` implementation lives at `app.swing.SwingApp` and exercises the same Control and Domain code without modification — proof that the Boundary swap is non-disruptive.

## Build and Run

The project is a standard Eclipse Java project (no build tool yet — Maven / Gradle is iteration-2 work).

**A) Eclipse**

1. File → Import → Existing Projects into Workspace
2. Select the cloned repository directory
3. Run `com.koreanair.reservation.app.App` (console) or `com.koreanair.reservation.app.swing.SwingApp` (Swing UI)

**B) Command line (javac / java)**

```bash
cd src
find . -name "*.java" > sources.txt
javac -d ../bin @sources.txt
cd ..
java -cp bin com.koreanair.reservation.app.App
```

## Diagrams

The four UML diagrams (use case, class, sequence, state) are generated from the source tree by emitter classes in `com.koreanair.reservation.tools`:

- `GenerateUseCaseDiagram`
- `GenerateClassDiagram`
- `GenerateSequenceDiagrams`
- `GenerateStateDiagrams`

Each emitter writes an AmaterasUML XML file into the workspace; open it in Eclipse with the AmaterasUML plug-in and export PNG. Generating diagrams from source rather than drawing them by hand keeps every diagram in sync with the codebase as the design evolves across iterations.

## Submissions

Iteration-by-iteration deliverables live under `docs/`:

- [`docs/proposal-0/proposal-0-feature-inventory.md`](docs/proposal-0/proposal-0-feature-inventory.md) — English (submission-grade)
- [`docs/proposal-0/proposal-0-feature-inventory-ko.md`](docs/proposal-0/proposal-0-feature-inventory-ko.md) — Korean (review copy)

## Team A

| Member | Layer of ownership | Concrete responsibilities |
| --- | --- | --- |
| Jungwook Kim | Domain & Patterns | `Reservation` aggregate; State / Strategy / Observer / Singleton patterns; AmaterasUML emitters; integration |
| Jaeho Lee | Boundary | Swing UI panels and console front-end |
| Gyungdong Kim | Control & Adapters | `PaymentProcessor`, `RefundHandler`, `PaymentGatewayInterface` mock, `AuthService`, JUnit suite |

## License and Academic Integrity

This repository is shared for academic and reference purposes. Reuse for an active OODP submission at any institution constitutes academic dishonesty and is not permitted.
