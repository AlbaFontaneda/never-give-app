package com.rigobertosl.nevergiveapp.objects;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    /******************  Variables  ********************/
    private String ID, email, password, name, profileImage;
    private HashMap<String, String> targetedEvents = new HashMap<>();

    /******************  Constructores  ********************/
    public Profile(){}

    public Profile(String ID, String email, String password, String username, String profileImage) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.name = username;
        this.profileImage = profileImage;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public HashMap<String, String> getTargetedEvents() {
        return targetedEvents;
    }

    public void addTargetedEvents(String eventID) {
        this.targetedEvents.put(eventID, eventID);
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", ID);
        result.put("email", email);
        result.put("password", password);
        result.put("name", name);
        result.put("targetedEvents", targetedEvents);
        return result;
    }
}
