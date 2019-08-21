package com.rigobertosl.nevergiveapp.objects;

public class Exercise {

    private long id;
    private String nombre, series, repeticiones, descanso, tipo, description;
    private byte[] image;

    public Exercise(String nombre, String series, String repeticiones, String descanso, String tipo,  byte[] image, String description) {
        // Para limpiar todos los ceros a la izquierda tanto en repeticiones como en descanso
        series = series.replaceFirst ("^0*", "");
        repeticiones = repeticiones.replaceFirst ("^0*", "");

        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
        this.descanso = descanso;
        this.tipo = tipo;
        this.image = image;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getDescanso() {
        return descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}