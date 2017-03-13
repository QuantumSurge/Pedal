package com.example.pratyushsharma.test;



public class Userinfo {
    public String mobile;
    public String username;



    public Userinfo(){

    }


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
    public String getMobile() {		
         return mobile;		
     }
    public void setMobile(String mobile) {		 +    public Userinfo(String address, String username) {
         this.mobile = mobile;		 +        this.address = address;
         this.username = username;
      }
}
