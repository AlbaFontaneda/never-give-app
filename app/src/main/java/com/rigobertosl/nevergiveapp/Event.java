package com.rigobertosl.nevergiveapp;

public class Event {


    private GooglePlace location;
    private String sport;
    private int hour;
    private int people;

    public Event(String sport, GooglePlace location, int hour, int people) {
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

    public GooglePlace getLocation() {
        return location;
    }

    public void setLocation(GooglePlace location) {
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
