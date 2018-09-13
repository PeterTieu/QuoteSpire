package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.IntroActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


//
public class QuoteOfTheDayIntentService extends IntentService{

    private static final String TAG = "QODIntentService";

    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.MINUTES.toMillis(1);
    public static final String ACTION_SHOW_PUSH_NOTIFICATION = "com.petertieu.android.quotesearch.ACTION_SHOW_PUSH_NOTIFICATION";
    public static final String PRIVATE_PERMISSION = "com.petertieu.android.quotesearch.PRIVATE";
    public static final String ORDERED_BROADCAST_INTENT_REQUEST_CODE = "ORDERED_BROADCAST_INTENT_REQUEST_CODE";
    public static final String PUSH_NOTIFICATION_REQUEST_CODE = "PUSH_NOTIFICATION_REQUEST_CODE";

    private static final int ALARM_MANAGER_START_HOUR = 10;
    private static final int ALARM_MANAGER_START_MINUTE = 5;
    private static final int ALARM_MANAGER_REPEAT_INTERVAL_HOURS = 4;

    public static Context sContext;


    //Make the Intent a SINGLETON, so that only ONE instance of this Intent is created and linked to this IntentService class
    public static Intent newIntent(Context context){
        return new Intent(context, QuoteOfTheDayIntentService.class);
    }



    public QuoteOfTheDayIntentService(){
        super(TAG);
    }



    public static boolean isPushNotificationIntentServiceOn(Context context){

        //Refer the Intent reference variable (pushNotificationIntentServiceState) to the Intent SINGLETON object of this class
        Intent pushNotificationIntentServiceIntent = QuoteOfTheDayIntentService.newIntent(context);

        //Retrieve a PendingIntent (if it exists), so as to start the service
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationIntentServiceIntent, PendingIntent.FLAG_NO_CREATE);

        //If PendingIntent EXISTS
        if (pendingIntent != null){
            return true;
        }
        //If PendingIntent DOES NOT exist
        else{
            return false;
        }

    }



    public static void setPushNotificationIntentServiceState(Context context, boolean isPushNotificationOn){

        sContext = context;


        Intent pushNotificationIntentService = QuoteOfTheDayIntentService.newIntent(context); //Refer the Intent reference variable (pushNotificationServiceIntent) to the Intent SINGLETON object of this class
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationIntentService, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);



        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, ALARM_MANAGER_START_HOUR);
        calendar.set(Calendar.MINUTE, ALARM_MANAGER_START_MINUTE);


        if (isPushNotificationOn) {
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTENT_SERVICE_TIME_INTERVAL, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000 * 60 * ALARM_MANAGER_REPEAT_INTERVAL_HOURS, pendingIntent);
        }
        else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

        QuoteOfTheDaySharedPreferences.setPushNotificationState(context, isPushNotificationOn);
    }





    @Override
    protected void onHandleIntent(Intent intent){

        if (!isNetworkAvailableAndConnected()){
            return;
        }


        Quote latestQuoteOfTheDay = new Quote("FirstID");
        latestQuoteOfTheDay.setId("FirstLatestQuoteOfTheDayID");


        String storedQuoteOfTheDayId = QuoteOfTheDaySharedPreferences.getStoredQuoteOfTheDayId(this);


        if (storedQuoteOfTheDayId == null){
            storedQuoteOfTheDayId = "FirstStoredQuoteOfTheDayID";
        }


        latestQuoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay();
        String latestQuoteOfTheDayId = latestQuoteOfTheDay.getId();






        if (storedQuoteOfTheDayId.equals(latestQuoteOfTheDayId)){
            Log.i(TAG, "Quote of the Day UNCHANGED");
            //Do nothing
//            Notification notification = createPushNotification(latestQuoteOfTheDay);
//            showBackgroundNotification(0, notification);
        }
        else {
            Log.i(TAG, "Quote of the Day CHANGED");
            Notification notification = createPushNotification(latestQuoteOfTheDay);
            showBackgroundNotification(0, notification);
        }


        QuoteOfTheDaySharedPreferences.setStoredQuoteOfTheDayId(this, latestQuoteOfTheDayId);

    }







    private boolean isNetworkAvailableAndConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = (connectivityManager.getActiveNetworkInfo() != null);

        boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();


        return isNetworkConnected;
    }








    private Notification createPushNotification(Quote latestQuoteOfTheDay) {
        NotificationManager mNotificationManager;

        Resources resources = getResources();

        Intent ii = new Intent(sContext.getApplicationContext(), IntroActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(sContext, 0, ii, 0);


        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setSummaryText("Quote of the Day");
        bigText.setBigContentTitle("Quote from " + latestQuoteOfTheDay.getAuthor());
        bigText.bigText("\"" + latestQuoteOfTheDay.getQuote() + "\"");


        Notification notification = new NotificationCompat.Builder(sContext, "notify_001")
                .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Quote from " + latestQuoteOfTheDay.getAuthor())
                .setContentText("\"" + latestQuoteOfTheDay.getQuote() + "\"")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setStyle(bigText)
                .build();


        mNotificationManager = (NotificationManager) sContext.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001", "Quote of the Day", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }


        mNotificationManager.notify(0, notification);


        return notification;

    }










        private void showBackgroundNotification(int requestCode, Notification notification){

        Intent intent = new Intent(ACTION_SHOW_PUSH_NOTIFICATION);

        intent.putExtra(ORDERED_BROADCAST_INTENT_REQUEST_CODE, requestCode);


        intent.putExtra(PUSH_NOTIFICATION_REQUEST_CODE, notification);

        sendOrderedBroadcast(intent, PRIVATE_PERMISSION, null, null, Activity.RESULT_OK, null, null);


    }





}
