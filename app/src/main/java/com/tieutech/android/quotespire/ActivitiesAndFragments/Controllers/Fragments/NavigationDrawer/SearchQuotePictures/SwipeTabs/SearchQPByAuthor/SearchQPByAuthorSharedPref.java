package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor;

import android.content.Context;
import android.preference.PreferenceManager;


//Shared Preferences for storing/retrieving the Quote Picture search queries for AUTHOR
public class SearchQPByAuthorSharedPref {


    private static final String SEARCH_QUOTE_PICTURES_BY_AUTHOR_SEARCH_QUERY_KEY = "SearchQPByAuthorSearchQueryKey"; //Key for saving and loading search query from the SharedPreferences


    //Set (write) the search query into SharedPreferences
    public static void setSearchQuotesByAuthorStoredQuery(Context context, String searchQPByAuthorSearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTE_PICTURES_BY_AUTHOR_SEARCH_QUERY_KEY, searchQPByAuthorSearchQuery)
                .apply();
    }


    //Get (read) the search query from SharedPreferences
    public static String getSearchQuotesByAuthorStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTE_PICTURES_BY_AUTHOR_SEARCH_QUERY_KEY, null);
    }
}
