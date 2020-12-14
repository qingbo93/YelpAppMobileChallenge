package com.example.mobilecodingchallenge.model;

import java.io.Serializable;

public class Region implements Serializable {

    private Center center;

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
