package com.rigobertosl.nevergiveapp.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile {

    /******************  Variables  ********************/
    private String ID, email, password, name;
    private HashMap<String, Event> targetedEvents;

    /******************  Constructores  ********************/
    public Profile(){}

    public Profile(String ID, String email, String password) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.name = "Anónimo";
    }


    //ToDo: Borrar cuando ya no se use nada a mano.
    public Profile(String name) {
        this.name = name;
    }

    /******************  Getters and Setters  ********************/
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Event> getTargetedEvents() {
        return targetedEvents;
    }

    public void setTargetedEvents(HashMap<String, Event> targetedEvents) {
        this.targetedEvents = targetedEvents;
    }
}
