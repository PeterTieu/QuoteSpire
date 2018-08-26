package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes.SwipeTabs.FavoriteQuotePicturesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes.SwipeTabs.FavoriteQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced.SearchQuotesByAdvancedFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory.SearchQuotesByCategoryFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword.SearchQuotesByKeywordFragment;


//Adapter for the ViewPager of the "Favorites" navigation drawer fragment item.
// Function is to display the Swipe Tabs fragments: "Quotes", "Quote Pictures"
public class FavoritesFragmentPagerAdapter extends FragmentPagerAdapter{


    //================= Declare INSTANCE VARIABLES ==============================================================
    //Number of Swipe Tabs fragments
    private static final int FRAGMENT_COUNT = 2;


    //================= Define METHODS ===========================================================================
    //Constructor
    public FavoritesFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }


    //Override getItem(..) method from FragmentPagerAdapter
    @Override
    public Fragment getItem(int position){

        //Scan through the position numbers of the Swipe Tabs selected, and return the associated Fragments
        switch (position){
            case 0:
                return new FavoriteQuotesFragment();
            case 1:
                return new FavoriteQuotePicturesFragment();
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
                return "Quotes";
            case 1:
                return "Quote Pictures";
        }
        return null;
    }

}
