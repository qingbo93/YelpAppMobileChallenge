package com.example.mobilecodingchallenge.model;

public class BusinessListItem {

    private int itemType;

    private String text;

    private Businesses businesses;

    public BusinessListItem(int itemType, Businesses businesses, String text)
    {
        this.itemType = itemType;
        this.text = text;
        this.businesses = businesses;
    }

    public Businesses getBusinesses() {
        return businesses;
    }

    public void setBusinesses(Businesses businesses) {
        this.businesses = businesses;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
