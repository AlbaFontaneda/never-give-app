package com.rigobertosl.nevergiveapp.objects;

public class Date {

    /******************  Variables  ********************/
    private int day, month, year, hour, minutes;

    /******************  Constructores  ********************/
    public Date(){}

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Date(int day, int month, int year, int hour, int minutes) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
    }

    /******************  Getters and Setters  ********************/
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
        String day = String.valueOf(this.day);
        if(this.day < 10){
            day = "0" + day;
        }
        String month = String.valueOf(this.month);
        if(this.month < 10){
            month = "0" + month;
        }
        String year = String.valueOf(this.year);

        return day+"/"+month+"/"+year;
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
