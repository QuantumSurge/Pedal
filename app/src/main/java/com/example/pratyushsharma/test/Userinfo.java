package com.example.pratyushsharma.test;


public class Userinfo {
    public String username;
    public String address;
    public String mobile;

    public Userinfo(String mobile, String username, String address) {
        this.mobile = mobile;
        this.username = username;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
