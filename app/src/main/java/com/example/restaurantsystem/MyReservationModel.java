package com.example.restaurantsystem;

public class MyReservationModel {

    String date,time,peopleNumber;

    public MyReservationModel(String date, String time, String peopleNumber) {
        this.date = date;
        this.time = time;
        this.peopleNumber = peopleNumber;
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

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
