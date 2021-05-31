package com.example.restaurantsystem;

public class MyCardModel {
    String name, number;

    public MyCardModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public MyCardModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}