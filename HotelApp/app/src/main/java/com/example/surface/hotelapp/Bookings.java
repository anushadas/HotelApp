package com.example.surface.hotelapp;

public class Bookings {

    public String userID;
    public String type;
    public String date;
    public int duration;
    public int numberOfGuests;
    public double priceOfBooking;

    // Default empty constructor
    public Bookings()    {

    }

    public Bookings(String userID, String type, String date, int duration, int numberOfGuests, double priceOfBooking)    {
        this.userID = userID;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.numberOfGuests = numberOfGuests;
        this.priceOfBooking = priceOfBooking;
    }

}
