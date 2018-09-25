package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Activities.IntroActivity;


//Base class for code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent)
public class StartupBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "StartupBR";



    @Override
    public void onReceive(Context context, Intent intent){

        //Log that the broadcast intent has been received
        //NOTE: We will expect to see:
        //"Received broadcast intent: android.intent.action.BOOT_COMPLETED"
        Log.i(TAG, "Received broadcast intent: " + intent.getAction());

//        boolean isPushNotificationOn = QuoteOfTheDaySharedPreferences.isPushNotificationOn(context);
//        QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(context, isPushNotificationOn);
//        QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(sContext, true);



        boolean isPushNotificationOn = QuoteOfTheDayIntentService.isPushNotificationIntentServiceOn(context);
        QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(context, isPushNotificationOn);

        //Start Service
        String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
        Intent startIntent = new Intent(context, MyService.class);
        startIntent.setAction(ACTION_START_SERVICE);
        context.startService(startIntent);




    }

}
