package com.rigobertosl.nevergiveapp.objects;

import com.google.android.gms.maps.model.LatLng;

public class GooglePlace {

    /******************  Variables  ********************/
    private String name, address;
    private double latitude, longitude, rating;
    private int userRatings;
    private boolean opened;

    /******************  Constructores  ********************/
    public GooglePlace() {
    }

    public GooglePlace(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GooglePlace(String name, String address, double latitude, double longitude, double rating, int userRatings, boolean opened) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.userRatings = userRatings;
        this.opened = opened;
    }

    /******************  Getters and Setters  ********************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
        if(address == null){
            this.address = "";
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(int userRatings) {
        this.userRatings = userRatings;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /******************  Otros métodos  ********************/
    public LatLng getLatLng(){
        return new LatLng(this.latitude, this.longitude);
    }
}
