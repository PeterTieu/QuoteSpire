package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private TextView mQuoteOfTheDayAuthorQuoteTitleAuthorName;
    private TextView mQuoteOfTheDayAuthorQuoteQuote;
    private TextView mQuoteOfTheDayAuthorQuoteAuthor;
    private TextView mQuoteOfTheDayAuthorQuoteCategory;





    private Quote mQuoteOfTheDayCategoryQuote = new Quote();

    private TextView mQuoteOfTheDayCategoryQuoteTitleCategoryName;
    private TextView mQuoteOfTheDayCategoryQuoteQuote;
    private TextView mQuoteOfTheDayCategoryQuoteAuthor;
    private TextView mQuoteOfTheDayCategoryQuoteCategory;



    private ProgressBar mProgressBarQuoteOfTheDayQuoteQuote;
    private ProgressBar mProgressBarQuoteOfTheDayAuthorQuote;
    private ProgressBar mProgressBarQuoteOfTheDayCategoryQuote;



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


        mQuoteOfTheDayAuthorQuoteTitleAuthorName = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title_author_name);
        mQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        mQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        mQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);


        mQuoteOfTheDayCategoryQuoteTitleCategoryName = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title_category_name);
        mQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        mQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        mQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);


        mProgressBarQuoteOfTheDayQuoteQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_quote_quote);
        mProgressBarQuoteOfTheDayAuthorQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_author_quote);
        mProgressBarQuoteOfTheDayCategoryQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_category_quote);




        displayQuoteOfTheDay();

        getActivity().setTitle("Quote of the Day");


        return view;
    }

























    private void displayQuoteOfTheDay(){

        if (isAdded()){

            Log.i(TAG, "called");







            mQuoteOfTheDay = IntroActivity.sQuoteOfTheDay;
            mQuoteOfTheDayAuthorQuote = IntroActivity.sQuoteOfTheDayAuthorQuote;
            mQuoteOfTheDayCategoryQuote = IntroActivity.sQuoteOfTheDayCategoryQuote;



            if (mQuoteOfTheDay == null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){
                new GetQuoteOfTheDayAsyncTask().execute();
            }



            //TODO: COMPARTMENTALISE THE BELOW "setText(..)" CALLS INTO METHODS

            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);

                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());

                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

            }





            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote == null){

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);



                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());


                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                mQuoteOfTheDayAuthorQuoteAuthor.setText("-" + mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());

                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();
            }






            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote != null){


                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);


                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());


                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("-" + mQuoteOfTheDayCategoryQuote.getAuthor());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());

                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            }






            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote != null){


                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);



                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());


                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                mQuoteOfTheDayAuthorQuoteAuthor.setText("-" + mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Category: " + mQuoteOfTheDayAuthorQuote.getCategory());


                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("-" + mQuoteOfTheDayCategoryQuote.getAuthor());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());

            }







        }

    }







    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){

            mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDay().getQuoteOfTheDay();
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDay){
            mQuoteOfTheDay = quoteOfTheDay;

            Log.i(TAG, "Quote of the day - Quote String: " + mQuoteOfTheDay.getQuote());
            Log.i(TAG, "Quote of the day - Category: " + mQuoteOfTheDay.getCategory());
            Log.i(TAG, "Quote of the day - Author: " + mQuoteOfTheDay.getAuthor());
            Log.i(TAG, "Quote of the day - ID: " + mQuoteOfTheDay.getId());


            if (mQuoteOfTheDayQuote != null){

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);

                mQuoteOfTheDayQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
            }




            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


        }
    }








    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        String quoteOfTheDayAuthor = mQuoteOfTheDay.getAuthor();

        //Build constructor
        public GetQuoteOfTheDayAuthorQuoteAsyncTask(){

        }


        @Override
        protected Quote doInBackground(Void... params){


            mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayAuthorQuote().getQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthor);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayAuthorQuote){


            mQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote;

            Log.i(TAG, "Quote of the day Author Quote - Quote String: " + mQuoteOfTheDayAuthorQuote.getQuote());
            Log.i(TAG, "Quote of the day Author Quote - Category: " + mQuoteOfTheDayAuthorQuote.getCategory());
            Log.i(TAG, "Quote of the day Author Quote - Author: " + mQuoteOfTheDayAuthorQuote.getAuthor());
            Log.i(TAG, "Quote of the day Author Quote - ID: " + mQuoteOfTheDayAuthorQuote.getId());





            if (mQuoteOfTheDayAuthorQuote != null){


                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);

                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                mQuoteOfTheDayAuthorQuoteAuthor.setText("-" + mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());



            }


        }

    }















    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = mQuoteOfTheDay.getCategory();

        //Build constructor
        public GetQuoteOfTheDayCategoryQuoteAsyncTask(){
        }


        @Override
        protected Quote doInBackground(Void... params){

            mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayCategoryQuote().getQuoteOfTheDayCategoryQuote(quoteOfTheDayCategory);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayCategoryQuote){
            mQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote;


            Log.i(TAG, "Quote of the day Category Quote - Quote String: " + mQuoteOfTheDayCategoryQuote.getQuote());
            Log.i(TAG, "Quote of the day Category Quote - Category: " + mQuoteOfTheDayCategoryQuote.getCategory());
            Log.i(TAG, "Quote of the day Category Quote - Author: " + mQuoteOfTheDayCategoryQuote.getAuthor());
            Log.i(TAG, "Quote of the day Category Quote - ID: " + mQuoteOfTheDayCategoryQuote.getId());



            if (mQuoteOfTheDayCategoryQuote != null){

                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);

                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("-" + mQuoteOfTheDayCategoryQuote.getAuthor());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
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