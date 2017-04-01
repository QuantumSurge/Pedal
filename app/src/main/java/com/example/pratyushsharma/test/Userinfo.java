package com.example.pratyushsharma.test;



public class Userinfo {
    public String mobile;
    public String username;
    public String status;


    public Userinfo(){

    }

    public Userinfo(String mobile, String username,String status) {
        this.mobile = mobile;
        this.username = username;
        this.status = status;
    }
     public String getUsername() {		
         return username;		
     }
    public void setUsername(String username) {		
         this.username = username;		
     }
    public String getMobile() {		
         return mobile;		
     }
    public void setMobile(String mobile) {
         this.mobile = mobile;
      }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
