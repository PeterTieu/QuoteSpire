package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.petertieu.android.quotesearch.R;


//Activity serving as a launcher page
public class IntroActivity extends AppCompatActivity {


    //================= Declare INSTANCE VARIABLES ==============================================================
    private static final String TAG = "IntroActivity";      //Tag for Logcat
    private final int INTRO_ACTIVITY_DISPLAY_TIME = 3000;   //Time to display the activity for (in ms)




    public static Intent newIntent(Context context){
        return new Intent(context, IntroActivity.class);
    }




    //==================================== Define METHODS ============================================================================

    //Override onCreate(..) activity lifecycle callback method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Request for "no title" feature, turning off the title at the top of the screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Request for full screen of the activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        //Set the view of the activity
        setContentView(R.layout.activity_intro);

        //Get the reference variable to action bar, and hide it if it exists
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        //Create a new Handler object - to be run on a thread asynchronously to the main thread.
        //Call postDelayed(..) to so that the Runnable object (1st parameter) could be added to the message queue, which is run after the specified amount of time elapses (nd parameter)
        // Parameter #1 (Runnable): Runnable to be added to the message queueu and run
        // Parameter #2 (int): Time (in ms) delay in which the Runnable (parameter #1) is to be run
        new Handler().postDelayed(

                new Runnable() {
                    @Override
                    public void run() {
                        Intent startActivityIntent = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(startActivityIntent);
                        IntroActivity.this.finish();
                    }
                },

                INTRO_ACTIVITY_DISPLAY_TIME);
    }
}
