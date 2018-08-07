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
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.List;


public class SearchQuotesByKeywordFragment extends Fragment {

    //Log for Logcat
    private final String TAG = "SQBKFragment";


    private static final int NUMBER_OF_QUOTES_TO_LOAD = 7;

    private RecyclerView mSearchQuotesByKeywordQuoteRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private static SearchQuotesByKeywordAdapter mSearchQuotesByKeywordQuoteAdapter;
    private SearchQuotesByKeywordQuoteViewHolder mSearchQuotesByKeywordQuoteViewHolder;


    //Declare and INITIALISE the List of Quote objects to size 8
    private static List<Quote> mSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]);

    private boolean shouldEnableSearchMenuItem = false;





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

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_by_keyword, viewGroup, false);


        mSearchQuotesByKeywordQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_keyword_recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mSearchQuotesByKeywordQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);



        //Display the list of Quotes IF and ONLY IF a search has begun.
        //Otherwise, display the list of suggestions
        if (hasSearchBegan == true) {
            mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);
            mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);
            mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);
        }
        else{
            //TODO: Create layout for "Keyword search suggestions: "
        }




//        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++){
//
//            if (mSearchQuotesByKeywordQuotes.get(i) != null){
//                mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);
//                mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);
//                mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);
//
//            }
//        }


//        if (!mSearchQuotesByKeywordQuotes.isEmpty()){
//            mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);
//            mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);
//            mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);
//        }




//        mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);
//        mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);
//        mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);





        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Search Quotes by Keywords");



//        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
//
//
//            if (mSearchQuotesByKeywordQuotes.get(i) == null){
//
//
//                Integer quotePosition = i;
//
//                new GetSearchQuotesByKeywordAsyncTask().execute(quotePosition);
//            }
//
//        }



        return view;
    }








    GetSearchQuotesByKeywordAsyncTask mGetSearchQuotesByKeywordAsyncTask;
    public static boolean hasSearchBegan;




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_keyword, menu);







        final MenuItem searchItem = menu.findItem(R.id.menu_item_search_quotes_by_keyword_fragment);


        final SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                Log.d(TAG, searchQuery);

                //Return a boolean:
                //true: If this method has been handled
                //false: If this method has not been handled.
                //In this case, we only logged an event, so it doesn't count as a 'handle'
                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String searchQuery){
                Log.d(TAG, "Submited query: " + searchQuery);


                searchView.onActionViewCollapsed();


                hasSearchBegan = true;



                //Cancel any existing and running AsyncTasks that are fetching Quotes based on the keyword query
                if (mGetSearchQuotesByKeywordAsyncTask != null){
                    mGetSearchQuotesByKeywordAsyncTask.cancel(true);
                }


                mSearchQuotesByKeywordQuotes = null;
                mSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]);
                mSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(mSearchQuotesByKeywordQuotes);
                mSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(mSearchQuotesByKeywordQuotes);
                mSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged();
                mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(mSearchQuotesByKeywordQuoteAdapter);


//                updateUI();






                for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {


                    Integer quotePosition = i;

                    mGetSearchQuotesByKeywordAsyncTask = new GetSearchQuotesByKeywordAsyncTask(searchQuery);

                    mGetSearchQuotesByKeywordAsyncTask.execute(quotePosition);

//                    new GetSearchQuotesByKeywordAsyncTask(searchQuery).execute(quotePosition);



                }



                //Return a boolean...
                //true: if an action is handled by the listener (as is the case)
                //false: if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;
            }
        });















    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        switch (menuItem.getItemId()){
            case (R.id.menu_item_search_quotes_by_keyword_fragment):

                //Disable the "Randomise" menu item button (the moment it is pressed)
                menuItem.setEnabled(false);





                return true;


            default:
                return super.onOptionsItemSelected(menuItem);

        }




    }



















    private class GetSearchQuotesByKeywordAsyncTask extends AsyncTask<Integer, Void, Quote> {


        //NOTE: mQuotePosition is an ARRAY of Integer!
        Integer mQuotePosition[];

        String mSearchQuery;


        public GetSearchQuotesByKeywordAsyncTask(String searchQeury) {
            mSearchQuery = searchQeury;
        }

        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition;

            Quote searchQuotesByKeywordQuote = new GetSearchQuotesByKeywordQuote().getSearchQuotesByKeywordQuote(mSearchQuery);


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





            try{

                if (mQuotePosition[0] == NUMBER_OF_QUOTES_TO_LOAD-1){
                    shouldEnableSearchMenuItem = true;
                    getActivity().invalidateOptionsMenu();
                }

                //If the final Quote has NOT been created yet
                else{
                    shouldEnableSearchMenuItem = false;
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
            mSearchQuotesByKeywordQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_unavailable);
            mSearchQuotesByKeywordQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_quote);
            mSearchQuotesByKeywordQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_author);
            mSearchQuotesByKeywordQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_categories);






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



                mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByKeywordQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteCategories.setVisibility(View.VISIBLE);





                mSearchQuotesByKeywordQuotePositionText.setText("Quote #" + mSearchQuotesByKeywordQuote.getSearchQuotesByKeywordQuotePosition());


                if (mSearchQuotesByKeywordQuote.getQuote() != null){
                    mSearchQuotesByKeywordQuoteQuote.setText("\" " + searchQuotesByKeywordQuote.getQuote() + " \"");
                }
                else{
                    mSearchQuotesByKeywordQuoteQuote.setText("* No Quote to View *");
                }


                if (mSearchQuotesByKeywordQuote.getAuthor() != null){
                    mSearchQuotesByKeywordQuoteAuthor.setText(mSearchQuotesByKeywordQuote.getAuthor());
                }
                else{
                    mSearchQuotesByKeywordQuoteAuthor.setText("* No Author *");
                }






                if (mSearchQuotesByKeywordQuote.getCategories() == null){
                    mSearchQuotesByKeywordQuoteCategories.setText("Categories: *No categories*");
                }
                else{
                    mSearchQuotesByKeywordQuoteCategories.setText("Categories: " + TextUtils.join(", ", mSearchQuotesByKeywordQuote.getCategories()));
                }


                mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(mSearchQuotesByKeywordQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);







                try {


                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) != null) {

                        mSearchQuotesByKeywordQuote.setFavorite(true);
                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                    }
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) == null) {

                        mSearchQuotesByKeywordQuote.setFavorite(false);
                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                    }


                }
                catch (NullPointerException npe){

                    mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuotePositionText.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteQuote.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteCategories.setVisibility(View.GONE);


                    mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.VISIBLE);

                }






                mSearchQuotesByKeywordQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                        mSearchQuotesByKeywordQuote.setFavorite(isChecked);


                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                        if (isChecked == true){

                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mSearchQuotesByKeywordQuote);
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByKeywordQuote);
                            }
                            else{
                                //Do nothing
                            }

                        }

                        if (isChecked == false){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mSearchQuotesByKeywordQuote);
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByKeywordQuote);

                        }
                    }
                });
            }







            mSearchQuotesByKeywordQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mSearchQuotesByKeywordQuote.getAuthor());

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSearchQuotesByKeywordQuoteShareString());

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote of the Day via");

                    startActivity(shareIntent);
                }
            });


        }




        private String getSearchQuotesByKeywordQuoteShareString(){
            String searchQuotesByKeywordQuoteString = "\"" + mSearchQuotesByKeywordQuote.getQuote() + "\"";

            String searchQuotesByKeywordQuoteAuthorString = " - " + mSearchQuotesByKeywordQuote.getAuthor();

            return searchQuotesByKeywordQuoteString + searchQuotesByKeywordQuoteAuthorString;
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