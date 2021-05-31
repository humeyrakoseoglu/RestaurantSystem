package com.example.restaurantsystem;

public class MyCartModel {
    String title, image, quantity, price;

    public MyCartModel(String title, String image,String quantity, String price) {
        this.title = title;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }

    public MyCartModel() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}