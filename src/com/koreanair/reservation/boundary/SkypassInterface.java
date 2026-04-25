package com.koreanair.reservation.boundary;

public interface SkypassInterface {

    Object verifyMembership(String skypassNumber, String password);

    int getMileageBalance(String skypassNumber);

    boolean deductMileage(String skypassNumber, int amount);

    Object verifyAndDeduct(String skypassNumber, int amount);
}
