package com.rigobertosl.nevergiveapp.objects;

import com.google.android.gms.maps.model.LatLng;

public class GooglePlace {

    /******************  Variables  ********************/
    private String name;
    private double latitude, longitude;

    /******************  Constructores  ********************/
    public GooglePlace() {
    }

    public GooglePlace(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /******************  Getters and Setters  ********************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    /******************  Otros métodos  ********************/
    public LatLng getLatLng(){
        return new LatLng(this.latitude, this.longitude);
    }
}
