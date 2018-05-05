package com.example.myfirstapp;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Emre on 07-Apr-18.
 */

public class RidesListAdapter extends ArrayAdapter<Ride> {

    public RidesListAdapter(@NonNull Context context, List<Ride> resource) {
        super(context, R.layout.custom_row_rides_list, resource);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater dummyInflater = LayoutInflater.from(getContext());
//        View customView = dummyInflater.inflate(R.layout.custom_card_row_rides_list, parent, false);
        View customView = dummyInflater.inflate(R.layout.custom_row_rides_list, parent, false);

        // Set the list view fields
        Ride singleRideItem = getItem(position);
//        ImageView rOwnerPic = (ImageView) customView.findViewById(R.id.userPic);
        TextView rOwnerId = (TextView) customView.findViewById(R.id.userID);
        TextView rDestination = (TextView) customView.findViewById(R.id.destination);
        TextView rNumSeats = (TextView) customView.findViewById(R.id.numSeats);
        TextView rTime = (TextView) customView.findViewById(R.id.departureTime);

//        rOwnerPic.setImageResource(R.mipmap.ic_launcher_round);
        rOwnerId.setText(singleRideItem.getOwnerID());
        rDestination.setText(singleRideItem.getDestination());
        rNumSeats.setText(Integer.toString(singleRideItem.getNumSeats()));
        rTime.setText(new SimpleDateFormat("HH:mm").format(singleRideItem.getDepartureDate()));

        return customView;
    }
}
