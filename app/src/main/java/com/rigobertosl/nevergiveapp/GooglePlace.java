package com.rigobertosl.nevergiveapp;

public class GooglePlace {

    private String name;
    private double latitude, longitude;

    public GooglePlace() {
        this.name = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
