package com.rigobertosl.nevergiveapp;

import com.google.android.gms.maps.model.LatLng;

public class Event {


    private LatLng location;
    private String sport;
    private int hour;
    private int people;

    public Event(String sport, LatLng location, int hour, int people) {
        this.sport = sport;
        this.location = location;
        this.hour = hour;
        this.people = people;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}
