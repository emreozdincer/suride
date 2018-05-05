package com.example.myfirstapp;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Emre on 30-Apr-18.
 */

public class ViewSingleRideFragment extends Fragment {

    private MainActivity mainActivity;
    private CommentsAdapter commentsAdapter;

    public static ViewSingleRideFragment newInstance() {
        ViewSingleRideFragment fragment = new ViewSingleRideFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get the selected ride and inflate view screen
        final int rideID = getArguments().getInt("RideID");
        final Ride ride = mainActivity.getRide(rideID);

        View view = inflater.inflate(R.layout.fragment_view_single_ride, container, false);

        TextView tvRiderName = (TextView) view.findViewById(R.id.tv_Rider);
        TextView tvRideDestination = (TextView) view.findViewById(R.id.tv_Destination);
        TextView tvRideDescription = (TextView) view.findViewById(R.id.tv_Description);
        TextView tvRidedDepartureTime = (TextView) view.findViewById(R.id.tv_DepartureTime);
        ListView lvRideComments = (ListView) view.findViewById(R.id.lv_RideComments);

        // Set adapter for comments
        List<Ride.Comment> commentsList = ride.getComments();
        commentsAdapter = new CommentsAdapter(mainActivity.getApplicationContext(), commentsList);
        lvRideComments.setAdapter(commentsAdapter);

        // Set Text View texts
        tvRiderName.setText(ride.getOwnerID());
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
                Ride.Comment comment = new Ride.Comment(commentText, mainActivity.getUser().getUsername(), new Date());
                mainActivity.addCommentToRide(comment, rideID);
                commentsAdapter.notifyDataSetChanged();
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
