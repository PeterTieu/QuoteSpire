package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures;

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


public class SearchQuotePicturesFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "LanguageChooserFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public SearchQuotePicturesFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes, viewGroup, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.search_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.search_view_pager);

        mViewPager.setAdapter(new SearchQuotePicturesFragmentPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);


        getActivity().setTitle("Search Quotes");

        return view;
    }

}