package com.rigobertosl.nevergiveapp.objects;

public class Date {

    /******************  Variables  ********************/
    private int dayOfMonth, month, year, hour, minutes;
    //private final String[] weekDays = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

    /******************  Constructores  ********************/
    public Date(){}

    public Date(int dayOfMonth, int month, int year) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;

    }

    public Date(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Date(int dayOfMonth, int month, int year, int hour, int minutes) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
    }

    /******************  Getters and Setters  ********************/
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    /******************  Otros métodos  ********************/
    public String getDayMonthYear(){
        String dayOfMonth = String.valueOf(this.dayOfMonth);
        if(this.dayOfMonth < 10){
            dayOfMonth = "0" + dayOfMonth;
        }
        String month = String.valueOf(this.month);
        if(this.month < 10){
            month = "0" + month;
        }
        String year = String.valueOf(this.year);

        return dayOfMonth +"/"+month+"/"+year;
    }

    public String getTime(){
        String hour = String.valueOf(this.hour);
        if(this.hour < 10){
            hour = "0" + hour;
        }
        String minutes = String.valueOf(this.minutes);
        if(this.minutes < 10){
            minutes = "0" + minutes;
        }
        return hour+":"+minutes;
    }
}
