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

public class QuoteOfTheDayIntentService extends IntentService{

    private static final String TAG = "QODIntentService";

    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.MINUTES.toMillis(1);
//    private static final long INTENT_SERVICE_TIME_INTERVAL = TimeUnit.MINUTES.toMillis(60);

    public static final String ACTION_SHOW_PUSH_NOTIFICATION = "com.petertieu.android.quotesearch.SHOW_NOTIFICATION";

    public static final String PRIVATE_PERMISSION = "com.petertieu.android.quotesearch.PRIVATE";

    public static final String ORDERED_BROADCAST_INTENT_REQUEST_CODE = "ORDERED_BROADCAST_INTENT_REQUEST_CODE";

    public static final String PUSH_NOTIFICATION_REQUEST_CODE = "PUSH_NOTIFICATION_REQUEST_CODE";




    //Make the Intent a SINGLETON, so that only ONE instance of this Intent is created and linked to this IntentService class
    public static Intent newIntent(Context context){
        return new Intent(context, QuoteOfTheDayIntentService.class);
    }



    public QuoteOfTheDayIntentService(){
        super(TAG);
    }



    public static boolean isPushNotificationIntentServiceOn(Context context){

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





    public static Context mContext;


    public static void setPushNotificationIntentServiceState(Context context, boolean isPushNotificationOn){

        mContext = context;


        //Refer the Intent reference variable (pushNotificationServiceIntent) to the Intent SINGLETON object of this class
        Intent pushNotificationIntentService = QuoteOfTheDayIntentService.newIntent(context);


        PendingIntent pendingIntent = PendingIntent.getService(context, 0, pushNotificationIntentService, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);





        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 8);













        if (isPushNotificationOn) {

            Log.i(TAG, "ALARMMM");

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTENT_SERVICE_TIME_INTERVAL, pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000 * 60 * 12, pendingIntent);


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


        Log.i(TAG, "Received an intent: " + intent);


        Quote latestQuoteOfTheDay = new Quote("FirstID");
        latestQuoteOfTheDay.setId("FirstLatestQuoteOfTheDayID");


        String storedQuoteOfTheDayId = QuoteOfTheDaySharedPreferences.getStoredQuoteOfTheDayId(this);


        if (storedQuoteOfTheDayId == null){
            storedQuoteOfTheDayId = "FirstStoredQuoteOfTheDayID";
        }




        latestQuoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay();

        String latestQuoteOfTheDayId = latestQuoteOfTheDay.getId();

        String latestQuoteOfTheDayString = latestQuoteOfTheDay.getQuote();








        if (storedQuoteOfTheDayId.equals(latestQuoteOfTheDayId)){
            Log.i(TAG, "Quote of the Day UNCHANGED");

            Log.i(TAG, "storedQuoteOfTheDayId: " + storedQuoteOfTheDayId);
            Log.i(TAG, "latestQuoteOfTheDayId: " + latestQuoteOfTheDayId);








            NotificationManager mNotificationManager;

            Resources resources = getResources();

            Intent ii = new Intent(mContext.getApplicationContext(), IntroActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);


            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.setSummaryText("Quote of the Day");
            bigText.setBigContentTitle(latestQuoteOfTheDay.getAuthor());
            bigText.bigText("\"" + latestQuoteOfTheDay.getQuote() + "\"");


            Notification notification = new NotificationCompat.Builder(mContext, "notify_001")
                    .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("\"" + latestQuoteOfTheDay.getQuote() + "\"")
                    .setContentText(latestQuoteOfTheDay.getAuthor())
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setStyle(bigText)
                    .build();






            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001", "Quote of the Day", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }


            mNotificationManager.notify(0, notification);






            showBackgroundNotification(0, notification);





//            NotificationManager mNotificationManager;
//
//            Resources resources = getResources();
//
//            Intent ii = new Intent(mContext.getApplicationContext(), IntroActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);
//
//
//            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//            bigText.bigText("hello");
//            bigText.setBigContentTitle("Today's Bible Verse");
//            bigText.setSummaryText("Text in detail");
//
//            Notification notification = new NotificationCompat.Builder(mContext, "notify_001")
//                    .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
//                    .setSmallIcon(R.mipmap.ic_launcher_round)
//                    .setContentTitle(resources.getString(R.string.new_quote_of_the_day_title))
//                    .setContentText(latestQuoteOfTheDayString)
//                    .setContentIntent(pendingIntent)
//                    .setPriority(Notification.PRIORITY_MAX)
//                    .setAutoCancel(true)
//                    .setStyle(bigText)
//                    .build();
//
//
//
//
//
//
//            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
//                mNotificationManager.createNotificationChannel(channel);
//            }
//
//
//            mNotificationManager.notify(0, notification);
//
//
//
//
//
//
//            showBackgroundNotification(0, notification);



















//            NotificationManager mNotificationManager;
//
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
//            Intent ii = new Intent(mContext.getApplicationContext(), IntroActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);
//
//            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//            bigText.bigText("hello");
//            bigText.setBigContentTitle("Today's Bible Verse");
//            bigText.setSummaryText("Text in detail");
//
//
//            mBuilder.setContentIntent(pendingIntent);
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
//            mBuilder.setContentTitle("Your Title");
//            mBuilder.setContentText("Your text");
//            mBuilder.setPriority(Notification.PRIORITY_MAX);
//            mBuilder.setStyle(bigText);
//
//            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
//                mNotificationManager.createNotificationChannel(channel);
//            }
//
//
//            mNotificationManager.notify(0, mBuilder.build());










        }
        else{
            Log.i(TAG, "Quote of the Day CHANGED");

            Log.i(TAG, "storedQuoteOfTheDayId: " + storedQuoteOfTheDayId);
            Log.i(TAG, "latestQuoteOfTheDayId: " + latestQuoteOfTheDayId);


            //=======CREATE A NOTIFICATION TO USE (When there is a new result ID================

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
//            showBackgroundNotification(0, notification);














            NotificationManager mNotificationManager;

            Resources resources = getResources();

            Intent ii = new Intent(mContext.getApplicationContext(), IntroActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);


            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Quote Search");
            bigText.setBigContentTitle("Quote of the Day");
            bigText.setSummaryText(latestQuoteOfTheDay.getQuote());

            Notification notification = new NotificationCompat.Builder(mContext, "notify_001")
                    .setTicker(resources.getString(R.string.new_quote_of_the_day_title))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(resources.getString(R.string.new_quote_of_the_day_title))
                    .setContentText(latestQuoteOfTheDayString)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setStyle(bigText)
                    .build();






            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001", "Quote of the Day Title", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }


            mNotificationManager.notify(0, notification);






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








    private void showBackgroundNotification(int requestCode, Notification notification){

        Intent intent = new Intent(ACTION_SHOW_PUSH_NOTIFICATION);

        intent.putExtra(ORDERED_BROADCAST_INTENT_REQUEST_CODE, requestCode);


        intent.putExtra(PUSH_NOTIFICATION_REQUEST_CODE, notification);

        sendOrderedBroadcast(intent, PRIVATE_PERMISSION, null, null, Activity.RESULT_OK, null, null);


    }





}
