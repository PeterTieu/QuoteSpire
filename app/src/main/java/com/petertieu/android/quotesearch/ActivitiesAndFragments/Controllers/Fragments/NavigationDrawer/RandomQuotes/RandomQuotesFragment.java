package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.List;

public class RandomQuotesFragment extends Fragment {


    private static final int NUMBER_OF_RANDOM_QUOTES_TO_LOAD = 12;

    //Log for Logcat
    private final String TAG = "RandomQuotesFragment";

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRandomQuotesRecyclerView;
    private RandomQuotesAdaper mRandomQuotesAdaper;
    private RandomQuotesViewHolder mRandomQuotesViewHolder;

//    private List<Quote> mRandomQuotes = new ArrayList<>();

    //Declare and INITIALISE the List of Quote objects to size 7
    private static List<Quote> mRandomQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_QUOTES_TO_LOAD]);

    //Flag to decide whether the "Randomise" menut item button should be enabled or disabled
    boolean shouldEnableRandomiseMenuItem = false;






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

        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes);

        mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper);



        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Random Quotes");




        for (int i = 0; i < NUMBER_OF_RANDOM_QUOTES_TO_LOAD; i++) {


            //If each of the Quote objects in the mRandomQuotes ArrayList are EMPTY
            if (mRandomQuotes.get(i) == null){


                Integer randomQuotePosition = i;

                new GetRandomQuoteAsyncTask().execute(randomQuotePosition);
            }

        }







        return view;
    }













    private class GetRandomQuoteAsyncTask extends AsyncTask<Integer, Void, Quote>{


        //NOTE: mQuotePosition is an ARRAY of Integer!
        Integer mQuotePosition[];

        public GetRandomQuoteAsyncTask(){
        }

        @Override
        protected Quote doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber;

            Quote randomQuote = new GetRandomQuote().getRandomQuote();


            return randomQuote;

        }


        @Override
        protected void onPostExecute(Quote randomQuote){

            Log.i(TAG, "Random Quote - method - Quote String: " + randomQuote.getQuote());
            Log.i(TAG, "Random Quote - method  - Category: " + randomQuote.getCategory());
            Log.i(TAG, "Random Quote - method  - Author: " + randomQuote.getAuthor());
            Log.i(TAG, "Random Quote - method - ID: " + randomQuote.getId());


//            mRandomQuotes.add(randomQuote);
            mRandomQuotes.set(mQuotePosition[0], randomQuote);


            mRandomQuotes.get(mQuotePosition[0]).setRandomQuotePosition(mQuotePosition[0] + 1);



            //Decide whether to enable or disable the "Randomise" menu item button
                //If the last Random Quote has been creted
            try{

                getActivity().invalidateOptionsMenu();
//                if (mQuotePosition[0] == NUMBER_OF_RANDOM_QUOTES_TO_LOAD-1){
//                    shouldEnableRandomiseMenuItem = true;
//                    getActivity().invalidateOptionsMenu();
//                }
//                //If the last Random Quote has NOT been created yet
//                else{
//                    shouldEnableRandomiseMenuItem = false;
//                    getActivity().invalidateOptionsMenu();
//                }

            }
            catch (NullPointerException npe){
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because RandomQuotesFragment has been closed");
            }





            updateUI();
        }


    }





    public void updateUI(){

//        mRandomQuotesAdaper = new RandomQuotesAdaper(mRandomQuotes);

//        mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper);

        mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes);


        mRandomQuotesAdaper.notifyDataSetChanged();

    }












    private class RandomQuotesAdaper extends RecyclerView.Adapter<RandomQuotesViewHolder>{


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

//            int randomQuotePosition = position + 1;
//            mRandomQuotesViewHolder.mRandomQuotePositionText.setText("Random Quote #" + randomQuotePosition);

            randomQuotesViewHolder.bind(randomQuote);

        }


        public void setRandomQuotes(List<Quote> randomQuotes){
            mRandomQuotes = randomQuotes;
        }


    }














    private class RandomQuotesViewHolder extends RecyclerView.ViewHolder{


        Quote mRandomQuote;

        LinearLayout mRandomQuoteBubbleLayout;
        TextView mRandomQuotePositionText;
        CheckBox mRandomQuoteFavoriteIcon;
        Button mRandomQuoteShareIcon;
        ProgressBar mRandomQuoteProgressBar;
        TextView mRandomQuoteUnavailable;
        TextView mRandomQuoteQuote;
        TextView mRandomQuoteAuthor;
        TextView mRandomQuoteCategories;



        public RandomQuotesViewHolder(View view){
            super(view);

            mRandomQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.random_quote_bubble_layout);
            mRandomQuotePositionText = (TextView) view.findViewById(R.id.random_quote_position);
            mRandomQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.random_quote_favorite_icon);
            mRandomQuoteShareIcon = (Button) view.findViewById(R.id.random_quote_share_icon);
            mRandomQuoteProgressBar = (ProgressBar) view.findViewById(R.id.random_quote_progress_bar);
            mRandomQuoteQuote = (TextView) view.findViewById(R.id.random_quote_quote);
            mRandomQuoteAuthor = (TextView) view.findViewById(R.id.random_quote_author);
            mRandomQuoteCategories = (TextView) view.findViewById(R.id.random_quote_categories);
            mRandomQuoteUnavailable = (TextView) view.findViewById(R.id.random_quote_unavailable);







            mRandomQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
            mRandomQuoteProgressBar.setVisibility(View.VISIBLE);

            mRandomQuotePositionText.setVisibility(View.GONE);
            mRandomQuoteFavoriteIcon.setVisibility(View.GONE);
            mRandomQuoteShareIcon.setVisibility(View.GONE);
            mRandomQuoteUnavailable.setVisibility(View.GONE);
            mRandomQuoteQuote.setVisibility(View.GONE);
            mRandomQuoteAuthor.setVisibility(View.GONE);
            mRandomQuoteCategories.setVisibility(View.GONE);

        }




        public void bind(Quote randomQuote){

            if (randomQuote != null){

                mRandomQuote = randomQuote;

//                mRandomQuotePositionText.setText("Random Quote #" + mRandomQuotesViewHolder.getLayoutPosition());


                mRandomQuoteProgressBar.setVisibility(View.GONE);
                mRandomQuoteUnavailable.setVisibility(View.GONE);

                mRandomQuotePositionText.setVisibility(View.VISIBLE);
                mRandomQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mRandomQuoteShareIcon.setVisibility(View.VISIBLE);
                mRandomQuoteQuote.setVisibility(View.VISIBLE);
                mRandomQuoteAuthor.setVisibility(View.VISIBLE);
                mRandomQuoteCategories.setVisibility(View.VISIBLE);





                mRandomQuotePositionText.setText("Random Quote #" + mRandomQuote.getRandomQuotePosition());


                if (mRandomQuote.getQuote() != null){
                    mRandomQuoteQuote.setText("\" " + randomQuote.getQuote() + " \"");
                }
                else{
                    mRandomQuoteQuote.setText("* No Quote to View *");
                }


                if (mRandomQuote.getAuthor() != null){
                    mRandomQuoteAuthor.setText("- " + mRandomQuote.getAuthor());
                }
                else{
                    mRandomQuoteAuthor.setText("* No Author *");
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





//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//
//        MenuItem randomiseItem = menu.findItem(R.id.menu_item_randomise_random_quotes_fragment);
//
//        if (shouldEnableRandomiseMenuItem){
//
//            randomiseItem.setEnabled(true);
//        }
//
//        return true;
//    }














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