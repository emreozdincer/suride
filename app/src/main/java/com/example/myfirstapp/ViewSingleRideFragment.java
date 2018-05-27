package com.example.myfirstapp;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Emre on 30-Apr-18.
 */

public class ViewSingleRideFragment extends Fragment {

    private MainActivity mainActivity;
    private CommentCursorAdapter commentsAdapter;
    private DBHelper dbHelper;
    private Cursor cursor;

    public static ViewSingleRideFragment newInstance() {
        ViewSingleRideFragment fragment = new ViewSingleRideFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dbHelper = new DBHelper(mainActivity.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Get the selected ride and inflate view screen
        final long rideID = (long) getArguments().getInt("RideID");
//        final Ride ride = dbHelper.getRideByID(rideID);
        String strRide = getArguments().getString("Ride");
        final Ride ride = new Gson().fromJson(strRide, Ride.class);

        View view = inflater.inflate(R.layout.fragment_view_single_ride, container, false);

        TextView tvRiderName = (TextView) view.findViewById(R.id.tv_Rider);
        TextView tvRideDestination = (TextView) view.findViewById(R.id.tv_Destination);
        TextView tvRideDescription = (TextView) view.findViewById(R.id.tv_Description);
        TextView tvRidedDepartureTime = (TextView) view.findViewById(R.id.tv_DepartureTime);
        ListView lvRideComments = (ListView) view.findViewById(R.id.lv_RideComments);

//        // Set adapter for comments
        cursor = dbHelper.getCommentsByRideID(rideID);
        commentsAdapter = new CommentCursorAdapter(mainActivity.getApplicationContext(), cursor);
        lvRideComments.setAdapter(commentsAdapter);

        // Set Text View texts
        tvRiderName.setText(ride.getOwnerName());
        tvRideDestination.setText(ride.getDestination());
        tvRideDescription.setText(ride.getDescription());
        tvRidedDepartureTime.setText(new SimpleDateFormat("HH:mm").format(ride.getDepartureDate()));

        // Listen for new comments
        Button btComment = (Button) view.findViewById(R.id.bt_Comment);
        final EditText etComment = (EditText) view.findViewById(R.id.et_Comment);

        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = etComment.getText().toString();
                mainActivity.addCommentToRide(commentText, rideID);
                cursor = dbHelper.getCommentsByRideID(rideID);
                commentsAdapter.changeCursor(cursor);
            }
        });

        // TODO: Listen for join ride requests
//        ToggleButton tbtJoinRide = (ToggleButton) view.findViewById(R.id.tbt_JoinRide);
//        tbtJoinRide.setOnClickListener(new View.OnClickListener() {
//           @Override
//            public void onClick (View v) {
//               Toast.makeText(mainActivity, "Join request sent", Toast.LENGTH_SHORT).show();
//           }
//        });


        return view;
    }


}
