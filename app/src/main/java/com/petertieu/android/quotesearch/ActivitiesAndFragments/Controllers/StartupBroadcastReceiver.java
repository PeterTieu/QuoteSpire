package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


//Base class for code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent)
public class StartupBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "StartupBR";


    @Override
    public void onReceive(Context context, Intent intent){

        //Log that the broadcast intent has been received
        //NOTE: We will expect to see:
        //"Received broadcast intent: android.intent.action.BOOT_COMPLETED"
        Log.i(TAG, "Received broadcast intent: " + intent.getAction());

        boolean pushNotificationState = QuotesSharedPreferences.getPushNotificationState(context);

        //TODO: Add PollService code here, taking into account the pushNotiticaitonState



    }

}
