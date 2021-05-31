package com.example.restaurantsystem;

public class MyGetProductModel {
    String title,price,quantity;

    public MyGetProductModel(String title, String price, String quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public MyGetProductModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}