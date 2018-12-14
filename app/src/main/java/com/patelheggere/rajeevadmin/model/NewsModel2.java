package com.patelheggere.rajeevadmin.model;

public class NewsModel2 {
    private String title;
    private String description;
    private String profileURL;
    private String imageURL;
    private long dateTime;
    private String linkURL;
    private long id;
    private long likeCount;
    private long commentCount;
    private String newsKey;

    public NewsModel2() {
    }

    public NewsModel2(String title, String description, String profileURL, String imageURL, long dateTime, String linkURL, long id, long likeCount, long commentCount, String newsKey) {
        this.title = title;
        this.description = description;
        this.profileURL = profileURL;
        this.imageURL = imageURL;
        this.dateTime = dateTime;
        this.linkURL = linkURL;
        this.id = id;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.newsKey = newsKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getNewsKey() {
        return newsKey;
    }

    public void setNewsKey(String newsKey) {
        this.newsKey = newsKey;
    }
}
