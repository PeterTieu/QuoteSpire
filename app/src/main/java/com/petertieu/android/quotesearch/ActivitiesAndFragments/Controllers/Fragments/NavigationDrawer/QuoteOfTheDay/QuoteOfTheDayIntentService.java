package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.IntroActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.concurrent.TimeUnit;

public class QuoteOfTheDayIntentService extends IntentService{

    private static final String TAG = "QODIntentService";

//    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.HOURS.toMillis(4);
    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.MINUTES.toMillis(1);

    public static final String ACTION_SHOW_PUSH_NOTIFICATION = "ACTION_SHOW_PUSH_NOTIFICATION";

    public static final String ORDERED_BROADCAST_INTENT_REQUEST_CODE = "ORDERED_BROADCAST_INTENT_REQUEST_CODE";

    public static final String PUSH_NOTIFICATION_REQUEST_CODE = "PUSH_NOTIFICATION_REQUEST_CODE";

    public static final String PRIVATE_PERMISSION = "quoteOfTheDay_PRIVATE_PERMISSION";


    //Make the Intent a SINGLETON, so that only ONE instance of this Intent is created and linked to this IntentService class
    public static Intent newIntent(Context context){
        return new Intent(context, QuoteOfTheDayIntentService.class);
    }



    public QuoteOfTheDayIntentService(){
        super(TAG);
    }



    public static boolean getPushNotificationIntentServiceState(Context context){

        //Refer the Intent reference variable (pushNotificationIntentServiceState) to the Intent SINGLETON object of this class
        Intent pushNotificationIntentService = QuoteOfTheDayIntentService.newIntent(context);

        //Retrieve a PendingIntent (if it exists), so as to start the service
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationIntentService, PendingIntent.FLAG_NO_CREATE);

        //If PendingIntent EXISTS
        if (pendingIntent != null){
            return true;
        }
        //If PendingIntent DOES NOT exist
        else{
            return false;
        }

    }



    public static void setPushNotificationIntentServiceState(Context context, boolean pushNotificationState){

        //Refer the Intent reference variable (pushNotificationServiceIntent) to the Intent SINGLETON object of this class
        Intent pushNotificationIntentService = QuoteOfTheDayIntentService.newIntent(context);


        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationIntentService, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);




        if (pushNotificationState == true) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTENT_SERVICE_TIME_INTERVAL, pendingIntent);

        }
        else{
            alarmManager.cancel(pendingIntent);

            pendingIntent.cancel();
        }


        QuoteOfTheDaySharedPreferences.setPushNotificationState(context, pushNotificationState);


    }




    @Override
    protected void onHandleIntent(Intent intent){

        if (!isNetworkAvailableAndConnected()){
            return;
        }


        Log.i(TAG, "Received an intent: " + intent);


        Quote latestQuoteOfTheDay;


        String storedQuoteOfTheDayId = QuoteOfTheDaySharedPreferences.getQuoteOfTheDayId(this);

        latestQuoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay();

        String latestQuoteOfTheDayId = latestQuoteOfTheDay.getId();
        String latestQuoteOfTheDayString = latestQuoteOfTheDay.getQuote();


//        if (storedQuoteOfTheDayId.equals(latestQuoteOfTheDayId)){
//            Log.i(TAG, "Quote of the Day UNCHANGED");
//        }
//        else{
//            Log.i(TAG, "Quote of the Day CHANGED");
//
//
//            //=======CREATE A NOTIFICATION TO USE (When there is a new result ID================
//
//            Resources resources = getResources();
//
//            Intent introActivityIntent = IntroActivity.newIntent(this);
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, introActivityIntent, 0);
//
//            Notification notification = new NotificationCompat.Builder(this)
//                    .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
//                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
//                    .setContentTitle(resources.getString(R.string.new_quote_of_the_day_title))
//                    .setContentText(latestQuoteOfTheDayString)
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true)
//                    .build();
//
//
//
//
//
//            showBackgroundNotification(0, notification);
//
//        }








        //=======CREATE A NOTIFICATION TO USE (When there is a new result ID================

        Resources resources = getResources();

        Intent introActivityIntent = IntroActivity.newIntent(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, introActivityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_quote_of_the_day_title))
                .setContentText("hello")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();





        showBackgroundNotification(0, notification);







        QuoteOfTheDaySharedPreferences.setQuoteOfTheDayId(this, latestQuoteOfTheDayId);




    }





    private boolean isNetworkAvailableAndConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = (connectivityManager.getActiveNetworkInfo() != null);

        boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();


        return isNetworkConnected;
    }





    private void showBackgroundNotification(int requestCode, Notification notification){

        Intent intent = new Intent(ACTION_SHOW_PUSH_NOTIFICATION);

        intent.putExtra(ORDERED_BROADCAST_INTENT_REQUEST_CODE, requestCode);


        intent.putExtra(PUSH_NOTIFICATION_REQUEST_CODE, notification);

        sendOrderedBroadcast(intent, PRIVATE_PERMISSION, null, null, Activity.RESULT_OK, null, null);


    }





}
