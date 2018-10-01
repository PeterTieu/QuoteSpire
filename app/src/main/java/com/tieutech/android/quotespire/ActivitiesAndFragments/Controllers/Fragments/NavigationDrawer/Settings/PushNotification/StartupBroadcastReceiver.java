package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Activities.IntroActivity;


//Base class for code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent)
public class StartupBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "StartupBR"; //Tag for Logcat

    //Override onReceive(..) lifecycle callback method
    @Override
    public void onReceive(Context context, Intent intent){

        //Log that the broadcast intent has been received
        Log.i(TAG, "Received broadcast intent: " + intent.getAction()); //NOTE: Expected to see: "Received broadcast intent: android.intent.action.BOOT_COMPLETED"

        //
        boolean isPushNotificationOn = QuoteOfTheDayIntentService.isPushNotificationIntentServiceOn(context);
        QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(context, isPushNotificationOn);

        //Start Service in order to allow the app to persist in the background
        String ACTION_START_SERVICE = "com.tieutech.android.quotespire.ACTION_START_SERVICE";
        Intent serviceIntent = new Intent(context, MyService.class);
        serviceIntent.setAction(ACTION_START_SERVICE);
        context.startService(serviceIntent);
    }
}
