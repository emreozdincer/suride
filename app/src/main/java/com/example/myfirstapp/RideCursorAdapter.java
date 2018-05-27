package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RideCursorAdapter extends CursorAdapter{


    public RideCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.custom_row_rides_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Ride ride = new Ride(cursor);

        try {
            ((TextView)view.findViewById(R.id.userID)).setText(ride.getOwnerName());
            ((TextView)view.findViewById(R.id.destination)).setText(ride.getDestination());
            ((TextView)view.findViewById(R.id.numSeats)).setText(Integer.toString(ride.getNumSeats()));

            Date rideDate = ride.getDepartureDate();
            String cuteDate = new SimpleDateFormat("HH:mm").format(rideDate);
            ((TextView)view.findViewById(R.id.departureTime)).setText(cuteDate);

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


}
