package com.example.surface.hotelapp;
//class for "users" object in the db to write in SignUpFragment
public class User {

    public String email;
    public String firstName;
    public String lastName;
    public String region;

    public User() {
        //empty constructor
    }

    public User(String email, String firstName, String lastName, String region) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
    }

}
