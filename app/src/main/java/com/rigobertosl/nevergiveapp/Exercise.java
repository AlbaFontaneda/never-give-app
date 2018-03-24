package com.rigobertosl.nevergiveapp;

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
}