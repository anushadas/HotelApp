package com.example.surface.hotelapp;

//class for "room_review" object in the db for "Rank" page
public class RoomRating {
    public String userID;
    public float rank;
    public String review;

    // Default empty constructor
    public RoomRating()    {

    }

    public RoomRating(String userID, float rank, String review)    {
        this.userID = userID;
        this.rank = rank;
        this.review = review;

    }
}
