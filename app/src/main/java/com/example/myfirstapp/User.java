package com.example.myfirstapp;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre on 25-Apr-18.
 */

public class User {
    private Long id;
    private String username;
    private int totalRating;
    private int ratingCount;
    private int ownedRideId;
    private int joinedRideId;
    private List<Ride> previousRides;

    public User(String username) {
        this.username = username;
        this.totalRating = 13;
        this.ratingCount = 3;
        this.previousRides = new ArrayList<Ride>();
        this.ownedRideId = -1;
        this.joinedRideId = -1;
        this.id =  -1L;
    }

    public User(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(DBHelper.USER_ID));
        this.username = cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME));
        this.totalRating = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_TOTAL_RATING));
        this.ratingCount = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_RATING_COUNT));
        this.ownedRideId = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_OWNED_RIDE_ID));
        this.joinedRideId = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_JOINED_RIDE_ID));
        this.previousRides = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getOwnedRideId() {
        return ownedRideId;
    }

    public void setOwnedRideId(int ownedRideId) {
        this.ownedRideId = ownedRideId;
    }

    public int getJoinedRideId() {
        return joinedRideId;
    }

    public void setJoinedRideId(int joinedRideId) {
        this.joinedRideId = joinedRideId;
    }

    public List<Ride> getPreviousRides() {
        return previousRides;
    }

    public void setPreviousRides(List<Ride> previousRides) {
        this.previousRides = previousRides;
    }
}
