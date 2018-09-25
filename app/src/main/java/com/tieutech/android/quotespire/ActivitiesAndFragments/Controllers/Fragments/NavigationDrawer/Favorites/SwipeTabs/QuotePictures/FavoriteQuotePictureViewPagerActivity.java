package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.QuotePictures;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureDetailFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import com.tieutech.android.quotespire.R;

import java.util.List;

public class FavoriteQuotePictureViewPagerActivity extends AppCompatActivity{








    private final static String TAG = "QPViewPagerActivity";

    public final static String ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST = "quotePictureViewPagerActivityActualSizeOfQuotePicturesList";
    public final static String QUOTE_PICTURE_BITMAP_FILE_PATH = "quotePictureViewPagerActivityFilePath";
    public final static String QUOTE_PICTURE_ID = "quotePictureViewPagerIDKey";


    public static ViewPager sViewPager;
    private static final int OFF_SCREEN_PAGE_LIMIT = 3;



    public List<QuotePicture> mQuotePicturesList;
    public static int mActualSizeOfQuotePictursList;
    public String mQuotePictureFilePath;
    private String mQuotePictureID;




    //Params:
        //1 (context): Context
        //2 (int): The non-null size of mQuotePicturesList. If we instead use mQuotePicturesList.size(), a NullPointException thrown when the ViewPager tries to access null elements of mQuotePicturesList if not all QuotePictures are finished loading
    public static Intent newIntent(Context context, int actualSizeOfQuotePicturesList, String quotePictureFilePath, String quotePictureId){

        Log.i(TAG, "newIntent(..) called");

        Log.i(TAG, "ID in newIntent(): " + quotePictureId);

        Intent intent  = new Intent(context, FavoriteQuotePictureViewPagerActivity.class);

        intent.putExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, actualSizeOfQuotePicturesList);
        intent.putExtra(QUOTE_PICTURE_BITMAP_FILE_PATH, quotePictureFilePath);
        intent.putExtra(QUOTE_PICTURE_ID, quotePictureId);


        return intent;

    }




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_quote_picture_view_pager); //Set the activity content from the ViewPager layout resource
        sViewPager = (ViewPager) findViewById(R.id.quote_picture_view_pager); //Assign the ViewPager to its associated resource ID
        sViewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT); //Set total number of detail fragments to pre-load outside of the current fragment on screen



        mActualSizeOfQuotePictursList = (int) getIntent().getIntExtra(ACTUAL_SIZE_OF_QUOTE_PICTURES_LIST, 0);
        mQuotePictureFilePath = (String) getIntent().getStringExtra(QUOTE_PICTURE_BITMAP_FILE_PATH);
        mQuotePictureID = (String) getIntent().getStringExtra(QUOTE_PICTURE_ID);


        Log.i(TAG, "mQuotePictureID: " + mQuotePictureID);



        FragmentManager fragmentManager = getSupportFragmentManager(); //Create a FragmentManager


        mQuotePicturesList = FavoriteQuotePicturesManager.get(this).getFavoriteQuotePictures();
//        Collections.reverse(mQuotePicturesList);



        //Set the Adapter to the ViewPager
        sViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {


            //Override method from the FragmentStatePagerAdapter
            @Override
            public Fragment getItem(int position) {

                position = (mActualSizeOfQuotePictursList - 1) - position; //Reverse the order of the creation of the Fragments.. otherwise, clicking on item #1 in the list would open item #16 and vice versa!


                //Get a specific Pix from the List of Pix objects
                QuotePicture quotePicture = mQuotePicturesList.get(position);

                //Create and return a new PixDetailFragment fragment
//                return QuotePictureDetailFragment.newInstance(quotePicture.getId(), quotePicture.getQuotePictureBitmapByteArray());
                Log.i(TAG, "quotePicture.getId(): " + quotePicture.getId());
//                Log.i(TAG, "quotePicture.getQuotePictureBitmapByteArray(): " + quotePicture.getQuotePictureBitmapByteArray());

                return FavoriteQuotePictureDetailFragment.newInstance(quotePicture.getQuotePictureBitmapFilePath(), quotePicture.getId());
            }

            //Override method from the FragmentStatePagerAdapter
            @Override
            public int getCount() {
                //Get the size of the List of Pix objects
                return mActualSizeOfQuotePictursList;
            }
        });










        for (currentItemNumber = 0; currentItemNumber < mActualSizeOfQuotePictursList; currentItemNumber++) {

            Log.i(TAG, "mQuotePicturesList.get(0).getId(): " + mQuotePicturesList.get(0).getId());

            if (mQuotePicturesList.get(currentItemNumber).getId().equals(mQuotePictureID)) {

                sViewPager.setCurrentItem(currentItemNumber);

//                sViewPager.setCurrentItem(i+1);
                break;
            }
        }




    }







    public static int currentItemNumber;











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
