package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

import android.content.Context;
import android.preference.PreferenceManager;


//A SharedPreferences class used for setting (writing) and getting (reading) search queries to/from the SearchView of the SearchQuotesByKeywordFragment class.
//NOTE: Values saved into ShredPreferences is retrievable even between device restarts.
public class SearchQuotesByKeywordSharedPref {

    private static final String SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY = "SearchQuotesByKeywordSearchQueryKey"; //Key for saving and loading search query from the SharedPreferences


    //Set (write) the search query into SharedPreferences
    public static void setSearchQuotesByKeywordStoredQuery(Context context, String searchQuotesByKeywordSearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY, searchQuotesByKeywordSearchQuery)
                .apply();
    }


    //Get (read) the search query from SharedPreferences
    public static String getSearchQuotesByKeywordStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY, null);
    }

}
