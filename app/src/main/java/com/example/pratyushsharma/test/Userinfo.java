package com.example.pratyushsharma.test;


public class Userinfo {
    public String username;
    public String mobile;

    public Userinfo(String mobile, String username) {
        this.mobile = mobile;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Userinfo(){

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
