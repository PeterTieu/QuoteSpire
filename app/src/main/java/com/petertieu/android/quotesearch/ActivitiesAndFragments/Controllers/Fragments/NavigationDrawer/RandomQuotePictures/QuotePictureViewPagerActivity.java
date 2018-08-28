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


import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.util.List;

public class QuotePictureViewPagerActivity extends AppCompatActivity{

    private final static String TAG = "QPViewPagerActivity";

    public final static String ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST = "quotePictureViewPagerActivityActualSizeOfQuotePicturesList";
    public final static String QUOTE_PICTURE_ID = "quotePictureViewPagerIDKey";
    public final static String QUOTE_PICTURE_BYTE_ARRAY_KEY = "quotePictureViewPagerByteArrayKey";

    private List<QuotePicture> mQuotePicturesList = RandomQuotePicturesFragment.mRandomQuotePictureQuotes;

    public static ViewPager sViewPager;
    private static final int OFF_SCREEN_PAGE_LIMIT = 5;





    public static Intent newIntent(Context context, int actualSizeOfQuotePicturesList, String quotePictureId, byte[] quotePictureBitmapByteArray){

        Log.i(TAG, "newIntent(..) called");

        Log.i(TAG, "ID in newIntent(): " + quotePictureId);

        Intent intent  = new Intent(context, QuotePictureViewPagerActivity.class);

        intent.putExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, actualSizeOfQuotePicturesList);
        intent.putExtra(QUOTE_PICTURE_ID, quotePictureId);
        intent.putExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureBitmapByteArray);


        return intent;

    }




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");


        setContentView(R.layout.activity_quote_picture_view_pager); //Set the activity content from the ViewPager layout resource
        sViewPager = (ViewPager) findViewById(R.id.quote_picture_view_pager); //Assign the ViewPager to its associated resource ID
        sViewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT); //Set total number of detail fragments to pre-load outside of the current fragment on screen



        final int actualSizeOfQuotePicturesList = (int) getIntent().getIntExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, 0);
        String quotePictureID = (String) getIntent().getStringExtra(QUOTE_PICTURE_ID);



        FragmentManager fragmentManager = getSupportFragmentManager(); //Create a FragmentManager



        //Set the Adapter to the ViewPager
        sViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            //Override method from the FragmentStatePagerAdapter
            @Override
            public Fragment getItem(int position) {

                //Get a specific Pix from the List of Pix objects
                QuotePicture quotePicture = mQuotePicturesList.get(position);

                //Create and return a new PixDetailFragment fragment
//                return QuotePictureDetailFragment.newInstance(quotePicture.getId(), quotePicture.getQuotePictureBitmapByteArray());
                return QuotePictureDetailFragment.newInstance(quotePicture.getId(), quotePicture.getQuotePictureBitmapByteArray());
            }

            //Override method from the FragmentStatePagerAdapter
            @Override
            public int getCount() {
                //Get the size of the List of Pix objects
                return actualSizeOfQuotePicturesList;
            }
        });



        for (int i = 0; i < actualSizeOfQuotePicturesList; i++) {


            if (mQuotePicturesList.get(i).getId().equals(quotePictureID)) {

                sViewPager.setCurrentItem(i);

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
