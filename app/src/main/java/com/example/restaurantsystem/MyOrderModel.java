package com.example.restaurantsystem;

public class MyOrderModel {
    String date,time,totalPrice;

    public MyOrderModel( String date, String time,String totalPrice) {

        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
    }


    public MyOrderModel() {
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