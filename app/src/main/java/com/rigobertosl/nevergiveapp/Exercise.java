package com.rigobertosl.nevergiveapp;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    String nombre, series, repeticiones, descanso;

    public Exercise(){}

    public Exercise(String nombre, String repeticiones) {
        this.nombre = nombre;
        this.repeticiones = repeticiones;
    }

    public Exercise(String nombre, String series, String repeticiones, String descanso) {
        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
        this.descanso = descanso;
    }

    private List<Exercise> exercises;

    public void initializeData(){
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Press de banca", "12"));
        exercises.add(new Exercise("Press superior", "20"));
    }
}