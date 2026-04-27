package com.koreanair.reservation.domain.passenger;

public class SkypassMember extends Passenger {

    private String skypassNumber;
    private String tier;
    private MileageAccount mileageAccount;

    public String getSkypassNumber() {
        return skypassNumber;
    }

    public String getTier() {
        return tier;
    }

    public MileageAccount getMileageAccount() {
        return mileageAccount;
    }
}
