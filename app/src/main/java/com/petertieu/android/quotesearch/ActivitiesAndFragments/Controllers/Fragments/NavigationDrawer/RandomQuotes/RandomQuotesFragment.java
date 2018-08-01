package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomQuotesFragment extends Fragment {


    private final int NUMBER_OF_RANDOM_QUOTES = 8;

    //Log for Logcat
    private final String TAG = "RandomQuotesFragment";

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRandomQuotesRecyclerView;
    private RandomQuotesAdaper mRandomQuotesAdaper;

    private List<Quote> mRandomQuotes = new ArrayList<>();
//    private List<Quote> mRandomQuotes = Arrays.asList(new Quote[8]);





    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttached(..) called");

    }


    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);

        Log.i(TAG, "onCreate(..) caled");

        setHasOptionsMenu(true);



    }

    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");



        View view = layoutInflater.inflate(R.layout.fragment_random_quotes, viewGroup, false);


        mRandomQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.random_quotes_recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mRandomQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);




        mRandomQuotesAdaper = new RandomQuotesAdaper(mRandomQuotes);

//        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes);

        mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper);



        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Random Quotes");




        for (int i=0; i<NUMBER_OF_RANDOM_QUOTES; i++){
            new GetRandomQuoteAsyncTask().execute();
        }





        return view;
    }













    private class GetRandomQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        public GetRandomQuoteAsyncTask(){
        }

        @Override
        protected Quote doInBackground(Void... params){

            Quote randomQuote = new GetRandomQuote().getRandomQuote();

            return randomQuote;

        }


        @Override
        protected void onPostExecute(Quote randomQuote){

            Log.i(TAG, "Random Quote - method - Quote String: " + randomQuote.getQuote());
            Log.i(TAG, "Random Quote - method  - Category: " + randomQuote.getCategory());
            Log.i(TAG, "Random Quote - method  - Author: " + randomQuote.getAuthor());
            Log.i(TAG, "Random Quote - method - ID: " + randomQuote.getId());


            mRandomQuotes.add(randomQuote);
//            mRandomQuotes.set(i, randomQuote);



            setupAdapter();
        }


    }





    public void setupAdapter(){

//        mRandomQuotesAdaper = new RandomQuotesAdaper(mRandomQuotes);

//        mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper);

        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes);
        mRandomQuotesAdaper.notifyDataSetChanged();

    }












    private class RandomQuotesAdaper extends RecyclerView.Adapter<RandomQuotesViewHolder>{

        private RandomQuotesViewHolder mRandomQuotesViewHolder;



        public RandomQuotesAdaper(List<Quote> randomQuotes){
            mRandomQuotes = randomQuotes;
            Log.i(TAG, "ADAPTER - mRandomQuotes: " + mRandomQuotes);
        }


        @Override
        public int getItemCount(){
            return mRandomQuotes.size();
        }




        @Override
        public RandomQuotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_random_quote, viewGroup, false);

            mRandomQuotesViewHolder = new RandomQuotesViewHolder(view);

            return mRandomQuotesViewHolder;
        }


        @Override
        public void onBindViewHolder(RandomQuotesViewHolder randomQuotesViewHolder, int position){
            Quote randomQuote = mRandomQuotes.get(position);

            randomQuotesViewHolder.bind(randomQuote);

        }


        public void setRandomQuotes(List<Quote> randomQuotes){
            mRandomQuotes = randomQuotes;
        }


    }














    private class RandomQuotesViewHolder extends RecyclerView.ViewHolder{


        TextView mRandomQuoteNumber;
        CheckBox mRandomQuoteFavoriteIcon;
        Button mRandomQuoteShareIcon;
        ProgressBar mRandomQuoteProgressBar;
        TextView mRandomQuoteUnavailable;
        TextView mRandomQuoteQuote;
        TextView mRandomQuoteAuthor;
        TextView mRandomQuoteCategory;



        public RandomQuotesViewHolder(View view){
            super(view);

            mRandomQuoteNumber = (TextView) view.findViewById(R.id.random_quote_number);
            mRandomQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.random_quote_favorite_icon);
            mRandomQuoteShareIcon = (Button) view.findViewById(R.id.random_quote_share_icon);
            mRandomQuoteProgressBar = (ProgressBar) view.findViewById(R.id.random_quote_progress_bar);
            mRandomQuoteUnavailable = (TextView) view.findViewById(R.id.random_quote_unavailable);
            mRandomQuoteQuote = (TextView) view.findViewById(R.id.random_quote_quote);
            mRandomQuoteAuthor = (TextView) view.findViewById(R.id.random_quote_author);
            mRandomQuoteCategory = (TextView) view.findViewById(R.id.random_quote_category);

        }




        public void bind(Quote randomQuote){

            if (randomQuote != null){
                mRandomQuoteQuote.setText(randomQuote.getQuote());
            }



        }




    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated called");
    }



    @Override
    public void onStart(){
        super.onStart();

        Log.i(TAG, "onStart called");
    }



    @Override
    public void onResume(){
        super.onResume();

        Log.i(TAG, "onResume called");
    }




    @Override
    public void onPause(){
        super.onPause();

        Log.i(TAG, "onPause() called");
    }




    @Override
    public void onStop(){
        super.onStop();

        Log.i(TAG, "onStop() called");
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();

        Log.i(TAG, "onDestroyView() called");
    }



    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.i(TAG, "onDestroy() called");
    }


    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDeatch() caled");
    }

}