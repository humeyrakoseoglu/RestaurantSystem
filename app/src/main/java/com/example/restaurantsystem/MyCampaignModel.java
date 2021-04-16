package com.example.restaurantsystem;

public class MyCampaignModel {
    String title, description;

    public MyCampaignModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public MyCampaignModel() {
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

}