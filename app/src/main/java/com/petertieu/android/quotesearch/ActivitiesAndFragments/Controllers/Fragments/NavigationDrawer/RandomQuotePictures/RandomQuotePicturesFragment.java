package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

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
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;


//Fragment that fetches and displays Quotes Pictures by Random
public class RandomQuotePicturesFragment extends Fragment {


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "RQPFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
//    private final static int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 26; //Number of quotes to load upon search
    private final static int NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD = 16; //Number of quotes to load upon search


    //-------------- LIST variables ------------------------------------
    private static List<QuotePicture> mRandomQuotePictureQuotes = Arrays.asList(new QuotePicture[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Quotes
//    private static List<QuotePicture> mFullyFetchedRandomQuotePictures = Arrays.asList(new QuotePicture[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Random Quote Pictures that have been fetched via the AsyncTask AND HandlerThread
    private static List<String> mRandomQuotePictureIDs = Arrays.asList(new String[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]);
    private static List<Bitmap> mRandomQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD]); //List of Bitmaps of Quote Pictures - feteched from the HandlerThread

    //-------------- VIEW variables ------------------------------------
    private GridLayoutManager mGridLayoutManager; //GridLayoutMager - to be fed into the the RecyclerView
    private RecyclerView mRandomPicturesOfQuotesRecyclerView; //RecyclerView for displying Quote Picture ViewHolders
    private RandomQuotePicturesAdapter mRandomQuotePicturesAdapter; //Adapter for creating and binding the ViewHolders
    private RandomQuotePictureViewHolder mRandomQuotePictureViewHolder; //ViewHolder for displying single Quote Picture list item

    //-------------- THREAD variable ------------------------------------
    private QuotePictureDownloaderHandlerThread<QuotePicture> mRandomQuotePicturesDownloaderHandlerThread; //HandlerThread for fetching the Quote Picture from the Picture Download URI

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



//        for (int i=0; i<mFullyFetchedRandomQuotePictures.size(); i++){
//            mFullyFetchedRandomQuotePictures.set(i, new QuotePicture());
//        }



        //-------------- Manage HandlerThreads ------------------------------------------------------------------------
        //Used for fetching Quote Pictures (from Picture Download URIs)

        Handler responseHandler = new Handler(); //Handler created in this thread - therefore, is attached to this thread and runs here. This Handler will be called at the end of the worker HandlerThread

        mRandomQuotePicturesDownloaderHandlerThread = new QuotePictureDownloaderHandlerThread<>(responseHandler); //HandlerThread to be run on the side for fetching Quote Pictures


        //Set listener for when the Quote Picture has been fetched in the worker HandlerThread
        mRandomQuotePicturesDownloaderHandlerThread.setQuoteQuotePictureDownloadListener(new QuotePictureDownloaderHandlerThread.QuotePictureDownloadListener<QuotePicture>() {

            @Override
            public void onQuotePictureDownloaded(QuotePicture randomQuotePictureQuote, Bitmap quotePicture) {


                mRandomQuotePictureQuotes.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, randomQuotePictureQuote);


//                mFullyFetchedRandomQuotePictures.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, randomQuotePictureQuote);

                mRandomQuotePictureIDs.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, randomQuotePictureQuote.getId());


                //Resize the Quote Picture fetched from the HandlerThread so that the watermark at the bottom is removed
                Bitmap resizedQuotePicture = Bitmap.createBitmap(quotePicture, 0, 0, quotePicture.getWidth(), quotePicture.getHeight()-30);


                mRandomQuotePictureBitmaps.set(randomQuotePictureQuote.getRandomQuotePicturePosition() - 1, resizedQuotePicture); //Add the Quote Picture (Bitmap) to the Bitmap List member variable



                //If the Adapter does not exist yet..
                if (mRandomQuotePicturesAdapter == null) {
                    mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
                }

//                mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomQuotePicturesAdapter); //Link RecyclerView and Adapter
                mRandomQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)


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


        if (mRandomQuotePictureBitmaps.get(NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD-1) == null) {
            mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes);
        }

        mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomQuotePicturesAdapter);


        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Random Quote Pictures");


        //Loop through the total number of Quotes to load
        for (int i = 0; i < NUMBER_OF_RANDOM_PICTURES_OF_QUOTES_TO_LOAD; i++) {

            //If the specific index of the Quote Pictures Bitmap List contain a Bitmap object (i.e. no Quote Picture have been fetched for that index)
            if (mRandomQuotePictureBitmaps.get(i) == null) {

                Integer randomPicturesOfQuotesPosition = i; //Obtain Integer variable to feed into the AsyncTask
                new GetRandomQuotePictureAsyncTask().execute(randomPicturesOfQuotesPosition); //Begin an AsyncTask

            }
        }




        return view;
    }




    //AsyncTask for fetching the Quote Picture Download URI - i.e. the URL link to a picture
    //Generic parameters:
        //Integer (Params): What is passed to execute(..) method
    private class GetRandomQuotePictureAsyncTask extends AsyncTask<Integer, Void, QuotePicture> {

        Integer mQuotePosition[]; //Position of the Random Quote Picture in the list - obtained from execute() method call.
                                    //NOTE: Since we only passed ONE Params Generic parameter, then mQuotePosition[0] would be this parameter


        //Constructor
        public GetRandomQuotePictureAsyncTask(){
        }


        //Override doInBackground(..) method - to define what is to be done in the asynchronous thread
        @Override
        protected QuotePicture doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber; //Stash position of the Quote in the member variable. NOTE: quoteNumber local variable is the variable passed into execute(..)

            QuotePicture randomQuotePicture = new GetRandomQuotePictureQuote().getRandomQuotePictureQuote(); //Fetch the Quote Picture Download URI via the networking class (GetRandomQuotePictureQuote)

            return randomQuotePicture; //Return the Quote class fetched from the networking class. NOTE: This class is passed to onPostExecute(..)
        }


        @Override
        protected void onPostExecute(QuotePicture randomQuotePictureQuote){

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
            mRandomQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(mRandomQuotePictureQuotes.get(mQuotePosition[0]), randomQuotePictureQuote.getQuotePictureDownloadURI());


        }

    }




    //Adapter - for linking the RecyclerView to the ViewHolders
    private class RandomQuotePicturesAdapter extends RecyclerView.Adapter<RandomQuotePictureViewHolder>{


        public RandomQuotePicturesAdapter(List<QuotePicture> randomQuotePictureQuotes){
            mRandomQuotePictureQuotes = randomQuotePictureQuotes;
        }


        @Override
        public int getItemCount(){
            return mRandomQuotePictureQuotes.size();
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

            //            String randomQuotePictureID = "hello";

//            QuotePicture randomQuotePicture = mFullyFetchedRandomQuotePictures.get(position);

            String randomQuotePictureID = mRandomQuotePictureIDs.get(position);


            Bitmap randomQuotePictureBitmap = mRandomQuotePictureBitmaps.get(position);

//            randomQuotePictureViewHolder.bindListItem(randomQuotePicture, randomQuotePictureID, randomQuotePictureBitmap);
            randomQuotePictureViewHolder.bindListItem(randomQuotePictureID, randomQuotePictureBitmap);
        }

    }





//    private String mRandomQuotePictureID;
//    private ImageView mQuotePictureListItemImageView;
//    private Drawable mRandomQuotePictureDrawable;

    private class RandomQuotePictureViewHolder extends RecyclerView.ViewHolder{


        private QuotePicture mRandomQuotePicture;

        private String mRandomQuotePictureID;
        private ImageView mQuotePictureListItemImageView;
        private Drawable mRandomQuotePictureDrawable;

//        private ProgressBar mQuotePictureListItemProgressBar;
        private SpinKitView mQuotePictureListItemProgressBar;





//
//        public String getRandomQuotePictureID() {
//            return mRandomQuotePictureID;
//        }
//
//        public Drawable getRandomQuotePictureDrawable() {
//            return mRandomQuotePictureDrawable;
//        }
//
//        public ImageView getQuotePictureListItemImageView() {
//            return mQuotePictureListItemImageView;
//        }






        View mListItemView;


        public RandomQuotePictureViewHolder(View listItemView){
            super(listItemView);


            mQuotePictureListItemImageView = (ImageView) listItemView.findViewById(R.id.quote_picture_list_item_bitmap);
            mQuotePictureListItemProgressBar = (SpinKitView) listItemView.findViewById(R.id.quote_picture_list_item_progress_bar);



            mQuotePictureListItemImageView.setVisibility(View.GONE);
            mQuotePictureListItemProgressBar.setVisibility(View.VISIBLE);





            mListItemView = listItemView;

        }





        public void bindListItem(String randomQuotePictureID, Bitmap randomQuotePictureBitmap){
//            mRandomQuotePictureDrawable = randomQuotePictureBitmap;


//            mRandomQuotePicture = randomQuotePicture;

            mRandomQuotePictureID = randomQuotePictureID;


            mRandomQuotePictureDrawable = new BitmapDrawable(getResources(), randomQuotePictureBitmap);
            mQuotePictureListItemImageView.setImageDrawable(mRandomQuotePictureDrawable);





//            if (mRandomQuotePictureID != null){
//
//                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
//                if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mRandomQuotePictureID) != null) {
//
//
//                    Bitmap randomQuotePictureBitmapppp = Bitmap.createBitmap(randomQuotePictureBitmap, 0, 0, randomQuotePictureBitmap.getWidth()-100, randomQuotePictureBitmap.getHeight()-100);
//
//                    Drawable drawable = new BitmapDrawable(getResources(), randomQuotePictureBitmapppp);
//
//                    mQuotePictureListItemImageView.setImageDrawable(drawable);
////            mRandomQuote.setFavorite(true);
//
//
//
//                }
//
//            }













            if (mRandomQuotePictureDrawable == null){
                Log.i(TAG, "NULLLL");
            }
            else{
                Log.i(TAG, "EXISTSSS");
            }


            if (randomQuotePictureBitmap != null){
                mQuotePictureListItemProgressBar.setVisibility(View.GONE);
                mQuotePictureListItemImageView.setVisibility(View.VISIBLE);
            }






            if (mRandomQuotePictureID != null) {

                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mRandomQuotePictureID) != null) {

                    mQuotePictureListItemImageView.setVisibility(View.INVISIBLE);
//            mRandomQuote.setFavorite(true);

                }
            }









            final Bitmap randomQuotePictureBitmapFinal = randomQuotePictureBitmap;


            if (randomQuotePictureBitmapFinal != null){

                mListItemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        randomQuotePictureBitmapFinal.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();


//                        Intent quotePictureActivityIntent = QuotePictureDetailActivity.newIntent(getContext(), mRandomQuotePicture, mRandomQuotePictureID, byteArray);
                        Intent quotePictureActivityIntent = QuotePictureDetailActivity.newIntent(getContext(), mRandomQuotePictureID, byteArray);


                        startActivity(quotePictureActivityIntent);
                    }
                });


            }





        }



    }







    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        super.onCreateOptionsMenu(menu, menuInflater);

        //Log lifecycle callback
        Log.i(TAG, "onCreateOptionsMenu(..) called");

        menuInflater.inflate(R.menu.fragment_random_picture_quotes, menu);

        MenuItem randomiseItem = menu.findItem(R.id.menu_item_randomise_random_picture_quotes_fragment);



        //If the flag to enable the "Randomise" menu item is TRUE
        if (shouldEnableRandomiseMenuItem == true){

            randomiseItem.setEnabled(true);

        }
        //If the flag to enable the "Randomise" menu item is FALSE
        else{
            randomiseItem.setEnabled(false);
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        //Log lifecycle callback
        Log.i(TAG, "onOptionsItemSelected(..) called");

        switch (menuItem.getItemId()){
            case (R.id.menu_item_randomise_random_picture_quotes_fragment):

                //Disable the "Randomise" menu item button (the moment it is pressed)
                menuItem.setEnabled(false);


                //Set the flag to enable "Randomise" menu item button to FALSE (i.e. DISABLE "Randomise" menu item button)
                shouldEnableRandomiseMenuItem = false;

                //'Reset' the instance variables
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
                    menuItem.setEnabled(true);
                }



                return true;


            default:
                return super.onOptionsItemSelected(menuItem);

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


        //If the Adapter does not exist yet..
        if (mRandomQuotePicturesAdapter == null) {
            mRandomQuotePicturesAdapter = new RandomQuotePicturesAdapter(mRandomQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
        }

//                mRandomPicturesOfQuotesRecyclerView.setAdapter(mRandomQuotePicturesAdapter); //Link RecyclerView and Adapter
        mRandomQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)


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
