package com.rigobertosl.nevergiveapp.objects;

import java.util.ArrayList;

public class Profile {

    private String ID, email, name, age, height, weight, image;
    private int phone;
    private boolean gender; // True=Male, False=Female
    private ArrayList<Profile> friends;
    private ArrayList<Profile> blacklist;

    public Profile(){}

    public Profile(String name) {
        this.name = name;
    }

    public Profile(String email, String name, String age, String height, String weight, String image, int phone, boolean gender) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.image = image;
        this.phone = phone;
        this.gender = gender;
        this.friends = null;
        this.blacklist = null;
    }

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

    public String getNick() {
        return name;
    }

    public void setNick(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public ArrayList<Profile> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }

    public ArrayList<Profile> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(ArrayList<Profile> blacklist) {
        this.blacklist = blacklist;
    }
}
