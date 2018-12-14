package com.patelheggere.rajeevadmin.model;

public class NotificationReqModel {
public String[] registration_ids;
public String collapse_key;
public Notification notification;
public Data data;

    public NotificationReqModel() {

    }

    public NotificationReqModel(String[] registration_ids, String collapse_key, Notification notification, Data data) {
        this.registration_ids = registration_ids;
        this.collapse_key = collapse_key;
        this.notification = notification;
        this.data = data;
    }

    public static class Data{
        public String body;
        public String title;

        public Data() {
        }

        public Data(String body, String title) {
            this.body = body;
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Notification{
        public String body;
        public String title;

        public Notification() {
        }

        public Notification(String body, String title) {
            this.body = body;
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String[] getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
