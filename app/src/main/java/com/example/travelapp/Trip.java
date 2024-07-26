package com.example.travelapp;

public class Trip {
    private int imageResource;
    private String tripName;
    private String country;
    private String location;
    private String date;
    private String price;
    private String description;

    // Constructor
    public Trip(int imageResource, String tripName, String country, String location, String date, String price, String description) {
        this.imageResource = imageResource;
        this.tripName = tripName;
        this.country = country;
        this.location = location;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    // Getters
    public int getImageResource() {
        return imageResource;
    }

    public String getTripName() {
        return tripName;
    }

    public String getCountry() {
        return country;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
