package com.rigobertosl.nevergiveapp;

import java.util.ArrayList;

public class Profile {

    private String ID, email, nick, age, height, weight, image;
    private int phone;
    private boolean gender; // True=Male, False=Female
    private boolean booleanEmail, booleanAge, booleanHeight, booleanWeight, booleanImage, booleanPhone; //True=public, False=private
    private ArrayList<Profile> friends;
    private ArrayList<Profile> blacklist;

    public Profile(String nick) {
        this.nick = nick;
    }

    public Profile(String ID, String email, String nick, String age, String height, String weight, String image, int phone, boolean gender) {
        this.ID = ID;
        this.email = email;
        this.nick = nick;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.image = image;
        this.phone = phone;
        this.gender = gender;
        this.booleanEmail = false;
        this.booleanAge = false;
        this.booleanHeight = false;
        this.booleanWeight = false;
        this.booleanImage = false;
        this.booleanPhone = false;
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
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public boolean isBooleanEmail() {
        return booleanEmail;
    }

    public void setBooleanEmail(boolean booleanEmail) {
        this.booleanEmail = booleanEmail;
    }

    public boolean isBooleanAge() {
        return booleanAge;
    }

    public void setBooleanAge(boolean booleanAge) {
        this.booleanAge = booleanAge;
    }

    public boolean isBooleanHeight() {
        return booleanHeight;
    }

    public void setBooleanHeight(boolean booleanHeight) {
        this.booleanHeight = booleanHeight;
    }

    public boolean isBooleanWeight() {
        return booleanWeight;
    }

    public void setBooleanWeight(boolean booleanWeight) {
        this.booleanWeight = booleanWeight;
    }

    public boolean isBooleanImage() {
        return booleanImage;
    }

    public void setBooleanImage(boolean booleanImage) {
        this.booleanImage = booleanImage;
    }

    public boolean isBooleanPhone() {
        return booleanPhone;
    }

    public void setBooleanPhone(boolean booleanPhone) {
        this.booleanPhone = booleanPhone;
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
