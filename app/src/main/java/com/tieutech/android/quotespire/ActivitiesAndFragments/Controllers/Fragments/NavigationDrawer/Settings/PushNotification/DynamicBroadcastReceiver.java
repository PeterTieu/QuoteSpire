package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDayIntentService;


public abstract class DynamicBroadcastReceiver extends Fragment {

    private static final String TAG = "DynamicBR";



    private BroadcastReceiver mDynamicBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getActivity(), "Received a BroadcastIntent: " + intent.getAction(), Toast.LENGTH_LONG).show();

            Log.i(TAG, "Received a BroadcastIntent: " + intent.getAction());


            setResultCode(Activity.RESULT_CANCELED);

        }
    };




    @Override
    public void onStart(){
        super.onStart();

        Log.i(TAG, "onStart() called");

        IntentFilter intentFilter = new IntentFilter(QuoteOfTheDayIntentService.ACTION_SHOW_PUSH_NOTIFICATION);

        getActivity().registerReceiver(mDynamicBroadcastReceiver, intentFilter, QuoteOfTheDayIntentService.PRIVATE_PERMISSION, null);
    }




    @Override
    public void onStop(){
        super.onStop();

        Log.i(TAG, "onStop() called");

        getActivity().unregisterReceiver(mDynamicBroadcastReceiver);
    }


}
