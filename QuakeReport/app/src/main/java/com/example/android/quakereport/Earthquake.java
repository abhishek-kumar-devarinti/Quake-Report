package com.example.android.quakereport;

public class Earthquake {
    private Double mMagnitude;
    private String mCity, mUrl;
    private Long mTime;

    public Earthquake(Double magnitude, String city, long time, String url) {
        mMagnitude = magnitude;
        mCity = city;
        mTime = time;
        mUrl = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getCity() {
        return mCity;
    }

    public Long getTime() {
        return mTime;
    }

    public String getUrl() {
        return mUrl;
    }
}
