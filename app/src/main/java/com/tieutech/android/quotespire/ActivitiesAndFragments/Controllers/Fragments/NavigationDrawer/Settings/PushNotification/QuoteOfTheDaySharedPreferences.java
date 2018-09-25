package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification;

import android.content.Context;
import android.preference.PreferenceManager;

public class QuoteOfTheDaySharedPreferences {

    //Declare key
    private static final String PUSH_NOTIFICATION_SWITCH_ID = "pushNotificationSwitchID";
    private static final String LATEST_QUOTE_OF_THE_DAY_ID = "latestQuoteOfTheDayID";
    private static final String PUSH_NOTIFICATION_STATE_ID = "pushNotificationStateID";
    private static final String TIME_PICKER_HOUR_ID = "timePickerHourID";
    private static final String TIME_PICKER_MINUTE_ID = "timePickerMinuteID";




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
                .getBoolean(PUSH_NOTIFICATION_STATE_ID, true);
    }



    public static void setPushNotificationState(Context context, boolean isPushNotificationOn){
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putBoolean(PUSH_NOTIFICATION_STATE_ID, isPushNotificationOn)
                         .apply();
    }




    public static int getQuoteOfTheDayUpdateHour(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TIME_PICKER_HOUR_ID, 10);
    }



    public static void setQuoteOfTheDayUpdateHour(Context context, int quoteOfTheDayUpdateHour){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(TIME_PICKER_HOUR_ID, quoteOfTheDayUpdateHour)
                .apply();
    }



    public static int getQuoteOfTheDayUpdateMinute(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TIME_PICKER_MINUTE_ID, 05);
    }



    public static void setQuoteOfTheDayUpdateMinute(Context context, int quoteOfTheDayUpdateHour){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(TIME_PICKER_MINUTE_ID, quoteOfTheDayUpdateHour)
                .apply();
    }



}
