package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.IntroActivity;
import com.petertieu.android.quotesearch.R;

public class MyService extends Service {
    static final int NOTIFICATION_ID = 543;

    public static boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification(); //Needed so that the service could start. This is so the user could be in control of the app and be able to "force close" it
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null && intent.getAction().equals("com.petertieu.android.quotesearch.ACTION_START_SERVICE")) {
//            startServiceWithNotification();
//        }
//        else stopMyService();
        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    void startServiceWithNotification() {
        if (isServiceRunning) return;
        isServiceRunning = true;

        Intent notificationIntent = new Intent(getApplicationContext(), IntroActivity.class);
        notificationIntent.setAction("com.petertieu.android.quotesearch.ACTIION_MAIN");  // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_imageview_favorite_on);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.search_pictures_of_quotes))
                .setSmallIcon(R.drawable.ic_button_add)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
//                .setDeleteIntent(contentPendingIntent)  // if needed
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(NOTIFICATION_ID, notification);
    }


    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}





//import android.app.Service;
//        import android.content.Intent;
//        import android.os.Handler;
//        import android.os.IBinder;
//        import android.util.Log;
//
//public class MyService extends Service {
//
//    private static String TAG = "MyService";
//    private Handler handler;
//    private Runnable runnable;
//    private final int runTime = 5000;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.i(TAG, "onCreate");
//
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                handler.postDelayed(runnable, runTime);
//            }
//        };
//        handler.post(runnable);
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        if (handler != null) {
//            handler.removeCallbacks(runnable);
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_STICKY;
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Log.i(TAG, "onStart");
//    }
//
//}