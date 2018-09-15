package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SuppressWarnings({"RedundantCast", "FieldCanBeLocal", "deprecation", "ConstantConditions"})

//Fragment for searching Quotes by category
public class SearchQuotesByCategoryFragment extends Fragment implements View.OnClickListener {


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "SQBCFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private static final int NUMBER_OF_QUOTES_TO_LOAD = 10; //Number of quotes to load upon search
    private static String sSearchQuery;
    private TextView mCategorySearchedText;


    //-------------- LIST variables ------------------------------------
    private static List<Quote> sSearchQuotesByCategoryQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //List of Quote objects (of size 12)
    private List<String> mRandomSearchSuggestions; //List of Search Suggestions. They are 10 Strings that are randomly picked from a larger list of Strings
    @SuppressWarnings("CanBeFinal") private List<SearchQuotesByCategoryFragment.GetSearchQuotesByCategoryAsyncTask> mGetSearchQuotesByCategoryAsyncTasksList = Arrays.asList(new SearchQuotesByCategoryFragment.GetSearchQuotesByCategoryAsyncTask[NUMBER_OF_QUOTES_TO_LOAD]); //List of AsyncTass for fetching the Quote (of size 12)


    //-------------- FLAG variables ------------------------------------
    private static boolean shouldDisplaySearchResultsWhenFragmentIsReloaded; //Flag that is activated when a search has begun or the search items are in View.


    //-------------- VIEW variables ------------------------------------
    private LinearLayoutManager mLinearLayoutManager; //LinearLayoutManager for RecyclerView
    private RecyclerView mSearchQuotesByCategoryQuoteRecyclerView; //RecyclerView to store list of Quote items
    private static SearchQuotesByCategoryFragment.SearchQuotesByCategoryAdapter sSearchQuotesByCategoryQuoteAdapter; //RecyclerView Adapter
    private SearchQuotesByCategoryFragment.SearchQuotesByCategoryQuoteViewHolder mSearchQuotesByCategoryQuoteViewHolder; //RecyclerView ViewHolder for storing single Quote items

    private SearchView mSearchView; //Search button in the options menu

    private TextView mSearchSuggestionsText; //Search suggestions text
    private Button mSearchSuggestionsRefresh; //Search suggestions refresh button

    private Button mSearchSuggestionOne;    //Search suggestion #1
    private Button mSearchSuggestionTwo;    //Search suggestion #2
    private Button mSearchSuggestionThree;  //Search suggestion #3
    private Button mSearchSuggestionFour;   //Search suggestion #4
    private Button mSearchSuggestionFive;   //Search suggestion #5
    private Button mSearchSuggestionSix;    //Search suggestion #6
    private Button mSearchSuggestionSeven;  //Search suggestion #7
    private Button mSearchSuggestionEight;  //Search suggestion #8
    private Button mSearchSuggestionNine;   //Search suggestion #9
    private Button mSearchSuggestionTen;    //Search suggestion #10





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

        getActivity().setTitle(getResources().getString(R.string.search_quotes_fragment_title)); //Set title for fragment

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_by_category, viewGroup, false); //Inflate fragment layout


        //Assign View instance variables to their associated resource files
        mCategorySearchedText = (TextView) view.findViewById(R.id.search_quotes_by_category_category_searched);

        mSearchQuotesByCategoryQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_category_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchQuotesByCategoryQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSearchSuggestionsText = (TextView) view.findViewById(R.id.search_quotes_by_category_search_suggestions_text);
        mSearchSuggestionsRefresh = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestions_refresh);

        mSearchSuggestionOne = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_one);
        mSearchSuggestionTwo = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_two);
        mSearchSuggestionThree = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_three);
        mSearchSuggestionFour = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_four);
        mSearchSuggestionFive = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_five);
        mSearchSuggestionSix = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_six);
        mSearchSuggestionSeven = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_seven);
        mSearchSuggestionEight = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_eight);
        mSearchSuggestionNine = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_nine);
        mSearchSuggestionTen = (Button) view.findViewById(R.id.search_quotes_by_category_search_suggestion_ten);



        //Set listener for Search Suggestions Refresh button
        mSearchSuggestionsRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                setUpRandomSearchSuggestions(); //Get a new set of Search Suggestions
            }

        });



        //IF a Search has begun/is in place, then display the RESULT (i.e. search items)
        if (shouldDisplaySearchResultsWhenFragmentIsReloaded) {

            getActivity().invalidateOptionsMenu(); //Update the options menu

            //---------- Configure View variables ----------------------
            mCategorySearchedText.setVisibility(View.VISIBLE);
            mSearchQuotesByCategoryQuoteRecyclerView.setVisibility(View.VISIBLE);

            mSearchSuggestionsText.setVisibility(View.GONE);
            mSearchSuggestionsRefresh.setVisibility(View.GONE);

            mSearchSuggestionOne.setVisibility(View.GONE);
            mSearchSuggestionTwo.setVisibility(View.GONE);
            mSearchSuggestionThree.setVisibility(View.GONE);
            mSearchSuggestionFour.setVisibility(View.GONE);
            mSearchSuggestionFive.setVisibility(View.GONE);
            mSearchSuggestionSix.setVisibility(View.GONE);
            mSearchSuggestionSeven.setVisibility(View.GONE);
            mSearchSuggestionEight.setVisibility(View.GONE);
            mSearchSuggestionNine.setVisibility(View.GONE);
            mSearchSuggestionTen.setVisibility(View.GONE);


            //---------- Configure View variables ----------------------
            mCategorySearchedText.setText(Html.fromHtml("Category searched: " + " " + " \"" +"<i>" + sSearchQuery.toUpperCase() + "</i>" + "\"")); //Text for displaying the Category Searched

            sSearchQuotesByCategoryQuoteAdapter = new SearchQuotesByCategoryFragment.SearchQuotesByCategoryAdapter(sSearchQuotesByCategoryQuotes); //Create a new RecyclerView Adapter
            sSearchQuotesByCategoryQuoteAdapter.setSearchQuotesByCategoryQuotes(sSearchQuotesByCategoryQuotes); //Link the RecyclerView adapter to the List of Quotes from the search result
            mSearchQuotesByCategoryQuoteRecyclerView.setAdapter(sSearchQuotesByCategoryQuoteAdapter); //Set up the RecyclerView to the RecyclerView Adapter
        }

        //If a Search has not begun/is in place, then display the SEARCH SUGGESTIONS
        else{
            //---------- Configure View variables ----------------------
            mCategorySearchedText.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteRecyclerView.setVisibility(View.GONE);

            mSearchSuggestionsText.setVisibility(View.VISIBLE);
            mSearchSuggestionsRefresh.setVisibility(View.VISIBLE);

            mSearchSuggestionOne.setVisibility(View.VISIBLE);
            mSearchSuggestionTwo.setVisibility(View.VISIBLE);
            mSearchSuggestionThree.setVisibility(View.VISIBLE);
            mSearchSuggestionFour.setVisibility(View.VISIBLE);
            mSearchSuggestionFive.setVisibility(View.VISIBLE);
            mSearchSuggestionSix.setVisibility(View.VISIBLE);
            mSearchSuggestionSeven.setVisibility(View.VISIBLE);
            mSearchSuggestionEight.setVisibility(View.VISIBLE);
            mSearchSuggestionNine.setVisibility(View.VISIBLE);
            mSearchSuggestionTen.setVisibility(View.VISIBLE);

            setUpRandomSearchSuggestions(); //Get a new set of Search Suggestions from a List of ALL possible Search Suggestions
        }

        getActivity().invalidateOptionsMenu(); //Result options menu

        return view;
    }




    //Method for setting up the set of Search Suggestions - randomly picked from a List of Strings of ALL possible Search Suggestions
    @SuppressWarnings("UnnecessaryLocalVariable")
    private void setUpRandomSearchSuggestions() {

        //List of all possible Search Suggestions
        List<String> mSearchSuggestionsFullList = Arrays.asList("Inspire", "Action", "Experience", "Life", "Discover", "Self", "Right Way", "Learning-From-Failure",
                "Failure", "Fortune", "Dream", "Vision", "Curiosity", "Greed", "Insight", "Accumulate", "Simplicity", "Understanding", "Business", "Economics", "Finance",
                "Self-Expression", "Books", "Reading", "Diversity", "Equality", "Philosophy", "Wisdom", "Positive-Thinking", "Conservation", "Nature", "Success",
                "Motivation", "Opportunities", "Government", "Truth", "People", "Universe", "Harmony", "Duty", "Culture", "Education", "Soul", "Mind", "Work", "Mind-Body-Spirit",
                "Cosmic", "Self-Improvement", "Money", "Wealth", "Aspirations", "Inspirational", "Beliefs", "Self-Help", "Startup", "Time", "Politics", "Teaching", "Futurism",
                "Poetry", "Confidence", "Trust", "Hope", "Humanity", "Future", "War", "Peace", "Society", "Psychology", "Greatness", "Progress", "Meaning", "Knowledge", "Zen",
                "Meditation", "Reality", "Mindfulness", "Imagination", "Technology", "Science", "Funny", "Comedy", "Humor", "Sarcasm", "Nature");

        //Shuffle the List of al possible Search Suggestions
        Collections.shuffle(mSearchSuggestionsFullList);

        int randomSeriesLength = 10; //Length of the List containing the Search Suggestions

        mRandomSearchSuggestions = mSearchSuggestionsFullList.subList(0, randomSeriesLength); //Get the List of 10 random Search Suggestions

        //Set the Search Suggestion variables  to each of the Search Suggestion Buttons
        mSearchSuggestionOne.setText(mRandomSearchSuggestions.get(0));
        mSearchSuggestionTwo.setText(mRandomSearchSuggestions.get(1));
        mSearchSuggestionThree.setText(mRandomSearchSuggestions.get(2));
        mSearchSuggestionFour.setText(mRandomSearchSuggestions.get(3));
        mSearchSuggestionFive.setText(mRandomSearchSuggestions.get(4));
        mSearchSuggestionSix.setText(mRandomSearchSuggestions.get(5));
        mSearchSuggestionSeven.setText(mRandomSearchSuggestions.get(6));
        mSearchSuggestionEight.setText(mRandomSearchSuggestions.get(7));
        mSearchSuggestionNine.setText(mRandomSearchSuggestions.get(8));
        mSearchSuggestionTen.setText(mRandomSearchSuggestions.get(9));

        //Link to the Search Suggestion buttons to an overriden onClick(..) method
        mSearchSuggestionOne.setOnClickListener(this);
        mSearchSuggestionTwo.setOnClickListener(this);
        mSearchSuggestionThree.setOnClickListener(this);
        mSearchSuggestionFour.setOnClickListener(this);
        mSearchSuggestionFive.setOnClickListener(this);
        mSearchSuggestionSix.setOnClickListener(this);
        mSearchSuggestionSeven.setOnClickListener(this);
        mSearchSuggestionEight.setOnClickListener(this);
        mSearchSuggestionNine.setOnClickListener(this);
        mSearchSuggestionTen.setOnClickListener(this);
    }




    //Override onClick(..) method that this class implements
    @Override
    public void onClick(View view) {

        //When a Search Suggestion button is pressed, set the SearchView query to what it says on the Button
        switch (view.getId()) {

            case R.id.search_quotes_by_category_search_suggestion_one:
                mSearchView.setQuery(mRandomSearchSuggestions.get(0), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_two:
                mSearchView.setQuery(mRandomSearchSuggestions.get(1), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_three:
                mSearchView.setQuery(mRandomSearchSuggestions.get(2), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_four:
                mSearchView.setQuery(mRandomSearchSuggestions.get(3), true);
                break;
            case R.id.search_quotes_by_category_search_suggestion_five:
                mSearchView.setQuery(mRandomSearchSuggestions.get(4), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_six:
                mSearchView.setQuery(mRandomSearchSuggestions.get(5), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_seven:
                mSearchView.setQuery(mRandomSearchSuggestions.get(6), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_eight:
                mSearchView.setQuery(mRandomSearchSuggestions.get(7), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_nine:
                mSearchView.setQuery(mRandomSearchSuggestions.get(8), true);
                break;

            case R.id.search_quotes_by_category_search_suggestion_ten:
                mSearchView.setQuery(mRandomSearchSuggestions.get(9), true);
                break;
        }
    }




    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_category, menu); //Inflate options menu layout


        //---------------- Configure SEARCH menu item -------------------------------------------------------------------------------
        MenuItem searchItem = menu.findItem(R.id.menu_item_search_quotes_by_category_fragment_search); //Menu item for "Search"

        mSearchView = (SearchView) searchItem.getActionView(); //Assign SearchView instance variable to this menu item
        mSearchView.setQueryHint(getResources().getString(R.string.search_quotes_by_category_search_view_hint)); //Set hint to SearchView


        //Set listeners to the query Text of the SearchView
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //When SearchView query text is changed
            @Override
            public boolean onQueryTextChange(String searchQuery) {
                Log.d(TAG, searchQuery); //Log to Logcat

                //Return a boolean:
                //true: If this method has been handled
                //false: If this method has not been handled.
                //In this case, we only logged an event, so it doesn't count as a 'handle'
                return false;
            }

            //When SearchView query text is submitted
            @Override
            public boolean onQueryTextSubmit(String searchQuery){
                Log.d(TAG, "Submitted query: " + searchQuery);

                sSearchQuery = searchQuery.replaceAll("( +)","-").trim(); //Replace all space characters (either single or multiple spaces) to a single hyphen character in the search query

                SearchQuotesByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), sSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                mSearchView.onActionViewCollapsed(); //Collapse the SearchView

                shouldDisplaySearchResultsWhenFragmentIsReloaded = true; //Toggle flag to display the search results to TRUE

                getActivity().invalidateOptionsMenu(); //Update the options menu


                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the category query


                mCategorySearchedText.setVisibility(View.VISIBLE); //Show the "Searched Text" TextView
                mCategorySearchedText.setText(Html.fromHtml("Category searched: " + " " + " \"" +"<i>" +  sSearchQuery.toUpperCase() + "</i>" + "\"")); //Update the "Searched Text" text


                mSearchQuotesByCategoryQuoteRecyclerView.setVisibility(View.VISIBLE); //Show the RecyclerView
                sSearchQuotesByCategoryQuotes = null; //Nullify the List of all Quotes if they already point to an existing set from the previous search
                sSearchQuotesByCategoryQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //Re-initialise the size of the List of all Quotes
                sSearchQuotesByCategoryQuoteAdapter = new SearchQuotesByCategoryFragment.SearchQuotesByCategoryAdapter(sSearchQuotesByCategoryQuotes); //Create a new RecyclerView Adapter
                sSearchQuotesByCategoryQuoteAdapter.setSearchQuotesByCategoryQuotes(sSearchQuotesByCategoryQuotes);  //Link the RecyclerView adapter to the List of Quotes from the search result
                sSearchQuotesByCategoryQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
                mSearchQuotesByCategoryQuoteRecyclerView.setAdapter(sSearchQuotesByCategoryQuoteAdapter); //Set the RecyclerView to the new RecyclerView Adapter


                //Begin a new set of AsyncTasks to fetch a new set of Quotes from the search query
                for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

                    Integer quotePosition = i; //Let the quote position equal the index

                    mGetSearchQuotesByCategoryAsyncTasksList.set(i, new SearchQuotesByCategoryFragment.GetSearchQuotesByCategoryAsyncTask(sSearchQuery)); //Assign the AsyncTask reference in the List to a new AsyncTask object
                    mGetSearchQuotesByCategoryAsyncTasksList.get(i).execute(quotePosition); //Execute the AsyncTask object (i.e. begin fetching the Quote)
                }

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;
            }
        });


        //Set listener to the SearchView - make it load/retrieve the search query in the SharedPreferences when pressed on
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String searchQuery = SearchQuotesByCategorySharedPref.getSearchQuotesByCategoryStoredQuery(getActivity()); //Load/retrieve search query from SharedPreferences
                mSearchView.setQuery(searchQuery, false); //Set the search query to the SearchView, but not load it yet
            }
        });


        //---------------- Configure CLEAR ALL menu item -------------------------------------------------------------------------------
        final MenuItem clearAllItem = menu.findItem(R.id.menu_item_search_quotes_by_category_fragment_clear_all); //Menu item for "Clear All"

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

            //"SEARCH" menu item
            case (R.id.menu_item_search_quotes_by_category_fragment_search):
                //Do nothing. All covered in onCreateOptionsMenu(..) method
                return true;

            //"CLEAR ALL" menu item
            case (R.id.menu_item_search_quotes_by_category_fragment_clear_all):

                shouldDisplaySearchResultsWhenFragmentIsReloaded = false; //Toggle flag to display the search results to FALSE

                getActivity().invalidateOptionsMenu(); //Update options menu - i.e. call onCreateOptionsMenu() in order to make the "CLEAR ALL" menu item disappear

                //Configure visibilities of each of the Views
                mCategorySearchedText.setVisibility(View.GONE);
                mSearchQuotesByCategoryQuoteRecyclerView.setVisibility(View.GONE);

                mSearchSuggestionsText.setVisibility(View.VISIBLE);
                mSearchSuggestionsRefresh.setVisibility(View.VISIBLE);

                mSearchSuggestionOne.setVisibility(View.VISIBLE);
                mSearchSuggestionTwo.setVisibility(View.VISIBLE);
                mSearchSuggestionThree.setVisibility(View.VISIBLE);
                mSearchSuggestionFour.setVisibility(View.VISIBLE);
                mSearchSuggestionFive.setVisibility(View.VISIBLE);
                mSearchSuggestionSix.setVisibility(View.VISIBLE);
                mSearchSuggestionSeven.setVisibility(View.VISIBLE);
                mSearchSuggestionEight.setVisibility(View.VISIBLE);
                mSearchSuggestionNine.setVisibility(View.VISIBLE);
                mSearchSuggestionTen.setVisibility(View.VISIBLE);

                setUpRandomSearchSuggestions(); //Get a new set of Search Suggestions from a List of ALL possible Search Suggestions, now they are to be displayed

                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the category query

                Toast.makeText(getActivity(), "Cleared Search Results for: " + sSearchQuery.toUpperCase(), Toast.LENGTH_LONG).show(); //Create Toast notifying that the search results have been cleared

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }

    }




    //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the category query
    private void cancelAllCurrentAsyncTasks(){

        if (!mGetSearchQuotesByCategoryAsyncTasksList.isEmpty()) {

            for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
                //Try risky task - mGetSearchQuotesByCategoryAsyncTasksList.get(i).cancel() may throw a NullPointerException if the AsyncTask is not running
                try{
                    mGetSearchQuotesByCategoryAsyncTasksList.get(i).cancel(true);
                }
                catch (NullPointerException npe){
                    Log.e(TAG, "GetSearchQuotesByCategoryAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
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
    private class GetSearchQuotesByCategoryAsyncTask extends AsyncTask<Integer, Void, Quote> {

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Integer mQuotePosition[]; //NOTE: mQuotePosition is an ARRAY of type Integer
        final String mSearchQuery; //Search query


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        GetSearchQuotesByCategoryAsyncTask(String searchQuery) {
            mSearchQuery = searchQuery; //Stash search query to the instance variable (mSearchQuery)
        }



        //Perform the main work of the asynchronous task - fetch the Quote
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition; //Stash the Quote position to the instance variable (mQuotePosition)

            Quote searchQuotesByCategoryQuote = new GetSearchQuotesByCategoryQuote().getSearchQuotesByCategoryQuote(mSearchQuery); //Fetch the Quote based on the search query

            return searchQuotesByCategoryQuote; //Return the fetched Quote - to be passed to onPostExecute()
        }



        //Perform work after the Quote is fetched - save the fetched Quote to the List of Quotes
        @Override
        protected void onPostExecute(Quote searchQuotesByCategoryQuote) {

            Log.i(TAG, "Search Quote by Category Quotes - method - Quote String: " + searchQuotesByCategoryQuote.getQuote());
            Log.i(TAG, "Search Quote by Category Quotes - method  - Category: " + searchQuotesByCategoryQuote.getCategory());
            Log.i(TAG, "Search Quote by Category Quotes - method  - Author: " + searchQuotesByCategoryQuote.getAuthor());
            Log.i(TAG, "Search Quote by Category Quotes - method - ID: " + searchQuotesByCategoryQuote.getId());


            searchQuotesByCategoryQuote.setSearchQuotesByCategoryQuotePosition(mQuotePosition[0] + 1); //Set the position member variable of the fetched Quote

            sSearchQuotesByCategoryQuotes.set(mQuotePosition[0], searchQuotesByCategoryQuote); //Save the fetched Quote to the list of Quotes, to the index in this list based on the quote position

            sSearchQuotesByCategoryQuoteAdapter.setSearchQuotesByCategoryQuotes(sSearchQuotesByCategoryQuotes); //Set the RecyclerView Adapter to the newly updated List of Quotes

            sSearchQuotesByCategoryQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
        }

    }




    //RecyclerView Adapter
    private class SearchQuotesByCategoryAdapter extends RecyclerView.Adapter<SearchQuotesByCategoryFragment.SearchQuotesByCategoryQuoteViewHolder>{

        //Constructor
        SearchQuotesByCategoryAdapter(List<Quote> searchQuotesByCategoryQuotes){
            sSearchQuotesByCategoryQuotes = searchQuotesByCategoryQuotes; //Stash passed List of Quotes to the instance variable (sSearchQuotesByCategoryQuotes)
        }



        //Get count of the size of the List of Quotes
        @Override
        public int getItemCount(){
            return sSearchQuotesByCategoryQuotes.size();
        }



        //Create the RecyclerView ViewHolder
        @SuppressWarnings("NullableProblems")
        @Override
        public SearchQuotesByCategoryFragment.SearchQuotesByCategoryQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater

            View listItemView = layoutInflater.inflate(R.layout.list_item_search_quotes_by_category, viewGroup, false); //Obtain list-item view

            mSearchQuotesByCategoryQuoteViewHolder = new SearchQuotesByCategoryFragment.SearchQuotesByCategoryQuoteViewHolder(listItemView); //Send list-item view to the RecyclerView ViewHolder

            return mSearchQuotesByCategoryQuoteViewHolder; //Return RecyclerView ViewHolder
        }



        //Bind RecyclerView ViewHolder to the data of the Quote
        @SuppressWarnings("NullableProblems")
        @Override
        public void onBindViewHolder(SearchQuotesByCategoryFragment.SearchQuotesByCategoryQuoteViewHolder searchQuotesByCategoryQuoteViewHolder, int position){

            Quote searchQuotesByCategoryQuote = sSearchQuotesByCategoryQuotes.get(position); //Get the Quote

            searchQuotesByCategoryQuoteViewHolder.bind(searchQuotesByCategoryQuote); //Bind RecyclerView ViewHolder to the data of the Quote
        }



        //Stash the List of Quotes to the instance variable
        void setSearchQuotesByCategoryQuotes(List<Quote> searchQuotesByCategoryQuotes){
            sSearchQuotesByCategoryQuotes = searchQuotesByCategoryQuotes;
        }

    }




    //RecyclerView ViewHolder
    @SuppressWarnings("ConstantConditions")
    private class SearchQuotesByCategoryQuoteViewHolder extends RecyclerView.ViewHolder{

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Quote mSearchQuotesByCategoryQuote; //Quote - contains data for the list item

        //-------------- VIEW variables of the list item ------------------------------------
        final LinearLayout mSearchQuotesByCategoryQuoteBubbleLayout;
        final TextView mSearchQuotesByCategoryQuotePositionText;
        final CheckBox mSearchQuotesByCategoryQuoteFavoriteIcon;
        final Button mSearchQuotesByCategoryQuoteShareIcon;
        final ProgressBar mSearchQuotesByCategoryQuoteProgressBar;
        final TextView mSearchQuotesByCategoryQuoteUnavailable;
        final TextView mSearchQuotesByCategoryQuoteQuote;
        final TextView mSearchQuotesByCategoryQuoteAuthor;
        final TextView mSearchQuotesByCategoryQuoteCategories;


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        SearchQuotesByCategoryQuoteViewHolder(View view){
            super(view);

            //Assign list item view instance variables to their associated resource files
            mSearchQuotesByCategoryQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.search_quotes_by_category_quote_bubble_layout);
            mSearchQuotesByCategoryQuotePositionText = (TextView) view.findViewById(R.id.search_quotes_by_category_quote_position);
            mSearchQuotesByCategoryQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.search_quotes_by_category_quote_favorite_icon);
            mSearchQuotesByCategoryQuoteShareIcon = (Button) view.findViewById(R.id.search_quotes_by_category_quote_share_icon);
            mSearchQuotesByCategoryQuoteProgressBar = (ProgressBar) view.findViewById(R.id.search_quotes_by_category_quote_progress_bar);
            mSearchQuotesByCategoryQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_category_quote_unavailable);
            mSearchQuotesByCategoryQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_category_quote_quote);
            mSearchQuotesByCategoryQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_category_quote_author);
            mSearchQuotesByCategoryQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_category_quote_categories);

            mSearchQuotesByCategoryQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges)); //Set background drawable for each list item

            //Set visibilities of each of the list item views - set all list item views to GONE (except for the ProgressBar) as they have only just been created
            mSearchQuotesByCategoryQuoteProgressBar.setVisibility(View.VISIBLE);
            mSearchQuotesByCategoryQuotePositionText.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteShareIcon.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteUnavailable.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteQuote.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteAuthor.setVisibility(View.GONE);
            mSearchQuotesByCategoryQuoteCategories.setVisibility(View.GONE);
        }



        //Bind the RecyclerView ViewHolder to the data from the Quote
        @SuppressWarnings("WeakerAccess")
        @SuppressLint("SetTextI18n")
        public void bind(Quote searchQuotesByCategoryQuote){

            //If the Quote object exists
            if (searchQuotesByCategoryQuote != null){

                mSearchQuotesByCategoryQuote = searchQuotesByCategoryQuote; //Stash passed Quote parameter to instance variable

                //Configure visibilities of the list item views
                mSearchQuotesByCategoryQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByCategoryQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByCategoryQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByCategoryQuoteCategories.setVisibility(View.VISIBLE);


                mSearchQuotesByCategoryQuotePositionText.setText("Quote #" + mSearchQuotesByCategoryQuote.getSearchQuotesByCategoryQuotePosition()); //Set the text of the position of the Quote

                //Set drawable for Quote favorite icon, based on whether it is Favorited or not
                mSearchQuotesByCategoryQuoteFavoriteIcon.setButtonDrawable(mSearchQuotesByCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //If the Quote text exists
                if (mSearchQuotesByCategoryQuote.getQuote() != null){
                    mSearchQuotesByCategoryQuoteQuote.setText("\" " + searchQuotesByCategoryQuote.getQuote() + " \""); //Set the Quote text
                }
                //If the Quote text DOES NOT exist
                else{
                    mSearchQuotesByCategoryQuoteQuote.setText("* No Quote to View *"); //Set message to say Quote text could not be obtained
                }


                //If the Quote author exists
                if (mSearchQuotesByCategoryQuote.getAuthor() != null){
                    mSearchQuotesByCategoryQuoteAuthor.setText("- " + mSearchQuotesByCategoryQuote.getAuthor()); //Set the Quote author text
                }
                //If the Quote author DOES NOT exist
                else{
                    mSearchQuotesByCategoryQuoteAuthor.setText("* No Author *"); //Set message to say Quote author could not be obtained
                }


                //If the Quote category exists
                if (mSearchQuotesByCategoryQuote.getCategories() != null){
                    mSearchQuotesByCategoryQuoteCategories.setText("Categories: " + TextUtils.join(", ", mSearchQuotesByCategoryQuote.getCategories())); //Set the Quote category text
                }
                else{
                    mSearchQuotesByCategoryQuoteCategories.setText("Categories: *No categories*"); //Set message to say Quote author could not be obtained
                }


                //Try risky task - mSearchQuotesByCategoryQuoteFavoriteIcon.setButtonDrawable() may throw NullPointerException if it does not exist IF there is no internet connection
                try {

                    //If the Quote is in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByCategoryQuote.getId()) != null) {

                        mSearchQuotesByCategoryQuote.setFavorite(true); //Set favorite field of the Quote to TRUE
                        mSearchQuotesByCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Display the favorite icon to 'active'

                    }
                    //If the Quote is NOT in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByCategoryQuote.getId()) == null) {

                        mSearchQuotesByCategoryQuote.setFavorite(false); //Set favorite field of the Quote to FALSE
                        mSearchQuotesByCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off); //Display the favorite icon to 'inactive'
                    }
                }
                //Catch the NullPointerException - when there are no internet connections
                catch (NullPointerException npe){

                    //Remove visibility of all list item views, except for the view indicating that the quotes are unavailable
                    mSearchQuotesByCategoryQuoteProgressBar.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuotePositionText.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuoteShareIcon.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuoteQuote.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuoteAuthor.setVisibility(View.GONE);
                    mSearchQuotesByCategoryQuoteCategories.setVisibility(View.GONE);

                    mSearchQuotesByCategoryQuoteUnavailable.setVisibility(View.VISIBLE);
                }



                //Set listener for Quote favorite icon
                mSearchQuotesByCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        //Set whether the Quote is to be Favorited based on the boolean parameter (i.e. whether the icon is checked or unchecked)
                        mSearchQuotesByCategoryQuote.setFavorite(isChecked);

                        //Set drawable button based on the boolean parameter
                        mSearchQuotesByCategoryQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        //If Favorites icon is CHECKED
                        if (isChecked){

                            //If Quote is NOT in the Favorites SQLite database
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByCategoryQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mSearchQuotesByCategoryQuote); //Add the Quote to the Favorites SQLite database
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByCategoryQuote); //Update the SQLite database as per the Quote addition
                            }
                        }
                        //If Favorites icon is UNCHECKED
                        if (!isChecked){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mSearchQuotesByCategoryQuote); //Delete the Quote from the Favorites SQLite database
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByCategoryQuote); //Update the SQLite database as per the Quote deletion

                        }
                    }
                });
            }



            //Set listener for Share icon
            mSearchQuotesByCategoryQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create implicit Intent with send action

                    shareIntent.setType("text/plain"); //Set the type of the Intent to text

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mSearchQuotesByCategoryQuote.getAuthor()); //Set subject of Intent

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSearchQuotesByCategoryQuoteShareString()); //Set text of Intent

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Set chooser title

                    startActivity(shareIntent); //Start the Intent
                }
            });


        }



        //Set the Quote sharing text
        private String getSearchQuotesByCategoryQuoteShareString(){

            String searchQuotesByCategoryQuoteString = "\"" + mSearchQuotesByCategoryQuote.getQuote() + "\""; //Quote text body

            String searchQuotesByCategoryQuoteAuthorString = " - " + mSearchQuotesByCategoryQuote.getAuthor(); //Quote author

            return searchQuotesByCategoryQuoteString + searchQuotesByCategoryQuoteAuthorString; //Concatenate Quote text body with author
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