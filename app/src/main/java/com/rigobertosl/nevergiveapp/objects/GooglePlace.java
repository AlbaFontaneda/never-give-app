package com.rigobertosl.nevergiveapp.objects;

import com.google.android.gms.maps.model.LatLng;

public class GooglePlace {

    private String name;
    private double latitude, longitude;

    public GooglePlace() {
    }

    public GooglePlace(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLongitude() {
        return longitude;
    }

    public LatLng getLatLng(){
        return new LatLng(this.latitude, this.longitude);
    }
}
