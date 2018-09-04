package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayIntentService;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDaySharedPreferences;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategoryFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced.SearchQuotesByAdvancedFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategorySharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordSharedPref;
import com.petertieu.android.quotesearch.R;

public class SettingsFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "SettingsFragment";

    private Switch mPushNotificationSwitch;
    private TextView mClearAllRecentSearchQueries;




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);


        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);

        mPushNotificationSwitch = (Switch) view.findViewById(R.id.push_notification_switch);
        mClearAllRecentSearchQueries = (TextView) view.findViewById(R.id.clear_all_recent_search_queries);



        mPushNotificationSwitch.setChecked(QuoteOfTheDaySharedPreferences.getPushNotificationState(getContext()));


        mPushNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    QuoteOfTheDaySharedPreferences.setPushNotificationState(getContext(), true);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), true);

                    Log.i(TAG, "Push notification state: " + QuoteOfTheDaySharedPreferences.getPushNotificationState(getContext()));
                }
                else{
                    QuoteOfTheDaySharedPreferences.setPushNotificationState(getContext(), false);

                    QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(getContext(), false);

                    Log.i(TAG, "Push notification state: " + QuoteOfTheDaySharedPreferences.getPushNotificationState(getContext()));
                }

            }
        });




        mClearAllRecentSearchQueries.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){



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

            }
        });


        getActivity().setTitle("Settings");

        return view;
    }
}