package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced.SearchQuotesByAdvancedFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategoryFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordFragment;


//Adapter for the ViewPager of the "Search Quotes" navigation drawer fragment item.
// Function is to display the Swipe Tabs fragments: "Keyword", "Category", "Author", "Advanced"
public class SearchQuotesFragmentPagerAdapter extends FragmentPagerAdapter {


    //================= Declare INSTANCE VARIABLES ==============================================================
    //Number of Swipe Tabs fragments
    private static final int FRAGMENT_COUNT = 4;


    //================= Define METHODS ===========================================================================
    //Constructor
    public SearchQuotesFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }


    //Override getItem(..) method from FragmentPagerAdapter
    @Override
    public Fragment getItem(int position){

        //Scan through the position numbers of the Swipe Tabs selected, and return the associated Fragments
        switch (position){
            case 0:
                return new SearchQuotesByKeywordFragment();
            case 1:
                return new SearchQuotesByCategoryFragment();
            case 2:
                return new SearchQuotesByAuthorFragment();
            case 3:
                return new SearchQuotesByAdvancedFragment();
        }
        return null;
    }


    //Override getCount() method from FragmentPagerAdapter
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }


    //Override getPageTitle(..) method from FragmentPagerAdapter - to assign the page titles of the Swipe Tabs selected
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Keyword";
            case 1:
                return "Category";
            case 2:
                return "Author";
            case 3:
                return "Advanced";
        }
        return null;
    }

}
