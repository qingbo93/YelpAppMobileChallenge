package com.example.mobilecodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Hours implements Serializable {

    @JsonProperty
    private List<Open> open;

    @JsonProperty
    private String hours_type;

    @JsonProperty
    private String is_now_open;
}
