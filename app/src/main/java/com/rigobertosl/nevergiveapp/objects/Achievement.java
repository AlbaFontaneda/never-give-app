package com.rigobertosl.nevergiveapp.objects;


public class Achievement {

    private String title, description, type, points, id;
    private boolean isCompleted;

    public Achievement(String id, String title, String description, String type, String points, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.points = points;
        this.id = id;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }


    public String getPoints() {
        return points;
    }

    public String getId() {
        return id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}