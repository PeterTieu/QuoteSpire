package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.R;

import java.util.Arrays;
import java.util.List;

//Fragment for displaying list of Random Quotes
public class RandomQuotesFragment extends Fragment {

    //================= Declare INSTANCE VARIABLES ==============================================================

    private static final int NUMBER_OF_RANDOM_QUOTES_TO_LOAD = 12; //Number of random Quotes to display

    private final String TAG = "RandomQuotesFragment"; //Log for Logcat

    //RecyclerView variables
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRandomQuotesRecyclerView;
    private RandomQuotesAdaper mRandomQuotesAdaper;
    private RandomQuotesViewHolder mRandomQuotesViewHolder;

    private static List<Quote> mRandomQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_QUOTES_TO_LOAD]); //Declare and INITIALISE the List of Quote objects to size 12

    boolean shouldEnableRandomiseMenuItem = false; //Flag to decide whether the "Randomise" menut item button should be enabled or disabled



    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.i(TAG, "onAttached(..) called"); //Log to Logcat
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        Log.i(TAG, "onCreate(..) caled"); //Log to Logcat

        setHasOptionsMenu(true); //Declaqre that this fragment has an options menu
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_random_quotes, viewGroup, false); //Inflate the layout view for RandomQuotesFragment

        //Configure view for RecyclerView
            //---- Set up RecyclerView ------------
        mRandomQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.random_quotes_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRandomQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);
            //---- Set RecyclerView Adapter and link it to the RecyclerView --------
        mRandomQuotesAdaper = new RandomQuotesAdaper(mRandomQuotes);
        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes);
        mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper);

        getActivity().invalidateOptionsMenu(); //Update options menu

        getActivity().setTitle("Random Quotes"); //Set title for Fragment


        //Run the following operations for as many times as there are random Quotes
        for (int i = 0; i < NUMBER_OF_RANDOM_QUOTES_TO_LOAD; i++) {

            //If EACH of the Quote objects in the mRandomQuotes ArrayList are EMPTY
            if (mRandomQuotes.get(i) == null){
                Integer randomQuotePosition = i; //Position of random Quote
                new GetRandomQuoteAsyncTask().execute(randomQuotePosition); //Obtain the random Quote
            }
        }

        return view; //Return the view
    }




    //AsyncTask - Fetches the random Quote via JSON networking, then stashes them to the randomQuote static instance variable
    //Generic return type #1 (Integer): The type passed from execute(..) to the parameter in doInBackground(..) (i.e. quoteNumber)
    private class GetRandomQuoteAsyncTask extends AsyncTask<Integer, Void, Quote>{

        Integer mQuotePosition[]; //Position of the random Quote (to be displayed in the list). NOTE: mQuotePosition is an ARRAY of Integer!

        //Contructor
        public GetRandomQuoteAsyncTask(){
        }


        //Perform main AsyncTask operation
        @Override
        protected Quote doInBackground(Integer... quoteNumber){
            mQuotePosition = quoteNumber; //Pass the Generic return type #1 (i.e. quoteNumber) to mQuotePosition
            Quote randomQuote = new GetRandomQuote().getRandomQuote(); //Obtain random Quote object via networking
            return randomQuote;
        }


        //When main AsyncTask operation is completed
        @Override
        protected void onPostExecute(Quote randomQuote){

            mRandomQuotes.set(mQuotePosition[0], randomQuote); //Add the random Quote AND its position to list of Quote objects (mRandomQuotes)
            mRandomQuotes.get(mQuotePosition[0]).setRandomQuotePosition(mQuotePosition[0] + 1); //Set the position of the random Quote


            //Try risky task - invalidateOptionsMenu() may throw NullPointerException IF the fragment is closed before it is run (i.e. because the AsyncTask has not finished yet)
            try{
                getActivity().invalidateOptionsMenu(); //Update the options menu (in case the last random Quote in the list has been obtained, meaning that the "Randomise" button should be re-enabled
            }
            catch (NullPointerException npe){
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because RandomQuotesFragment has been closed");
            }


            updateUI(); //Update the RecyclerView Adapter and display the new list
        }
    }




    //Helper method - Update the RecyclerView Adapter and display the new list
    public void updateUI(){
        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes); //Set the RecyclerView Adapter with the newly updated List of Quote objects (mRandomQuotes)
        mRandomQuotesAdaper.notifyDataSetChanged(); //Update the RecyclerView Adapter
    }




    //RecyclerView Adapter
    private class RandomQuotesAdaper extends RecyclerView.Adapter<RandomQuotesViewHolder>{

        //Constructor
        public RandomQuotesAdaper(List<Quote> randomQuotes){
            mRandomQuotes = randomQuotes; //Stash the randomQuotes to the mRandomQuotes static instance variable of RandomQuotesFragment
            Log.i(TAG, "ADAPTER - mRandomQuotes: " + mRandomQuotes); //Log to Logcat
        }


        //Count of the size of the list
        @Override
        public int getItemCount(){
            return mRandomQuotes.size();
        }


        //Create the RecyclerView ViewHolder
        @Override
        public RandomQuotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater
            View view = layoutInflater.inflate(R.layout.list_item_random_quote, viewGroup, false); //Inflate the list item layout
            mRandomQuotesViewHolder = new RandomQuotesViewHolder(view); //Set ViewHolder to the list item layout
            return mRandomQuotesViewHolder; //Return the ViewHolder
        }


        //Bind data to ViewHolder
        @Override
        public void onBindViewHolder(RandomQuotesViewHolder randomQuotesViewHolder, int position){
            Quote randomQuote = mRandomQuotes.get(position); //Get random Quote from the List of random Quote objects (mRandomQuotes)
            randomQuotesViewHolder.bind(randomQuote); //Bind ViewHolder to the randomQuote
        }


        //Constructor #2
        public void setRandomQuotes(List<Quote> randomQuotes){
            mRandomQuotes = randomQuotes;
        }
    }




    //RecyclerView ViewHolder
    private class RandomQuotesViewHolder extends RecyclerView.ViewHolder{

        //=============== Instance Variables ================================
        Quote mRandomQuote; //Quote

        //Views
        LinearLayout mRandomQuoteBubbleLayout;  //Bubble layout
        TextView mRandomQuotePositionText;      //Quote Position
        CheckBox mRandomQuoteFavoriteIcon;      //Favorite Icon
        Button mRandomQuoteShareIcon;           //Share Icon
        ProgressBar mRandomQuoteProgressBar;    //Progress Bar - while random Quote list item is being fetched
        TextView mRandomQuoteUnavailable;       //Random Quote unavailable
        TextView mRandomQuoteQuote;             //Random Quote Quote
        TextView mRandomQuoteAuthor;            //Random Quote Author
        TextView mRandomQuoteCategories;        //Random Quote Categories



        //=============== Methods ==============================================

        //Constructor - called in RandomQuotesAdaper.onCreateViewHolder(..)
        public RandomQuotesViewHolder(View view){
            super(view);

            //Assign View reference variables to the resource varibles
            mRandomQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.random_quote_bubble_layout); //
            mRandomQuotePositionText = (TextView) view.findViewById(R.id.random_quote_position);
            mRandomQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.random_quote_favorite_icon);
            mRandomQuoteShareIcon = (Button) view.findViewById(R.id.random_quote_share_icon);
            mRandomQuoteProgressBar = (ProgressBar) view.findViewById(R.id.random_quote_progress_bar);
            mRandomQuoteQuote = (TextView) view.findViewById(R.id.random_quote_quote);
            mRandomQuoteAuthor = (TextView) view.findViewById(R.id.random_quote_author);
            mRandomQuoteCategories = (TextView) view.findViewById(R.id.random_quote_categories);
            mRandomQuoteUnavailable = (TextView) view.findViewById(R.id.random_quote_unavailable);

            //Set drawable for bubble layout
            mRandomQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));

            //Configure view visibilities - only the Progress Bar is visible
            mRandomQuoteProgressBar.setVisibility(View.VISIBLE);
            mRandomQuotePositionText.setVisibility(View.GONE);
            mRandomQuoteFavoriteIcon.setVisibility(View.GONE);
            mRandomQuoteShareIcon.setVisibility(View.GONE);
            mRandomQuoteUnavailable.setVisibility(View.GONE);
            mRandomQuoteQuote.setVisibility(View.GONE);
            mRandomQuoteAuthor.setVisibility(View.GONE);
            mRandomQuoteCategories.setVisibility(View.GONE);
        }



        //Bind data to the ViewHolder
        public void bind(Quote randomQuote){

            //If random Quote EXISTS
            if (randomQuote != null){

                mRandomQuote = randomQuote; //Stash randomQuote to mRandomeQuote reference variable

                //Configure view visibilities - display the random Quote
                mRandomQuoteProgressBar.setVisibility(View.GONE);
                mRandomQuoteUnavailable.setVisibility(View.GONE);
                mRandomQuotePositionText.setVisibility(View.VISIBLE);
                mRandomQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mRandomQuoteShareIcon.setVisibility(View.VISIBLE);
                mRandomQuoteQuote.setVisibility(View.VISIBLE);
                mRandomQuoteAuthor.setVisibility(View.VISIBLE);
                mRandomQuoteCategories.setVisibility(View.VISIBLE);

                mRandomQuotePositionText.setText("Random Quote #" + mRandomQuote.getRandomQuotePosition()); //Set the title of the random Quote

                //If the random Quote quote EXISTS
                if (mRandomQuote.getQuote() != null){
                    mRandomQuoteQuote.setText("\" " + randomQuote.getQuote() + " \""); //Display the random Quote quote
                }
                //If the random Quote quote DOES NOT exist
                else{
                    mRandomQuoteQuote.setText("* No Quote to View *"); //Display message to indicate that the random Quote quote does not exist
                }

                //If the random Quote author EXISTS
                if (mRandomQuote.getAuthor() != null){
                    mRandomQuoteAuthor.setText("- " + mRandomQuote.getAuthor()); //Display the random Quote author
                }
                else{
                    mRandomQuoteAuthor.setText("* No Author *"); //Display message to indicate that the random Quote author does not exist
                }



                if (mRandomQuote.getCategories() == null){
                    mRandomQuoteCategories.setText("Categories: *No categories*");
                }
                else{
                    mRandomQuoteCategories.setText("Categories: " + TextUtils.join(", ", mRandomQuote.getCategories()));
                }


                mRandomQuoteFavoriteIcon.setButtonDrawable(mRandomQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);







                try {


                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mRandomQuote.getId()) != null) {

                        mRandomQuote.setFavorite(true);
                        mRandomQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                    }
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mRandomQuote.getId()) == null) {

                        mRandomQuote.setFavorite(false);
                        mRandomQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                    }


                }
                catch (NullPointerException npe){

                    mRandomQuoteProgressBar.setVisibility(View.GONE);
                    mRandomQuotePositionText.setVisibility(View.GONE);
                    mRandomQuoteFavoriteIcon.setVisibility(View.GONE);
                    mRandomQuoteShareIcon.setVisibility(View.GONE);
                    mRandomQuoteQuote.setVisibility(View.GONE);
                    mRandomQuoteAuthor.setVisibility(View.GONE);
                    mRandomQuoteCategories.setVisibility(View.GONE);


                    mRandomQuoteUnavailable.setVisibility(View.VISIBLE);

                }






                mRandomQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                        mRandomQuote.setFavorite(isChecked);


                        mRandomQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                        if (isChecked == true){

                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mRandomQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mRandomQuote);
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mRandomQuote);
                            }
                            else{
                                //Do nothing
                            }

                        }

                        if (isChecked == false){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mRandomQuote);
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mRandomQuote);

                        }
                    }
                });
            }







            mRandomQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mRandomQuote.getAuthor());

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getRandomQuoteShareString());

                    shareIntent = Intent.createChooser(shareIntent, "Share this quote via");

                    startActivity(shareIntent);
                }
            });


        }




        private String getRandomQuoteShareString(){
            String randomQuoteString = "\"" + mRandomQuote.getQuote() + "\"";

            String randomQuoteAuthorString = " - " + mRandomQuote.getAuthor();

            return randomQuoteString + randomQuoteAuthorString;
        }




    }
















    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_random_quotes, menu);

        MenuItem randomiseItem = menu.findItem(R.id.menu_item_randomise_random_quotes_fragment);



        int actualSizeOfRandomQuotesList = 0;

        for (Quote randomQuote : mRandomQuotes){

            if (randomQuote == null){
            }
            if (randomQuote != null){
                actualSizeOfRandomQuotesList++;
            }
        }



        if (actualSizeOfRandomQuotesList == NUMBER_OF_RANDOM_QUOTES_TO_LOAD){
            shouldEnableRandomiseMenuItem = true;
            randomiseItem.setEnabled(true);
        }
        else{
            shouldEnableRandomiseMenuItem = false;
            randomiseItem.setEnabled(false);
        }





//        //If the flag to enable the "Randomise" menu item is TRUE
//        if (shouldEnableRandomiseMenuItem == true){
//
//            randomiseItem.setEnabled(true);
//
//        }
//        //If the flag to enable the "Randomise" menu item is FALSE
//        else{
//            randomiseItem.setEnabled(false);
//        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case (R.id.menu_item_randomise_random_quotes_fragment):

                //Disable the "Randomise" menu item button (the moment it is pressed)
                menuItem.setEnabled(false);


                //Set the flag to enable "Randomise" menu item button to FALSE (i.e. DISABLE "Randomise" menu item button)
                shouldEnableRandomiseMenuItem = false;

                //'Reset' the instance variables
                mRandomQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_QUOTES_TO_LOAD]); //Re-assign mRandomQuotes to a new object
                mRandomQuotesAdaper = new RandomQuotesAdaper(mRandomQuotes); //Re-assign the RecyclerView Adapter
                mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes); //Set the re-assigned RecyclerView Adapter to the re-assigned mRandomQuotes reference variable
                mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper); //Re-assign the RecyclerView to the re-assigned RecyclerView Adapter




                //Perforrm fetching of new Random Quotes via the AsyncTask
                for (int i = 0; i < NUMBER_OF_RANDOM_QUOTES_TO_LOAD; i++) {

                    if (mRandomQuotes.get(i) == null){

                        Integer randomQuotePosition = i;

                        new GetRandomQuoteAsyncTask().execute(randomQuotePosition);
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