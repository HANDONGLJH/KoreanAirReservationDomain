package com.koreanair.reservation.domain.user;

public class GuestBookingRequester extends User {

    private String guestRequestId;
    private String verificationCode;

    public String getGuestRequestId() {
        return guestRequestId;
    }

    public void verifyReservationAccess(String reservationNumber, String email) {
    }
}
