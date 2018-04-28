package com.rigobertosl.nevergiveapp;


public class Achievement {

    private String title, description, type, points;

    public Achievement(String title, String description, String type, String points) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.points = points;
    }

    /*
    public Achievement(String title, String description, String type, String dificultad) {
        this.title = title;
        this.description = description;
        this.type = type;

        if(dificultad.equals("medio")){
            this.points = 5;
        }else if(dificultad.equals("alto")){
            this.points = 10;
        }else {
            this.points = 1;
        }
    }
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
