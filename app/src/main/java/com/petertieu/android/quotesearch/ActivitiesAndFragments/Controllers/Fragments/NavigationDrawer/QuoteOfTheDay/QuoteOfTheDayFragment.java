package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import org.w3c.dom.Text;

import java.util.List;

public class QuoteOfTheDayFragment extends Fragment{

    //Log for Logcat
    private final String TAG = "QuoteOfTheDayFragment";

    private Quote mQuoteOfTheDay = new Quote();

    private TextView mQuoteOfTheDayQuote;
    private TextView mQuoteOfTheDayAuthor;
    private TextView mQuoteOfTheDayCategory;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        new GetQuoteOfTheDayAsyncTask().execute();
    }


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_quote_of_the_day, viewGroup, false);

        mQuoteOfTheDayQuote = (TextView) view.findViewById(R.id.quote_of_the_day_quote);

        mQuoteOfTheDayCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category);

        mQuoteOfTheDayAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author);


        displayQuoteOfTheDay();

        getActivity().setTitle("Quote of the Day");


        return view;
    }









    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote>{




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){
            return new GetQuoteOfTheDay().getQuoteOfTheDay();
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDay){
            mQuoteOfTheDay = quoteOfTheDay;

            Log.i(TAG, "Quote of the day - Quote: " + mQuoteOfTheDay.getQuote());
            Log.i(TAG, "Quote of the day - Category: " + mQuoteOfTheDay.getCategory());
            Log.i(TAG, "Quote of the day - Author: " + mQuoteOfTheDay.getAuthor());


            displayQuoteOfTheDay();
        }
    }








    private void displayQuoteOfTheDay(){

        if (isAdded()){

            Log.i(TAG, "called");

            mQuoteOfTheDayQuote.setText(mQuoteOfTheDay.getQuote());
            mQuoteOfTheDayCategory.setText(mQuoteOfTheDay.getCategory());
            mQuoteOfTheDayAuthor.setText(mQuoteOfTheDay.getAuthor());
        }

    }


}
