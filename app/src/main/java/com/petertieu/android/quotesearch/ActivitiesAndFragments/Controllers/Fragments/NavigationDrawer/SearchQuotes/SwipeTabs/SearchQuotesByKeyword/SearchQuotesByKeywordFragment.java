package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

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

//Fragment for searching Quotes by keyword OR phrase
public class SearchQuotesByKeywordFragment extends Fragment implements View.OnClickListener {



    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "SQBKFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private static final int NUMBER_OF_QUOTES_TO_LOAD = 10; //Number of quotes to load upon search
    private static String sSearchQuery;
    private TextView mKeywordSearchedText;


    //-------------- LIST variables ------------------------------------
    private static List<Quote> sSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //List of Quote objects (of size 12)
    private List<String> mRandomSearchSuggestions; //List of Search Suggestions. They are 10 Strings that are randomly picked from a larger list of Strings
    @SuppressWarnings("CanBeFinal") private List<GetSearchQuotesByKeywordAsyncTask> mGetSearchQuotesByKeywordAsyncTasksList = Arrays.asList(new GetSearchQuotesByKeywordAsyncTask[NUMBER_OF_QUOTES_TO_LOAD]); //List of AsyncTass for fetching the Quote (of size 12)


    //-------------- FLAG variables ------------------------------------
    private static boolean shouldDisplaySearchResultsWhenFragmentIsReloaded; //Flag that is activated when a search has begun or the search items are in View.


    //-------------- VIEW variables ------------------------------------
    private LinearLayoutManager mLinearLayoutManager; //LinearLayoutManager for RecyclerView
    private RecyclerView mSearchQuotesByKeywordQuoteRecyclerView; //RecyclerView to store list of Quote items
    private static SearchQuotesByKeywordAdapter sSearchQuotesByKeywordQuoteAdapter; //RecyclerView Adapter
    private SearchQuotesByKeywordQuoteViewHolder mSearchQuotesByKeywordQuoteViewHolder; //RecyclerView ViewHolder for storing single Quote items

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

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_by_keyword, viewGroup, false); //Inflate fragment layout


        //Assign View instance variables to their associated resource files
        mKeywordSearchedText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_keyword_searched);

        mSearchQuotesByKeywordQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_keyword_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchQuotesByKeywordQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSearchSuggestionsText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_search_suggestions_text);
        mSearchSuggestionsRefresh = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestions_refresh);

        mSearchSuggestionOne = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_one);
        mSearchSuggestionTwo = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_two);
        mSearchSuggestionThree = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_three);
        mSearchSuggestionFour = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_four);
        mSearchSuggestionFive = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_five);
        mSearchSuggestionSix = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_six);
        mSearchSuggestionSeven = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_seven);
        mSearchSuggestionEight = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_eight);
        mSearchSuggestionNine = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_nine);
        mSearchSuggestionTen = (Button) view.findViewById(R.id.search_quotes_by_keyword_search_suggestion_ten);



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
            mKeywordSearchedText.setVisibility(View.VISIBLE);
            mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.VISIBLE);

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
            mKeywordSearchedText.setText(Html.fromHtml("Keyword searched: " + " " + " \"" +"<i>" + sSearchQuery.toUpperCase() + "</i>" + "\"")); //Text for displaying the Keyword Searched

            sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes); //Create a new RecyclerView Adapter
            sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes); //Link the RecyclerView adapter to the List of Quotes from the search result
            mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter); //Set up the RecyclerView to the RecyclerView Adapter
        }

        //If a Search has not begun/is in place, then display the SEARCH SUGGESTIONS
        else{
            //---------- Configure View variables ----------------------
            mKeywordSearchedText.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.GONE);

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
        List<String> mSearchSuggestionsFullList = Arrays.asList("Dog", "Cat", "Power", "Strong", "Imagination", "Strong",
                "Past", "Present", "Man", "Woman", "House", "Luck", "Weak", "Water", "Metal", "Fire", "Random", "Events", "Politics",
                "Fast", "Slow", "Learn", "Teach", "Student", "Humanity", "Fortune", "Rich", "Food", "Famine", "Hot", "Cold",
                "Small", "Term", "Long", "Understand", "Wise", "Coward", "Forgive", "Happy", "Sad", "Emotion", "Ambition", "Rain",
                "Mental", "Artist", "Expert", "Universe", "Give", "More", "Movement", "Healthy", "Culture", "Heart", "Amazing",
                "Remarkable", "Interest", "Desire", "Time", "Sun", "Infinity", "Human", "Robot", "Machine", "Society", "Anthropology",
                "Cog", "Wall", "Divide", "Unity", "Wheel", "Invent", "Normal", "Strange", "Weird", "Adult", "Children", "Future",
                "Conquer", "Loss", "Success", "Defeat", "Win", "Lose", "Fly", "Run", "Walk", "Courage", "Brave", "King", "Queen",
                "Royal", "Equality", "Greed", "Commitment", "Spirituality", "War", "Peace", "Utopia", "Seek", "Friend", "Evolution",
                "Friendship", "Happiness", "Love", "Fear", "Good", "Connect", "Family", "Relationship", "Education", "Sentience", "Character",
                "Empathy", "Sadness", "Loneliness", "Joy", "Castle", "Humility", "World", "History", "Change", "Experience", "Perspective",
                "Money", "Values", "Patience", "Mind", "Body", "Spirit", "Reality", "Illusion", "Dream", "Life", "Wealth", "Luck", "Business",
                "Coffee", "Spring", "Summer", "Autumn", "Winter", "Young", "Old", "Generation", "Baby-boomers", "Lust", "Perfection",
                "Gratitude", "Create", "Art", "Fitness", "Milkyway", "Food", "Morals", "Memories", "Moon", "Reflection", "Machine Learning", "Cosmos",
                "Technology", "Morality");

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

            case R.id.search_quotes_by_keyword_search_suggestion_one:
                mSearchView.setQuery(mRandomSearchSuggestions.get(0), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_two:
                mSearchView.setQuery(mRandomSearchSuggestions.get(1), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_three:
                mSearchView.setQuery(mRandomSearchSuggestions.get(2), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_four:
                mSearchView.setQuery(mRandomSearchSuggestions.get(3), true);
                break;
            case R.id.search_quotes_by_keyword_search_suggestion_five:
                mSearchView.setQuery(mRandomSearchSuggestions.get(4), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_six:
                mSearchView.setQuery(mRandomSearchSuggestions.get(5), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_seven:
                mSearchView.setQuery(mRandomSearchSuggestions.get(6), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_eight:
                mSearchView.setQuery(mRandomSearchSuggestions.get(7), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_nine:
                mSearchView.setQuery(mRandomSearchSuggestions.get(8), true);
                break;

            case R.id.search_quotes_by_keyword_search_suggestion_ten:
                mSearchView.setQuery(mRandomSearchSuggestions.get(9), true);
                break;
        }
    }




    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_keyword, menu); //Inflate options menu layout


        //---------------- Configure SEARCH menu item -------------------------------------------------------------------------------
        MenuItem searchItem = menu.findItem(R.id.menu_item_search_quotes_by_keyword_fragment_search); //Menu item for "Search"

        mSearchView = (SearchView) searchItem.getActionView(); //Assign SearchView instance variable to this menu item
        mSearchView.setQueryHint(getResources().getString(R.string.search_quotes_by_keyword_search_view_hint)); //Set hint to SearchView


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

                sSearchQuery = searchQuery; //Assign static instance variable to the local variable

                SearchQuotesByKeywordSharedPref.setSearchQuotesByKeywordStoredQuery(getActivity(), searchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                mSearchView.onActionViewCollapsed(); //Collapse the SearchView

                shouldDisplaySearchResultsWhenFragmentIsReloaded = true; //Toggle flag to display the search results to TRUE

                getActivity().invalidateOptionsMenu(); //Update the options menu


                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the keyword query


                mKeywordSearchedText.setVisibility(View.VISIBLE); //Show the "Searched Text" TextView
                mKeywordSearchedText.setText(Html.fromHtml("Keyword searched: " + " " + " \"" +"<i>" +  searchQuery.toUpperCase() + "</i>" + "\"")); //Update the "Searched Text" text


                mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.VISIBLE); //Show the RecyclerView
                sSearchQuotesByKeywordQuotes = null; //Nullify the List of all Quotes if they already point to an existing set from the previous search
                sSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //Re-initialise the size of the List of all Quotes
                sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes); //Create a new RecyclerView Adapter
                sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);  //Link the RecyclerView adapter to the List of Quotes from the search result
                sSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
                mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter); //Set the RecyclerView to the new RecyclerView Adapter


                //Begin a new set of AsyncTasks to fetch a new set of Quotes from the search query
                for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

                    Integer quotePosition = i; //Let the quote position equal the index

                    mGetSearchQuotesByKeywordAsyncTasksList.set(i, new GetSearchQuotesByKeywordAsyncTask(searchQuery)); //Assign the AsyncTask reference in the List to a new AsyncTask object
                    mGetSearchQuotesByKeywordAsyncTasksList.get(i).execute(quotePosition); //Execute the AsyncTask object (i.e. begin fetching the Quote)
                }

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;
            }
        });


        //Set listener to the SearchView - make it load/retrieve the search query in the SharedPreferences when pressed on
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String searchQuery = SearchQuotesByKeywordSharedPref.getSearchQuotesByKeywordStoredQuery(getActivity()); //Load/retrieve search query from SharedPreferences
                mSearchView.setQuery(searchQuery, false); //Set the search query to the SearchView, but not load it yet
            }
        });


        //---------------- Configure CLEAR ALL menu item -------------------------------------------------------------------------------
        final MenuItem clearAllItem = menu.findItem(R.id.menu_item_search_quotes_by_keyword_fragment_clear_all); //Menu item for "Clear All"

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
            case (R.id.menu_item_search_quotes_by_keyword_fragment_search):
                //Do nothing. All covered in onCreateOptionsMenu(..) method
                return true;

            //"CLEAR ALL" menu item
            case (R.id.menu_item_search_quotes_by_keyword_fragment_clear_all):

                shouldDisplaySearchResultsWhenFragmentIsReloaded = false; //Toggle flag to display the search results to FALSE

                getActivity().invalidateOptionsMenu(); //Update options menu - i.e. call onCreateOptionsMenu() in order to make the "CLEAR ALL" menu item disappear

                //Configure visibilities of each of the Views
                mKeywordSearchedText.setVisibility(View.GONE);
                mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.GONE);

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

                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the keyword query

                Toast.makeText(getActivity(), "Cleared Search Results for: " + sSearchQuery.toUpperCase(), Toast.LENGTH_LONG).show(); //Create Toast notifying that the search results have been cleared

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }

    }




    //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the keyword query
    private void cancelAllCurrentAsyncTasks(){

        if (!mGetSearchQuotesByKeywordAsyncTasksList.isEmpty()) {

            for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
                //Try risky task - mGetSearchQuotesByKeywordAsyncTasksList.get(i).cancel() may throw a NullPointerException if the AsyncTask is not running
                try{
                    mGetSearchQuotesByKeywordAsyncTasksList.get(i).cancel(true);
                }
                catch (NullPointerException npe){
                    Log.e(TAG, "GetSearchQuotesByKeywordAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
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
    private class GetSearchQuotesByKeywordAsyncTask extends AsyncTask<Integer, Void, Quote> {

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Integer mQuotePosition[]; //NOTE: mQuotePosition is an ARRAY of type Integer
        final String mSearchQuery; //Search query


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        GetSearchQuotesByKeywordAsyncTask(String searchQuery) {
            mSearchQuery = searchQuery; //Stash search query to the instance variable (mSearchQuery)
        }



        //Perform the main work of the asynchronous task - fetch the Quote
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition; //Stash the Quote position to the instance variable (mQuotePosition)

            Quote searchQuotesByKeywordQuote = new GetSearchQuotesByKeywordQuote().getSearchQuotesByKeywordQuote(mSearchQuery); //Fetch the Quote based on the search query

            return searchQuotesByKeywordQuote; //Return the fetched Quote - to be passed to onPostExecute()
        }



        //Perform work after the Quote is fetched - save the fetched Quote to the List of Quotes
        @Override
        protected void onPostExecute(Quote searchQuotesByKeywordQuote) {

            Log.i(TAG, "Search Quote by Keyword Quotes - method - Quote String: " + searchQuotesByKeywordQuote.getQuote());
            Log.i(TAG, "Search Quote by Keyword Quotes - method  - Category: " + searchQuotesByKeywordQuote.getCategory());
            Log.i(TAG, "Search Quote by Keyword Quotes - method  - Author: " + searchQuotesByKeywordQuote.getAuthor());
            Log.i(TAG, "Search Quote by Keyword Quotes - method - ID: " + searchQuotesByKeywordQuote.getId());


            searchQuotesByKeywordQuote.setSearchQuotesByKeywordQuotePosition(mQuotePosition[0] + 1); //Set the position member variable of the fetched Quote

            sSearchQuotesByKeywordQuotes.set(mQuotePosition[0], searchQuotesByKeywordQuote); //Save the fetched Quote to the list of Quotes, to the index in this list based on the quote position

            sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes); //Set the RecyclerView Adapter to the newly updated List of Quotes

            sSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
        }

    }




    //RecyclerView Adapter
    private class SearchQuotesByKeywordAdapter extends RecyclerView.Adapter<SearchQuotesByKeywordQuoteViewHolder>{

        //Constructor
        SearchQuotesByKeywordAdapter(List<Quote> searchQuotesByKeywordQuotes){
            sSearchQuotesByKeywordQuotes = searchQuotesByKeywordQuotes; //Stash passed List of Quotes to the instance variable (sSearchQuotesByKeywordQuotes)
        }



        //Get count of the size of the List of Quotes
        @Override
        public int getItemCount(){
            return sSearchQuotesByKeywordQuotes.size();
        }



        //Create the RecyclerView ViewHolder
        @SuppressWarnings("NullableProblems")
        @Override
        public SearchQuotesByKeywordQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater

            View listItemView = layoutInflater.inflate(R.layout.list_item_search_quotes_by_keyword, viewGroup, false); //Obtain list-item view

            mSearchQuotesByKeywordQuoteViewHolder = new SearchQuotesByKeywordQuoteViewHolder(listItemView); //Send list-item view to the RecyclerView ViewHolder

            return mSearchQuotesByKeywordQuoteViewHolder; //Return RecyclerView ViewHolder
        }



        //Bind RecyclerView ViewHolder to the data of the Quote
        @SuppressWarnings("NullableProblems")
        @Override
        public void onBindViewHolder(SearchQuotesByKeywordQuoteViewHolder searchQuotesByKeywordQuoteViewHolder, int position){

            Quote searchQuotesByKeywordQuote = sSearchQuotesByKeywordQuotes.get(position); //Get the Quote

            searchQuotesByKeywordQuoteViewHolder.bind(searchQuotesByKeywordQuote); //Bind RecyclerView ViewHolder to the data of the Quote
        }



        //Stash the List of Quotes to the instance variable
        void setSearchQuotesByKeywordQuotes(List<Quote> searchQuotesByKeywordQuotes){
            sSearchQuotesByKeywordQuotes = searchQuotesByKeywordQuotes;
        }

    }




    //RecyclerView ViewHolder
    @SuppressWarnings("ConstantConditions")
    private class SearchQuotesByKeywordQuoteViewHolder extends RecyclerView.ViewHolder{

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Quote mSearchQuotesByKeywordQuote; //Quote - contains data for the list item

        //-------------- VIEW variables of the list item ------------------------------------
        final LinearLayout mSearchQuotesByKeywordQuoteBubbleLayout;
        final TextView mSearchQuotesByKeywordQuotePositionText;
        final CheckBox mSearchQuotesByKeywordQuoteFavoriteIcon;
        final Button mSearchQuotesByKeywordQuoteShareIcon;
        final ProgressBar mSearchQuotesByKeywordQuoteProgressBar;
        final TextView mSearchQuotesByKeywordQuoteUnavailable;
        final TextView mSearchQuotesByKeywordQuoteQuote;
        final TextView mSearchQuotesByKeywordQuoteAuthor;
        final TextView mSearchQuotesByKeywordQuoteCategories;


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        SearchQuotesByKeywordQuoteViewHolder(View view){
            super(view);

            //Assign list item view instance variables to their associated resource files
            mSearchQuotesByKeywordQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.search_quotes_by_keyword_quote_bubble_layout);
            mSearchQuotesByKeywordQuotePositionText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_position);
            mSearchQuotesByKeywordQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.search_quotes_by_keyword_quote_favorite_icon);
            mSearchQuotesByKeywordQuoteShareIcon = (Button) view.findViewById(R.id.search_quotes_by_keyword_quote_share_icon);
            mSearchQuotesByKeywordQuoteProgressBar = (ProgressBar) view.findViewById(R.id.search_quotes_by_keyword_quote_progress_bar);
            mSearchQuotesByKeywordQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_unavailable);
            mSearchQuotesByKeywordQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_quote);
            mSearchQuotesByKeywordQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_author);
            mSearchQuotesByKeywordQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_keyword_quote_categories);

            mSearchQuotesByKeywordQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges)); //Set background drawable for each list item

            //Set visibilities of each of the list item views - set all list item views to GONE (except for the ProgressBar) as they have only just been created
            mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.VISIBLE);
            mSearchQuotesByKeywordQuotePositionText.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteQuote.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteCategories.setVisibility(View.GONE);
        }



        //Bind the RecyclerView ViewHolder to the data from the Quote
        @SuppressWarnings("WeakerAccess")
        @SuppressLint("SetTextI18n")
        public void bind(Quote searchQuotesByKeywordQuote){

            //If the Quote object exists
            if (searchQuotesByKeywordQuote != null){

                mSearchQuotesByKeywordQuote = searchQuotesByKeywordQuote; //Stash passed Quote parameter to instance variable

                //Configure visibilities of the list item views
                mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByKeywordQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByKeywordQuoteCategories.setVisibility(View.VISIBLE);


                mSearchQuotesByKeywordQuotePositionText.setText("Quote #" + mSearchQuotesByKeywordQuote.getSearchQuotesByKeywordQuotePosition()); //Set the text of the position of the Quote

                //Set drawable for Quote favorite icon, based on whether it is Favorited or not
                mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(mSearchQuotesByKeywordQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //If the Quote text exists
                if (mSearchQuotesByKeywordQuote.getQuote() != null){
                    mSearchQuotesByKeywordQuoteQuote.setText("\" " + searchQuotesByKeywordQuote.getQuote() + " \""); //Set the Quote text
                }
                //If the Quote text DOES NOT exist
                else{
                    mSearchQuotesByKeywordQuoteQuote.setText("* No Quote to View *"); //Set message to say Quote text could not be obtained
                }


                //If the Quote author exists
                if (mSearchQuotesByKeywordQuote.getAuthor() != null){
                    mSearchQuotesByKeywordQuoteAuthor.setText("- " + mSearchQuotesByKeywordQuote.getAuthor()); //Set the Quote author text
                }
                //If the Quote author DOES NOT exist
                else{
                    mSearchQuotesByKeywordQuoteAuthor.setText("* No Author *"); //Set message to say Quote author could not be obtained
                }


                //If the Quote category exists
                if (mSearchQuotesByKeywordQuote.getCategories() != null){
                    mSearchQuotesByKeywordQuoteCategories.setText("Categories: " + TextUtils.join(", ", mSearchQuotesByKeywordQuote.getCategories())); //Set the Quote category text
                }
                else{
                    mSearchQuotesByKeywordQuoteCategories.setText("Categories: *No categories*"); //Set message to say Quote author could not be obtained
                }


                //Try risky task - mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable() may throw NullPointerException if it does not exist IF there is no internet connection
                try {

                    //If the Quote is in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) != null) {

                        mSearchQuotesByKeywordQuote.setFavorite(true); //Set favorite field of the Quote to TRUE
                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Display the favorite icon to 'active'

                    }
                    //If the Quote is NOT in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) == null) {

                        mSearchQuotesByKeywordQuote.setFavorite(false); //Set favorite field of the Quote to FALSE
                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off); //Display the favorite icon to 'inactive'
                    }
                }
                //Catch the NullPointerException - when there are no internet connections
                catch (NullPointerException npe){

                    //Remove visibility of all list item views, except for the view indicating that the quotes are unavailable
                    mSearchQuotesByKeywordQuoteProgressBar.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuotePositionText.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteFavoriteIcon.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteShareIcon.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteQuote.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteAuthor.setVisibility(View.GONE);
                    mSearchQuotesByKeywordQuoteCategories.setVisibility(View.GONE);

                    mSearchQuotesByKeywordQuoteUnavailable.setVisibility(View.VISIBLE);
                }



                //Set listener for Quote favorite icon
                mSearchQuotesByKeywordQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        //Set whether the Quote is to be Favorited based on the boolean parameter (i.e. whether the icon is checked or unchecked)
                        mSearchQuotesByKeywordQuote.setFavorite(isChecked);

                        //Set drawable button based on the boolean parameter
                        mSearchQuotesByKeywordQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        //If Favorites icon is CHECKED
                        if (isChecked){

                            //If Quote is NOT in the Favorites SQLite database
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByKeywordQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mSearchQuotesByKeywordQuote); //Add the Quote to the Favorites SQLite database
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByKeywordQuote); //Update the SQLite database as per the Quote addition
                            }
                        }
                        //If Favorites icon is UNCHECKED
                        if (!isChecked){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mSearchQuotesByKeywordQuote); //Delete the Quote from the Favorites SQLite database
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByKeywordQuote); //Update the SQLite database as per the Quote deletion

                        }
                    }
                });
            }



            //Set listener for Share icon
            mSearchQuotesByKeywordQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create implicit Intent with send action

                    shareIntent.setType("text/plain"); //Set the type of the Intent to text

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mSearchQuotesByKeywordQuote.getAuthor()); //Set subject of Intent

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSearchQuotesByKeywordQuoteShareString()); //Set text of Intent

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Set chooser title

                    startActivity(shareIntent); //Start the Intent
                }
            });


        }



        //Set the Quote sharing text
        private String getSearchQuotesByKeywordQuoteShareString(){

            String searchQuotesByKeywordQuoteString = "\"" + mSearchQuotesByKeywordQuote.getQuote() + "\""; //Quote text body

            String searchQuotesByKeywordQuoteAuthorString = " - " + mSearchQuotesByKeywordQuote.getAuthor(); //Quote author

            return searchQuotesByKeywordQuoteString + searchQuotesByKeywordQuoteAuthorString; //Concatenate Quote text body with author
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