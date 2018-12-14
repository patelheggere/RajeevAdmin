package com.patelheggere.rajeevadmin.model;

public class GalleryModel {
    private String url;
    private String desc;
    private String title;

    public GalleryModel() {
    }

    public GalleryModel(String url, String desc, String title) {
        this.url = url;
        this.desc = desc;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
