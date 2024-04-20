package com.example.quickpack.Models;

public class UserCalendarDate {
    private int day;
    private int month;
    private int year;

    private String event;

    public UserCalendarDate() {

    }

    public UserCalendarDate(int day, int month, int year, String event) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.event = event;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getEvent() {
        return event;
    }

    public String id() {
        return idByDate(day, month, year);
    }

    public static String idByDate(int day, int month, int year) {
        return day + "_" + month + "_" + year;
    }
}
