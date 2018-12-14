package com.patelheggere.rajeevadmin.model;

public class UserDetails {
    private String name;
    private String mobile;
    private String interest;
    private String email;
    private String token;

    public UserDetails() {
    }

    public UserDetails(String name, String mobile, String interest, String email, String token) {
        this.name = name;
        this.mobile = mobile;
        this.interest = interest;
        this.email = email;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
