package com.example.pratyushsharma.test;

/**
 * Created by Vikramaditya Patil on 11-03-2017.
 */

public class Bike {
    private String mBikename,mBikeAddress,mUID;
    private Price mPrice;


    public Bike(String bikename, String bikeAddress, String uid, Price price){
        mBikeAddress = bikeAddress;
        mBikename = bikename;
        mUID = uid;
        mPrice = price;
    }
    public Bike(){

    }

    public String getBikeAddress(){
        return mBikeAddress;
    }

    public void setBikeAddress(String bikeAddress) {
        mBikeAddress = bikeAddress;
    }

    public String getUID() {
        return mUID;
    }

    public void setUID(String UID) {
        mUID = UID;
    }

    public String getBikename() {
        return mBikename;
    }

    public void setBikename(String bikename) {
        mBikename = bikename;
    }

    public Price getPrice() {
        return mPrice;
    }

    public void setPrice(Price price) {
        mPrice = price;
    }
}
