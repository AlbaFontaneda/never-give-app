package com.rigobertosl.nevergiveapp.objects;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Event {

    private String ID, sport, hour, minutes, day, month, year, people, notes;
    private LatLong location;
    private Profile userHost;
    private ArrayList<Profile> members;
    private boolean full;

    public Event(){}

    public Event(String sport, String hour, String minutes, String day, String month, String year, String people, String notes, LatLong location, Profile userHost) {
        this.sport = sport;
        this.hour = hour;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
        this.people = people;
        this.notes = notes;
        this.location = location;
        this.userHost = userHost;
        this.members = new ArrayList<>();
        this.members.add(userHost);
        this.full = false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public LatLong getLocation() {
        return location;
    }

    public void setLocation(LatLong location) {
        this.location = location;
    }

    public Profile getUserHost() {
        return userHost;
    }

    public void setUserHost(Profile userHost) {
        this.userHost = userHost;
    }

    public ArrayList<Profile> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Profile> members) {
        this.members = members;
        if(this.members.size() == Integer.parseInt(people)){
            this.full = true;
        }
    }

    public boolean getFull() {
        return full;
    }

    public String getDate(){
        return this.day + "/" + this.month + "/" + this.year;
    }

    public String getTime(){
        return this.hour + ":" + this.minutes;
    }

    public LatLng getLatLng(){
        return new LatLng(this.location.getLatitude(), this.location.getLongitude());
    }

    public String creacionDeEvento(){
        return "Se ha creado el evento: " + sport + " el día "+ day + "/" + month + " a las " + hour + ":" + minutes + ".";
    }
}
