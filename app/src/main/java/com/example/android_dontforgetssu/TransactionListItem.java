package com.example.android_dontforgetssu;

public class TransactionListItem {
    private int image;
    private String name;
    private String money;
    private String memo;
    private String date;

    public TransactionListItem(int image, String name, String money, String memo, String date) {
        this.image = image;
        this.name = name;
        this.money = money;
        this.memo = memo;
        this.date = date;
    }

    public int getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getMoney() {
        return money;
    }
    public String getMemo() {
        return memo;
    }
    public String getDate() {
        return date;
    }
}