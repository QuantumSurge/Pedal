package com.example.pratyushsharma.test;


public class Userinfo {
    public String address;
    public String username;

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

    public Userinfo(String address, String username) {
        this.address = address;
        this.username = username;
    }
}
