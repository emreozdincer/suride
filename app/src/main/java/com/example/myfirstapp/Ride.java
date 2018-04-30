package com.example.myfirstapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Emre on 09-Apr-18.
 */

public class Ride {
    private String ownerID;
    private String destination;
    private Date departureDate;
    private int numSeats;
    private String description;
    private List<String> comments;

    // Constructor
    public Ride(String ownerID, String destination, Date departureDate, int numSeats, String description) {
        this.ownerID = ownerID;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numSeats = numSeats;
        this.description = description;
        this.comments = new ArrayList<String>();
    }

    public void addComment (String comment) {
        this.comments.add(comment);
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureDateDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
