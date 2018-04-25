package com.example.myfirstapp;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Emre on 24-Apr-18.
 */

public class ProfilePageFragment extends Fragment {
    MainActivity mainActivity;
    public static ProfilePageFragment newInstance() {
        ProfilePageFragment fragment = new ProfilePageFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        TextView tvUserName = (TextView) view.findViewById(R.id.tv_ProfileName);
        TextView tvRating = (TextView) view.findViewById(R.id.tv_Rating);

        User user = mainActivity.getUser();
        tvUserName.setText(user.getUsername());
        tvRating.setText(Float.toString(user.getAverageRating()));

        return view;
    }
}