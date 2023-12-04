package com.example.android_dontforgetssu;

public class BorrowInfo {
    private String lenderName;
    private String lenderPhoneNumber;
    private String borrowMoney;
    private String exchangeRate;
    private String borrowDate;
    private String borrowAcceptDate;
    private String interest;
    private String memo;
    private String country;
    private String calculatedMoney;

    public BorrowInfo() {
        // 필드가 없는 기본 생성자
    }

    public BorrowInfo(String lenderName, String lenderPhoneNumber, String borrowMoney, String exchangeRate, String borrowDate, String borrowAcceptDate, String interest, String memo, String country, String calculatedMoney) {
        this.lenderName = lenderName;
        this.lenderPhoneNumber = lenderPhoneNumber;
        this.borrowMoney = borrowMoney;
        this.exchangeRate = exchangeRate;
        this.borrowDate = borrowDate;
        this.borrowAcceptDate = borrowAcceptDate;
        this.interest = interest;
        this.memo = memo;
        this.country = country;
        this.calculatedMoney = calculatedMoney;
    }
    public String getLenderName() {
        return lenderName;
    }

    public String getLenderPhoneNumber() {
        return lenderPhoneNumber;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getBorrowAcceptDate() { return borrowAcceptDate; }

    public String getInterest() { return interest; }

    public String getMemo() {
        return memo;
    }

    public String getCalculatedMoney() { return calculatedMoney; }

    public String getCountry() { return country; }
}
