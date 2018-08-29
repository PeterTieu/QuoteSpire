package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchPicturesOfQuotes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchPicturesOfQuotes.SwipeTabs.SearchPicturesOfQuotesAuthorFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchPicturesOfQuotes.SwipeTabs.SearchPicturesOfQuotesCategoryFragment;

public class SearchPicturesOfQuotesFragmentPagerAdapter extends FragmentPagerAdapter {


    private static final int FRAGMENT_COUNT = 3;

    public SearchPicturesOfQuotesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SearchPicturesOfQuotesAuthorFragment();
            case 1:
                return new SearchPicturesOfQuotesCategoryFragment();
            case 2:
                return new SearchPicturesOfQuotesAuthorFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Keyword";
            case 1:
                return "Category";
            case 2:
                return "Author";
            case 3:
                return "Advanced";
//            case 4:
//                return "Search Quote Images";
//            case 5:
//                return "Settings";
        }
        return null;
    }
}