package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tieutech.android.quotespire.R;


//Fragment of the "Search Quotes" navigation drawer item
//Contains the swipe tabs of Search Quotes (i.e. "Keyword", "Category", "Author", "Advanced")
public class SearchQuotesFragment extends Fragment {


    //================= Declare INSTANCE VARIABLES ==============================================================

    //Log for Logcat
    private final String TAG = "LanguageChooserFragment";

    //---- SWIPE TABS VARIABLES ----
    private TabLayout mTabLayout; //TabLayout view - component of the Swipe Tabs layout
    private ViewPager mViewPager; //ViewPager - Allows swiping between multiple TabLayouts, making them into Swipe Tabs



    //================= Define METHODS ===========================================================================

    //Constructor
    public SearchQuotesFragment() {
    }


    //Announce that the fragment may only be run on API level Lollipop or higher
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        //Inflate the fragment_search_quotes layout
        View view = layoutInflater.inflate(R.layout.fragment_search_quotes, viewGroup, false);


        //Assign the View elements to the intance variables
        mTabLayout = (TabLayout) view.findViewById(R.id.search_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.search_view_pager);


        //Set an adapter to the ViewPager - to display all the Swipe Tabs fragments (i.e. "Keyword", "Category", "Author", "Advanced")
        mViewPager.setAdapter(new SearchQuotesFragmentPagerAdapter(getChildFragmentManager()));

        //Set the TabLayout view to the ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        //Set title of the Fragment
        getActivity().setTitle("Search Quotes");


//        setHasOptionsMenu(true);

        return view;
    }



//    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
//
//        //Log lifecycle callback
//        Log.i(TAG, "onCreateOptionsMenu(..) called");
//
//        //Inflate a menu hierarchy from specified resource
//        menuInflater.inflate(R.menu.activity_main, menu);
//    }

}
