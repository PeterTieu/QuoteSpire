package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory;

import android.content.Context;
import android.preference.PreferenceManager;


//A SharedPreferences class used for setting (writing) and getting (reading) search queries to/from the SearchView of the SearchQuotesByCategoryFragment class.
//NOTE: Values saved into ShredPreferences is retrievable even between device restarts.
public class SearchQuotesByCategorySharedPref {

    private static final String SEARCH_QUOTES_BY_CATEGORY_SEARCH_QUERY_KEY = "SearchQuotesByCategorySearchQueryKey"; //Key for saving and loading search query from the SharedPreferences


    //Set (write) the search query into SharedPreferences
    public static void setSearchQuotesByCategoryStoredQuery(Context context, String searchQuotesByCategorySearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTES_BY_CATEGORY_SEARCH_QUERY_KEY, searchQuotesByCategorySearchQuery)
                .apply();
    }


    //Get (read) the search query from SharedPreferences
    public static String getSearchQuotesByCategoryStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTES_BY_CATEGORY_SEARCH_QUERY_KEY, null);
    }
}
