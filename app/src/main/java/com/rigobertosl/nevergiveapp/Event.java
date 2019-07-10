package com.rigobertosl.nevergiveapp;

public class Event {

    private String name;
    private String sport;
    private String location;
    private int hour;
    private int people;

    public Event(String name, String sport, String location, int hour, int people) {
        this.name = name;
        this.sport = sport;
        this.location = location;
        this.hour = hour;
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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
