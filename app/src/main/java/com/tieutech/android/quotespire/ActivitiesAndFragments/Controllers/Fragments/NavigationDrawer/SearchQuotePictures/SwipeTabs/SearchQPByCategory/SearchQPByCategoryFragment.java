package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SwipeTabs.SearchQPByCategory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureDetailActivity;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureDownloaderHandlerThread;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import com.tieutech.android.quotespire.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//Displays Quote Pictures based on CATEGORY search query
@SuppressWarnings({"ConstantConditions", "RedundantCast", "CanBeFinal"})
public class SearchQPByCategoryFragment extends Fragment implements View.OnClickListener{


    //================== DECLARE/DEFINE INSTANCE VARIABLES =========================================================

    private final String TAG = "SQPByCategoryFragment"; //Log for Logcat

    //-------------- SEARCH variables ------------------------------------
    private final static int NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD = 10; //Number of quotes to load upon search


    //-------------- LIST variables ------------------------------------
    private static List<QuotePicture> sCategoryQuotePictureQuotes = Arrays.asList(new QuotePicture[NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD]); //List of QuotePicture objects...
                                                                                                                                                //To contain all fields in QuotePicture (NOTE: does not contain the Bitmap, as this is too large. Just contains the ByteArray for the Bitmap))
    private static List<Bitmap> mCategoryQuotePictureBitmaps = Arrays.asList(new Bitmap[NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD]); //List of Bitmaps of Quote Pictures - fetched from the HandlerThread
    @SuppressWarnings("CanBeFinal") private List<GetCategoryQuotePictureAsyncTask> mGetCategoryQuotePictureAsyncTasksList = Arrays.asList(new GetCategoryQuotePictureAsyncTask[NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD]); //List of AsyncTass for fetching the Quote (of size 12)
    private List<String> mRandomSearchSuggestions; //List of Search Suggestions. They are 10 Strings that are randomly picked from a larger list of Strings

    private RecyclerView mCategoryQuotePictureRecyclerView; //RecyclerView for displaying Quote Picture ViewHolders
    private CategoryQuotePicturesAdapter mCategoryQuotePicturesAdapter; //Adapter for creating and binding the ViewHolders

    private SearchView mSearchView; //Search button in the options menu

    private TextView mCategorySearchedText;     //Category Searched text
    private TextView mSearchSuggestionsText;    //Search suggestions text
    private Button mSearchSuggestionsRefresh;   //Search suggestions refresh button

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


    //-------------- THREAD variable ------------------------------------
    private QuotePictureDownloaderHandlerThread<QuotePicture> mCategoryQuotePicturesDownloaderHandlerThread; //HandlerThread for fetching the Quote Picture from the Picture Download URI

    //-------------- FLAG variables ------------------------------------
    private static boolean shouldDisplaySearchResultsWhenFragmentIsReloaded; //Flag that is activated when a search has begun or the search items are in View.

    //-------------- OTHER variables ------------------------------------
    public static String sQuotePictureCategorySearchQuery; //Search query obtained from SearchView




    //================== DEFINE METHODS ===================================================================================

    //Override onAttach(..) fragment lifecycle callback method
    @SuppressWarnings("deprecation")
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

        setHasOptionsMenu(true); //Declare that there is an options menu



        //-------------- Manage HandlerThreads ------------------------------------------------------------------------
        //Used for fetching Quote Pictures (from Picture Download URIs)

        Handler responseHandler = new Handler(); //Handler created in this thread - therefore, is attached to this thread and RUNS ON THIS THREAD. This Handler will be called at the end of the worker HandlerThread

        mCategoryQuotePicturesDownloaderHandlerThread = new QuotePictureDownloaderHandlerThread<>(responseHandler); //HandlerThread to be run on the side for fetching Quote Pictures


        //Set listener for WHEN the Quote Picture has been fetched in the worker HandlerThread
        mCategoryQuotePicturesDownloaderHandlerThread.setQuoteQuotePictureDownloadListener(new QuotePictureDownloaderHandlerThread.QuotePictureDownloadListener<QuotePicture>() {

            //Override onQuotePictureDownloaded(..) method from the QuotePictureDownloaderHandlerThread
            //NOTE:
            //categoryQuotePictureQuote: The QuotePicture fetched from the HandlerThread
            //quotePicture: The Bitmap fetched from the HandlerThread
            @Override
            public void onQuotePictureDownloaded(QuotePicture categoryQuotePictureQuote, Bitmap quotePicture) {

                sCategoryQuotePictureQuotes.set(categoryQuotePictureQuote.getCategoryQuotePicturePosition() - 1, categoryQuotePictureQuote); //'Add' the fetched QuotePicture object to the QuotePicture list

                Bitmap resizedQuotePicture = Bitmap.createBitmap(quotePicture, 0, 0, quotePicture.getWidth(), quotePicture.getHeight()-30); //Resize the Quote Picture fetched from the HandlerThread so that the watermark at the bottom is removed
                mCategoryQuotePictureBitmaps.set(categoryQuotePictureQuote.getCategoryQuotePicturePosition() - 1, resizedQuotePicture); //Add the Quote Picture (Bitmap) to the Bitmap List member variable


                //If the Adapter does not exist yet..
                if (mCategoryQuotePicturesAdapter == null) {
                    mCategoryQuotePicturesAdapter = new CategoryQuotePicturesAdapter(sCategoryQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
                }

                mCategoryQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)
            }
        });


        mCategoryQuotePicturesDownloaderHandlerThread.start(); //Start the HandlerThread - NOTE: This line is important, because if it is excluded, the HandlerThread wouldn't be able to run. An exception would be thrown
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        getActivity().setTitle("Search Quote Pictures"); //Set title for the Fragment on the toolbar

        Log.i(TAG, "onCreateView(..) called");  //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_search_quote_pictures_by_category, viewGroup, false); //Inflate View


        //Assign View instance variables to their associated resource files
        mCategoryQuotePictureRecyclerView = view.findViewById(R.id.category_quote_pictures_recycler_view); //Assign RecyclerView member variable to the view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCategoryQuotePictureRecyclerView.setLayoutManager(gridLayoutManager); //Link the RecyclerView to the LayoutManager


        mCategorySearchedText = (TextView) view.findViewById(R.id.search_quote_pictures_by_category_searched);

        mSearchSuggestionsText = (TextView) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestions_text);
        mSearchSuggestionsRefresh = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestions_refresh);

        mSearchSuggestionOne = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_one);
        mSearchSuggestionTwo = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_two);
        mSearchSuggestionThree = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_three);
        mSearchSuggestionFour = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_four);
        mSearchSuggestionFive = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_five);
        mSearchSuggestionSix = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_six);
        mSearchSuggestionSeven = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_seven);
        mSearchSuggestionEight = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_eight);
        mSearchSuggestionNine = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_nine);
        mSearchSuggestionTen = (Button) view.findViewById(R.id.search_quote_pictures_by_category_search_suggestion_ten);



        //If the last element of the Bitmaps list is not existent yet - i.e. not every ViewHolder has been completely loaded
        if (mCategoryQuotePictureBitmaps.get(NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD-1) == null) {
            mCategoryQuotePicturesAdapter = new CategoryQuotePicturesAdapter(sCategoryQuotePictureQuotes); //Set the QuotePictures list to the RecyclerView Adapter
        }

        mCategoryQuotePictureRecyclerView.setAdapter(mCategoryQuotePicturesAdapter); //Set the RecyclerView to the RecyclerView Adapter



        // Set listener for Search Suggestions Refresh button
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
            mCategoryQuotePictureRecyclerView.setVisibility(View.VISIBLE);

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
            mCategorySearchedText.setText(Html.fromHtml("Category searched: " + " " + " \"" +"<i>" + sQuotePictureCategorySearchQuery.toUpperCase() + "</i>" + "\"")); //Text for displaying the Category Searched

            mCategoryQuotePicturesAdapter = new CategoryQuotePicturesAdapter(sCategoryQuotePictureQuotes); //Create a new RecyclerView Adapter
            mCategoryQuotePictureRecyclerView.setAdapter(mCategoryQuotePicturesAdapter); //Set up the RecyclerView to the RecyclerView Adapter
        }

        //If a Search has NOT begun/is in place, then display the SEARCH SUGGESTIONS
        else{
            //---------- Configure View variables ----------------------
            mCategorySearchedText.setVisibility(View.GONE);
            mCategoryQuotePictureRecyclerView.setVisibility(View.GONE);

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


        getActivity().invalidateOptionsMenu(); //Update the OptionsMenu


        return view; //Return View
    }




    //Method for setting up the set of Search Suggestions - randomly picked from a List of Strings of ALL possible Search Suggestions
    @SuppressWarnings("UnnecessaryLocalVariable")
    private void setUpRandomSearchSuggestions() {

        //List of all possible Search Suggestions
        List<String> mSearchSuggestionsFullList = Arrays.asList("Inspire", "Experience", "Life", "Self", "Right Way",
                "Failure", "Fortune", "Dream", "Vision", "Curiosity", "Greed", "Insight", "Simplicity", "Understanding", "Business", "Economics", "Finance",
                "Self-Expression", "Books", "Reading", "Diversity", "Equality", "Philosophy", "Wisdom", "Positive-Thinking", "Conservation", "Nature", "Success",
                "Motivation", "Opportunities", "Government", "Truth", "People", "Universe", "Culture", "Education", "Soul", "Mind", "Work", "Mind-Body-Spirit",
                "Cosmic", "Self-Improvement", "Money", "Wealth", "Inspirational", "Beliefs", "Self-Help", "Time", "Politics", "Teaching", "Futurism",
                "Poetry", "Confidence", "Trust", "Hope", "Humanity", "Future", "War", "Peace", "Society", "Psychology", "Greatness", "Progress", "Meaning", "Knowledge", "Zen",
                "Meditation", "Reality", "Mindfulness", "Imagination", "Technology", "Science", "Funny", "Nature");

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

            case R.id.search_quote_pictures_by_category_search_suggestion_one:
                mSearchView.setQuery(mRandomSearchSuggestions.get(0), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_two:
                mSearchView.setQuery(mRandomSearchSuggestions.get(1), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_three:
                mSearchView.setQuery(mRandomSearchSuggestions.get(2), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_four:
                mSearchView.setQuery(mRandomSearchSuggestions.get(3), true);
                break;
            case R.id.search_quote_pictures_by_category_search_suggestion_five:
                mSearchView.setQuery(mRandomSearchSuggestions.get(4), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_six:
                mSearchView.setQuery(mRandomSearchSuggestions.get(5), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_seven:
                mSearchView.setQuery(mRandomSearchSuggestions.get(6), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_eight:
                mSearchView.setQuery(mRandomSearchSuggestions.get(7), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_nine:
                mSearchView.setQuery(mRandomSearchSuggestions.get(8), true);
                break;

            case R.id.search_quote_pictures_by_category_search_suggestion_ten:
                mSearchView.setQuery(mRandomSearchSuggestions.get(9), true);
                break;
        }
    }








    //Override onCrateOptionsMenu(..) (fragment lifecycle callback method) for creating the OptionsMenu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        Log.i(TAG, "onCreateOptionsMenu(..) called"); //Log lifecycle callback

        menuInflater.inflate(R.menu.fragment_search_quote_pictures_by_category, menu); //Inflate the view of the OptionsMenu


        //---------------- Configure SEARCH menu item -------------------------------------------------------------------------------
        MenuItem searchItem = menu.findItem(R.id.menu_item_search_quote_pictures_by_category_fragment_search); //Menu item for "Search"

        mSearchView = (SearchView) searchItem.getActionView(); //Assign SearchView instance variable to this menu item
        mSearchView.setQueryHint(getResources().getString(R.string.search_quote_pictures_by_category_search_view_hint)); //Set hint to SearchView

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

                sQuotePictureCategorySearchQuery = searchQuery;

                SearchQPByCategorySharedPref.setSearchQuotesByCategoryStoredQuery(getActivity(), sQuotePictureCategorySearchQuery); //Set the search query to the SharedPreferences - to be retrieved later

                mSearchView.onActionViewCollapsed(); //Collapse the SearchView

                shouldDisplaySearchResultsWhenFragmentIsReloaded = true; //Toggle flag to display the search results to TRUE

                getActivity().invalidateOptionsMenu(); //Update the options menu


                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the Category query


                mCategorySearchedText.setVisibility(View.VISIBLE); //Show the "Searched Text" TextView
                mCategorySearchedText.setText(Html.fromHtml("Category searched: " + " " + " \"" +"<i>" +  sQuotePictureCategorySearchQuery.toUpperCase() + "</i>" + "\"")); //Update the "Searched Text" text


                mCategoryQuotePictureRecyclerView.setVisibility(View.VISIBLE); //Show the RecyclerView
                sCategoryQuotePictureQuotes = null; //Nullify the List of all Quotes if they already point to an existing set from the previous search
                sCategoryQuotePictureQuotes = Arrays.asList(new QuotePicture[NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD]); //Re-initialise the size of the List of all Quotes
                mCategoryQuotePicturesAdapter = new CategoryQuotePicturesAdapter(sCategoryQuotePictureQuotes); //Create a new RecyclerView Adapter
                mCategoryQuotePicturesAdapter.notifyDataSetChanged(); //Update the RecyclerView Adapter
                mCategoryQuotePictureRecyclerView.setAdapter(mCategoryQuotePicturesAdapter); //Set the RecyclerView to the new RecyclerView Adapter


                //Begin a new set of AsyncTasks to fetch a new set of Quotes from the search query
                for (int i = 0; i < NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD; i++) {

                    Integer quotePosition = i; //Let the quote position equal the index

                    mGetCategoryQuotePictureAsyncTasksList.set(i, new GetCategoryQuotePictureAsyncTask(sQuotePictureCategorySearchQuery)); //Assign the AsyncTask reference in the List to a new AsyncTask object
                    mGetCategoryQuotePictureAsyncTasksList.get(i).execute(quotePosition); //Execute the AsyncTask object (i.e. begin fetching the Quote)
                }

                //Return a boolean. TRUE if an action is handled by the listener (as is the case); FALSE if the SearchView should perform the DEFAULT action (i.e. show any suggestions if available)
                return true;
            }
        });


        //Set listener to the SearchView - make it load/retrieve the search query in the SharedPreferences when pressed on
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String searchQuery = SearchQPByCategorySharedPref.getSearchQuotesByCategoryStoredQuery(getActivity()); //Load/retrieve search query from SharedPreferences
                mSearchView.setQuery(searchQuery, false); //Set the search query to the SearchView, but not load it yet
            }
        });


        //---------------- Configure CLEAR ALL menu item -------------------------------------------------------------------------------
        final MenuItem clearAllItem = menu.findItem(R.id.menu_item_search_quote_pictures_by_category_fragment_clear_all); //Menu item for "Clear All"

        //Show the "Clear All" menu item IF and ONLY IF the flag indicating that the search results are displayed is TRUE
        if (shouldDisplaySearchResultsWhenFragmentIsReloaded){
            clearAllItem.setVisible(true);
        }
        else{
            clearAllItem.setVisible(false);
        }
    }




    //Override onOptionsItemSelected(..) (fragment lifecycle callback method) for deciding what happens when the "randomise" button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        switch (menuItem.getItemId()){

            //"SEARCH" menu item
            case (R.id.menu_item_search_quote_pictures_by_category_fragment_search):
                //Do nothing. All covered in onCreateOptionsMenu(..) method
                return true;

            //"CLEAR ALL" menu item
            case (R.id.menu_item_search_quote_pictures_by_category_fragment_clear_all):

                shouldDisplaySearchResultsWhenFragmentIsReloaded = false; //Toggle flag to display the search results to FALSE

                getActivity().invalidateOptionsMenu(); //Update options menu - i.e. call onCreateOptionsMenu() in order to make the "CLEAR ALL" menu item disappear

                //Configure visibilities of each of the Views
                mCategorySearchedText.setVisibility(View.GONE);
                mCategoryQuotePictureRecyclerView.setVisibility(View.GONE);

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

                cancelAllCurrentAsyncTasks(); //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the Category query

                Toast.makeText(getActivity(), "Cleared Search Results for: " + sQuotePictureCategorySearchQuery.toUpperCase(), Toast.LENGTH_LONG).show(); //Create Toast notifying that the search results have been cleared

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);

        }
    }




    //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the Category query
    private void cancelAllCurrentAsyncTasks(){

        if (!mGetCategoryQuotePictureAsyncTasksList.isEmpty()) {

            for (int i = 0; i < NUMBER_OF_CATEGORY_QUOTE_PICTURES_TO_LOAD; i++) {
                //Try risky task - mGetCategoryQuotePictureAsyncTasksList.get(i).cancel() may throw a NullPointerException if the AsyncTask is not running
                try{
                    mGetCategoryQuotePictureAsyncTasksList.get(i).cancel(true);
                }
                catch (NullPointerException npe){
                    Log.e(TAG, "GetCategoryQuotePictureAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
                }
            }
        }

    }




    //AsyncTask for fetching a Quote based on the search query
    //GENERIC TYPES:
    //#1: PARAMS:   Integer: Type passed to execute(..). NOTE: It could be passed in multiples (e.g. ..execute(1, 2, 3, 4))
    //#2: PROGRESS: Void: Type published during background computation
    //#3: RESULTS:  Quote: Type returned from doInBackground()
    @SuppressWarnings({"UnnecessaryLocalVariable", "CanBeFinal"})
    @SuppressLint("StaticFieldLeak")
    private class GetCategoryQuotePictureAsyncTask extends AsyncTask<Integer, Void, QuotePicture> {

        Integer mQuotePosition[]; //Position of the Category Quote Picture in the list - obtained from execute() method call.
                                    //NOTE: Since we only passed ONE Params Generic parameter, then mQuotePosition[0] would be this parameter
        String mSearchQuery; //Search query that is submitted in the SearchView


        //Constructor
        GetCategoryQuotePictureAsyncTask(String searchQuery){
            mSearchQuery = searchQuery;
        }


        //Override doInBackground(..) method - to define what is to be done in the asynchronous thread
        //NOTE: The parameters of doInBackground(..) were taken from the execute(..) method from AsyncTask
        @Override
        protected QuotePicture doInBackground(Integer... quoteNumber){

            mQuotePosition = quoteNumber; //Stash position of the Quote in the member variable. NOTE: quoteNumber local variable is the variable passed into execute(..)

            QuotePicture categoryQuotePicture = new GetCategoryQuotePicture().getCategoryQuotePictureQuote(mSearchQuery); //Fetch the Quote Picture Download URI via the networking class (GetCategoryQuotePictureQuote)

            return categoryQuotePicture; //Return the Quote class fetched from the networking class. NOTE: This class is passed to onPostExecute(..)
        }


        //Override onPostExecute(..) method - to define what happens AFTER QuotePicture Download URI (i.e. the URL link to a picture) has been fetched
        //NOTE: The parameter of onPostExecute(..) (i.e. QuotePicture), is what has been returned from doInBackground(..)
        @Override
        protected void onPostExecute(QuotePicture categoryQuotePictureQuote){

            categoryQuotePictureQuote.setCategoryQuotePicturePosition(mQuotePosition[0] + 1); //Set the obtained Quote's position to be the same as the position in the Category Quote Pictures list

            sCategoryQuotePictureQuotes.set(mQuotePosition[0], categoryQuotePictureQuote); //Add the obtained Quote to the List of Quote objects

            //Call the worker HandlerThread - by sending a Message to its MessageQueue. This message contains:
            //1: The KEY (Position of the QuotePicture) - for identifying the Message
            //2: The VALUE (QuotePicture Picture Download URI) - to be used to fetch the Quote Picture
            mCategoryQuotePicturesDownloaderHandlerThread.enqueueQuotePictureDownloadURIIToMessageQueue(sCategoryQuotePictureQuotes.get(mQuotePosition[0]), categoryQuotePictureQuote.getQuotePictureDownloadURI());
        }

    }




    //Adapter - for linking the RecyclerView to the ViewHolders
    private class CategoryQuotePicturesAdapter extends RecyclerView.Adapter<CategoryQuotePictureViewHolder>{

        //Constructor - to set the list to be used for the RecyclerView
        CategoryQuotePicturesAdapter(List<QuotePicture> categoryQuotePictureQuotes){
            sCategoryQuotePictureQuotes = categoryQuotePictureQuotes;
        }


        //Get the size of the list
        @Override
        public int getItemCount(){
            return sCategoryQuotePictureQuotes.size();
        }


        //Create the ViewHolder and link the list-view View to it
        @NonNull
        @Override
        public CategoryQuotePictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Create LayoutInflater

            View view = layoutInflater.inflate(R.layout.list_item_quote_picture, viewGroup, false); //Fetch list-view View and link it to the View variable

            CategoryQuotePictureViewHolder categoryQuotePictureViewHolder = new CategoryQuotePictureViewHolder(view);

            return categoryQuotePictureViewHolder; //Return the ViewHolder
        }


        //Bind the ViewHolder to the necessary variables/data
        @Override
        public void onBindViewHolder(@NonNull CategoryQuotePictureViewHolder categoryQuotePictureViewHolder, int position){

            //If the element in the QuotePicture list contains/refers to ta QuotePicture object
            if (sCategoryQuotePictureQuotes.get(position) != null){

                categoryQuotePictureViewHolder.bindListItem(position, mCategoryQuotePictureBitmaps.get(position)); //Bind the ViewHolder to the necessary variables/data
            }

        }

    }




    //ViewHolder - for linking the list-item to the appropriate variable/data and displaying it
    private class CategoryQuotePictureViewHolder extends RecyclerView.ViewHolder{

        View mListItemView; //View of the list-item
        private ImageView mQuotePictureListItemImageView; //ImageView to display the QuotePicture Bitmap
        private Drawable mCategoryQuotePictureDrawable; //Drawable to bridge between the ImageView and the Bitmap
        private SpinKitView mQuotePictureListItemProgressBar; //ProgressBar to represent the list-item being loaded when the Bitmap has not been fetched from the HandlerThread and binded to the ViewHolder yet



        //Constructor - called by Adapter's onCreateViewHolder(..)
        CategoryQuotePictureViewHolder(View listItemView){
            super(listItemView);

            mListItemView = listItemView; //Define the list-view variable

            mQuotePictureListItemImageView = (ImageView) listItemView.findViewById(R.id.quote_picture_list_item_bitmap); //ImageView to display the QuotePicture Bitmap
            mQuotePictureListItemProgressBar = (SpinKitView) listItemView.findViewById(R.id.quote_picture_list_item_progress_bar); //ProgressBar to represent the list-item being loaded when the Bitmap has not been fetched from the HandlerThread and binded to the ViewHolder yet

            mQuotePictureListItemProgressBar.setVisibility(View.VISIBLE); //Make the ProgressBar VISIBLE
            mQuotePictureListItemImageView.setVisibility(View.GONE); //Make the ImageView GONE
        }



        //Bind method - called by Adapter's onBindViewHolder(..)
        void bindListItem(final int position, Bitmap categoryQuotePictureBitmap){


            //If the Bitmap EXIST
            if (categoryQuotePictureBitmap != null){
                mQuotePictureListItemProgressBar.setVisibility(View.GONE); //Make the ProgressBar VISIBLE
                mQuotePictureListItemImageView.setVisibility(View.VISIBLE);  //Make the ImageView GONE
            }


            mCategoryQuotePictureDrawable = new BitmapDrawable(getResources(), categoryQuotePictureBitmap); //Parse the Quote Picture Bitmap into a Drawable object
            mQuotePictureListItemImageView.setImageDrawable(mCategoryQuotePictureDrawable); //Display the Drawable object on the ImageView


            //If the QuotePicture's ID EXISTS
            if (sCategoryQuotePictureQuotes.get(position).getId() != null) {

                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(sCategoryQuotePictureQuotes.get(position).getId()) != null) {

                    mQuotePictureListItemImageView.setBackgroundColor(getResources().getColor(R.color.favorite_on)); //Set the
                    mQuotePictureListItemImageView.setPadding(20,18,20,18); //Display an "orange" border around the liar-item to show that it is 'Favorited'


                }
            }



            //If the Bitmap EXISTS
            if (categoryQuotePictureBitmap != null){

                final Bitmap categoryQuotePictureBitmapFinal = categoryQuotePictureBitmap; //Make a variable that also points to the Bitmap object referred to by categoryQuotePictureBitmap, but make it final, so that it could be passed into the list-view's listener

                //Set listener for the ListView - OPEN up the QuotePictureDetailFragment (view of the QuotePicture picture) when the list-item is clicked on
                mListItemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Create a ByteArrayOutputStream for reading the Bitmap
                        categoryQuotePictureBitmapFinal.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); //Compress the Bitmap into the ByteArrayOutputStream
                        byte[] quotePictureBitmapByteArray = byteArrayOutputStream.toByteArray(); //Create a byte array and refer to the Bitmap's byte array
                        sCategoryQuotePictureQuotes.get(position).setQuotePictureBitmapByteArray(quotePictureBitmapByteArray); //Set the QuotePicture in the QuotePicture list's byte array member variable to the newly obtained Bitmap byte array

                        //OPEN up the QuotePictureDetailFragment (view of the QuotePicture picture) when the list-item is clicked on
                        //NOTE: Parameters being sent with the intent:
                        //1: ID of the QuotePicture - to identify whether it has been 'Favorited' or not, in order to display the 'Favorites' button accordingly (i.e. activated vs. not activated)
                        //2: Bitmap byte array of the QuotePicture - so as to display the Bitmap. NOTE: The Bitmap could not be sent as an extra, since its SIZE is TOO LARGE
                        Intent quotePictureActivityIntent = QuotePictureDetailActivity.newIntent(getContext(), sCategoryQuotePictureQuotes.get(position).getId(), sCategoryQuotePictureQuotes.get(position).getQuotePictureBitmapByteArray());

                        startActivity(quotePictureActivityIntent); //Start the Intent
                    }
                });


            }


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


        //If the Adapter does not exist yet.. NOTE: When the navigation item holding this fragment (i.e. "Category Quote Pictures" is re-opened), only onStart() and onResume() are called
        if (mCategoryQuotePicturesAdapter == null) {
            mCategoryQuotePicturesAdapter = new CategoryQuotePicturesAdapter(sCategoryQuotePictureQuotes); //Create Adapter and link it to the list of Quote Picture Bitmaps
        }

        mCategoryQuotePicturesAdapter.notifyDataSetChanged(); //Notify that the Adapter's list items list has changed (as per above)

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

//        mCategoryQuotePicturesDownloaderHandlerThread.clearQueue();

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

        Log.i(TAG, "onDetach() called");
    }


}