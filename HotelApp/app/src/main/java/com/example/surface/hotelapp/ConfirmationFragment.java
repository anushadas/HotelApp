package com.example.surface.hotelapp;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {
    private static int _actNum=-1;
    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_confirmation_fragment, container, false);
        TextView tv=rootView.findViewById(R.id.fragment_header);
        if(_actNum==1)//for activity page
            tv.setText("Activity Booking Confirmed");
        else if(_actNum==2)//for room booking page
            tv.setText("Room Booking Confirmed");
        else if (_actNum ==3)//for room service page
            tv.setText("Room Service Booking Confirmed");

        return rootView;
    }

    public static ConfirmationFragment newInstance(int i)
    {
        _actNum=i;
        return new ConfirmationFragment();
    }

}
