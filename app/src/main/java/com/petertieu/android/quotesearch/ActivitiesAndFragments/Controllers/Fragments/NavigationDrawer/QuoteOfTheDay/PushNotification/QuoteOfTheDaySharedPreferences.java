package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification;

import android.content.Context;
import android.preference.PreferenceManager;

public class QuoteOfTheDaySharedPreferences {

    //Declare key
    private static final String PUSH_NOTIFICATION_SWITCH_ID = "pushNotificationSwitchID";
    private static final String LATEST_QUOTE_OF_THE_DAY_ID = "latestQuoteOfTheDayID";
    private static final String PUSH_NOTIFICATION_STATE = "pushNotificationState";




    public static boolean isPushNotificationSwitchPressed(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PUSH_NOTIFICATION_SWITCH_ID, false);
    }

    public static void setPushNotificationSwitchPressed(Context context, boolean isPushNotificationSwitchButtonPressed){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PUSH_NOTIFICATION_SWITCH_ID, isPushNotificationSwitchButtonPressed)
                .apply();
    }



    public static String getStoredQuoteOfTheDayId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(LATEST_QUOTE_OF_THE_DAY_ID, null);
    }



    public static void setStoredQuoteOfTheDayId(Context context, String latestQuoteOfTheDayId){
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putString(LATEST_QUOTE_OF_THE_DAY_ID, latestQuoteOfTheDayId)
                         .apply();
    }






    public static boolean isPushNotificationOn(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PUSH_NOTIFICATION_STATE, true);
    }



    public static void setPushNotificationState(Context context, boolean isPushNotificationOn){
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putBoolean(PUSH_NOTIFICATION_STATE, isPushNotificationOn)
                         .apply();
    }





}
