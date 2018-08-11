package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

import android.content.Context;
import android.preference.PreferenceManager;

public class SearchQuotesByKeywordSharedPref {


    //Declare key
    private static final String SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY = "SearchQuotesByKeywordSearchQueryKey";



    public static void setSearchQuotesByKeywordStoredQuery(Context context, String searchQuotesByKeywordSearchQuery){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY, searchQuotesByKeywordSearchQuery)
                .apply();
    }


    public static String getSearchQuotesByKeywordStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SEARCH_QUOTES_BY_KEYWORD_SEARCH_QUERY_KEY, null);
    }




}
