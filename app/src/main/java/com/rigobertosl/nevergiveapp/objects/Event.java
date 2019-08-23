package com.rigobertosl.nevergiveapp.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Event {

    /******************  Variables  ********************/
    private String ID, sport, notes = "";
    private Date date;
    private int assistants;
    private Profile host;
    private GooglePlace place;
    private HashMap<String, Profile> members = new HashMap<>();

    /******************  Constructores  ********************/
    public Event(){}

    /******************  Getters and Setters  ********************/
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAssistants() {
        return assistants;
    }

    public void setAssistants(int assistants) {
        this.assistants = assistants;
    }

    public Profile getHost() {
        return host;
    }

    public void setHost(Profile host) {
        this.host = host;
        this.members.put(host.getID(), host);
    }

    public GooglePlace getPlace() {
        return place;
    }

    public void setPlace(GooglePlace place) {
        this.place = place;
    }

    public HashMap<String, Profile> getMembers() {
        return members;
    }

    public void addMember(Profile newMember) {
        this.members.put(newMember.getID(), newMember);
    }

    /******************  Otros métodos  ********************/
    public boolean isFull(){
        if(members.size() == assistants){
            return true;
        }else{
            return false;
        }
    }

    public String creacionDeEvento(){
        return "Se ha creado el evento: " + sport + " el día " + date.getDayMonthYear() +" a las " + date.getTime() + ".";
    }
}
