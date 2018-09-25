package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.QuotePictures.FavoriteQuotePicturesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.Quotes.FavoriteQuotesFragment;


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
