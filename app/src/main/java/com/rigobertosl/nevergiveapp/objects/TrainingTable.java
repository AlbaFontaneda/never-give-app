package com.rigobertosl.nevergiveapp.objects;

public class TrainingTable {
    private long id;
    private String name, days;

    public TrainingTable(long id, String name, String days) {
        this.id = id;
        this.name = name;
        this.days = days;
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
}
