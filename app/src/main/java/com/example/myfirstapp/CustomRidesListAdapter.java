package com.example.myfirstapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Emre on 07-Apr-18.
 */

public class CustomRidesListAdapter extends ArrayAdapter<Ride> {

    public CustomRidesListAdapter(@NonNull Context context, List<Ride> resource) {
        super(context, R.layout.custom_row_rides_list, resource);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater dummyInflater = LayoutInflater.from(getContext());
        View customView = dummyInflater.inflate(R.layout.custom_row_rides_list, parent, false);

        // Set the list view texts
        Ride singleRideItem = getItem(position);
        TextView rOwnerId = (TextView) customView.findViewById(R.id.userID);
        TextView rDestination = (TextView) customView.findViewById(R.id.destination);
        TextView rNumSeats = (TextView) customView.findViewById(R.id.numSeats);

        rOwnerId.setText(singleRideItem.getOwnerID());
        rDestination.setText(singleRideItem.getDestination());
        rNumSeats.setText(Integer.toString(singleRideItem.getNumSeats()));

        return customView;
    }
}
