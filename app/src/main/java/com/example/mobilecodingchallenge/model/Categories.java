package com.example.mobilecodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Categories implements Serializable {

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("title")
    private String title;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
