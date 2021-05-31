package com.example.restaurantsystem;

public class MyOrderModel {
    String date,time,totalPrice,cardName,addressName;

    public MyOrderModel( String date, String time,String totalPrice) {
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
    }

    public MyOrderModel(String date, String time, String totalPrice, String cardName, String addressName) {
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.cardName = cardName;
        this.addressName = addressName;
    }

    public MyOrderModel() {
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}