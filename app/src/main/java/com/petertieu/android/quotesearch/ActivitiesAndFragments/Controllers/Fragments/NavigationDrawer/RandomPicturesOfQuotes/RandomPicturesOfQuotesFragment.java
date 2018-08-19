package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomPicturesOfQuotes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.List;

public class RandomPicturesOfQuotesFragment extends Fragment {

    //Log for Logcat
    private final String TAG = "RPOQuotesFragment";

    //-------------- SEARCH variables ------------------------------------
    private final int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 12; //Number of quotes to load upon search

    //-------------- LIST variables ------------------------------------
    private List<Quote> mRandomQuotePictures = Arrays.asList(new Quote[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);
    private List<Bitmap> mRandomQuotePicturesBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);

    //-------------- VIEW variables ------------------------------------
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRandomPicturesOfQuotesRecyclerView;


    //-------------- FLAG variable ------------------------------------
    public boolean shouldEnableRandomiseMenuItem = false;









//    public RandomPicturesOfQuotesFragment(){
//    }

    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach(..) called");
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setRetainInstance(true);

        setHasOptionsMenu(true);

    }





    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_random_pictures_of_quotes, viewGroup, false);


        mRandomPicturesOfQuotesRecyclerView = view.findViewById(R.id.random_pictures_of_quotes_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRandomPicturesOfQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);




        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Random Pictures of Quotes");



        for (int i=0; i<NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++){

            if (mRandomQuotePicturesBitmaps.get(i) == null){

                Integer randomPicturesOfQuotesPosition = i;

                new GetRandomQuotePictureAsyncTask().execute(randomPicturesOfQuotesPosition);

            }

        }




        return view;
    }







    private class GetRandomQuotePictureAsyncTask extends AsyncTask<Integer, Void, Quote> {


        //NOTE: mQuotePosition is an ARRAY of Integer!
        Integer mQuotePosition[];


        public GetRandomQuotePictureAsyncTask(){

        }


        @Override
        protected Quote doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber;

            Quote randomQuotePicture = new GetRandomQuotePictureQuote().getRandomQuotePictureQuote();


            return randomQuotePicture;

        }



        @Override
        protected void onPostExecute(Quote randomQuotePictures){

            //Set obtained Quote to the List of Quote objects
            mRandomQuotePictures.set(mQuotePosition[0], randomQuotePictures);

            //Set the obtained Quote's position to be the same as the position in the Random Quote Pictures list
            mRandomQuotePictures.get(mQuotePosition[0]).setRandomQuotePicturePosition(mQuotePosition[0] + 1);


            //Decide whether to enable or disable the "Randomise" menu item button
            //If the last Random Quote has been creted
            try{

                if (mQuotePosition[0] == NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD-1){
                    shouldEnableRandomiseMenuItem = true;
                    getActivity().invalidateOptionsMenu();
                }
                //If the last Random Quote has NOT been created yet
                else{
                    shouldEnableRandomiseMenuItem = false;
                    getActivity().invalidateOptionsMenu();
                }

            }
            catch (NullPointerException npe){
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because RandomQuotesFragment has been closed");
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
