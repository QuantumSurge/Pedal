package com.example.pratyushsharma.test;

/**
 * Created by pratyushsharma on 25/03/17.
 */

public class Price {
    private int mHourly;
    private int mDaily;
    private int mWeekly;

    public Price(int hourly, int daily, int weekly) {
        mHourly = hourly;
        mDaily = daily;
        mWeekly = weekly;
    }

    public  Price(){

    }

    public int getHourly() {
        return mHourly;
    }

    public void setHourly(int hourly) {
        mHourly = hourly;
    }

    public int getDaily() {
        return mDaily;
    }

    public void setDaily(int daily) {
        mDaily = daily;
    }

    public int getWeekly() {
        return mWeekly;
    }

    public void setWeekly(int weekly) {
        mWeekly = weekly;
    }
}
