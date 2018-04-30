package com.example.myfirstapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myfirstapp.CustomRidesListAdapter;
import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;

import java.util.List;

/**
 * Created by Emre on 30-Apr-18.
 */

public class ViewSingleRideFragment extends Fragment {

    private MainActivity mainActivity;

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
        Ride ride = mainActivity.getRide(rideID);

        View view = inflater.inflate(R.layout.fragment_view_single_ride, container, false);

        TextView tvRiderName = (TextView) view.findViewById(R.id.tv_Rider);
        TextView tvRideDestination = (TextView) view.findViewById(R.id.tv_Destination);
        TextView tvRideDescription = (TextView) view.findViewById(R.id.tv_Description);
        TextView tvRideComments = (TextView) view.findViewById(R.id.tv_RideComments);

        // Set Texts
        tvRiderName.setText(ride.getOwnerID());
        tvRideDestination.setText(ride.getDestination());
        tvRideDescription.setText(ride.getDescription());
        List<String> comments = ride.getComments();
        if (comments.size() > 0) {
            String sComments = "Comments:\n";

            for(int i=0; i<  comments.size(); i++){
                sComments += Integer.toString(i+1);
                sComments += ". " + comments.get(i) + "\n";
            }
            tvRideComments.setText(sComments);
        }

        // Listen for new comments
        Button btComment = (Button) view.findViewById(R.id.bt_Comment);
        final EditText etComment = (EditText) view.findViewById(R.id.et_Comment);

        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etComment.getText().toString();
                mainActivity.addCommentToRide(comment, rideID);
                Toast.makeText(mainActivity, "Comment added, refresh page to see", Toast.LENGTH_SHORT).show();
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
