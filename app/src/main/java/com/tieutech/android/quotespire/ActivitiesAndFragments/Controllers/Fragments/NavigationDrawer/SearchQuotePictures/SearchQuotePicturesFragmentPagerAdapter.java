package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByAuthor.SearchQPByAuthorFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory.SearchQPByCategoryFragment;


//Adapter called by SearchQuotePicturesFragment
    //Holds the following fragments:
        //1: SearchQPByCategoryFragment
        //2: SearchQPByAuthorFragment
public class SearchQuotePicturesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 2; //How many fragments/swipe tabs to display


    //Constructor - called by SearchQuotePicturesFragment
    public SearchQuotePicturesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    //Override getItem(..) method from FragmentPagerAdapter
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SearchQPByCategoryFragment();
            case 1:
                return new SearchQPByAuthorFragment();
        }
        return null;
    }



    //Override getCount(..) method from FragmentPagerAdapter
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }



    //Override getPageTitle(..) method from FragmentPagerAdapter
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Category";
            case 1:
                return "Author";
        }
        return null;
    }

}