package com.example.restaurantsystem;

public class MyItemModel {
    String image, price, title;

    public MyItemModel(String image, String price, String title) {
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public MyItemModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}