package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre on 25-Apr-18.
 */

public class User {
    private String username;
    private float averageRating;
    private List ratings;
    private Ride currentRide;
    private List<Ride> previousRides;

    public User(String username) {
        this.username = username;
        this.averageRating = -1;
        this.ratings = null;
        this.previousRides = new ArrayList<Ride>();
        this.currentRide = null;
    }

    public String getUsername() {
        return username;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public List getRatings() {
        return ratings;
    }

    public List<Ride> getPreviousRides() {
        return previousRides;
    }
}
