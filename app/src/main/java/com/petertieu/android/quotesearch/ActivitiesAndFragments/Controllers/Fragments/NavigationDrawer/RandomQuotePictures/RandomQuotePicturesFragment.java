package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.List;

public class RandomQuotePicturesFragment extends Fragment {

    //Log for Logcat
    private final String TAG = "RQPFragment";

    //-------------- SEARCH variables ------------------------------------
    private final int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 12; //Number of quotes to load upon search

    //-------------- LIST variables ------------------------------------
    private List<Quote> mRandomQuotePictureQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);
    private List<Bitmap> mRandomQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);
    private List<String> mRandomQuotePictureDownloadURIs = Arrays.asList(new String[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);

    //-------------- VIEW variables ------------------------------------
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRandomPicturesOfQuotesRecyclerView;
    private RandomQuotePictureViewHolder mRandomQuotePictureViewHolder;


    //-------------- FLAG variable ------------------------------------
    public boolean shouldEnableRandomiseMenuItem = false;


    private QuotePictureDownloaderHandlerThread<RandomQuotePictureViewHolder> mRandomQuotePicturesDownloaderHandlerThread;


    private RandomQuotePicturesAdapter mRandomeQuotePicturesAdapter;




//    public RandomQuotePicturesFragment(){
////    }

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


        Handler responseHandler = new Handler();

        mRandomQuotePicturesDownloaderHandlerThread = new QuotePictureDownloaderHandlerThread<>(responseHandler);

        mRandomQuotePicturesDownloaderHandlerThread.setQuoteQuotePictureDownloadListener(new QuotePictureDownloaderHandlerThread.QuotePictureDownloadListener<RandomQuotePictureViewHolder>() {

            @Override
            public void onQuotePictureDownloaded(RandomQuotePictureViewHolder randomQuotePictureViewHolder, Bitmap quotePicture) {

                Drawable quotePictureDrawable = new BitmapDrawable(getResources(), quotePicture);

                randomQuotePictureViewHolder.bindRandomQuotePictureDrawable(quotePictureDrawable);

//                updateUI();
            }
        });

        mRandomQuotePicturesDownloaderHandlerThread.start();



    }


    public void updateUI(){
//        mRandomeQuotePicturesAdapter.setRandomQuotes(mRandomQuotePictureBitmaps);
//        mRandomeQuotePicturesAdapter.notifyDataSetChanged();
    }



    GridLayoutManager mGridLayoutManager;


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_random_pictures_of_quotes, viewGroup, false);


        mRandomPicturesOfQuotesRecyclerView = view.findViewById(R.id.random_pictures_of_quotes_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRandomPicturesOfQuotesRecyclerView.setLayoutManager(mGridLayoutManager);




        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Random Pictures of Quotes");



        for (int i=0; i<NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++){

            if (mRandomQuotePictureBitmaps.get(i) == null){

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
        protected void onPostExecute(Quote randomQuotePicture){

            //Set obtained Quote to the List of Quote objects
            mRandomQuotePictureQuotes.set(mQuotePosition[0], randomQuotePicture);

            //Set the obtained Quote's position to be the same as the position in the Random Quote Pictures list
            mRandomQuotePictureQuotes.get(mQuotePosition[0]).setRandomQuotePicturePosition(mQuotePosition[0] + 1);


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



            Log.i(TAG, "Random Quote Picture - Position: " + mQuotePosition[0]);
            Log.i(TAG, "Random Quote Picture - ID: " + mRandomQuotePictureQuotes.get(mQuotePosition[0]).getId());
            Log.i(TAG, "Random Quote Picture - Picture Download URI: " + mRandomQuotePictureQuotes.get(mQuotePosition[0]).getPictureDownloadURI());








            mRandomQuotePictureQuotes.set(mQuotePosition[0], randomQuotePicture);


//            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(mRandomQuotePictureQuotes.get(mQuotePosition[0]), randomQuotePicture.getPictureDownloadURI());
//            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(randomQuotePicture, randomQuotePicture.getPictureDownloadURI());
//
//            if (randomQuotePicture != null){
//                Log.i(TAG, "randomQuotePicture exists");
//            }
//            else{
//                Log.i(TAG, "randomQuotePicture DOES NOT exist");
//            }
//
//
//            if (randomQuotePicture.getPictureDownloadURI() != null){
//                Log.i(TAG, "randomQuotePicture.getPictureDownloadURI() exists");
//            }
//            else{
//                Log.i(TAG, "randomQuotePicture.getPictureDownloadURI() DOES NOT exist");
//            }




            mRandomQuotePictureDownloadURIs.set(mQuotePosition[0], randomQuotePicture.getPictureDownloadURI());


            Log.i(TAG, "mRandomQuotePictureDownloadURI: " + mRandomQuotePictureDownloadURIs);

            Log.i(TAG, "mRandomQuotePictureDownloadURIs.size(): " + mRandomQuotePictureDownloadURIs.size());





            if (mRandomQuotePictureDownloadURIs.get(NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD-1) != null){
                Log.i(TAG, "HERRRRRRRRRRRRRRRE");
                mRandomeQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureDownloadURIs);
                mRandomeQuotePicturesAdapter.setRandomQuotePictures(mRandomQuotePictureDownloadURIs);
                mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomeQuotePicturesAdapter);
                mRandomeQuotePicturesAdapter.notifyDataSetChanged();


            }










        }

    }





    private class RandomQuotePicturesAdapter extends RecyclerView.Adapter<RandomQuotePictureViewHolder>{


        public RandomQuotePicturesAdapter(List<String> randomQuotePictureDownaloadURIs){
            mRandomQuotePictureDownloadURIs = randomQuotePictureDownaloadURIs;
        }


        @Override
        public int getItemCount(){
            return mRandomQuotePictureDownloadURIs.size();
        }




        @Override
        public RandomQuotePictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_quote_picture, viewGroup, false);

            mRandomQuotePictureViewHolder = new RandomQuotePictureViewHolder(view);

            return mRandomQuotePictureViewHolder;
        }


        @Override
        public void onBindViewHolder(RandomQuotePictureViewHolder randomQuotePictureViewHolder, int position){
            String randomQuotePictureDownloadURI = mRandomQuotePictureDownloadURIs.get(position);

//            int randomQuotePosition = position + 1;
//            mRandomQuotesViewHolder.mRandomQuotePositionText.setText("Random Quote #" + randomQuotePosition);


            randomQuotePictureViewHolder.bindListItem(randomQuotePictureDownloadURI);

            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(randomQuotePictureViewHolder, randomQuotePictureDownloadURI);




        }


        public void setRandomQuotePictures(List<String> randomQuotePictures){
            mRandomQuotePictureDownloadURIs = randomQuotePictures;
        }


    }







    private class RandomQuotePictureViewHolder extends RecyclerView.ViewHolder{

        private String mRandomQuotePictureDownloadURI;
        ImageView mQuotePictureListItem;


        public RandomQuotePictureViewHolder(View listItemView){
            super(listItemView);

            mQuotePictureListItem = listItemView.findViewById(R.id.quote_picture_list_item);

        }


        public void bindListItem(String randomQuotePictureDownloadURI){
            mRandomQuotePictureDownloadURI = randomQuotePictureDownloadURI;
        }


        public void bindRandomQuotePictureDrawable(Drawable randomQuotePictureDrawable){
            mQuotePictureListItem.setImageDrawable(randomQuotePictureDrawable);
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

        mRandomQuotePicturesDownloaderHandlerThread.clearQueue();

        Log.i(TAG, "onDestroyView() called");
    }



    @Override
    public void onDestroy(){
        super.onDestroy();


        mRandomQuotePicturesDownloaderHandlerThread.quit();

        Log.i(TAG, "onDestroy() called");
    }


    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDeatch() caled");
    }






}
