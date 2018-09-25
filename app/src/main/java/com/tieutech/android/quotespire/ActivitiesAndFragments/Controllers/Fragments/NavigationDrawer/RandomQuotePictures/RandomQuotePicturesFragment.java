package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import com.tieutech.android.quotespire.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;


//Displays Quotes Pictures by Random
@SuppressWarnings("ALL")
public class RandomQuotePicturesFragment extends Fragment {


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "RQPFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
//    private final static int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 26; //Number of quotes to load upon search
    private final static int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 28; //Number of quotes to load upon search


    //-------------- LIST variables ------------------------------------
    private static List<QuotePicture> mRandomQuotePictureQuotes = Arrays.asList(new QuotePicture[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of QuotePicture objects...
                                                                                                                                                //To contain all fields in QuotePicture (NOTE: does not contain the Bitmap, as this is too large. Just contains the ByteArray for the Bitmap))
    private static List<Bitmap> mRandomQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Bitmaps of Quote Pictures - feteched from the HandlerThread

    //-------------- VIEW variables ------------------------------------
    private GridLayoutManager mGridLayoutManager; //GridLayoutMager - to be fed into the the RecyclerView
    private RecyclerView mRandomPicturesOfQuotesRecyclerView; //RecyclerView for displying Quote Picture ViewHolders
    private RandomQuotePicturesAdapter mRandomQuotePicturesAdapter; //Adapter for creating and binding the ViewHolders
    private RandomQuotePictureViewHolder mRandomQuotePictureViewHolder; //ViewHolder for displying single Quote Picture list item

    //-------------- THREAD variable ------------------------------------
    private QuotePictureDownloaderHandlerThread<QuotePicture> mRandomQuotePicturesDownloaderHandlerThread; //HandlerThread for fetching the Quote Picture from the Picture Download URI

    //-------------- FLAG variable ------------------------------------
    private boolean shouldEnableRandomiseMenuItem = false; //Flag to indicate whether to activate the Randomise MenutItem button





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

        Handler responseHandler = new Handler(); //Handler created in this thread - therefore, is attached to this thread and RUNS ON THIS THREAD. This Handler will be called at the end of the worker HandlerThread

        mRandomQuotePicturesDownloaderHandlerThread = new QuotePictureDownloaderHandlerThread<>(responseHandler); //HandlerThread to be run on the side for fetching Quote Pictures


        //Set listener for WHEN the Quote Picture has been fetched in the worker HandlerThread
        mRandomQuotePicturesDownloaderHandlerThread.setQuoteQuotePictureDownloadListener(new QuotePictureDownloaderHandlerThread.QuotePictureDownloadListener<QuotePicture>() {

            //Override onQuotePictureDownloaded(..) method from the QuotePictureDownloaderHandlerThread
            //NOTE:
                //randomQuotePictureQuote: The QuotePicture fetched from the HandlerThread
                //quotePicture: The Bitmap feteched from the HandlerThread
            @Override
            public void onQuotePictureDownloaded(QuotePicture randomQuotePictureQuote, Bitmap quotePicture) {

                mRandomQuotePictureQuotes.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, randomQuotePictureQuote); //'Add' the fetched QuotePicture object to the QuotePicture list

                Bitmap resizedQuotePicture = Bitmap.createBitmap(quotePicture, 0, 0, quotePicture.getWidth(), quotePicture.getHeight()-30); //Resize the Quote Picture fetched from the HandlerThread so that the watermark at the bottom is removed
                mRandomQuotePictureBitmaps.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, resizedQuotePicture); //Add the Quote Picture (Bitmap) to the Bitmap List member variable


                try {
                    getActivity().invalidateOptionsMenu(); //Update the OptionsMenu
                } catch (NullPointerException npe) {
                    Log.e(TAG, "invalideOptionsMenu() method calls null object - because RandomQuotePicturesFragment has been closed");
                }


                //If the Adapter does not exist yet..
                if (mRandomQuotePicturesAdapter == null) {
                    mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
                }

                mRandomQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)
            }
        });


        mRandomQuotePicturesDownloaderHandlerThread.start(); //Start the HandlerThread - NOTE: This line is important, because if it is excluded, the HandlerThread wouldn't be able to run. An exception would be thrown
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        getActivity().setTitle("Random Quote Pictures"); //Set title for the Fragment on the toolbar

        Log.i(TAG, "onCreateView(..) called");  //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_random_quote_pictures, viewGroup, false); //Inflate View


        mRandomPicturesOfQuotesRecyclerView = view.findViewById(R.id.random_quote_pictures_recycler_view); //Assign RecyclerView member variable to the view

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2); //Create LayoutManager to link the RecyclerView to

        mRandomPicturesOfQuotesRecyclerView.setLayoutManager(mGridLayoutManager); //Link the RecyclerView to the LayoutManagher


        //If the last element of the Bitmaps list is not existent yet - i.e. not every ViewHolder has been completedly loaded
        if (mRandomQuotePictureBitmaps.get(NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD-1) == null) {
            mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Set the QuotePictures list to the RecyclerView Adapter
        }

        mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomQuotePicturesAdapter); //Set the RecyclerView to the RecyclerView Adapter


        getActivity().invalidateOptionsMenu(); //Update the OptionsMenu



        //Loop through the total number of Quotes to load
        for (int i = 0; i < NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++) {

            //If the specific index of the Quote Pictures Bitmap List does NOT contain a Bitmap object (i.e. no Quote Picture have been fetched for that index)
            if (mRandomQuotePictureBitmaps.get(i) == null) {

                Integer randomPicturesOfQuotesPosition = i; //Obtain Integer variable to feed into the AsyncTask
                new GetRandomQuotePictureAsyncTask().execute(randomPicturesOfQuotesPosition); //Begin an AsyncTask

            }
        }


        return view; //Return View
    }




    //AsyncTask for fetching the Quote Picture Download URI - i.e. the URL link to a picture
    //Generic parameters:
        //Integer (Params): What is passed to execute(..) method
    @SuppressLint("StaticFieldLeak")
    private class GetRandomQuotePictureAsyncTask extends AsyncTask<Integer, Void, QuotePicture> {

        Integer mQuotePosition[]; //Position of the Random Quote Picture in the list - obtained from execute() method call.
                                    //NOTE: Since we only passed ONE Params Generic parameter, then mQuotePosition[0] would be this parameter


        //Constructor
        public GetRandomQuotePictureAsyncTask(){
        }


        //Override doInBackground(..) method - to define what is to be done in the asynchronous thread
        //NOTE: The parameters of doInBackground(..) were taken from the execute(..) method from AsyncTask
        @Override
        protected QuotePicture doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber; //Stash position of the Quote in the member variable. NOTE: quoteNumber local variable is the variable passed into execute(..)

            QuotePicture randomQuotePicture = new GetRandomQuotePictureQuote().getRandomQuotePictureQuote(); //Fetch the Quote Picture Download URI via the networking class (GetRandomQuotePictureQuote)

            return randomQuotePicture; //Return the Quote class fetched from the networking class. NOTE: This class is passed to onPostExecute(..)
        }


        //Override onPostExecute(..) method - to define what happens AFTER QuotePicture Download URI (i.e. the URL link to a picture) has been feteched
        //NOTE: The parameter of onPostExecute(..) (i.e. QuotePicture), is what has been retur4ned from doInBackground(..)
        @Override
        protected void onPostExecute(QuotePicture randomQuotePictureQuote){

            randomQuotePictureQuote.setRandomQuotePicturePosition(mQuotePosition[0] + 1); //Set the obtained Quote's position to be the same as the position in the Random Quote Pictures list

            mRandomQuotePictureQuotes.set(mQuotePosition[0], randomQuotePictureQuote); //Add the obtained Quote to the List of Quote objects

            //Call the worker HandlerThread - by sending a Message to its MessageQueue. This message contains:
                //1: The KEY (Position of the QuotePicture) - for identifyng the Message
                //2: The VALUE (QuotePicture Picture Download URI) - to be used to fetch the Quote Picture
            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(mRandomQuotePictureQuotes.get(mQuotePosition[0]), randomQuotePictureQuote.getQuotePictureDownloadURI());
        }

    }




    //Adapter - for linking the RecyclerView to the ViewHolders
    private class RandomQuotePicturesAdapter extends RecyclerView.Adapter<RandomQuotePictureViewHolder>{

        //Constructor - to set the list to be used for the RecyclerView
        public RandomQuotePicturesAdapter(List<QuotePicture> randomQuotePictureQuotes){
            mRandomQuotePictureQuotes = randomQuotePictureQuotes;
        }


        //Get the size of the list
        @Override
        public int getItemCount(){
            return mRandomQuotePictureQuotes.size();
        }


        //Create the ViewHolder and link the list-view View to it
        @Override
        public RandomQuotePictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayotuInflater

            View view = layoutInflater.inflate(R.layout.list_item_quote_picture, viewGroup, false); //Fetch list-view View and link it to the View variable

            mRandomQuotePictureViewHolder = new RandomQuotePictureViewHolder(view); //Attach the list-view View to the ViewHolder

            return mRandomQuotePictureViewHolder; //Return the ViewHolder
        }


        //Bind the ViewHolder to the necessary variables/data
        @Override
        public void onBindViewHolder(RandomQuotePictureViewHolder randomQuotePictureViewHolder, int position){

            //If the element in the QuotePicture list contains/refers to ta QuotePicture object
            if (mRandomQuotePictureQuotes.get(position) != null){

                randomQuotePictureViewHolder.bindListItem(position, mRandomQuotePictureBitmaps.get(position)); //Bind the ViewHolder to the necessary variables/data
            }


        }

    }




    //ViewHolder - for linking the list-item to the appropriate variable/data and displaying it
    private class RandomQuotePictureViewHolder extends RecyclerView.ViewHolder{

        View mListItemView; //View of the list-item
        private ImageView mQuotePictureListItemImageView; //ImageView to display the QuotePicture Bitmap
        private Drawable mRandomQuotePictureDrawable; //Drawable to bridge between the ImageView and the Bitmap
        private SpinKitView mQuotePictureListItemProgressBar; //ProgressBar to represent the list-item being loaded when the Bitmap has not been fetched from the HandlerThread and binded to the ViewHolder yet



        //Constructor - called by Adapter's onCreateViewHolder(..)
        public RandomQuotePictureViewHolder(View listItemView){
            super(listItemView);

            mListItemView = listItemView; //Define the list-view variable

            mQuotePictureListItemImageView = (ImageView) listItemView.findViewById(R.id.quote_picture_list_item_bitmap); //ImageView to display the QuotePicture Bitmap
            mQuotePictureListItemProgressBar = (SpinKitView) listItemView.findViewById(R.id.quote_picture_list_item_progress_bar); //ProgressBar to represent the list-item being loaded when the Bitmap has not been fetched from the HandlerThread and binded to the ViewHolder yet

            mQuotePictureListItemProgressBar.setVisibility(View.VISIBLE); //Make the ProgressBar VISIBLE
            mQuotePictureListItemImageView.setVisibility(View.GONE); //Make the ImageView GONE
        }



        //Bind method - called by Adapter's onBindViewHolder(..)
        public void bindListItem(final int position, Bitmap randomQuotePictureBitmap){


            //If the Bitmap EXIST
            if (randomQuotePictureBitmap != null){
                mQuotePictureListItemProgressBar.setVisibility(View.GONE); //Make the ProgressBar VISIBLE
                mQuotePictureListItemImageView.setVisibility(View.VISIBLE);  //Make the ImageView GONE
            }


            mRandomQuotePictureDrawable = new BitmapDrawable(getResources(), randomQuotePictureBitmap); //Parse the Quote Picture Bitmap into a Drawable object
            mQuotePictureListItemImageView.setImageDrawable(mRandomQuotePictureDrawable); //Display the Drawable object on the ImageView


            //If the QuotePicture's ID EXISTS
            if (mRandomQuotePictureQuotes.get(position).getId() != null) {

                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mRandomQuotePictureQuotes.get(position).getId()) != null) {

                    mQuotePictureListItemImageView.setBackgroundColor(getResources().getColor(R.color.favorite_on)); //Set the
                    mQuotePictureListItemImageView.setPadding(20,18,20,18); //Display an "orange" border around the liar-item to show that it is 'Favorited'


                }
            }



            //If the Bitmap EXISTS
            if (randomQuotePictureBitmap != null){

                final Bitmap randomQuotePictureBitmapFinal = randomQuotePictureBitmap; //Make a variable that also points to the Bitmap object referrred to by randomQuotePictureBitmap, but make it final, so that it could be passed into the list-view's listener

                //Set listener for the ListView - OPEN up the QuotePictureDetailFragment (view of the QuotePicture picture) when the list-item is clicked on
                mListItemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Create a ByteArrayOutputStream for reading the Bitmap
                        randomQuotePictureBitmapFinal.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); //Compress the Bitmap into the ByteArrayOutputStream
                        byte[] quotePictureBitmapByteArray = byteArrayOutputStream.toByteArray(); //Create a byte array and refer to the Bitmap's byte array
                        mRandomQuotePictureQuotes.get(position).setQuotePictureBitmapByteArray(quotePictureBitmapByteArray); //Set the QuotePicture in the QuotePicture list's byte array member variable to the newly obtained Bitmap byte array

                        //OPEN up the QuotePictureDetailFragment (view of the QuotePicture picture) when the list-item is clicked on
                        //NOTE: Parameters being sent with the intent:
                            //1: ID of the QuotePicture - to identify whether it has been 'Favorited' or not, in order to display the 'Favorites' button accordingly (i.e. activated vs. not activated)
                            //2: Bitmap byte array of the QuotePicture - so as to display the Bitmap. NOTE: The Bitmap could not be sent as an extra, since its SIZE is TOO LARGE
                        Intent quotePictureActivityIntent = QuotePictureDetailActivity.newIntent(getContext(), mRandomQuotePictureQuotes.get(position).getId(), mRandomQuotePictureQuotes.get(position).getQuotePictureBitmapByteArray());

                        startActivity(quotePictureActivityIntent); //Start the Intent
                    }
                });


            }


        }

    }




    //Override onCrateOpetionsMenu(..) (fragment lifecycle callback method) for creating the OptionsMenu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        Log.i(TAG, "onCreateOptionsMenu(..) called"); //Log lifecycle callback

        menuInflater.inflate(R.menu.fragment_random_picture_quotes, menu); //Inflate the view of the OpteionsMenu

        MenuItem randomiseItem = menu.findItem(R.id.menu_item_randomise_random_picture_quotes_fragment); //Refer to the View of the "Randomise" button


        //Work out whether all the Bitmaps have been fetched
        int actualSizeOfmRandomQuotePictureBitmaps = 0;
        for (Bitmap bitmap : mRandomQuotePictureBitmaps) {
            if (bitmap == null) {
            }
            if (bitmap != null) {
                actualSizeOfmRandomQuotePictureBitmaps++;
            }
        }

        //If all the Bitmaps have been fetched
        if (actualSizeOfmRandomQuotePictureBitmaps == NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD) {
            shouldEnableRandomiseMenuItem = true; //Toggle enable-"ranbomise" flag to true
            randomiseItem.setEnabled(true); //Enable the "randomise" button
        }
        //If NOT all the Bitmaps have been loaded (yet)
        else {
            shouldEnableRandomiseMenuItem = false; //Toggle enable-"ranbomise" flag to false
            randomiseItem.setEnabled(false); //Disable the "randomise" button
        }
    }




    //Override onOptionsItemSelected(..) (fragment lifecycle callback method) for deciding what happens when the "ranomise" button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        //Log lifecycle callback
        Log.i(TAG, "onOptionsItemSelected(..) called");

        switch (menuItem.getItemId()){
            case (R.id.menu_item_randomise_random_picture_quotes_fragment):

                menuItem.setEnabled(false); //Disable the "Randomise" menu item button (the moment it is pressed)

                shouldEnableRandomiseMenuItem = false; //Set the flag to enable "Randomise" menu item button to FALSE (i.e. DISABLE "Randomise" menu item button)

                //'Reset' the instance variables storing and displaying the current QuotePicture objects
                mRandomQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //Re-assign mRandomQuotes to a new object
                mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Re-assign the RecyclerView Adapter
                mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomQuotePicturesAdapter); //Re-assign the RecyclerView to the re-assigned RecyclerView Adapter



                //Loop through the total number of Quotes to load
                for (int i = 0; i < NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++) {

                    //If the specific index of the Quote Pictures Bitmap List contain a Bitmap object (i.e. no Quote Picture have been fetched for that index)
                    if (mRandomQuotePictureBitmaps.get(i) == null) {

                        Integer randomPicturesOfQuotesPosition = i; //Obtain Integer variable to feed into the AsyncTask
                        new GetRandomQuotePictureAsyncTask().execute(randomPicturesOfQuotesPosition); //Begin an AsyncTask

                    }
                }


                //If the flag to enable the "Randomise" menu item is TRUE.
                // Called when the last Random Quote has been created in the AsyncTask
                if (shouldEnableRandomiseMenuItem == true){
                    menuItem.setEnabled(true); //Enable the "randomise" button
                }

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }
    }




    //Override onActivityCreated(..) fragment lifecycle callback method
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated called"); //Log lifecycle callback method
    }




    //Override onStart(..) fragment lifecycle callback method
    @Override
    public void onStart(){
        super.onStart();

        Log.i(TAG, "onStart called");


        //If the Adapter does not exist yet.. NOTE: When the navigation item holding this framgnent (i.e. "Random Quote Pictures" is re-opened), only onStart() and onResume() are called
        if (mRandomQuotePicturesAdapter == null) {
            mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
        }

        mRandomQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)

    }



    //Override onResume(..) fragment lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();

        Log.i(TAG, "onResume called");
    }




    //Override onPause(..) fragment lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();

        Log.i(TAG, "onPause() called");
    }




    //Override onStop(..) fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();

        Log.i(TAG, "onStop() called");
    }




    //Override onDestroyView(..) fragment lifecycle callback method
    @Override
    public void onDestroyView(){
        super.onDestroyView();

//        mRandomQuotePicturesDownloaderHandlerThread.clearQueue();

        Log.i(TAG, "onDestroyView() called");
    }




    //Override onDestroy(..) fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.i(TAG, "onDestroy() called");
    }




    //Override onDetach(..) fragment lifecycle callback method
    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDetach() caled");
    }

}
