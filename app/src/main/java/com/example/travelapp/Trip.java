package com.example.travelapp;

public class Trip {
    private int imageResource;
    private String tripName;
    private String country;

    // Constructor
    public Trip(int imageResource, String tripName, String country) {
        this.imageResource = imageResource;
        this.tripName = tripName;
        this.country = country;
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

    // Setters (if you plan to change the properties after creation)
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
