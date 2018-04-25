package com.example.myfirstapp;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        // Set the adapter for list view
        ridesList = mainActivity.getRidesList();
        adapter = new CustomListAdapter(mainActivity.getApplicationContext(), ridesList);
        ListView tasksListView = (ListView) view.findViewById(R.id.listView_rides);
        tasksListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ridesList = mainActivity.getRidesList();
    }

    // TODO: Read rides_list from database on data change
}
