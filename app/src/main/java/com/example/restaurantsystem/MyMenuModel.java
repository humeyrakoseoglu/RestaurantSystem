package com.example.restaurantsystem;

public class MyMenuModel {
    String title, image;

    public MyMenuModel(String title, String image) {
        this.title = title;
        this.image = image;

    }

    public MyMenuModel() {
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
