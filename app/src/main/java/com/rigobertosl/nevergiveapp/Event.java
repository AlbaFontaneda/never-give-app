package com.rigobertosl.nevergiveapp;

import com.google.android.gms.maps.model.LatLng;

public class Event {


    private LatLng location;
    private String sport, hour, minutes, day, month, year, people, notes;
    private boolean isSelected;

    public Event(){}

    public Event( String sport, LatLng location, String hour, String minutes, String day, String month, String year, String people) {
        this.location = location;
        this.sport = sport;
        this.hour = hour;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
        this.people = people;
        this.isSelected = false;
    }

    public Event( String sport, LatLng location, String hour, String minutes, String day, String month, String year, String people, String notes) {
        this.location = location;
        this.sport = sport;
        this.hour = hour;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
        this.people = people;
        this.notes = notes;
        this.isSelected = false;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String creacionDeEvento(){
        return "Se ha creado el evento: " + sport + " el día "+ day + "/" + month + " a las " + hour + ":" + minutes + ".";
    }
}
