package com.example.android_dontforgetssu;

import java.io.Serializable;

public class LendInfo implements Serializable {
    private String borrowerName;
    private String borrowerPhoneNumber;
    private String lendMoney;
    private String exchangeRate;
    private String lendDate;
    private String lendAcceptDate;
    private String interest;
    private String memo;
    private String country;
    private String calculatedMoney;

    public LendInfo() {
        // 필드가 없는 기본 생성자
    }

    public LendInfo(String borrowerName, String borrowerPhoneNumber, String lendMoney, String exchangeRate, String lendDate, String lendAcceptDate, String interest, String memo, String country, String calculatedMoney) {
        this.borrowerName = borrowerName;
        this.borrowerPhoneNumber = borrowerPhoneNumber;
        this.lendMoney = lendMoney;
        this.exchangeRate = exchangeRate;
        this.lendDate = lendDate;
        this.lendAcceptDate = lendAcceptDate;
        this.interest = interest;
        this.memo = memo;
        this.country = country;
        this.calculatedMoney = calculatedMoney;
    }
    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowerPhoneNumber() {
        return borrowerPhoneNumber;
    }

    public String getLendMoney() {
        return lendMoney;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getLendDate() {
        return lendDate;
    }

    public String getLendAcceptDate() { return lendAcceptDate; }

    public String getInterest() { return interest; }

    public String getMemo() {
        return memo;
    }

    public String getCalculatedMoney() { return calculatedMoney; }

    public String getCountry() { return country; }
}
