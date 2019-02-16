package com.example.surface.hotelapp;

public class Activities {

    public String userID;
    public String activity;
    public int quantity;
    public int price;
    public String dateOfPurchase;

    // Default empty constructor
    public Activities() {

    }

    public Activities(String userID, String activity, int quantity, int price, String dateOfPurchase) {
        this.userID = userID;
        this.activity = activity;
        this.quantity = quantity;
        this.price = price;
        this.dateOfPurchase = dateOfPurchase;
    }

}
