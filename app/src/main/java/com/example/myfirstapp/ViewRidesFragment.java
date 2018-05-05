package com.example.myfirstapp;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Emre on 24-Apr-18.
 */

public class ViewRidesFragment extends Fragment {
    private ListAdapter adapter;
    private static final String VIEW_FRAGMENT_TAG = "Take this view_rides_fragment";
    private MainActivity mainActivity;
    List<Ride> ridesList;
    private View view;

    public static ViewRidesFragment newInstance() {
        ViewRidesFragment fragment = new ViewRidesFragment();
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

        view = inflater.inflate(R.layout.fragment_view_rides, container, false);

        // Listen to post button
        view.findViewById(R.id.button_postRide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.postRide();
            }
        });


        // Set the adapter for list view
        ridesList = mainActivity.getRidesList();
        adapter = new RidesListAdapter(mainActivity.getApplicationContext(), ridesList);
        ListView tasksListView = (ListView) view.findViewById(R.id.listView_rides);
        tasksListView.setAdapter(adapter);

        // Set the listener for list view
        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Send ride id (postiion) to ViewSingleRideFragment
                Bundle bundle = new Bundle();
                bundle.putInt("RideID", position);
                System.out.println("Position: " + position);

                ViewSingleRideFragment fragment = new ViewSingleRideFragment();
                fragment.setArguments(bundle);

                // Hide Post Button
                View rootView = view.getRootView();
                Button buttonPostRide = (Button) rootView.findViewById(R.id.button_postRide);
                buttonPostRide.setVisibility(View.GONE);

                // Change Fragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ridesList = mainActivity.getRidesList();
//    }

    // TODO: Read rides_list from database on data change
}
