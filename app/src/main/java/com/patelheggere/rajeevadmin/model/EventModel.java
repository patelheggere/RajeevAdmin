package com.patelheggere.rajeevadmin.model;

public class EventModel {
    private String date;
    private String place;
    private String desc;
    private String title;

    public EventModel() {
    }

    public EventModel(String date, String place, String desc, String title) {
        this.date = date;
        this.place = place;
        this.desc = desc;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
