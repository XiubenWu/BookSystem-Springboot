package com.work.booksystem.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookInfo {
    private int num;
    private String name;
    private String type;
    private String author;
    private String address;
    private String subscribeTime;
    private int isSubscirbe;
    private int isScrap;

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getAddress() {
        return address;
    }

    public String  getSubscribeTime() {
        return subscribeTime;
    }

    public int getIsSubscirbe() {
        return isSubscirbe;
    }

    public int getIsScrap() {
        return isScrap;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public void setIsSubscirbe(int isSubscirbe) {
        this.isSubscirbe = isSubscirbe;
    }

    public void setIsScrap(int isScrap) {
        this.isScrap = isScrap;
    }
}
