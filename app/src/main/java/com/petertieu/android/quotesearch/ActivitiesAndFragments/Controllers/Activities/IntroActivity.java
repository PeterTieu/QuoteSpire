package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.GetQuoteOfTheDay;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.GetQuoteOfTheDayAuthorQuote;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.GetQuoteOfTheDayCategoryQuote;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;


//Activity serving as a launcher page
public class IntroActivity extends AppCompatActivity {


    //================= Declare INSTANCE VARIABLES ==============================================================
    private static final String TAG = "IntroActivity";      //Tag for Logcat
    private final int INTRO_ACTIVITY_DISPLAY_TIME = 1200;   //Time to display the activity for (in ms)


    public static Quote sQuoteOfTheDay;

    public static Quote sQuoteOfTheDayCategoryQuote;
    public static Quote sQuoteOfTheDayAuthorQuote;





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





//        new GetQuoteOfTheDayAsyncTask().execute();


        //Create a new Handler object - to be run on a thread asynchronously to the main thread.
        //Call postDelayed(..) to so that the Runnable object (1st parameter) could be added to the message queue, which is run after the specified amount of time elapses (nd parameter)
        // Parameter #1 (Runnable): Runnable to be added to the message queueu and run
        // Parameter #2 (int): Time (in ms) delay in which the Runnable (parameter #1) is to be run
        new Handler().postDelayed(

                new Runnable() {

                    //Define what to do after the time delay
                    @Override
                    public void run() {
                        Intent startActivityIntent = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(startActivityIntent);
                        IntroActivity.this.finish();
                    }
                },

                INTRO_ACTIVITY_DISPLAY_TIME);





//        new GetQuoteOfTheDayAsyncTask().execute();



    }












    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){
            return new GetQuoteOfTheDay().getQuoteOfTheDay();
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDay){
            sQuoteOfTheDay = quoteOfTheDay;

            Log.i(TAG, "Quote of the day - Quote String: " + sQuoteOfTheDay.getQuote());
            Log.i(TAG, "Quote of the day - Category: " + sQuoteOfTheDay.getCategory());
            Log.i(TAG, "Quote of the day - Author: " + sQuoteOfTheDay.getAuthor());
            Log.i(TAG, "Quote of the day - ID: " + sQuoteOfTheDay.getId());





            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();





        }
    }






    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        String quoteOfTheDayAuthor = sQuoteOfTheDay.getAuthor();

        //Build constructor
        public GetQuoteOfTheDayAuthorQuoteAsyncTask(){

        }


        @Override
        protected Quote doInBackground(Void... params){
            return new GetQuoteOfTheDayAuthorQuote().getQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthor);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayAuthorQuote){

            sQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote;

            Log.i(TAG, "Quote of the day Author Quote - Quote String: " + sQuoteOfTheDayAuthorQuote.getQuote());
            Log.i(TAG, "Quote of the day Author Quote - Category: " + sQuoteOfTheDayAuthorQuote.getCategory());
            Log.i(TAG, "Quote of the day Author Quote - Author: " + sQuoteOfTheDayAuthorQuote.getAuthor());
            Log.i(TAG, "Quote of the day Author Quote - ID: " + sQuoteOfTheDayAuthorQuote.getId());



        }

    }




    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = sQuoteOfTheDay.getCategory();

        //Build constructor
        public GetQuoteOfTheDayCategoryQuoteAsyncTask(){
        }


        @Override
        protected Quote doInBackground(Void... params){
            return new GetQuoteOfTheDayCategoryQuote().getQuoteOfTheDayCategoryQuote(quoteOfTheDayCategory);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayCategoryQuote){
            sQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote;

//            if (sQuoteOfTheDayCategoryQuote != null){
//                Log.i(TAG, "Exists");
//            }
//            else{
//                Log.i(TAG, "DOES NOT EXIST");
//            }


            Log.i(TAG, "Quote of the day Category Quote - Quote String: " + sQuoteOfTheDayCategoryQuote.getQuote());
            Log.i(TAG, "Quote of the day Category Quote - Category: " + sQuoteOfTheDayCategoryQuote.getCategory());
            Log.i(TAG, "Quote of the day Category Quote - Author: " + sQuoteOfTheDayCategoryQuote.getAuthor());
            Log.i(TAG, "Quote of the day Category Quote - ID: " + sQuoteOfTheDayCategoryQuote.getId());



        }


    }










}
