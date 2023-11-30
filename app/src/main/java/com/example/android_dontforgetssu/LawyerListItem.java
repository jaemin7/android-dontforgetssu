package com.example.android_dontforgetssu;

public class LawyerListItem {
    private int image;
    private String name;
    private String memo;
    private String num;

    public LawyerListItem(int image, String name, String memo, String num) {
        this.image = image;
        this.name = name;
        this.memo = memo;
        this.num = num;
    }

    public int getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getMemo() {
        return memo;
    }
    public String getNum() {
        return num;
    }
}