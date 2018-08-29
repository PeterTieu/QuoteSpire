package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes.FavoriteQuotePictureDetailFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.util.List;

public class QuotePictureViewPagerActivity extends AppCompatActivity implements QuotePictureDetailFragment.Callbacks{



    @Override
    public void scrollViewPagerBackward(){

    }




    private final static String TAG = "QPViewPagerActivity";

    public final static String ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST = "quotePictureViewPagerActivityActualSizeOfQuotePicturesList";
    public final static String QUOTE_PICTURE_ID = "quotePictureViewPagerIDKey";
    public final static String QUOTE_PICTURE_BYTE_ARRAY_KEY = "quotePictureViewPagerByteArrayKey";
    public final static String VIEW_PAGER_POSITION = "viewPagerPosition";

    public static List<QuotePicture> mQuotePicturesList;

    public static ViewPager sViewPager;
    private static final int OFF_SCREEN_PAGE_LIMIT = 5;



    public static int mActualSizeOfQuotePictursList;
    private String mQuotePictureID;




    //Params:
        //1 (context): Context
        //2 (int): The non-null size of mQuotePicturesList. If we instead use mQuotePicturesList.size(), a NullPointException thrown when the ViewPager tries to access null elements of mQuotePicturesList if not all QuotePictures are finished loading
    public static Intent newIntent(Context context, int actualSizeOfQuotePicturesList, String quotePictureId){

        Log.i(TAG, "newIntent(..) called");

        Log.i(TAG, "ID in newIntent(): " + quotePictureId);

        Intent intent  = new Intent(context, QuotePictureViewPagerActivity.class);

        intent.putExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, actualSizeOfQuotePicturesList);
        intent.putExtra(QUOTE_PICTURE_ID, quotePictureId);


        return intent;

    }




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");


        setContentView(R.layout.activity_quote_picture_view_pager); //Set the activity content from the ViewPager layout resource
        sViewPager = (ViewPager) findViewById(R.id.quote_picture_view_pager); //Assign the ViewPager to its associated resource ID
        sViewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT); //Set total number of detail fragments to pre-load outside of the current fragment on screen



        mActualSizeOfQuotePictursList = (int) getIntent().getIntExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, 0);
        mQuotePictureID = (String) getIntent().getStringExtra(QUOTE_PICTURE_ID);


        Log.i(TAG, "mQuotePictureID: " + mQuotePictureID);



        FragmentManager fragmentManager = getSupportFragmentManager(); //Create a FragmentManager


        mQuotePicturesList = FavoriteQuotePicturesManager.get(this).getFavoriteQuotePictures();



        //Set the Adapter to the ViewPager
        sViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            //Override method from the FragmentStatePagerAdapter
            @Override
            public Fragment getItem(int position) {


                //Get a specific Pix from the List of Pix objects
                QuotePicture quotePicture = mQuotePicturesList.get(position);

                //Create and return a new PixDetailFragment fragment
//                return QuotePictureDetailFragment.newInstance(quotePicture.getId(), quotePicture.getQuotePictureBitmapByteArray());
                Log.i(TAG, "quotePicture.getId(): " + quotePicture.getId());
//                Log.i(TAG, "quotePicture.getQuotePictureBitmapByteArray(): " + quotePicture.getQuotePictureBitmapByteArray());

                return FavoriteQuotePictureDetailFragment.newInstance(quotePicture.getId());
            }

            //Override method from the FragmentStatePagerAdapter
            @Override
            public int getCount() {
                //Get the size of the List of Pix objects
                return mActualSizeOfQuotePictursList;
            }
        });







//        sViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                sViewPagerPositionSelected = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });











        for (int i = 0; i < mActualSizeOfQuotePictursList; i++) {

            Log.i(TAG, "mQuotePicturesList.get(0).getId(): " + mQuotePicturesList.get(0).getId());

            if (mQuotePicturesList.get(i).getId().equals(mQuotePictureID)) {

                sViewPager.setCurrentItem(i);

//                sViewPager.setCurrentItem(i+1);
                break;
            }
        }












    }






    @Override
    public void scrollViewPagerForward(){

        for (int i = 0; i < mActualSizeOfQuotePictursList; i++) {


            if (mQuotePicturesList.get(i).getId().equals(mQuotePictureID)) {

//                sViewPager.setCurrentItem(i);

                sViewPager.setCurrentItem(i+1);
                break;
            }
        }
    }







    //Override onStart() Activity lifecycle callback method
    @Override
    public void onStart(){
        super.onStart();

        //Log to Logcat
        Log.i(TAG, "onStart() called");
    }




    //Override onResume() Activity lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();

        //Log to Logcat
        Log.i(TAG, "onResume() called");
    }



    //Override onPause() Activity lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();

        //Log to Logcat
        Log.i(TAG, "onPause() called");
    }




    //Override onStop() Activity lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();

        //Log to Logcat
        Log.i(TAG, "onStop() called");
    }




    //Override onDestroy() Activity lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();

        //Log to Logcat
        Log.i(TAG, "onDestroy() called");
    }

}
