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


//Fragment that fetches and displays Quotes Pictures by Random
public class RandomQuotePicturesFragment extends Fragment {


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "RQPFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private final int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 26; //Number of quotes to load upon search

    //-------------- LIST variables ------------------------------------
    private List<Quote> mRandomQuotePictureQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Quotes
    private List<Bitmap> mRandomQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Bitmaps of Quote Pictures - feteched from the HandlerThread

    //-------------- VIEW variables ------------------------------------
    private GridLayoutManager mGridLayoutManager; //GridLayoutMager - to be fed into the the RecyclerView
    private RecyclerView mRandomPicturesOfQuotesRecyclerView; //RecyclerView for displying Quote Picture ViewHolders
    private RandomQuotePicturesAdapter mRandomeQuotePicturesAdapter; //Adapter for creating and binding the ViewHolders
    private RandomQuotePictureViewHolder mRandomQuotePictureViewHolder; //ViewHolder for displying single Quote Picture list item

    //-------------- THREAD variable ------------------------------------
    private QuotePictureDownloaderHandlerThread<Quote> mRandomQuotePicturesDownloaderHandlerThread; //HandlerThread for fetching the Quote Picture from the Picture Download URI

    //-------------- FLAG variable ------------------------------------
    public boolean shouldEnableRandomiseMenuItem = false; //Flag to indicate whether to activate the Randomise MenutItem button





    //================== DEFINE METHODS ===================================================================================

    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach(..) called"); //Log to Logcat
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);

        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setRetainInstance(true); //Retain data even when the view has changed (i.e. screen rotation)

        setHasOptionsMenu(true); //Declare that there is an opeionts menu


        //-------------- Manage HandlerThreads ------------------------------------------------------------------------
        //Used for fetching Quote Pictures (from Picture Download URIs)

        Handler responseHandler = new Handler(); //Handler created in this thread - therefore, is attached to this thread and runs here. This Handler will be called at the end of the worker HandlerThread

        mRandomQuotePicturesDownloaderHandlerThread = new QuotePictureDownloaderHandlerThread<>(responseHandler); //HandlerThread to be run on the side for fetching Quote Pictures


        //Set listener for when the Quote Picture has been fetched in the worker HandlerThread
        mRandomQuotePicturesDownloaderHandlerThread.setQuoteQuotePictureDownloadListener(new QuotePictureDownloaderHandlerThread.QuotePictureDownloadListener<Quote>() {

            @Override
            public void onQuotePictureDownloaded(Quote randomQuotePictureQuote, Bitmap quotePicture) {

                mRandomQuotePictureBitmaps.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, quotePicture); //Add the Quote Picture (Bitmap) to the Bitmap List member variable

                //If the Adapter does not exist yet..
                if (mRandomeQuotePicturesAdapter == null) {
                    mRandomeQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureBitmaps); //Create Adapter and link it to the list of Quote Picture Bitmaps
                }

                mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomeQuotePicturesAdapter); //Link RecyclerView and Adapter
                mRandomeQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)
            }
        });


        mRandomQuotePicturesDownloaderHandlerThread.start(); //Start the HandlerThread
    }





    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called");  //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_random_pictures_of_quotes, viewGroup, false); //Inflate


        mRandomPicturesOfQuotesRecyclerView = view.findViewById(R.id.random_pictures_of_quotes_recycler_view);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRandomPicturesOfQuotesRecyclerView.setLayoutManager(mGridLayoutManager);


        if (mRandomeQuotePicturesAdapter != null){
            mRandomeQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureBitmaps);
            mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomeQuotePicturesAdapter);
        }

        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Random Quote Pictures");


        //Loop through the total number of Quotes to load
        for (int i=0; i<NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++){

            //If the specific index of the Quote Pictures Bitmap List contain a Bitmap object (i.e. no Quote Picture have been fetched for that index)
            if (mRandomQuotePictureBitmaps.get(i) == null){

                Integer randomPicturesOfQuotesPosition = i; //Obtain Integer variable to feed into the AsyncTask
                new GetRandomQuotePictureAsyncTask().execute(randomPicturesOfQuotesPosition); //Begin an AsyncTask

            }
        }

        return view;
    }




    //AsyncTask for fetching the Quote Picture Download URI - i.e. the URL link to a picture
    //Generic parameters:
        //Integer (Params): What is passed to execute(..) method
    private class GetRandomQuotePictureAsyncTask extends AsyncTask<Integer, Void, Quote> {

        Integer mQuotePosition[]; //Position of the Random Quote Picture in the list - obtained from execute() method call.
                                    //NOTE: Since we only passed ONE Params Generic parameter, then mQuotePosition[0] would be this parameter


        //Constructor
        public GetRandomQuotePictureAsyncTask(){
        }


        //Override doInBackground(..) method - to define what is to be done in the asynchronous thread
        @Override
        protected Quote doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber; //Stash position of the Quote in the member variable. NOTE: quoteNumber local variable is the variable passed into execute(..)

            Quote randomQuotePicture = new GetRandomQuotePictureQuote().getRandomQuotePictureQuote(); //Fetch the Quote Picture Download URI via the networking class (GetRandomQuotePictureQuote)

            return randomQuotePicture; //Return the Quote class fetched from the networking class. NOTE: This class is passed to onPostExecute(..)
        }


        @Override
        protected void onPostExecute(Quote randomQuotePictureQuote){

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


            randomQuotePictureQuote.setRandomQuotePicturePosition(mQuotePosition[0] + 1); //Set the obtained Quote's position to be the same as the position in the Random Quote Pictures list

            mRandomQuotePictureQuotes.set(mQuotePosition[0], randomQuotePictureQuote); //Add the obtained Quote to the List of Quote objects

            //Call the worker HandlerThread - by sending a Message to its MessageQueue. This message contains:
                //1: The KEY (Quote) - for identifyng the Message
                //2: The VALUE (Quote Picture Download URI) - to be used to fetch the Quote Picture
            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(mRandomQuotePictureQuotes.get(mQuotePosition[0]), randomQuotePictureQuote.getPictureDownloadURI());

        }

    }




    //Adapter - for linking the RecyclerView to the ViewHolders
    private class RandomQuotePicturesAdapter extends RecyclerView.Adapter<RandomQuotePictureViewHolder>{


        public RandomQuotePicturesAdapter(List<Bitmap> randomQuotePictureBitmaps){
            mRandomQuotePictureBitmaps = randomQuotePictureBitmaps;
        }


        @Override
        public int getItemCount(){
            return mRandomQuotePictureBitmaps.size();
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

            Bitmap randomQuotePictureBitmap = mRandomQuotePictureBitmaps.get(position);

            randomQuotePictureViewHolder.bindListItem(randomQuotePictureBitmap);

        }

    }






    private class RandomQuotePictureViewHolder extends RecyclerView.ViewHolder{

        private Drawable mRandomQuotePictureBitmap;
        ImageView mQuotePictureListItem;


        public RandomQuotePictureViewHolder(View listItemView){
            super(listItemView);

            mQuotePictureListItem = listItemView.findViewById(R.id.quote_picture_list_item);

        }


        public void bindListItem(Bitmap randomQuotePictureBitmap){
//            mRandomQuotePictureBitmap = randomQuotePictureBitmap;

            mRandomQuotePictureBitmap = new BitmapDrawable(getResources(), randomQuotePictureBitmap);

            mQuotePictureListItem.setImageDrawable(mRandomQuotePictureBitmap);

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

//        mRandomQuotePicturesDownloaderHandlerThread.clearQueue();

        Log.i(TAG, "onDestroyView() called");
    }



    @Override
    public void onDestroy(){
        super.onDestroy();


//        mRandomQuotePicturesDownloaderHandlerThread.quit();

        Log.i(TAG, "onDestroy() called");
    }


    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDetach() caled");
    }






}
