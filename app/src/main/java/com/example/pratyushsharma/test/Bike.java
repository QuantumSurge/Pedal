package com.example.pratyushsharma.test;

/**
 * Created by Vikramaditya Patil on 11-03-2017.
 */

public class Bike {
    private String mBikePrice,mBikeAddress;
    private int mBikeId;

    Bike(String bikePrice, String bikeAddress, int bikeId){
        mBikeAddress = bikeAddress;
        mBikePrice = bikePrice;
        mBikeId = bikeId;
    }
    public String getBikePrice(){
        return mBikePrice;
    }
    public String getBikeAddress(){
        return mBikeAddress;
    }
    public int getBikeId(){
        return mBikeId;
    }

}
