package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.content.Context;
import android.preference.PreferenceManager;

public class QuoteOfTheDaySharedPreferences {

    //Declare key
    private static final String LATEST_QUOTE_OF_THE_DAY_ID = "latestQuoteOfTheDayID";
    private static final String PUSH_NOTIFICATION_STATE = "pushNotificationState";





    public static void setQuoteOfTheDayId(Context context, String latestQuoteOfTheDayId){
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putString(LATEST_QUOTE_OF_THE_DAY_ID, latestQuoteOfTheDayId)
                         .apply();
    }

    public static String getQuoteOfTheDayId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getString(LATEST_QUOTE_OF_THE_DAY_ID, null);
    }





    public static void setPushNotificationState(Context context, boolean pushNotificationState){
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putBoolean(PUSH_NOTIFICATION_STATE, pushNotificationState)
                         .apply();
    }


    public static boolean getPushNotificationState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getBoolean(PUSH_NOTIFICATION_STATE, false);
    }



}
