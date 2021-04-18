package com.example.restaurantsystem;

public class MyPastEventModel {
    String title, description,date,image;

    public MyPastEventModel(String title, String description, String date, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;

    }

    public MyPastEventModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}