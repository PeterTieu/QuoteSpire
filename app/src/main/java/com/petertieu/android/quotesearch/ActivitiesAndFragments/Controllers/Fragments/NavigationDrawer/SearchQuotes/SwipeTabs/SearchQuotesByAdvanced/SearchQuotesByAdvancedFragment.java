package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAdvanced;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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


@SuppressWarnings({"RedundantCast", "FieldCanBeLocal", "deprecation", "ConstantConditions"})

//Fragment for searching Quotes by advanced
public class SearchQuotesByAdvancedFragment extends Fragment{


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "SQBAdvancedFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private static final int NUMBER_OF_QUOTES_TO_LOAD = 10; //Number of quotes to load upon search
    public static String sKeywordSearchQuery;
    public static String sAuthorSearchQuery;
    public static String sCategorySearchQuery;
    private TextView mAdvancedSearchedText;


    //-------------- LIST variables ------------------------------------
    private static List<Quote> sSearchQuotesByAdvancedQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //List of Quote objects (of size 12)
    private List<String> mRandomSearchSuggestions; //List of Search Suggestions. They are 10 Strings that are randomly picked from a larger list of Strings
    @SuppressWarnings("CanBeFinal") private List<SearchQuotesByAdvancedFragment.GetSearchQuotesByAdvancedAsyncTask> mGetSearchQuotesByAdvancedAsyncTasksList = Arrays.asList(new SearchQuotesByAdvancedFragment.GetSearchQuotesByAdvancedAsyncTask[NUMBER_OF_QUOTES_TO_LOAD]); //List of AsyncTass for fetching the Quote (of size 12)


    //-------------- FLAG variables ------------------------------------
    private static boolean shouldDisplaySearchResultsWhenFragmentIsReloaded; //Flag that is activated when a search has begun or the search items are in View.


    //-------------- VIEW variables ------------------------------------
    private TextView mSearchQuotesByAdvancedTitle;
    private TextView mSearchQuotesByKeywordText;
    private SearchView mSearchQuotesByKeywordSearchView;
    private TextView mSearchQuotesByAuthorText;
    private SearchView mSearchQuotesByAuthorSearchView;
    private TextView mSearchQuotesByCategoryText;
    private SearchView mSearchQuotesByCategorySearchView;
    private Button mSearchQuotesByAdvancedSearchButton;


    private LinearLayoutManager mLinearLayoutManager; //LinearLayoutManager for RecyclerView
    private RecyclerView mSearchQuotesByAdvancedQuoteRecyclerView; //RecyclerView to store list of Quote items
    private static SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedAdapter sSearchQuotesByAdvancedQuoteAdapter; //RecyclerView Adapter
    private SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedQuoteViewHolder mSearchQuotesByAdvancedQuoteViewHolder; //RecyclerView ViewHolder for storing single Quote items





    //================== DEFINE METHODS ===================================================================================

    //Override onAttach(..) fragment lifecycle callback method
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttached(..) called"); //Log to Logcat
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);

        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setHasOptionsMenu(true);
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @SuppressWarnings("NullableProblems")
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log to Logcat

//        sKeywordSearchQuery = null;
//        sAuthorSearchQuery = null;
//        sCategorySearchQuery = null;


        getActivity().setTitle(getResources().getString(R.string.search_quotes_fragment_title)); //Set title for fragment

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_by_advanced, viewGroup, false); //Inflate fragment layout


        //Assign View instance variables to their associated resource files
        mAdvancedSearchedText = (TextView) view.findViewById(R.id.search_quotes_by_advanced_advanced_searched);


        mSearchQuotesByAdvancedTitle = (TextView) view.findViewById(R.id.search_quotes_by_advanced_title);
        mSearchQuotesByKeywordText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_text);
        mSearchQuotesByKeywordSearchView = (SearchView) view.findViewById(R.id.search_quotes_by_keyword_search_view);
        mSearchQuotesByAuthorText = (TextView) view.findViewById(R.id.search_quotes_by_author_text);
        mSearchQuotesByAuthorSearchView = (SearchView) view.findViewById(R.id.search_quotes_by_author_search_view);
        mSearchQuotesByCategoryText = (TextView) view.findViewById(R.id.search_quotes_by_category_text);
        mSearchQuotesByCategorySearchView = (SearchView) view.findViewById(R.id.search_quotes_by_category_search_view);
        mSearchQuotesByAdvancedSearchButton = (Button) view.findViewById(R.id.search_quotes_by_advanced_search_button);





        mSearchQuotesByAdvancedQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_advanced_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchQuotesByAdvancedQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);



        Log.i(TAG, "True or false:" + shouldDisplaySearchResultsWhenFragmentIsReloaded);


        //IF a Search has begun/is in place, then display the RESULT (i.e. search items)
        if (shouldDisplaySearchResultsWhenFragmentIsReloaded) {

            getActivity().invalidateOptionsMenu(); //Update the options menu

            //---------- Configure View variables ----------------------
            mAdvancedSearchedText.setVisibility(View.VISIBLE);
            mSearchQuotesByAdvancedQuoteRecyclerView.setVisibility(View.VISIBLE);

            mSearchQuotesByAdvancedTitle.setVisibility(View.GONE);
            mSearchQuotesByKeywordText.setVisibility(View.GONE);
            mSearchQuotesByKeywordSearchView.setVisibility(View.GONE);
            mSearchQuotesByAuthorText.setVisibility(View.GONE);
            mSearchQuotesByAuthorSearchView.setVisibility(View.GONE);
            mSearchQuotesByCategoryText.setVisibility(View.GONE);
            mSearchQuotesByCategorySearchView.setVisibility(View.GONE);
            mSearchQuotesByAdvancedSearchButton.setVisibility(View.GONE);


            //---------- Configure View variables ----------------------
//            mAdvancedSearchedText.setText(Html.fromHtml("Advanced searched: " + " " + " \"" +"<i>" + sKeywordSearchQuery.toUpperCase() + "</i>" + "\"")); //Text for displaying the Advanced Searched

            sSearchQuotesByAdvancedQuoteAdapter = new SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedAdapter(sSearchQuotesByAdvancedQuotes); //Create a new RecyclerView Adapter
            sSearchQuotesByAdvancedQuoteAdapter.setSearchQuotesByAdvancedQuotes(sSearchQuotesByAdvancedQuotes); //Link the RecyclerView adapter to the List of Quotes from the search result
            mSearchQuotesByAdvancedQuoteRecyclerView.setAdapter(sSearchQuotesByAdvancedQuoteAdapter); //Set up the RecyclerView to the RecyclerView Adapter
        }

        //If a Search has not begun/is in place, then display the SEARCH SUGGESTIONS
        else{
            //---------- Configure View variables ----------------------
            mAdvancedSearchedText.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteRecyclerView.setVisibility(View.GONE);

            mSearchQuotesByAdvancedTitle.setVisibility(View.VISIBLE);
            mSearchQuotesByKeywordText.setVisibility(View.VISIBLE);
            mSearchQuotesByKeywordSearchView.setVisibility(View.VISIBLE);
            mSearchQuotesByAuthorText.setVisibility(View.VISIBLE);
            mSearchQuotesByAuthorSearchView.setVisibility(View.VISIBLE);
            mSearchQuotesByCategoryText.setVisibility(View.VISIBLE);
            mSearchQuotesByCategorySearchView.setVisibility(View.VISIBLE);
            mSearchQuotesByAdvancedSearchButton.setVisibility(View.VISIBLE);



            Log.i(TAG, "sKeywordSearchQuery: " + sKeywordSearchQuery);
            Log.i(TAG, "sAuthorSearchQuery: " + sAuthorSearchQuery);
            Log.i(TAG, "sCategorySearchQuery: " + sCategorySearchQuery);


            mSearchQuotesByKeywordSearchView.setQuery(sKeywordSearchQuery, false);
            mSearchQuotesByAuthorSearchView.setQuery(sAuthorSearchQuery, false);
            mSearchQuotesByCategorySearchView.setQuery(sCategorySearchQuery, false);


//
//            sKeywordSearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedKeywordStoredQuery(getActivity());
//            sAuthorSearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedAuthorStoredQuery(getActivity());
//            sCategorySearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedCategoryStoredQuery(getActivity());


        }





        mSearchQuotesByKeywordSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String keywordQuery) {

                Log.i(TAG, keywordQuery);

                sKeywordSearchQuery = keywordQuery;


//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedKeywordStoredQuery(getActivity(), sKeywordSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String keywordQuery) {


//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedKeywordStoredQuery(getActivity(), sKeywordSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                searchQuotes();

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;

            }

        });









        mSearchQuotesByAuthorSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String authorQuery) {

                Log.i(TAG, authorQuery);

                sAuthorSearchQuery = authorQuery;


//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedAuthorStoredQuery(getActivity(), sAuthorSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String authorQuery) {


//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedAuthorStoredQuery(getActivity(), sAuthorSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later


                searchQuotes();

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;

            }

        });









        mSearchQuotesByCategorySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String categoryQuery) {

                Log.i(TAG, categoryQuery);

                sCategorySearchQuery = categoryQuery;


//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedCategoryStoredQuery(getActivity(), sCategorySearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String categoryQuery) {

//                SearchQuotesByAdvancedSharedPref.setSearchQuotesByAdvancedCategoryStoredQuery(getActivity(), sCategorySearchQuery); //Set the search query to the SharedPreferences - to be retrieved later


                searchQuotes();

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;

            }

        });




        mSearchQuotesByAdvancedSearchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                searchQuotes();
            }
        });



        getActivity().invalidateOptionsMenu(); //Result options menu

        return view;
    }





    private void searchQuotes(){

//        sKeywordSearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedKeywordStoredQuery(getActivity());
//        sAuthorSearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedAuthorStoredQuery(getActivity());
//        sCategorySearchQuery = SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedCategoryStoredQuery(getActivity());


        if ( (sKeywordSearchQuery == null || sKeywordSearchQuery.isEmpty()) &&
             (sAuthorSearchQuery == null || sAuthorSearchQuery.isEmpty()) &&
             (sCategorySearchQuery == null || sCategorySearchQuery.isEmpty()) ){

            Log.i(TAG, "Empty");

            noSearchQueriesDialogFragment();
            return;
        }


        Log.i(TAG, "post-Empty");


        getActivity().invalidateOptionsMenu();

        shouldDisplaySearchResultsWhenFragmentIsReloaded = true; //Toggle flag to display the search results to TRUE

        mSearchQuotesByAdvancedTitle.setVisibility(View.GONE);
        mSearchQuotesByKeywordText.setVisibility(View.GONE);
        mSearchQuotesByKeywordSearchView.setVisibility(View.GONE);
        mSearchQuotesByAuthorText.setVisibility(View.GONE);
        mSearchQuotesByAuthorSearchView.setVisibility(View.GONE);
        mSearchQuotesByCategoryText.setVisibility(View.GONE);
        mSearchQuotesByCategorySearchView.setVisibility(View.GONE);
        mSearchQuotesByAdvancedSearchButton.setVisibility(View.GONE);

        mSearchQuotesByAdvancedQuoteRecyclerView.setVisibility(View.VISIBLE); //Show the RecyclerView
        sSearchQuotesByAdvancedQuotes = null; //Nullify the List of all Quotes if they already point to an existing set from the previous search
        sSearchQuotesByAdvancedQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //Re-initialise the size of the List of all Quotes
        sSearchQuotesByAdvancedQuoteAdapter = new SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedAdapter(sSearchQuotesByAdvancedQuotes); //Create a new RecyclerView Adapter
        sSearchQuotesByAdvancedQuoteAdapter.setSearchQuotesByAdvancedQuotes(sSearchQuotesByAdvancedQuotes);  //Link the RecyclerView adapter to the List of Quotes from the search result
        sSearchQuotesByAdvancedQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
        mSearchQuotesByAdvancedQuoteRecyclerView.setAdapter(sSearchQuotesByAdvancedQuoteAdapter); //Set the RecyclerView to the new RecyclerView Adapter


        //Begin a new set of AsyncTasks to fetch a new set of Quotes from the search query
        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

            Integer quotePosition = i; //Let the quote position equal the index

            mGetSearchQuotesByAdvancedAsyncTasksList.set(i, new SearchQuotesByAdvancedFragment.GetSearchQuotesByAdvancedAsyncTask(sKeywordSearchQuery, sAuthorSearchQuery, sCategorySearchQuery)); //Assign the AsyncTask reference in the List to a new AsyncTask object
            mGetSearchQuotesByAdvancedAsyncTasksList.get(i).execute(quotePosition); //Execute the AsyncTask object (i.e. begin fetching the Quote)
        }
    }





    private void noSearchQueriesDialogFragment(){
        TextView dialogTitle = new TextView(getActivity());
        dialogTitle.setText("No Search Queries");
        dialogTitle.setTextSize(22);
        dialogTitle.setGravity(Gravity.CENTER);
        dialogTitle.setTypeface(null, Typeface.BOLD);
        dialogTitle.setTextColor(getResources().getColor(R.color.orange));
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey));

        String dialogMessage = "Please enter search queries for Keyword and/or Author and/or Category";


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quotes, null);

        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(android.R.string.ok, null)
                .create();

        alertDialog.show();
    }




    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_advanced, menu);

        //---------------- Configure CLEAR ALL menu item -------------------------------------------------------------------------------
        final MenuItem clearAllItem = menu.findItem(R.id.menu_item_search_quotes_by_advanced_fragment_clear_all); //Menu item for "Clear All"

        //Show the "Clear All" menu item IF and ONLY IF the flag indicating that the search results are displayed is TRUE
        if (shouldDisplaySearchResultsWhenFragmentIsReloaded){
            clearAllItem.setVisible(true);
        }
        else{
            clearAllItem.setVisible(false);
        }
    }




    //Define what happens when the menu items are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){

            //"CLEAR ALL" menu item
            case (R.id.menu_item_search_quotes_by_advanced_fragment_clear_all):

                shouldDisplaySearchResultsWhenFragmentIsReloaded = false; //Toggle flag to display the search results to FALSE

                getActivity().invalidateOptionsMenu(); //Update options menu - i.e. call onCreateOptionsMenu() in order to make the "CLEAR ALL" menu item disappear

                //Configure visibilities of each of the Views
                mAdvancedSearchedText.setVisibility(View.GONE);
                mSearchQuotesByAdvancedQuoteRecyclerView.setVisibility(View.GONE);

                mSearchQuotesByAdvancedTitle.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordText.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordSearchView.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorText.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorSearchView.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryText.setVisibility(View.VISIBLE);
                mSearchQuotesByCategorySearchView.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedSearchButton.setVisibility(View.VISIBLE);


                mSearchQuotesByKeywordSearchView.setQuery(sKeywordSearchQuery, false);
                mSearchQuotesByAuthorSearchView.setQuery(sAuthorSearchQuery, false);
                mSearchQuotesByCategorySearchView.setQuery(sCategorySearchQuery, false);

//                mSearchQuotesByKeywordSearchView.setQuery(SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedKeywordStoredQuery(getActivity()), false);
//                mSearchQuotesByAuthorSearchView.setQuery(SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedAuthorStoredQuery(getActivity()), false);
//                mSearchQuotesByCategorySearchView.setQuery(SearchQuotesByAdvancedSharedPref.getSearchQuotesByAdvancedCategoryStoredQuery(getActivity()), false);


                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the advanced query

//                Toast.makeText(getActivity(), "Cleared Search Results for: " + sKeywordSearchQuery.toUpperCase(), Toast.LENGTH_LONG).show(); //Create Toast notifying that the search results have been cleared

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }

    }




    //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the advanced query
    private void cancelAllCurrentAsyncTasks(){

        if (!mGetSearchQuotesByAdvancedAsyncTasksList.isEmpty()) {

            for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
                //Try risky task - mGetSearchQuotesByAdvancedAsyncTasksList.get(i).cancel() may throw a NullPointerException if the AsyncTask is not running
                try{
                    mGetSearchQuotesByAdvancedAsyncTasksList.get(i).cancel(true);
                }
                catch (NullPointerException npe){
                    Log.e(TAG, "GetSearchQuotesByAdvancedAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
                }
            }
        }

    }






    //AsyncTask for fetching a Quote based on the search query
    //GENERIC TYPES:
    //#1: PARAMS:   Integer: Type passed to execute(). NOTE: It could be passed in multiples (e.g. ..execute(1, 2, 3, 4))
    //#2: PROGRESS: Void: Type published during background computation
    //#3: RESULTS:  Quote: Type returned from doInBackground()
    @SuppressLint("StaticFieldLeak")
    private class GetSearchQuotesByAdvancedAsyncTask extends AsyncTask<Integer, Void, Quote> {

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Integer mQuotePosition[]; //NOTE: mQuotePosition is an ARRAY of type Integer
        final String mSearchQuery; //Search query
        final String mAuthorSearchQuery;
        final String mCategorySearchQuery;


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        GetSearchQuotesByAdvancedAsyncTask(String keywordSearchQuery, String authorSearchQuery, String categorySearchQuery) {
            mSearchQuery = keywordSearchQuery; //Stash search query to the instance variable (mSearchQuery)
            mAuthorSearchQuery = authorSearchQuery;
            mCategorySearchQuery = categorySearchQuery;
        }



        //Perform the main work of the asynchronous task - fetch the Quote
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition; //Stash the Quote position to the instance variable (mQuotePosition)

            Quote searchQuotesByAdvancedQuote = new GetSearchQuotesByAdvancedQuote().getSearchQuotesByAdvancedQuote(mSearchQuery, mAuthorSearchQuery, mCategorySearchQuery); //Fetch the Quote based on the search query

            return searchQuotesByAdvancedQuote; //Return the fetched Quote - to be passed to onPostExecute()
        }



        //Perform work after the Quote is fetched - save the fetched Quote to the List of Quotes
        @Override
        protected void onPostExecute(Quote searchQuotesByAdvancedQuote) {

            Log.i(TAG, "Search Quote by Advanced Quotes - method - Quote String: " + searchQuotesByAdvancedQuote.getQuote());
            Log.i(TAG, "Search Quote by Advanced Quotes - method  - Categories: " + searchQuotesByAdvancedQuote.getCategories());
            Log.i(TAG, "Search Quote by Advanced Quotes - method  - Author: " + searchQuotesByAdvancedQuote.getAuthor());
            Log.i(TAG, "Search Quote by Advanced Quotes - method - ID: " + searchQuotesByAdvancedQuote.getId());


            searchQuotesByAdvancedQuote.setSearchQuotesByAdvancedQuotePosition(mQuotePosition[0] + 1); //Set the position member variable of the fetched Quote
            sSearchQuotesByAdvancedQuotes.set(mQuotePosition[0], searchQuotesByAdvancedQuote); //Save the fetched Quote to the list of Quotes, to the index in this list based on the quote position
            sSearchQuotesByAdvancedQuoteAdapter.setSearchQuotesByAdvancedQuotes(sSearchQuotesByAdvancedQuotes); //Set the RecyclerView Adapter to the newly updated List of Quotes
            sSearchQuotesByAdvancedQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
        }

    }




    //RecyclerView Adapter
    private class SearchQuotesByAdvancedAdapter extends RecyclerView.Adapter<SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedQuoteViewHolder>{

        //Constructor
        SearchQuotesByAdvancedAdapter(List<Quote> searchQuotesByAdvancedQuotes){
            sSearchQuotesByAdvancedQuotes = searchQuotesByAdvancedQuotes; //Stash passed List of Quotes to the instance variable (sSearchQuotesByAdvancedQuotes)
        }



        //Get count of the size of the List of Quotes
        @Override
        public int getItemCount(){
            return sSearchQuotesByAdvancedQuotes.size();
        }



        //Create the RecyclerView ViewHolder
        @SuppressWarnings("NullableProblems")
        @Override
        public SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater

            View listItemView = layoutInflater.inflate(R.layout.list_item_search_quotes_by_advanced, viewGroup, false); //Obtain list-item view

            mSearchQuotesByAdvancedQuoteViewHolder = new SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedQuoteViewHolder(listItemView); //Send list-item view to the RecyclerView ViewHolder

            return mSearchQuotesByAdvancedQuoteViewHolder; //Return RecyclerView ViewHolder
        }



        //Bind RecyclerView ViewHolder to the data of the Quote
        @SuppressWarnings("NullableProblems")
        @Override
        public void onBindViewHolder(SearchQuotesByAdvancedFragment.SearchQuotesByAdvancedQuoteViewHolder searchQuotesByAdvancedQuoteViewHolder, int position){

            Quote searchQuotesByAdvancedQuote = sSearchQuotesByAdvancedQuotes.get(position); //Get the Quote

            searchQuotesByAdvancedQuoteViewHolder.bind(searchQuotesByAdvancedQuote); //Bind RecyclerView ViewHolder to the data of the Quote
        }



        //Stash the List of Quotes to the instance variable
        void setSearchQuotesByAdvancedQuotes(List<Quote> searchQuotesByAdvancedQuotes){
            sSearchQuotesByAdvancedQuotes = searchQuotesByAdvancedQuotes;
        }

    }




    //RecyclerView ViewHolder
    @SuppressWarnings("ConstantConditions")
    private class SearchQuotesByAdvancedQuoteViewHolder extends RecyclerView.ViewHolder{

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Quote mSearchQuotesByAdvancedQuote; //Quote - contains data for the list item

        //-------------- VIEW variables of the list item ------------------------------------
        final LinearLayout mSearchQuotesByAdvancedQuoteBubbleLayout;
        final TextView mSearchQuotesByAdvancedQuotePositionText;
        final CheckBox mSearchQuotesByAdvancedQuoteFavoriteIcon;
        final Button mSearchQuotesByAdvancedQuoteShareIcon;
        final ProgressBar mSearchQuotesByAdvancedQuoteProgressBar;
        final TextView mSearchQuotesByAdvancedQuoteUnavailable;
        final TextView mSearchQuotesByAdvancedQuoteQuote;
        final TextView mSearchQuotesByAdvancedQuoteAuthor;
        final TextView mSearchQuotesByAdvancedQuoteCategories;


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        SearchQuotesByAdvancedQuoteViewHolder(View view){
            super(view);

            //Assign list item view instance variables to their associated resource files
            mSearchQuotesByAdvancedQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.search_quotes_by_advanced_quote_bubble_layout);
            mSearchQuotesByAdvancedQuotePositionText = (TextView) view.findViewById(R.id.search_quotes_by_advanced_quote_position);
            mSearchQuotesByAdvancedQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.search_quotes_by_advanced_quote_favorite_icon);
            mSearchQuotesByAdvancedQuoteShareIcon = (Button) view.findViewById(R.id.search_quotes_by_advanced_quote_share_icon);
            mSearchQuotesByAdvancedQuoteProgressBar = (ProgressBar) view.findViewById(R.id.search_quotes_by_advanced_quote_progress_bar);
            mSearchQuotesByAdvancedQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_advanced_quote_unavailable);
            mSearchQuotesByAdvancedQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_advanced_quote_quote);
            mSearchQuotesByAdvancedQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_advanced_quote_author);
            mSearchQuotesByAdvancedQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_advanced_quote_categories);

            mSearchQuotesByAdvancedQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges)); //Set background drawable for each list item

            //Set visibilities of each of the list item views - set all list item views to GONE (except for the ProgressBar) as they have only just been created
            mSearchQuotesByAdvancedQuoteProgressBar.setVisibility(View.VISIBLE);
            mSearchQuotesByAdvancedQuotePositionText.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteFavoriteIcon.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteShareIcon.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteUnavailable.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteQuote.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteAuthor.setVisibility(View.GONE);
            mSearchQuotesByAdvancedQuoteCategories.setVisibility(View.GONE);
        }



        //Bind the RecyclerView ViewHolder to the data from the Quote
        @SuppressWarnings("WeakerAccess")
        @SuppressLint("SetTextI18n")
        public void bind(Quote searchQuotesByAdvancedQuote){

            //If the Quote object exists
            if (searchQuotesByAdvancedQuote != null){

                mSearchQuotesByAdvancedQuote = searchQuotesByAdvancedQuote; //Stash passed Quote parameter to instance variable

                //Configure visibilities of the list item views
                mSearchQuotesByAdvancedQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByAdvancedQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByAdvancedQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByAdvancedQuoteCategories.setVisibility(View.VISIBLE);


                mSearchQuotesByAdvancedQuotePositionText.setText("Quote #" + mSearchQuotesByAdvancedQuote.getSearchQuotesByAdvancedQuotePosition()); //Set the text of the position of the Quote

                //Set drawable for Quote favorite icon, based on whether it is Favorited or not
                mSearchQuotesByAdvancedQuoteFavoriteIcon.setButtonDrawable(mSearchQuotesByAdvancedQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //If the Quote text exists
                if (mSearchQuotesByAdvancedQuote.getQuote() != null){
                    mSearchQuotesByAdvancedQuoteQuote.setText("\" " + searchQuotesByAdvancedQuote.getQuote() + " \""); //Set the Quote text
                }
                //If the Quote text DOES NOT exist
                else{
                    mSearchQuotesByAdvancedQuoteQuote.setText("* No Quote to View *"); //Set message to say Quote text could not be obtained
                }


                //If the Quote author exists
                if (mSearchQuotesByAdvancedQuote.getAuthor() != null){
                    mSearchQuotesByAdvancedQuoteAuthor.setText("- " + mSearchQuotesByAdvancedQuote.getAuthor()); //Set the Quote author text
                }
                //If the Quote author DOES NOT exist
                else{
                    mSearchQuotesByAdvancedQuoteAuthor.setText("* No Author *"); //Set message to say Quote author could not be obtained
                }


                //If the Quote advanced exists
                if (mSearchQuotesByAdvancedQuote.getCategories() != null){
                    mSearchQuotesByAdvancedQuoteCategories.setText("Categories: " + TextUtils.join(", ", mSearchQuotesByAdvancedQuote.getCategories())); //Set the Quote advanced text
                }
                else{
                    mSearchQuotesByAdvancedQuoteCategories.setText("Categories: *No categories*"); //Set message to say Quote author could not be obtained
                }


                //Try risky task - mSearchQuotesByAdvancedQuoteFavoriteIcon.setButtonDrawable() may throw NullPointerException if it does not exist IF there is no internet connection
                try {

                    //If the Quote is in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAdvancedQuote.getId()) != null) {

                        mSearchQuotesByAdvancedQuote.setFavorite(true); //Set favorite field of the Quote to TRUE
                        mSearchQuotesByAdvancedQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Display the favorite icon to 'active'

                    }
                    //If the Quote is NOT in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAdvancedQuote.getId()) == null) {

                        mSearchQuotesByAdvancedQuote.setFavorite(false); //Set favorite field of the Quote to FALSE
                        mSearchQuotesByAdvancedQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off); //Display the favorite icon to 'inactive'
                    }
                }
                //Catch the NullPointerException - when there are no internet connections
                catch (NullPointerException npe){

                    //Remove visibility of all list item views, except for the view indicating that the quotes are unavailable
                    mSearchQuotesByAdvancedQuoteProgressBar.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuotePositionText.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuoteFavoriteIcon.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuoteShareIcon.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuoteQuote.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuoteAuthor.setVisibility(View.GONE);
                    mSearchQuotesByAdvancedQuoteCategories.setVisibility(View.GONE);

                    mSearchQuotesByAdvancedQuoteUnavailable.setVisibility(View.VISIBLE);
                }



                //Set listener for Quote favorite icon
                mSearchQuotesByAdvancedQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        //Set whether the Quote is to be Favorited based on the boolean parameter (i.e. whether the icon is checked or unchecked)
                        mSearchQuotesByAdvancedQuote.setFavorite(isChecked);

                        //Set drawable button based on the boolean parameter
                        mSearchQuotesByAdvancedQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        //If Favorites icon is CHECKED
                        if (isChecked){

                            //If Quote is NOT in the Favorites SQLite database
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAdvancedQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mSearchQuotesByAdvancedQuote); //Add the Quote to the Favorites SQLite database
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByAdvancedQuote); //Update the SQLite database as per the Quote addition
                            }
                        }
                        //If Favorites icon is UNCHECKED
                        if (!isChecked){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mSearchQuotesByAdvancedQuote); //Delete the Quote from the Favorites SQLite database
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByAdvancedQuote); //Update the SQLite database as per the Quote deletion

                        }
                    }
                });
            }



            //Set listener for Share icon
            mSearchQuotesByAdvancedQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create implicit Intent with send action

                    shareIntent.setType("text/plain"); //Set the type of the Intent to text

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mSearchQuotesByAdvancedQuote.getAuthor()); //Set subject of Intent

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSearchQuotesByAdvancedQuoteShareString()); //Set text of Intent

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Set chooser title

                    startActivity(shareIntent); //Start the Intent
                }
            });


        }



        //Set the Quote sharing text
        private String getSearchQuotesByAdvancedQuoteShareString(){

            String searchQuotesByAdvancedQuoteString = "\"" + mSearchQuotesByAdvancedQuote.getQuote() + "\""; //Quote text body

            String searchQuotesByAdvancedQuoteAuthorString = " - " + mSearchQuotesByAdvancedQuote.getAuthor(); //Quote author

            return searchQuotesByAdvancedQuoteString + searchQuotesByAdvancedQuoteAuthorString; //Concatenate Quote text body with author
        }

    }




    //Override onActivityCreated(..) fragment lifecycle callback method
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated called");
    }




    //Override onStart() fragment lifecycle callback method
    @Override
    public void onStart(){
        super.onStart();

        Log.i(TAG, "onStart called");
    }




    //Override onResume() fragment lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();

        Log.i(TAG, "onResume called");
    }




    //Override onPause() fragment lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();

        Log.i(TAG, "onPause() called");
    }




    //Override onStop() fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();

        Log.i(TAG, "onStop() called");
    }




    //Override onDestroyView() fragment lifecycle callback method
    @Override
    public void onDestroyView(){
        super.onDestroyView();

        Log.i(TAG, "onDestroyView() called");
    }




    //Override onDestroy( fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.i(TAG, "onDestroy() called");
    }




    //Override onDetach() fragment lifecycle callback method
    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDetach() called");
    }

}