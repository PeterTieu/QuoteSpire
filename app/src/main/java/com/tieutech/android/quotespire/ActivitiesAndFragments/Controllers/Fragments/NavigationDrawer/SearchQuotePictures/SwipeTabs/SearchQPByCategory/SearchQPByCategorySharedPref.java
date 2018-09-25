package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory;

import android.content.Context;
import android.preference.PreferenceManager;

//A SharedPreferences class used for setting (writing) and getting (reading) search queries to/from the SearchView of the SearchQPByCategoryFragment class.
//NOTE: Values saved into ShredPreferences is retrievable even between device restarts.
public class SearchQPByCategorySharedPref {

    private static final String SEARCH_QUOTE_PICTURES_BY_CATEGORY_SEARCH_QUERY_KEY = "SearchQPByCategorySearchQueryKey"; //Key for saving and loading search query from the SharedPreferences


    //Set (write) the search query into SharedPreferences
    public static void setSearchQuotesByCategoryStoredQuery(Context context, String searchQPByCategorySearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTE_PICTURES_BY_CATEGORY_SEARCH_QUERY_KEY, searchQPByCategorySearchQuery)
                .apply();
    }


    //Get (read) the search query from SharedPreferences
    public static String getSearchQuotesByCategoryStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTE_PICTURES_BY_CATEGORY_SEARCH_QUERY_KEY, null);
    }
}