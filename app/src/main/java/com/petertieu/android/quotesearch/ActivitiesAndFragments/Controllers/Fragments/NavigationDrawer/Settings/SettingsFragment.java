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
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.DynamicBroadcastReceiver;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.MyService;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.QuoteOfTheDayIntentService;
//import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.QuoteOfTheDaySharedPreferences;
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


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.MainActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.MyService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.QuoteOfTheDayIntentService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.PushNotification.QuoteOfTheDaySharedPreferences;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategoryFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced.SearchQuotesByAdvancedFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordSharedPref;
import com.petertieu.android.quotesearch.R;

public class SettingsFragment extends PreferenceFragmentCompat {


//    Preference mApplicationThemePreference;
    Preference mClearQueriesPreference;
    CheckBoxPreference mQODDailyNotificationsPreference;
    Preference mQODCheckTimePreference;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);



//        mApplicationThemePreference = (Preference) findPreference(getString(R.string.application_theme_preference));
        mClearQueriesPreference = (Preference) findPreference(getString(R.string.clear_queries_preference));
        mQODDailyNotificationsPreference = (CheckBoxPreference) findPreference(getString(R.string.daily_notification_checkbox_preference));
        mQODCheckTimePreference = (Preference) findPreference(getString(R.string.quote_of_the_day_check_time_preference));






//        mApplicationThemePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//
//                getActivity().setTheme(R.style.AppThemeLight);
//
//                getActivity().recreate();
//
////                Fragment currentFragment = new QuoteOfTheDayFragment();
////                FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
////                fragTransaction.detach(currentFragment);
////                fragTransaction.attach(currentFragment);
////                fragTransaction.commit();
//
////                Fragment currentFragment = new SettingsFragment();
////                FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
////                fragTransaction.detach(currentFragment);
////                fragTransaction.attach(currentFragment);
////                fragTransaction.commit();
//
//
//                return false;
//            }
//        });








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
                                        SearchQuotesByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), null);

                                        SearchQuotesByAdvancedFragment.sKeywordSearchQuery = null;
                                        SearchQuotesByAdvancedFragment.sAuthorSearchQuery = null;
                                        SearchQuotesByAdvancedFragment.sCategorySearchQuery = null;
                                        SearchQPByCategoryFragment.sQuotePictureCategorySearchQuery = null;
                                        SearchQPByAuthorFragment.sQuotePictureAuthorSearchQuery = null;

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
        mQODDailyNotificationsPreference.setChecked(QuoteOfTheDaySharedPreferences.isPushNotificationOn(getContext()));


        mQODDailyNotificationsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
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




        mQODCheckTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //Create FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                //Create DateDialogFragment fragment
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(QuoteOfTheDaySharedPreferences.getQuoteOfTheDayUpdateTime);

                //Start the dialog fragment
                timePickerDialogFragment.setTargetFragment(PixDetailFragment.this, REQUEST_CODE_DIALOG_FRAGMENT_DATE);

                //Show dialog
                timePickerDialogFragment.show(fragmentManager, IDENTIFIER_DIALOG_FRAGMENT_DATE);

                return false;
            }
        });











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








    }













}