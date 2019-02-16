package com.example.surface.hotelapp;

public class Orders {
    String item;
    double price;
    int quantity;
    String date;
    double totalPrice;

    // Default empty constructor
    public Orders() {

    }

    public Orders(String item, double price, int quantity, String date) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        totalPrice = price * quantity;
    }

}
