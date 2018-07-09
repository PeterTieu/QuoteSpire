package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class QuoteOfTheDayIntentService extends IntentService{

    private static final String TAG = "QODIntentService";

    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.HOURS.toMillis(8);

    public static final String ACTION_SHOW_PUSH_NOTIFICATION = "actionShowPushNotification";




    public static Intent newIntent(Context context){
        return new Intent(context, QuoteOfTheDayIntentService.class);
    }



    public QuoteOfTheDayIntentService(){
        super(TAG);
    }



    public static boolean getPushNotificationState(Context context){
        Intent quoteOfTheDayIntent = QuoteOfTheDayIntentService.newIntent(context);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, quoteOfTheDayIntent, PendingIntent.FLAG_NO_CREATE);


        if (pendingIntent != null){
            return true;
        }
        else{
            return false;
        }
    }



    public static void setPushNotificationState(Context context, boolean pushNotificationState){
        Intent pushNotificationServiceIntent = QuoteOfTheDayIntentService.newIntent(context);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationServiceIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (pushNotificationState) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTENT_SERVICE_TIME_INTERVAL, pendingIntent);

        }
        else{
            alarmManager.cancel(pendingIntent);

            pendingIntent.cancel();
        }





    }



}
