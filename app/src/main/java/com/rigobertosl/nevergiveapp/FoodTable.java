package com.rigobertosl.nevergiveapp;

import java.sql.Blob;

public class FoodTable {
    private long id;
    private String name, days, type;
    private byte[] image;

    public FoodTable(long id, String name, String days, String type, byte[] image) {
        this.id = id;
        this.name = name;
        this.days = days;
        this.type = type;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
