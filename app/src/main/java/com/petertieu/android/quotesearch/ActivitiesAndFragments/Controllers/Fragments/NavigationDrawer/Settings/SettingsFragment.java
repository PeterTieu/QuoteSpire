package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings;

//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.v7.preference.PreferenceFragmentCompat;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.DynamicBroadcastReceiver;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.MyService;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDayIntentService;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDaySharedPreferences;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorFragment;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorSharedPref;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategoryFragment;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategorySharedPref;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced.SearchQuotesByAdvancedFragment;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorSharedPref;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategorySharedPref;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordSharedPref;
//import com.petertieu.android.quotesearch.R;
//
//public class SettingsFragment extends DynamicBroadcastReceiver {
//
//
//    //Log for Logcat
//    private final String TAG = "SettingsFragment";
//
//    private Switch mPushNotificationSwitch;
//    private TextView mClearAllRecentSearchQueries;
//
//
//
//
//    //Override the onCreateView(..) fragment lifecycle callback method
//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
//        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);
//
//
//        //Log in Logcat
//        Log.i(TAG, "onCreateView(..) called");
//
//        View view = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);
//
//        mPushNotificationSwitch = (Switch) view.findViewById(R.id.push_notification_switch);
//        mClearAllRecentSearchQueries = (TextView) view.findViewById(R.id.clear_all_recent_search_queries);
//
//
//
//
//
//
//
//
//        boolean isPushNotificationOn = QuoteOfTheDayIntentService.isPushNotificationIntentServiceOn(getActivity());
//        QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getActivity(), isPushNotificationOn);
//
//
//
//        mPushNotificationSwitch.setChecked(QuoteOfTheDaySharedPreferences.isPushNotificationOn(getContext()));
//
//
//        mPushNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
//
//                if (isChecked == true){
//
//                    QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(getContext(), true);
//
//                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), true);
//
//                    //Start Service
//                    String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
//                    Intent startIntent = new Intent(getContext(), MyService.class);
//                    startIntent.setAction(ACTION_START_SERVICE);
//                    getActivity().startService(startIntent);
//
//
//                }
//                else{
//
//                    QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(getContext(), true);
//
//                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), false);
//
//
//                    //Stop Service
//                    String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
//                    Intent startIntent = new Intent(getContext(), MyService.class);
//                    startIntent.setAction(ACTION_START_SERVICE);
//                    getActivity().stopService(startIntent);
//
//                }
//
//            }
//        });
//
//
//
//
//
//
//
//        mClearAllRecentSearchQueries.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view){
//
//
//
//                TextView dialogTitle = new TextView(getActivity());
//                dialogTitle.setText("Clear Recent Search Queries");
//                dialogTitle.setTextSize(22);
//                dialogTitle.setGravity(Gravity.CENTER);
//                dialogTitle.setTypeface(null, Typeface.BOLD);
//                dialogTitle.setTextColor(getResources().getColor(R.color.orange));
//                dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey));
//
//                String dialogMessage = "Do you wish to clear all recent search queries?";
//
//
//                View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quotes, null);
//
//                AlertDialog alertDialog = new AlertDialog
//                        .Builder(getActivity())
//                        .setView(dialogFragmentView)
//                        .setCustomTitle(dialogTitle)
//                        .setMessage(dialogMessage)
//                        .setNegativeButton(android.R.string.no, null)
//                        .setPositiveButton(android.R.string.yes,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        SearchQuotesByKeywordSharedPref.setSearchQuotesByKeywordStoredQuery(getActivity(), null);
//                                        SearchQuotesByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);
//                                        SearchQuotesByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), null);
//                                        SearchQPByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), null);
//                                        SearchQPByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);
//                                        SearchQuotesByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);
//
//                                        SearchQuotesByAdvancedFragment.sKeywordSearchQuery = null;
//                                        SearchQuotesByAdvancedFragment.sAuthorSearchQuery = null;
//                                        SearchQuotesByAdvancedFragment.sCategorySearchQuery = null;
//                                        SearchQPByCategoryFragment.sQuotePictureCategorySearchQuery = null;
//                                        SearchQPByAuthorFragment.sQuotePictureAuthorSearchQuery = null;
//
//                                        Toast.makeText(getActivity(), "Cleared all recent search queries", Toast.LENGTH_LONG).show();
//
//                                    }
//                                })
//                        .create();
//
//                alertDialog.show();
//            }
//        });
//
//
//        getActivity().setTitle("Settings");
//
//        return view;
//    }
//}


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.MyService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDayIntentService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDaySharedPreferences;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordSharedPref;
import com.petertieu.android.quotesearch.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

//    Preference mApplicationThemePreference;
    Preference mClearQueriesPreference;
    CheckBoxPreference mDailyNotificationCheckBoxPreference;
    Preference mQODCheckTimePreference;
    Preference mShareAppPreference;
    Preference mAboutAppPreference;

    private static final int REQUEST_CODE_DIALOG_FRAGMENT_TIME_PICKER = 1; //Request code for receiving results from contact activity/app
    private static final String IDENTIFIER_DIALOG_FRAGMENT_TIME_PICKER = "DialogFragmentTimePicker";
    private static final String IDENTIFIER_DIALOG_FRAGMENT_ABOUT_APP = "DialogFragmentAboutApp";



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_settings);

        setHasOptionsMenu(true);


        getActivity().setTitle("Settings");

//        mApplicationThemePreference = (Preference) findPreference(getString(R.string.application_theme_preference));
        mClearQueriesPreference = (Preference) findPreference(getString(R.string.clear_queries_preference_key));
        mDailyNotificationCheckBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.daily_notification_checkbox_preference_key));
        mQODCheckTimePreference = (Preference) findPreference(getString(R.string.check_time_preference_key));
        mShareAppPreference = (Preference) findPreference(getString(R.string.share_app_preference_key));
        mAboutAppPreference = (Preference) findPreference(getString(R.string.about_app_preference_key));










        mClearQueriesPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                TextView dialogTitle = new TextView(getActivity());
                dialogTitle.setText("Clear Recent Search Queries");
                dialogTitle.setTextSize(22);
                dialogTitle.setGravity(Gravity.CENTER);
                dialogTitle.setTypeface(null, Typeface.BOLD);
                dialogTitle.setTextColor(getResources().getColor(R.color.orange));
                dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey));

                String dialogMessage = "Do you wish to clear all recent search queries?";


                View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quotes, null);

                AlertDialog alertDialog = new AlertDialog
                        .Builder(getActivity())
                        .setView(dialogFragmentView)
                        .setCustomTitle(dialogTitle)
                        .setMessage(dialogMessage)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SearchQuotesByKeywordSharedPref.setSearchQuotesByKeywordStoredQuery(getActivity(), null);
                                        SearchQuotesByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);
                                        SearchQuotesByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), null);
                                        SearchQPByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), null);
                                        SearchQPByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);

                                        Toast.makeText(getActivity(), "Cleared all recent search queries", Toast.LENGTH_LONG).show();

                                    }
                                })
                        .create();

                alertDialog.show();


                return false;
            }

        });







        boolean isPushNotificationOn = QuoteOfTheDayIntentService.isPushNotificationIntentServiceOn(getActivity());
        QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getActivity(), isPushNotificationOn);
        mDailyNotificationCheckBoxPreference.setChecked(QuoteOfTheDaySharedPreferences.isPushNotificationOn(getContext()));


        mDailyNotificationCheckBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {


                boolean isChecked = Boolean.valueOf(newValue.toString()); //Get the 'checked' state of the CheckBoxPreference




                if (isChecked == true){

                    QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(getContext(), true);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), true);

                    //Start Service
                    String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
                    Intent startIntent = new Intent(getContext(), MyService.class);
                    startIntent.setAction(ACTION_START_SERVICE);
                    getActivity().startService(startIntent);


                }
                else{

                    QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(getContext(), true);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), false);


                    //Stop Service
                    String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
                    Intent startIntent = new Intent(getContext(), MyService.class);
                    startIntent.setAction(ACTION_START_SERVICE);
                    getActivity().stopService(startIntent);

                }




                return true;
            }
        });










        int pickedHour = QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateHour(getContext());
        int pickedMinute = QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateMinute(getContext());

        String pickedHourString;
        String pickedMinuteString;
        String am_pm;

        if (pickedHour > 12){
            pickedHourString = Integer.toString(pickedHour - 12);
            am_pm = "PM";
        }
        else{
            am_pm = "AM";
            pickedHourString = Integer.toString(pickedHour);
        }

        if (pickedHour == 0){
            pickedHourString = "12";
        }


        if (pickedMinute < 10){
            pickedMinuteString = "0" + Integer.toString(pickedMinute);
        }
        else{
            pickedMinuteString = Integer.toString(pickedMinute);
        }


        mQODCheckTimePreference.setSummary(
                Html.fromHtml(getResources().getString(R.string.check_time_preference_summary) + " " +
                        "<b>" + " " + pickedHourString + ":" + pickedMinuteString + am_pm + "</b>"));







        mQODCheckTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                FragmentManager fragmentManager = getFragmentManager(); //Create FragmentManager
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateHour(getContext()), QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateMinute(getContext())); //Create TimePickerDialogFragment
                timePickerDialogFragment.setTargetFragment(SettingsFragment.this, REQUEST_CODE_DIALOG_FRAGMENT_TIME_PICKER); //Start the dialog fragment
                timePickerDialogFragment.show(fragmentManager, IDENTIFIER_DIALOG_FRAGMENT_TIME_PICKER); //Show dialog

                return false;
            }
        });










        mShareAppPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_app_implicit_intent_subject));
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_quote_implicit_intent_text));
                shareIntent = Intent.createChooser(shareIntent, getResources().getString(R.string.share_app_implicit_intent_chooser_text));

                startActivity(shareIntent);

                return false;
            }
        });





        mAboutAppPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                FragmentManager fragmentManager = getFragmentManager();

                AboutAppDialogFragment aboutAppDialogFragment = AboutAppDialogFragment.newInstance();


                aboutAppDialogFragment.show(fragmentManager, IDENTIFIER_DIALOG_FRAGMENT_ABOUT_APP);

                return false;

            }
        });








    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }










    //Override onActivityResult(..) callback method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //Log in Logcat
        Log.i(TAG, "onActivityResult(..) called");

        //If a result code DOES NOT exist
        if (resultCode != Activity.RESULT_OK) {
            return;
        }


        if (requestCode == REQUEST_CODE_DIALOG_FRAGMENT_TIME_PICKER) {
            //Get Time object from Date dialog fragment
            int pickedHour = (int) intent.getIntExtra(TimePickerDialogFragment.EXTRA_PICKED_HOUR, 10);
            int pickedMinute = (int) intent.getIntExtra(TimePickerDialogFragment.EXTRA_PICKED_MINUTE, 5);

            Log.i(TAG, "pickedHour: " + pickedHour);
            Log.i(TAG, "pickedMinute: " + pickedMinute);

            QuoteOfTheDaySharedPreferences.setQuoteOfTheDayUpdateHour(getContext(), pickedHour);
            QuoteOfTheDaySharedPreferences.setQuoteOfTheDayUpdateMinute(getContext(), pickedMinute);

            QuoteOfTheDayIntentService.ALARM_MANAGER_START_HOUR = QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateHour(getContext());
            QuoteOfTheDayIntentService.ALARM_MANAGER_START_MINUTE = QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateMinute(getContext());


            String pickedHourString;
            String pickedMinuteString;
            String am_pm;

            if (pickedHour > 12){
                pickedHourString = Integer.toString(pickedHour - 12);
                am_pm = "PM";
            }
            else{
                am_pm = "AM";
                pickedHourString = Integer.toString(pickedHour);
            }

            if (pickedHour == 0){
                pickedHourString = "12";
            }



            if (pickedMinute < 10){
                pickedMinuteString = "0" + Integer.toString(pickedMinute);
            }
            else{
                pickedMinuteString = Integer.toString(pickedMinute);
            }


            mQODCheckTimePreference.setSummary(
                            Html.fromHtml(getResources().getString(R.string.check_time_preference_summary) + " " +
                            "<b>" + " " + pickedHourString + ":" + pickedMinuteString + am_pm + "</b>"));













            QuoteOfTheDaySharedPreferences.setPushNotificationSwitchPressed(getContext(), true);

            //RESET the Service (i.e. start and stop it) so that changes in the update time in QuoteOfTheDayIntentService would be applied
            String ACTION_START_SERVICE = "com.petertieu.android.quotesearch.ACTION_START_SERVICE";
            Intent startIntent = new Intent(getContext(), MyService.class);
            startIntent.setAction(ACTION_START_SERVICE);
            getActivity().stopService(startIntent); //Stop Service
            getActivity().startService(startIntent); //Start Service

        }



    }











}