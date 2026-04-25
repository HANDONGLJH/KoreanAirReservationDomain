package com.koreanair.reservation.domain.passenger;

public class MileageAccount {

    private Long accountId;
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void updateBalance(int remainingMileage) {
        this.balance = remainingMileage;
    }
}
