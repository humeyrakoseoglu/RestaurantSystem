package com.example.restaurantsystem;

public class MyOrderModel {
    String date,time;

    public MyOrderModel( String date, String time) {

        this.date = date;
        this.time = time;
    }

    public MyOrderModel() {
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
