package com.koreanair.reservation.domain.passenger;

import java.time.LocalDate;

public class Passenger {

    private Long passengerId;
    private String name;
    private String contactInfo;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    private String passportNumber;
    private PassengerType passengerType;

    public Long getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name != null ? name : getFullName();
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) {
            return name;
        }
        return firstName + " " + lastName;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public void updatePassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
