package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.MyService;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDayIntentService;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.QuoteOfTheDaySharedPreferences;
import com.tieutech.android.quotespire.R;


//Activity serving as a launcher page
@SuppressWarnings({"FieldCanBeLocal", "PointlessBooleanExpression"})
public class IntroActivity extends AppCompatActivity {

    private final int INTRO_ACTIVITY_DISPLAY_TIME = 1200;   //Time to display the activity for (in ms)


    //==================================== Define METHODS ============================================================================

    //Override onCreate(..) activity lifecycle callback method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set screen orientation to vertical

        requestWindowFeature(Window.FEATURE_NO_TITLE); //Request for "no title" feature, turning off the title at the top of the screen.

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Request for full screen of the activity

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro); //Set the view of the activity

        //Get the reference variable to action bar, and hide it if it exists
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        //Create a new Handler object - to be run on a thread asynchronously to the main thread.
        //Call postDelayed(..) to so that the Runnable object (1st parameter) could be added to the message queue, which is run after the specified amount of time elapses (2nd parameter)
        // Parameter #1 (Runnable): Runnable to be added to the message queue and run
        // Parameter #2 (int): Time (in ms) delay in which the Runnable (parameter #1) is to be run
        new Handler().postDelayed(

                new Runnable() {

                    //Define what to do after the time delay
                    @Override
                    public void run() {
                        Intent startActivityIntent = new Intent(IntroActivity.this, MainActivity.class); //Intent to start MainActivity
                        startActivity(startActivityIntent); //Start MainActivity
                        IntroActivity.this.finish(); //Finish/close the IntroActivity activity
                    }
                },
                INTRO_ACTIVITY_DISPLAY_TIME);


        //Check if the CheckBox of the Push Notification in SettingsFragment has EVER been pressed.. Only act as per below if this button has NEVER been pressed
        //NOTE: This button would never have been pressed, for example, WHEN the app has (just) been installed into the device, and nothing has been done with this button
        if (QuoteOfTheDaySharedPreferences.isPushNotificationSwitchPressed(IntroActivity.this) == false){

            QuoteOfTheDayIntentService.setPushNotificationIntentServiceState(IntroActivity.this, true); //Set the Push Notification for Daily Quote Of The Day updates

            //Start Service - so that AlarmManager created in QuoteOfTheDayIntentService.setPushNotificationIntentServiceState() would be run in the background
            //NOTE: Starting a service just means that the app will be run in the background!
            String ACTION_START_SERVICE = "com.tieutech.android.quotespire.ACTION_START_SERVICE";
            Intent startIntent = new Intent(IntroActivity.this, MyService.class);
            startIntent.setAction(ACTION_START_SERVICE);
            IntroActivity.this.startService(startIntent);
        }
    }

}
