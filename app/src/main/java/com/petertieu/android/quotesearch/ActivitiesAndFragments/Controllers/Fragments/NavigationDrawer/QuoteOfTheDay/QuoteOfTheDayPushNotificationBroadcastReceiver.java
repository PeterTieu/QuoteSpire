package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


public class QuoteOfTheDayPushNotificationBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "QODPushNotBR";


    @Override
    public void onReceive(Context context, Intent intent){

        Log.i(TAG, "received result: " + getResultCode());

        if (getResultCode() != Activity.RESULT_OK){
            return;
        }



        int requestCode = intent.getIntExtra(QuoteOfTheDayIntentService.ORDERED_BROADCAST_INTENT_REQUEST_CODE, 0);

        Notification notification = (Notification) intent.getParcelableExtra(QuoteOfTheDayIntentService.PUSH_NOTIFICATION_REQUEST_CODE);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        notificationManager.notify(requestCode, notification);


    }




}
