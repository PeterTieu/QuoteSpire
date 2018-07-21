package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayIntentService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.QuotesSharedPreferences;
import com.petertieu.android.quotesearch.R;

public class SettingsFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "SettingsFragment";

    private Switch mPushNotificationSwitch;



    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);

        mPushNotificationSwitch = (Switch) view.findViewById(R.id.push_notification_switch);


        mPushNotificationSwitch.setChecked(QuotesSharedPreferences.getPushNotificationState(getContext()));


        mPushNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    QuotesSharedPreferences.setPushNotificationState(getContext(), true);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), true);

                    Log.i(TAG, "Push notification state: " + QuotesSharedPreferences.getPushNotificationState(getContext()));
                }
                else{
                    QuotesSharedPreferences.setPushNotificationState(getContext(), false);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), false);

                    Log.i(TAG, "Push notification state: " + QuotesSharedPreferences.getPushNotificationState(getContext()));
                }

            }
        });



        getActivity().setTitle("Settings");

        return view;
    }
}