package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.io.FileInputStream;

public class QuotePictureDetailActivity extends AppCompatActivity implements QuotePictureDetailFragment.Callbacks{

    private final static String TAG = "QPDActivity";

    public final static String QUOTE_PICTURE_ID = "quotePictureIDKey";
    public final static String QUOTE_PICTURE_BYTE_ARRAY_KEY = "quotePictureByteArrayKey";


    private String mQuotePictureID;
    private byte[] mQuotePictureByteArray;
    private Bitmap mQuotePictureBitmap;


    @Override
    public void scrollViewPagerBackward(){
        //Do nothing
    }

    @Override
    public void scrollViewPagerForward(){
        //Do nothing
    }




//    public static Intent newIntent(Context context, QuotePicture quotePicture, String quotePictureId, byte[] quotePictureByteArray){
//
//        Log.i(TAG, "newIntent(..) called");
//
//        Log.i(TAG, "ID in newIntent(): " + quotePictureId);
//
//        Intent intent  = new Intent(context, QuotePictureDetailActivity.class);
//
//        intent.putExtra(QUOTE_PICTURE_ID, quotePictureId);
//
//        intent.putExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureByteArray);
//
//
//        return intent;
//
//    }




    public static Intent newIntent(Context context, String quotePictureId, byte[] quotePictureBitmapByteArray){

        Log.i(TAG, "newIntent(..) called");

        Log.i(TAG, "ID in newIntent(): " + quotePictureId);

        Intent intent  = new Intent(context, QuotePictureDetailActivity.class);

        intent.putExtra(QUOTE_PICTURE_ID, quotePictureId);

        intent.putExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureBitmapByteArray);


        return intent;

    }






    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");



        setContentView(R.layout.activity_quote_picture_detail);

        mQuotePictureID = getIntent().getStringExtra(QUOTE_PICTURE_ID);
        mQuotePictureByteArray = getIntent().getByteArrayExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY);


//        Log.i(TAG, "ID in onCreate(): " + mQuotePicture.getId());








        //Return the FragmentManager for interacting with fragments associated with this activity
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Find a fragment that was identified by the given ID
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        //If the fragment doesn't exist yet
        if (fragment == null){
            //Create a fragment
            fragment = createFragment();
            //Start a series of edit operations on the Fragments associated with this FragmentManager
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }









    }




    protected Fragment createFragment(){

        //Create argument-bundle to pass data to QuotePictureDetailFragment
        Bundle argumentBundle = new Bundle();


        argumentBundle.putString(QUOTE_PICTURE_ID, mQuotePictureID); //Add Quote Picture ID (String) to the argument-bundle


        argumentBundle.putByteArray(QUOTE_PICTURE_BYTE_ARRAY_KEY, mQuotePictureByteArray); //Add Quote Picture Byte Array (byte[]) to the argument-bundle

        //Create QuotePictureDetailFragment
        QuotePictureDetailFragment quotePictureDetailFragment = new QuotePictureDetailFragment();

        //Link the argument-bundle to the QuotePictureDetailFragment object
        quotePictureDetailFragment.setArguments(argumentBundle);

        //Return the QuotePictureDetailFragment
        return quotePictureDetailFragment;
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
