package com.rigobertosl.nevergiveapp;

import com.google.android.gms.maps.model.LatLng;

public class Event {


    private LatLng location;
    private String sport, hour, minutes, people;

    public Event(String sport, LatLng location, int hour, int minutes, int people) {
        this.sport = sport;
        this.location = location;
        this.hour = String.valueOf(hour);
        this.minutes = String.valueOf(minutes);
        if(minutes == 0){
            this.minutes = "00";
        }
        this.people = String.valueOf(people);
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

    public String getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = String.valueOf(hour);;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = String.valueOf(minutes);;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = String.valueOf(people);;
    }
}
