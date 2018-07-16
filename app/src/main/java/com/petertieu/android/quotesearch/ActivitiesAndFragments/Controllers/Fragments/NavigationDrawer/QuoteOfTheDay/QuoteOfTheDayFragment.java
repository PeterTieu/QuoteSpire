package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.IntroActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

public class QuoteOfTheDayFragment extends DynamicBroadcastReceiver{

    //Log for Logcat
    private final String TAG = "QuoteOfTheDayFragment";




    private Quote mQuoteOfTheDay = new Quote();

    private TextView mQuoteOfTheDayQuote;
    private TextView mQuoteOfTheDayAuthor;
    private TextView mQuoteOfTheDayCategory;





    private Quote mQuoteOfTheDayAuthorQuote = new Quote();

    private TextView mQuoteOfTheDayAuthorQuoteQuote;
    private TextView mQuoteOfTheDayAuthorQuoteAuthor;
    private TextView mQuoteOfTheDayAuthorQuoteCategory;





    private Quote mQuoteOfTheDayCategoryQuote = new Quote();

    private TextView mQuoteOfTheDayCategoryQuoteQuote;
    private TextView mQuoteOfTheDayCategoryQuoteAuthor;
    private TextView mQuoteOfTheDayCategoryQuoteCategory;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");

        setRetainInstance(true);

//        new GetQuoteOfTheDayAsyncTask().execute();
    }


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called");

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_quote_of_the_day, viewGroup, false);

        mQuoteOfTheDayQuote = (TextView) view.findViewById(R.id.quote_of_the_day_quote);
        mQuoteOfTheDayCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category);
        mQuoteOfTheDayAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author);


        mQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        mQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        mQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);


        mQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        mQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        mQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);



        displayQuoteOfTheDay();

        getActivity().setTitle("Quote of the Day");


        return view;
    }









//    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote>{
//
//
//
//
//        //Build constructor
//        public GetQuoteOfTheDayAsyncTask(){
//        }
//
//
//
//        @Override
//        protected Quote doInBackground(Void... params){
//            return new GetQuoteOfTheDay().getQuoteOfTheDay();
//        }
//
//
//        @Override
//        protected void onPostExecute(Quote quoteOfTheDay){
//            sQuoteOfTheDay = quoteOfTheDay;
//
//            Log.i(TAG, "Quote of the day - Quote: " + sQuoteOfTheDay.getQuote());
//            Log.i(TAG, "Quote of the day - Category: " + sQuoteOfTheDay.getCategory());
//            Log.i(TAG, "Quote of the day - Author: " + sQuoteOfTheDay.getAuthor());
//            Log.i(TAG, "Quote of the day - ID: " + sQuoteOfTheDay.getId());
//
//
//            displayQuoteOfTheDay();
//        }
//    }








    private void displayQuoteOfTheDay(){

        if (isAdded()){

            Log.i(TAG, "called");

            mQuoteOfTheDay = IntroActivity.sQuoteOfTheDay;

            if (mQuoteOfTheDayQuote != null){
                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
            }


            if (mQuoteOfTheDayAuthor != null){
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
            }


            if (mQuoteOfTheDayCategory != null){
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
            }







            mQuoteOfTheDayAuthorQuote = IntroActivity.sQuoteOfTheDayAuthorQuote;

            if (mQuoteOfTheDayAuthorQuoteQuote != null){
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
            }

            if (mQuoteOfTheDayAuthorQuoteAuthor != null){
                mQuoteOfTheDayAuthorQuoteAuthor.setText("-" + mQuoteOfTheDayAuthorQuote.getAuthor());
            }

            if (mQuoteOfTheDayAuthorQuoteCategory != null){
                mQuoteOfTheDayAuthorQuoteCategory.setText("Category: " + mQuoteOfTheDayAuthorQuote.getCategory());
            }








            mQuoteOfTheDayCategoryQuote = IntroActivity.sQuoteOfTheDayCategoryQuote;

            if (mQuoteOfTheDayCategoryQuote == null){
                Toast.makeText(getContext(), "Does not exist", Toast.LENGTH_LONG).show();
            }

            if (IntroActivity.sQuoteOfTheDayCategoryQuote == null){
                Toast.makeText(getContext(), "Static Does NOT exist", Toast.LENGTH_LONG).show();
            }

            if (mQuoteOfTheDayCategoryQuote != null){
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
            }

            if (mQuoteOfTheDayCategoryQuote != null){
                mQuoteOfTheDayCategoryQuoteAuthor.setText("-" + mQuoteOfTheDayCategoryQuote.getAuthor());
            }

            if (mQuoteOfTheDayCategoryQuote != null){
                mQuoteOfTheDayCategoryQuoteCategory.setText("Category: " + mQuoteOfTheDayCategoryQuote.getCategory());
            }




        }

    }









    @Override
    public void onStart(){
        super.onStart();

        Log.i(TAG, "onStart() called");
    }




    @Override
    public void onResume(){
        super.onResume();

        Log.i(TAG, "onResume() called");
    }


    @Override
    public void onPause(){
        super.onPause();

        Log.i(TAG, "onPause() called");
    }







}