package com.rigobertosl.nevergiveapp;

public class FoodTable {
    private long id;
    private String name, days, type;

    public FoodTable(long id, String name, String days, String type) {
        this.id = id;
        this.name = name;
        this.days = days;
        this.type = type;
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

    public void getType(String type) {
        this.type = type;
    }
}
