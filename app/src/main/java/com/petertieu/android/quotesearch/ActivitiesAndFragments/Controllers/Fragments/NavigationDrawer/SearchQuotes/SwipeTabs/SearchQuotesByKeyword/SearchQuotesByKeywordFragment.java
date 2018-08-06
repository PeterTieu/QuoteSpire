package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes.RandomQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.List;


public class SearchQuotesByKeywordFragment extends Fragment {

    //Log for Logcat
    private final String TAG = "SearchKeywordFragment";


    private final int NUMBER_OF_QUOTES_TO_LOAD = 7;

    private RecyclerView mSearchQuotesByKeywordQuoteRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchQuotesByKeywordAdapter mSearchQuotesByKeywordQuoteAdapter;
    private SearchQuotesByKeywordQuoteViewHolder mSearchQuotesByKeywordQuoteViewHolder;


    //Declare and INITIALISE the List of Quote objects to size 8
    private List<Quote> mSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]);

    private boolean shouldEnableRandomiseMenuItem;





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

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_keyword, viewGroup, false);


        mSearchQuotesByKeywordQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_keyword_recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mSearchQuotesByKeywordQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);




        mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);

        mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);

        mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);


        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Search Quotes by Keywords");



        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {


            if (mSearchQuotesByKeywordQuotes.get(i) == null){


                Integer quotePosition = i;

                new GetSearchQuotesByKeywordAsyncTask().execute(quotePosition);
            }

        }



        return view;
    }










    private class GetSearchQuotesByKeywordAsyncTask extends AsyncTask<Integer, Void, Quote> {


        //NOTE: mQuotePosition is an ARRAY of Integer!
        Integer mQuotePosition[];

        public GetSearchQuotesByKeywordAsyncTask(){
        }

        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition;

            Quote searchQuotesByKeywordQuote = new GetSearchQuotesByKeywordQuote().getSearchQuotesByKeywordQuote();


            return searchQuotesByKeywordQuote;

        }


        @Override
        protected void onPostExecute(Quote searchQuotesByKeywordQuote){

            Log.i(TAG, "Search Quote by Keyword Quotes - method - Quote String: " + searchQuotesByKeywordQuote.getQuote());
            Log.i(TAG, "Search Quote by Keyword Quotes - method  - Category: " + searchQuotesByKeywordQuote.getCategory());
            Log.i(TAG, "Search Quote by Keyword Quotes - method  - Author: " + searchQuotesByKeywordQuote.getAuthor());
            Log.i(TAG, "Search Quote by Keyword Quotes - method - ID: " + searchQuotesByKeywordQuote.getId());


//            mRandomQuotes.add(randomQuote);
            mSearchQuotesByKeywordQuotes.set(mQuotePosition[0], searchQuotesByKeywordQuote);


            mSearchQuotesByKeywordQuotes.get(mQuotePosition[0]).setSearchQuotesByKeywordQuotePosition(mQuotePosition[0] + 1);



            //Decide whether to enable or disable the "Randomise" menu item button
            //If the last Random Quote has been creted

            try{

                if (mQuotePosition[0] == NUMBER_OF_QUOTES_TO_LOAD-1){
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
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because SearchQuotesByKeywordFragment has been closed");
            }





            updateUI();
        }


    }



    public void updateUI(){


        mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);


        mSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged();

    }








    private class SearchQuotesByKeywordAdapter extends RecyclerView.Adapter<SearchQuotesByKeywordQuoteViewHolder>{


        public SearchQuotesByKeywordAdapter(List<Quote> searchQuotesByKewordQuotes){
            mSearchQuotesByKeywordQuotes = searchQuotesByKewordQuotes;
            Log.i(TAG, "ADAPTER - mSearchQuotesByKeywordQuotes: " + mSearchQuotesByKeywordQuotes);
        }


        @Override
        public int getItemCount(){
            return mSearchQuotesByKeywordQuotes.size();
        }




        @Override
        public SearchQuotesByKeywordQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_search_quotes_by_keyword_quote, viewGroup, false);

            mSearchQuotesByKeywordQuoteViewHolder = new SearchQuotesByKeywordQuoteViewHolder(view);

            return mSearchQuotesByKeywordQuoteViewHolder;
        }


        @Override
        public void onBindViewHolder(SearchQuotesByKeywordQuoteViewHolder searchQuotesByKeywordQuoteViewHolder, int position){

            Quote searchQuotesByKeywordQuote = mSearchQuotesByKeywordQuotes.get(position);

//            int randomQuotePosition = position + 1;
//            mRandomQuotesViewHolder.mRandomQuotePositionText.setText("Random Quote #" + randomQuotePosition);

            searchQuotesByKeywordQuoteViewHolder.bind(searchQuotesByKeywordQuote);

        }


        public void setSearchQuotesByKeywordQuotes(List<Quote> searchQuotesByKeywordQuotes){
            mSearchQuotesByKeywordQuotes = searchQuotesByKeywordQuotes;
        }


    }












    private class SearchQuotesByKeywordQuoteViewHolder extends RecyclerView.ViewHolder{


        Quote mSearchQuotesByKeywordQuote;

        LinearLayout mSearchQuotesByKeywordQuoteBubbleLayout;
        TextView mSearchQuotesByKeywordQuotePositionText;
        CheckBox mSearchQuotesByKeywordQuoteFavoriteIcon;
        Button mSearchQuotesByKeywordQuoteShareIcon;
        ProgressBar mSearchQuotesByKeywordQuoteProgressBar;
        TextView mSearchQuotesByKeywordQuoteUnavailable;
        TextView mSearchQuotesByKeywordQuoteQuote;
        TextView mSearchQuotesByKeywordQuoteAuthor;
        TextView mSearchQuotesByKeywordQuoteCategories;



        public SearchQuotesByKeywordQuoteViewHolder(View view){
            super(view);

            mSearchQuotesByKeywordQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.search_quotes_by_keyword_quote_bubble_layout);
            mSearchQuotesByKeywordQuotePositionText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_position);
            mSearchQuotesByKeywordQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.search_quotes_by_keyword_quote_favorite_icon);
            mSearchQuotesByKeywordQuoteShareIcon = (Button) view.findViewById(R.id.search_quotes_by_keyword_quote_share_icon);
            mSearchQuotesByKeywordQuoteProgressBar = (ProgressBar) view.findViewById(R.id.search_quotes_by_keyword_quote_progress_bar);
            mSearchQuotesByKeywordQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_quote);
            mSearchQuotesByKeywordQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_author);
            mSearchQuotesByKeywordQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_categories);
            mSearchQuotesByKeywordQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_unavailable);







            mSearchQuotesByKeywordQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
            mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.VISIBLE);

            mSearchQuotesByKeywordQuotePositionText.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteQuote.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteCategories.setVisibility(View.GONE);

        }




        public void bind(Quote searchQuotesByKeywordQuote){

            if (searchQuotesByKeywordQuote != null){

                mSearchQuotesByKeywordQuote = searchQuotesByKeywordQuote;

//                mRandomQuotePositionText.setText("Random Quote #" + mRandomQuotesViewHolder.getLayoutPosition());


                mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByKeywordQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteCategories.setVisibility(View.VISIBLE);





                mSearchQuotesByKeywordQuotePositionText.setText("Random Quote #" + mSearchQuotesByKeywordQuote.getSearchQuotesByKeywordQuotePosition());


                if (mSearchQuotesByKeywordQuote.getQuote() != null){
                    mSearchQuotesByKeywordQuoteQuote.setText("\" " + searchQuotesByKeywordQuote.getQuote() + " \"");
                }
                else{
                    mSearchQuotesByKeywordQuoteQuote.setText("* No Quote to View *");
                }


                if (mSearchQuotesByKeywordQuote.getAuthor() != null){
                    mRandomQuoteAuthor.setText(mRandomQuote.getAuthor());
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

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getRandomQuoteShareString());

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote of the Day via");

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
















//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//
//        super.onCreateOptionsMenu(menu, menuInflater);
//
//        menuInflater.inflate(R.menu.fragment_random_quotes, menu);
//
//        MenuItem randomiseItem = menu.findItem(R.id.menu_item_randomise_random_quotes_fragment);
//
//
//
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
//    }
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem){
//
//        switch (menuItem.getItemId()){
//            case (R.id.menu_item_randomise_random_quotes_fragment):
//
//                //Disable the "Randomise" menu item button (the moment it is pressed)
//                menuItem.setEnabled(false);
//
//
//                //Set the flag to enable "Randomise" menu item button to FALSE (i.e. DISABLE "Randomise" menu item button)
//                shouldEnableRandomiseMenuItem = false;
//
//                //'Reset' the instance variables
//                mRandomQuotes = Arrays.asList(new Quote[NUMBER_OF_RANDOM_QUOTES_TO_LOAD]); //Re-assign mRandomQuotes to a new object
//                mRandomQuotesAdaper = new RandomQuotesFragment.RandomQuotesAdaper(mRandomQuotes); //Re-assign the RecyclerView Adapter
//                mRandomQuotesAdaper.setRandomQuotes(mRandomQuotes); //Set the re-assigned RecyclerView Adapter to the re-assigned mRandomQuotes reference variable
//                mRandomQuotesRecyclerView.setAdapter(mRandomQuotesAdaper); //Re-assign the RecyclerView to the re-assigned RecyclerView Adapter
//
//
//
//
//                //Perform fetching of new Random Quotes via the AsyncTask
//                for (int i = 0; i < NUMBER_OF_RANDOM_QUOTES_TO_LOAD; i++) {
//
//                    if (mRandomQuotes.get(i) == null){
//
//                        Integer randomQuotePosition = i;
//
//                        new RandomQuotesFragment.GetRandomQuoteAsyncTask().execute(randomQuotePosition);
//                    }
//                }
//
//
//
//                //If the flag to enable the "Randomise" menu item is TRUE.
//                // Called when the last Random Quote has been created in the AsyncTask
//                if (shouldEnableRandomiseMenuItem == true){
//                    menuItem.setEnabled(true);
//                }
//
//
//
//                return true;
//
//
//            default:
//                return super.onOptionsItemSelected(menuItem);
//
//        }
//
//
//
//
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