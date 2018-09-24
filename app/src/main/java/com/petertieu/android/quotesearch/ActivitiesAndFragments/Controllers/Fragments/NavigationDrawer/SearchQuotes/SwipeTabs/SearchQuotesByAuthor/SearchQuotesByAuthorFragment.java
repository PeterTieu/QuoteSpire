package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor;

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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.GetSearchQuotesByAuthorQuote;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByAuthor.SearchQuotesByAuthorSharedPref;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



@SuppressWarnings({"RedundantCast", "FieldCanBeLocal", "deprecation", "ConstantConditions"})

//Fragment for searching Quotes by author
public class SearchQuotesByAuthorFragment extends Fragment implements View.OnClickListener {


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "SQBAuthorFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private static final int NUMBER_OF_QUOTES_TO_LOAD = 10; //Number of quotes to load upon search
    private static String sSearchQuery; //Search query obtained from SearchView
    private TextView mAuthorSearchedText;


    //-------------- LIST variables ------------------------------------
    private static List<Quote> sSearchQuotesByAuthorQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //List of Quote objects (of size 12)
    private List<String> mRandomSearchSuggestions; //List of Search Suggestions. They are 10 Strings that are randomly picked from a larger list of Strings
    @SuppressWarnings("CanBeFinal") private List<SearchQuotesByAuthorFragment.GetSearchQuotesByAuthorAsyncTask> mGetSearchQuotesByAuthorAsyncTasksList = Arrays.asList(new SearchQuotesByAuthorFragment.GetSearchQuotesByAuthorAsyncTask[NUMBER_OF_QUOTES_TO_LOAD]); //List of AsyncTass for fetching the Quote (of size 12)


    //-------------- FLAG variables ------------------------------------
    private static boolean shouldDisplaySearchResultsWhenFragmentIsReloaded; //Flag that is activated when a search has begun or the search items are in View.


    //-------------- VIEW variables ------------------------------------
    private LinearLayoutManager mLinearLayoutManager; //LinearLayoutManager for RecyclerView
    private RecyclerView mSearchQuotesByAuthorQuoteRecyclerView; //RecyclerView to store list of Quote items
    private static SearchQuotesByAuthorFragment.SearchQuotesByAuthorAdapter sSearchQuotesByAuthorQuoteAdapter; //RecyclerView Adapter
    private SearchQuotesByAuthorFragment.SearchQuotesByAuthorQuoteViewHolder mSearchQuotesByAuthorQuoteViewHolder; //RecyclerView ViewHolder for storing single Quote items

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

        View view = layoutInflater.inflate(R.layout.fragment_search_quotes_by_author, viewGroup, false); //Inflate fragment layout


        //Assign View instance variables to their associated resource files
        mAuthorSearchedText = (TextView) view.findViewById(R.id.search_quotes_by_author_author_searched);

        mSearchQuotesByAuthorQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_author_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchQuotesByAuthorQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSearchSuggestionsText = (TextView) view.findViewById(R.id.search_quotes_by_author_search_suggestions_text);
        mSearchSuggestionsRefresh = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestions_refresh);

        mSearchSuggestionOne = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_one);
        mSearchSuggestionTwo = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_two);
        mSearchSuggestionThree = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_three);
        mSearchSuggestionFour = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_four);
        mSearchSuggestionFive = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_five);
        mSearchSuggestionSix = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_six);
        mSearchSuggestionSeven = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_seven);
        mSearchSuggestionEight = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_eight);
        mSearchSuggestionNine = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_nine);
        mSearchSuggestionTen = (Button) view.findViewById(R.id.search_quotes_by_author_search_suggestion_ten);



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
            mAuthorSearchedText.setVisibility(View.VISIBLE);
            mSearchQuotesByAuthorQuoteRecyclerView.setVisibility(View.VISIBLE);

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
            mAuthorSearchedText.setText(Html.fromHtml("Author searched: " + " " + " \"" +"<i>" + sSearchQuery.toUpperCase() + "</i>" + "\"")); //Text for displaying the Author Searched

            sSearchQuotesByAuthorQuoteAdapter = new SearchQuotesByAuthorFragment.SearchQuotesByAuthorAdapter(sSearchQuotesByAuthorQuotes); //Create a new RecyclerView Adapter
            sSearchQuotesByAuthorQuoteAdapter.setSearchQuotesByAuthorQuotes(sSearchQuotesByAuthorQuotes); //Link the RecyclerView adapter to the List of Quotes from the search result
            mSearchQuotesByAuthorQuoteRecyclerView.setAdapter(sSearchQuotesByAuthorQuoteAdapter); //Set up the RecyclerView to the RecyclerView Adapter
        }

        //If a Search has NOT begun/is in place, then display the SEARCH SUGGESTIONS
        else{
            //---------- Configure View variables ----------------------
            mAuthorSearchedText.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteRecyclerView.setVisibility(View.GONE);

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
        List<String> mSearchSuggestionsFullList = Arrays.asList("Albert Einstein", "Isaac Newton", "Marie Curie", "Richard Dawkins", "Galileo Galilei",
                "Charles Darwin", "Stephen Hawking", "Michio Kaku", "Nikola Tesla", "Nicolaus Copernicus", "Plato", "Louis Pasteur", "Leonardo Da Vinci",
                "Niels Bohr", "Aristotle", "Max Planck", "Francis Crick", "Rosalind Franklin", "Archimedes", "Richard Feyman", "Alfred Nobel", "Neil deGrasse Tyson",
                "Robert Boyle", "Erwin Schrodinger", "Thomas Edison", "William Shakespeare", "Barack Obama", "Oprah Winfrey", "Tom Cruise", "Matt Damon",
                "Elvis Presley", "Brad Pitt", "Elon Musk", "Bill Gates", "Mark Zuckerberg", "Warren Buffett", "Napoleon Bonaparte", "Christopher Columubus",
                "Arnaold Schwarzenegger", "Rihanna", "Will Smith", "Steven Spielberg", "Britney Spears", "Elton John", "Tiger Woods",
                "Michael Jordan", "David Beckham", "Roger Federer", "Serena Williams", "Abraham Lincoln", "Marilyn Monroe", "Mahatma Gandhi", "Socrates", "Confucius",
                "Steve Jobs", "Usain Bolt", "Hugh Jackman", "J.K. Rowling", "J.R.R. Tolkien", "Kevin Hart", "Neil Armstrong", "John Lennon", "Quentin Tarantino",
                "Angelina Jolie", "Richard Branson", "George Orwell", "Beethoven", "Anne Frank", "Madonna", "Julius Caesar", "Alexander The Great", "Eminem",
                "Kanye West", "Vincent Van Gogh", "Charlie Chaplin", "Rowan Atkinson", "Sun Tzu", "Alan Watts", "Benjamin Franklin", "Nelson Mandela",
                "Wolfgang Amadeus Mozart", "Homer", "Homer Simpson", "Ludwig van Beethoven", "Pablo Picasso", "Carl Sagan");

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

            case R.id.search_quotes_by_author_search_suggestion_one:
                mSearchView.setQuery(mRandomSearchSuggestions.get(0), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_two:
                mSearchView.setQuery(mRandomSearchSuggestions.get(1), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_three:
                mSearchView.setQuery(mRandomSearchSuggestions.get(2), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_four:
                mSearchView.setQuery(mRandomSearchSuggestions.get(3), true);
                break;
            case R.id.search_quotes_by_author_search_suggestion_five:
                mSearchView.setQuery(mRandomSearchSuggestions.get(4), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_six:
                mSearchView.setQuery(mRandomSearchSuggestions.get(5), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_seven:
                mSearchView.setQuery(mRandomSearchSuggestions.get(6), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_eight:
                mSearchView.setQuery(mRandomSearchSuggestions.get(7), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_nine:
                mSearchView.setQuery(mRandomSearchSuggestions.get(8), true);
                break;

            case R.id.search_quotes_by_author_search_suggestion_ten:
                mSearchView.setQuery(mRandomSearchSuggestions.get(9), true);
                break;
        }
    }




    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_author, menu); //Inflate options menu layout


        //---------------- Configure SEARCH menu item -------------------------------------------------------------------------------
        MenuItem searchItem = menu.findItem(R.id.menu_item_search_quotes_by_author_fragment_search); //Menu item for "Search"

        mSearchView = (SearchView) searchItem.getActionView(); //Assign SearchView instance variable to this menu item
        mSearchView.setQueryHint(getResources().getString(R.string.search_quotes_by_author_search_view_hint)); //Set hint to SearchView


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

//                sQuotePictureCategorySearchQuery = searchQuery.replaceAll("( +)","-").trim(); //Replace all space characters (either single or multiple spaces) to a single hyphen character in the search query

                sSearchQuery = searchQuery;

                SearchQuotesByAuthorSharedPref.setSearchQuotesByAuthorStoredQuery(getActivity(), sSearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                mSearchView.onActionViewCollapsed(); //Collapse the SearchView

                shouldDisplaySearchResultsWhenFragmentIsReloaded = true; //Toggle flag to display the search results to TRUE

                getActivity().invalidateOptionsMenu(); //Update the options menu


                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the author query


                mAuthorSearchedText.setVisibility(View.VISIBLE); //Show the "Searched Text" TextView
                mAuthorSearchedText.setText(Html.fromHtml("Author searched: " + " " + " \"" +"<i>" +  sSearchQuery.toUpperCase() + "</i>" + "\"")); //Update the "Searched Text" text


                mSearchQuotesByAuthorQuoteRecyclerView.setVisibility(View.VISIBLE); //Show the RecyclerView
                sSearchQuotesByAuthorQuotes = null; //Nullify the List of all Quotes if they already point to an existing set from the previous search
                sSearchQuotesByAuthorQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]); //Re-initialise the size of the List of all Quotes
                sSearchQuotesByAuthorQuoteAdapter = new SearchQuotesByAuthorFragment.SearchQuotesByAuthorAdapter(sSearchQuotesByAuthorQuotes); //Create a new RecyclerView Adapter
                sSearchQuotesByAuthorQuoteAdapter.setSearchQuotesByAuthorQuotes(sSearchQuotesByAuthorQuotes);  //Link the RecyclerView adapter to the List of Quotes from the search result
                sSearchQuotesByAuthorQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
                mSearchQuotesByAuthorQuoteRecyclerView.setAdapter(sSearchQuotesByAuthorQuoteAdapter); //Set the RecyclerView to the new RecyclerView Adapter


                //Begin a new set of AsyncTasks to fetch a new set of Quotes from the search query
                for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

                    Integer quotePosition = i; //Let the quote position equal the index

                    mGetSearchQuotesByAuthorAsyncTasksList.set(i, new SearchQuotesByAuthorFragment.GetSearchQuotesByAuthorAsyncTask(sSearchQuery)); //Assign the AsyncTask reference in the List to a new AsyncTask object
                    mGetSearchQuotesByAuthorAsyncTasksList.get(i).execute(quotePosition); //Execute the AsyncTask object (i.e. begin fetching the Quote)
                }

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;
            }
        });


        //Set listener to the SearchView - make it load/retrieve the search query in the SharedPreferences when pressed on
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String searchQuery = SearchQuotesByAuthorSharedPref.getSearchQuotesByAuthorStoredQuery(getActivity()); //Load/retrieve search query from SharedPreferences
                mSearchView.setQuery(searchQuery, false); //Set the search query to the SearchView, but not load it yet
            }
        });


        //---------------- Configure CLEAR ALL menu item -------------------------------------------------------------------------------
        final MenuItem clearAllItem = menu.findItem(R.id.menu_item_search_quotes_by_author_fragment_clear_all); //Menu item for "Clear All"

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
            case (R.id.menu_item_search_quotes_by_author_fragment_search):
                //Do nothing. All covered in onCreateOptionsMenu(..) method
                return true;

            //"CLEAR ALL" menu item
            case (R.id.menu_item_search_quotes_by_author_fragment_clear_all):

                shouldDisplaySearchResultsWhenFragmentIsReloaded = false; //Toggle flag to display the search results to FALSE

                getActivity().invalidateOptionsMenu(); //Update options menu - i.e. call onCreateOptionsMenu() in order to make the "CLEAR ALL" menu item disappear

                //Configure visibilities of each of the Views
                mAuthorSearchedText.setVisibility(View.GONE);
                mSearchQuotesByAuthorQuoteRecyclerView.setVisibility(View.GONE);

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

                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the author query

                Toast.makeText(getActivity(), "Cleared Search Results for: " + sSearchQuery.toUpperCase(), Toast.LENGTH_LONG).show(); //Create Toast notifying that the search results have been cleared

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }

    }




    //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the author query
    private void cancelAllCurrentAsyncTasks(){

        if (!mGetSearchQuotesByAuthorAsyncTasksList.isEmpty()) {

            for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
                //Try risky task - mGetSearchQuotesByAuthorAsyncTasksList.get(i).cancel() may throw a NullPointerException if the AsyncTask is not running
                try{
                    mGetSearchQuotesByAuthorAsyncTasksList.get(i).cancel(true);
                }
                catch (NullPointerException npe){
                    Log.e(TAG, "GetSearchQuotesByAuthorAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
                }
            }
        }

    }




    //AsyncTask for fetching a Quote based on the search query
    //GENERIC TYPES:
    //#1: PARAMS:   Integer: Type passed to execute(..). NOTE: It could be passed in multiples (e.g. ..execute(1, 2, 3, 4))
    //#2: PROGRESS: Void: Type published during background computation
    //#3: RESULTS:  Quote: Type returned from doInBackground()
    @SuppressLint("StaticFieldLeak")
    private class GetSearchQuotesByAuthorAsyncTask extends AsyncTask<Integer, Void, Quote> {

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Integer mQuotePosition[]; //NOTE: mQuotePosition is an ARRAY of type Integer
        final String mSearchQuery; //Search query


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        GetSearchQuotesByAuthorAsyncTask(String searchQuery) {
            mSearchQuery = searchQuery; //Stash search query to the instance variable (mSearchQuery)
        }



        //Perform the main work of the asynchronous task - fetch the Quote
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        protected Quote doInBackground(Integer... quotePosition){

            mQuotePosition = quotePosition; //Stash the Quote position to the instance variable (mQuotePosition)

            Quote searchQuotesByAuthorQuote = new GetSearchQuotesByAuthorQuote().getSearchQuotesByAuthorQuote(mSearchQuery); //Fetch the Quote based on the search query

            return searchQuotesByAuthorQuote; //Return the fetched Quote - to be passed to onPostExecute()
        }



        //Perform work after the Quote is fetched - save the fetched Quote to the List of Quotes
        @Override
        protected void onPostExecute(Quote searchQuotesByAuthorQuote) {

            Log.i(TAG, "Search Quote by Author Quotes - method - Quote String: " + searchQuotesByAuthorQuote.getQuote());
            Log.i(TAG, "Search Quote by Author Quotes - method  - Author: " + searchQuotesByAuthorQuote.getAuthor());
            Log.i(TAG, "Search Quote by Author Quotes - method  - Author: " + searchQuotesByAuthorQuote.getAuthor());
            Log.i(TAG, "Search Quote by Author Quotes - method - ID: " + searchQuotesByAuthorQuote.getId());


            searchQuotesByAuthorQuote.setSearchQuotesByAuthorQuotePosition(mQuotePosition[0] + 1); //Set the position member variable of the fetched Quote

            sSearchQuotesByAuthorQuotes.set(mQuotePosition[0], searchQuotesByAuthorQuote); //Save the fetched Quote to the list of Quotes, to the index in this list based on the quote position

            sSearchQuotesByAuthorQuoteAdapter.setSearchQuotesByAuthorQuotes(sSearchQuotesByAuthorQuotes); //Set the RecyclerView Adapter to the newly updated List of Quotes

            sSearchQuotesByAuthorQuoteAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
        }

    }




    //RecyclerView Adapter
    private class SearchQuotesByAuthorAdapter extends RecyclerView.Adapter<SearchQuotesByAuthorFragment.SearchQuotesByAuthorQuoteViewHolder>{

        //Constructor
        SearchQuotesByAuthorAdapter(List<Quote> searchQuotesByAuthorQuotes){
            sSearchQuotesByAuthorQuotes = searchQuotesByAuthorQuotes; //Stash passed List of Quotes to the instance variable (sSearchQuotesByAuthorQuotes)
        }



        //Get count of the size of the List of Quotes
        @Override
        public int getItemCount(){
            return sSearchQuotesByAuthorQuotes.size();
        }



        //Create the RecyclerView ViewHolder
        @SuppressWarnings("NullableProblems")
        @Override
        public SearchQuotesByAuthorFragment.SearchQuotesByAuthorQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater

            View listItemView = layoutInflater.inflate(R.layout.list_item_search_quotes_by_author, viewGroup, false); //Obtain list-item view

            mSearchQuotesByAuthorQuoteViewHolder = new SearchQuotesByAuthorFragment.SearchQuotesByAuthorQuoteViewHolder(listItemView); //Send list-item view to the RecyclerView ViewHolder

            return mSearchQuotesByAuthorQuoteViewHolder; //Return RecyclerView ViewHolder
        }



        //Bind RecyclerView ViewHolder to the data of the Quote
        @SuppressWarnings("NullableProblems")
        @Override
        public void onBindViewHolder(SearchQuotesByAuthorFragment.SearchQuotesByAuthorQuoteViewHolder searchQuotesByAuthorQuoteViewHolder, int position){

            Quote searchQuotesByAuthorQuote = sSearchQuotesByAuthorQuotes.get(position); //Get the Quote

            searchQuotesByAuthorQuoteViewHolder.bind(searchQuotesByAuthorQuote); //Bind RecyclerView ViewHolder to the data of the Quote
        }



        //Stash the List of Quotes to the instance variable
        void setSearchQuotesByAuthorQuotes(List<Quote> searchQuotesByAuthorQuotes){
            sSearchQuotesByAuthorQuotes = searchQuotesByAuthorQuotes;
        }

    }




    //RecyclerView ViewHolder
    @SuppressWarnings("ConstantConditions")
    private class SearchQuotesByAuthorQuoteViewHolder extends RecyclerView.ViewHolder{

        //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================
        Quote mSearchQuotesByAuthorQuote; //Quote - contains data for the list item

        //-------------- VIEW variables of the list item ------------------------------------
        final LinearLayout mSearchQuotesByAuthorQuoteBubbleLayout;
        final TextView mSearchQuotesByAuthorQuotePositionText;
        final CheckBox mSearchQuotesByAuthorQuoteFavoriteIcon;
        final Button mSearchQuotesByAuthorQuoteShareIcon;
        final ProgressBar mSearchQuotesByAuthorQuoteProgressBar;
        final TextView mSearchQuotesByAuthorQuoteUnavailable;
        final TextView mSearchQuotesByAuthorQuoteQuote;
        final TextView mSearchQuotesByAuthorQuoteAuthor;
        final TextView mSearchQuotesByAuthorQuoteCategories;


        //================== DEFINE METHODS ===================================================================================
        //Constructor
        SearchQuotesByAuthorQuoteViewHolder(View view){
            super(view);

            //Assign list item view instance variables to their associated resource files
            mSearchQuotesByAuthorQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.search_quotes_by_author_quote_bubble_layout);
            mSearchQuotesByAuthorQuotePositionText = (TextView) view.findViewById(R.id.search_quotes_by_author_quote_position);
            mSearchQuotesByAuthorQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.search_quotes_by_author_quote_favorite_icon);
            mSearchQuotesByAuthorQuoteShareIcon = (Button) view.findViewById(R.id.search_quotes_by_author_quote_share_icon);
            mSearchQuotesByAuthorQuoteProgressBar = (ProgressBar) view.findViewById(R.id.search_quotes_by_author_quote_progress_bar);
            mSearchQuotesByAuthorQuoteUnavailable = (TextView) view.findViewById(R.id.search_quotes_by_author_quote_unavailable);
            mSearchQuotesByAuthorQuoteQuote = (TextView) view.findViewById(R.id.search_quotes_by_author_quote_quote);
            mSearchQuotesByAuthorQuoteAuthor = (TextView) view.findViewById(R.id.search_quotes_by_author_quote_author);
            mSearchQuotesByAuthorQuoteCategories = (TextView) view.findViewById(R.id.search_quotes_by_author_quote_categories);

            mSearchQuotesByAuthorQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges)); //Set background drawable for each list item

            //Set visibilities of each of the list item views - set all list item views to GONE (except for the ProgressBar) as they have only just been created
            mSearchQuotesByAuthorQuoteProgressBar.setVisibility(View.VISIBLE);
            mSearchQuotesByAuthorQuotePositionText.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteShareIcon.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteUnavailable.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteQuote.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteAuthor.setVisibility(View.GONE);
            mSearchQuotesByAuthorQuoteCategories.setVisibility(View.GONE);
        }



        //Bind the RecyclerView ViewHolder to the data from the Quote
        @SuppressWarnings("WeakerAccess")
        @SuppressLint("SetTextI18n")
        public void bind(Quote searchQuotesByAuthorQuote){

            //If the Quote object exists
            if (searchQuotesByAuthorQuote != null){

                mSearchQuotesByAuthorQuote = searchQuotesByAuthorQuote; //Stash passed Quote parameter to instance variable

                //Configure visibilities of the list item views
                mSearchQuotesByAuthorQuoteProgressBar.setVisibility(View.GONE);
                mSearchQuotesByAuthorQuoteUnavailable.setVisibility(View.GONE);

                mSearchQuotesByAuthorQuotePositionText.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorQuoteQuote.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorQuoteAuthor.setVisibility(View.VISIBLE);
                mSearchQuotesByAuthorQuoteCategories.setVisibility(View.VISIBLE);


                mSearchQuotesByAuthorQuotePositionText.setText("Quote #" + mSearchQuotesByAuthorQuote.getSearchQuotesByAuthorQuotePosition()); //Set the text of the position of the Quote

                //Set drawable for Quote favorite icon, based on whether it is Favorited or not
                mSearchQuotesByAuthorQuoteFavoriteIcon.setButtonDrawable(mSearchQuotesByAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //If the Quote text exists
                if (mSearchQuotesByAuthorQuote.getQuote() != null){
                    mSearchQuotesByAuthorQuoteQuote.setText("\" " + searchQuotesByAuthorQuote.getQuote() + " \""); //Set the Quote text
                }
                //If the Quote text DOES NOT exist
                else{
                    mSearchQuotesByAuthorQuoteQuote.setText("* No Quote to View *"); //Set message to say Quote text could not be obtained
                }


                //If the Quote author exists
                if (mSearchQuotesByAuthorQuote.getAuthor() != null){
                    mSearchQuotesByAuthorQuoteAuthor.setText("- " + mSearchQuotesByAuthorQuote.getAuthor()); //Set the Quote author text
                }
                //If the Quote author DOES NOT exist
                else{
                    mSearchQuotesByAuthorQuoteAuthor.setText("* No Author *"); //Set message to say Quote author could not be obtained
                }


                //If the Quote author exists
                if (mSearchQuotesByAuthorQuote.getCategories() != null){
                    mSearchQuotesByAuthorQuoteCategories.setText("Categories: " + TextUtils.join(", ", mSearchQuotesByAuthorQuote.getCategories())); //Set the Quote author text
                }
                else{
                    mSearchQuotesByAuthorQuoteCategories.setText("Categories: *No categories*"); //Set message to say Quote author could not be obtained
                }


                //Try risky task - mSearchQuotesByAuthorQuoteFavoriteIcon.setButtonDrawable() may throw NullPointerException if it does not exist IF there is no internet connection
                try {

                    //If the Quote is in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAuthorQuote.getId()) != null) {

                        mSearchQuotesByAuthorQuote.setFavorite(true); //Set favorite field of the Quote to TRUE
                        mSearchQuotesByAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Display the favorite icon to 'active'

                    }
                    //If the Quote is NOT in the FavoriteQuotes SQLite database
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAuthorQuote.getId()) == null) {

                        mSearchQuotesByAuthorQuote.setFavorite(false); //Set favorite field of the Quote to FALSE
                        mSearchQuotesByAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off); //Display the favorite icon to 'inactive'
                    }
                }
                //Catch the NullPointerException - when there are no internet connections
                catch (NullPointerException npe){

                    //Remove visibility of all list item views, except for the view indicating that the quotes are unavailable
                    mSearchQuotesByAuthorQuoteProgressBar.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuotePositionText.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuoteShareIcon.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuoteQuote.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuoteAuthor.setVisibility(View.GONE);
                    mSearchQuotesByAuthorQuoteCategories.setVisibility(View.GONE);

                    mSearchQuotesByAuthorQuoteUnavailable.setVisibility(View.VISIBLE);
                }



                //Set listener for Quote favorite icon
                mSearchQuotesByAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        //Set whether the Quote is to be Favorited based on the boolean parameter (i.e. whether the icon is checked or unchecked)
                        mSearchQuotesByAuthorQuote.setFavorite(isChecked);

                        //Set drawable button based on the boolean parameter
                        mSearchQuotesByAuthorQuoteFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        //If Favorites icon is CHECKED
                        if (isChecked){

                            //If Quote is NOT in the Favorites SQLite database
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mSearchQuotesByAuthorQuote.getId()) == null){
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mSearchQuotesByAuthorQuote); //Add the Quote to the Favorites SQLite database
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByAuthorQuote); //Update the SQLite database as per the Quote addition
                            }
                        }
                        //If Favorites icon is UNCHECKED
                        if (!isChecked){
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mSearchQuotesByAuthorQuote); //Delete the Quote from the Favorites SQLite database
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mSearchQuotesByAuthorQuote); //Update the SQLite database as per the Quote deletion

                        }
                    }
                });
            }



            //Set listener for Share icon
            mSearchQuotesByAuthorQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create implicit Intent with send action

                    shareIntent.setType("text/plain"); //Set the type of the Intent to text

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote by " + mSearchQuotesByAuthorQuote.getAuthor()); //Set subject of Intent

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSearchQuotesByAuthorQuoteShareString()); //Set text of Intent

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Set chooser title

                    startActivity(shareIntent); //Start the Intent
                }
            });


        }



        //Set the Quote sharing text
        private String getSearchQuotesByAuthorQuoteShareString(){

            String searchQuotesByAuthorQuoteString = "\"" + mSearchQuotesByAuthorQuote.getQuote() + "\""; //Quote text body

            String searchQuotesByAuthorQuoteAuthorString = " - " + mSearchQuotesByAuthorQuote.getAuthor(); //Quote author

            return searchQuotesByAuthorQuoteString + searchQuotesByAuthorQuoteAuthorString; //Concatenate Quote text body with author
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