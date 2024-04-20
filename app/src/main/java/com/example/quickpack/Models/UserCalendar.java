package com.example.quickpack.Models;

import java.util.HashMap;
import java.util.Map;

public class UserCalendar {
    private String uid;
    private Map<String, UserCalendarDate> userCalendarDateMap;

    public UserCalendar() {

    }
    public UserCalendar(String uid, Map<String, UserCalendarDate> userCalendarDateMap) {
        this.uid = uid;
        this.userCalendarDateMap = userCalendarDateMap;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, UserCalendarDate> getUserCalendarDateMap() {
        if(userCalendarDateMap == null) {
            return new HashMap<>();
        } else {
            return userCalendarDateMap;
        }
    }

    public void setUserDateEvent(int day, int month, int year, String event) {
        UserCalendarDate userCalendarDate = new UserCalendarDate(day, month, year, event);

        if(userCalendarDateMap == null) {
            userCalendarDateMap = new HashMap<>();
        }

        userCalendarDateMap.put(userCalendarDate.id(), userCalendarDate);
    }

    public boolean removeUserDateEvent(int day, int month, int year) {
        String id = UserCalendarDate.idByDate(day, month, year);

        if(!getUserCalendarDateMap().containsKey(id)) {
            return false;
        }

        userCalendarDateMap.remove(id);

        if(userCalendarDateMap.size() == 0) {
            userCalendarDateMap = null;
        }

        return true;
    }

    public String getEvent(int day, int month, int year) {
        String id = UserCalendarDate.idByDate(day, month, year);

        if(getUserCalendarDateMap().containsKey(id)) {
            return userCalendarDateMap.get(id).getEvent();
        } else {
            return null;
        }
    }
}
