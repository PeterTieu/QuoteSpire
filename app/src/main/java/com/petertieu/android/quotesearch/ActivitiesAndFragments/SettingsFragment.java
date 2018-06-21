package com.petertieu.android.quotesearch.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petertieu.android.quotesearch.R;

public class SettingsFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "LanguageChooserFragment";



    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);

        return view;
    }
}