package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.petertieu.android.quotesearch.R;



//Activity that hosts the QuotePictureDetailFragment fragment - to display the Quote Picture selected in:
//RandomQuotePicturesFragment, SearchQPByCategoryFragment, or SearchQPByAuthorFragment
//NOTE: The data passed (as extras) to this activity from the intents of the above fragments are:
    //1: QuotePicture ID
    //2: QuotePicture Picture byte array. NOTE: The bitmap byte array is transferred across instead of the bitmap, because bitmaps are too large to be extras
@SuppressWarnings("FieldCanBeLocal")
public class QuotePictureDetailActivity extends AppCompatActivity{

    private final static String TAG = "QPDActivity"; //Tag for Logcat

    public final static String QUOTE_PICTURE_ID_KEY = "quotePictureIDKey"; //Key for Intent extra: Quote Picture ID
    public final static String QUOTE_PICTURE_BYTE_ARRAY_KEY = "quotePictureByteArrayKey"; //Key for Intent extra: Bitmap Byte Array

    private String mQuotePictureID; //Extracted Quote Picture ID
    private byte[] mQuotePictureByteArray; //Extracted Bitmap Byte Array





    //Encapsulating intent method - called by: RandomQuotePicturesFragment, SearchQPByCategoryFragment, or SearchQPByAuthorFragment
    // when a QuotePicture list item is clicked on
    public static Intent newIntent(Context context, String quotePictureId, byte[] quotePictureBitmapByteArray){

        Log.i(TAG, "newIntent(..) called"); //Log to logcat
        Log.i(TAG, "ID in newIntent(): " + quotePictureId); //Log to Logcat

        Intent intent  = new Intent(context, QuotePictureDetailActivity.class); //Create Intent to start this activity

        intent.putExtra(QUOTE_PICTURE_ID_KEY, quotePictureId); //Send QuotePicture ID as extra
        intent.putExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureBitmapByteArray); //Send Bitmap Byte Array as extra

        return intent; //Return Intent
    }




    //Override onCreate(..) activity lifecycle callback method
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setContentView(R.layout.activity_quote_picture_detail); //et the View of the activity

        mQuotePictureID = getIntent().getStringExtra(QUOTE_PICTURE_ID_KEY); //Retrieve intent extra: QuotePicture ID
        mQuotePictureByteArray = getIntent().getByteArrayExtra(QUOTE_PICTURE_BYTE_ARRAY_KEY); //Retrieve intent extra: Bitmap Byte Array

        FragmentManager fragmentManager = getSupportFragmentManager(); //Create FragmentManager for interacting with fragments associated with this activity
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container); //Find a fragment that was identified by the given ID

        //If the fragment doesn't exist yet
        if (fragment == null){

            //Create the QuotePictureDetailFragment fragment, passing as data (in the argument-bundle): QuotePicture ID... and... Bitmap Byte Array
            fragment = QuotePictureDetailFragment.newInstance(mQuotePictureID, mQuotePictureByteArray);

            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit(); //Add the Fragment and open it in the activity
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
