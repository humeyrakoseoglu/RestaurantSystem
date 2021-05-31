package com.example.restaurantsystem;

public class MyFavoritesModel {
    String title, image, price;

    public MyFavoritesModel(String title, String image,String price) {
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public MyFavoritesModel() {
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