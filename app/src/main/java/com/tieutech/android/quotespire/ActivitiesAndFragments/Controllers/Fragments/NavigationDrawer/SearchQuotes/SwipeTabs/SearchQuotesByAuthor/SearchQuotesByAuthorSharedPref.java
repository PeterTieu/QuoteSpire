package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor;

import android.content.Context;
import android.preference.PreferenceManager;


//A SharedPreferences class used for setting (writing) and getting (reading) search queries to/from the SearchView of the SearchQuotesByAuthorFragment class.
//NOTE: Values saved into ShredPreferences is retrievable even between device restarts.
public class SearchQuotesByAuthorSharedPref {

    private static final String SEARCH_QUOTES_BY_AUTHOR_SEARCH_QUERY_KEY = "SearchQuotesByAuthorSearchQueryKey"; //Key for saving and loading search query from the SharedPreferences


    //Set (write) the search query into SharedPreferences
    public static void setSearchQuotesByAuthorStoredQuery(Context context, String searchQuotesByAuthorSearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTES_BY_AUTHOR_SEARCH_QUERY_KEY, searchQuotesByAuthorSearchQuery)
                .apply();
    }


    //Get (read) the search query from SharedPreferences
    public static String getSearchQuotesByAuthorStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTES_BY_AUTHOR_SEARCH_QUERY_KEY, null);
    }
}
