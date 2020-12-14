package com.example.mobilecodingchallenge.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private long latitude;

    private long longitude;

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
