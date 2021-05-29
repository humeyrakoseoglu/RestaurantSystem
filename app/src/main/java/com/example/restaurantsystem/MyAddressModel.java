package com.example.restaurantsystem;

public class MyAddressModel {
    String name, address;

    public MyAddressModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public MyAddressModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
