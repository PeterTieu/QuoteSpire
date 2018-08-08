package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

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


public class SearchQuotesByKeywordFragment extends Fragment {

    //Log for Logcat
    private final String TAG = "SQBKFragment";


    private static final int NUMBER_OF_QUOTES_TO_LOAD = 7;


    private static String sSearchQuery;
    private TextView mKeywordSearchedString;


    //Declare and INITIALISE the List of Quote objects to size 8
    private static List<Quote> sSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]);

    private RecyclerView mSearchQuotesByKeywordQuoteRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private static SearchQuotesByKeywordAdapter sSearchQuotesByKeywordQuoteAdapter;
    private SearchQuotesByKeywordQuoteViewHolder mSearchQuotesByKeywordQuoteViewHolder;




    private boolean shouldEnableSearchMenuItem = false;


    private TextView mSearchSuggestionsText;
    private Button mSearchSuggestionOne;
    private Button mSearchSuggestionTwo;
    private Button mSearchSuggestionThree;
    private Button mSearchSuggestionFour;
    private Button mSearchSuggestionFive;
    private Button mSearchSuggestionSix;
    private Button mSearchSuggestionSeven;
    private Button mSearchSuggestionEight;
    private Button mSearchSuggestionNine;
    private Button mSearchSuggestionTen;





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


        mKeywordSearchedString = (TextView) view.findViewById(R.id.search_quotes_by_keyword_keyword_searched);
        mKeywordSearchedString.setVisibility(View.GONE);


        mSearchQuotesByKeywordQuoteRecyclerView = (RecyclerView) view.findViewById(R.id.search_quotes_by_keyword_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchQuotesByKeywordQuoteRecyclerView.setLayoutManager(mLinearLayoutManager);




        mSearchSuggestionsText = (TextView) view.findViewById(R.id.search_quotes_by_keyword_search_suggestions_text);
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









        //Display the list of Quotes IF and ONLY IF a search has begun.
        //Otherwise, display the list of suggestions
        if (shouldDisplaySearchItemsWhenFragmentIsReloaded == true) {

            mKeywordSearchedString.setVisibility(View.VISIBLE);
            mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.VISIBLE);

            mSearchSuggestionsText.setVisibility(View.GONE);
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



            mKeywordSearchedString.setText(Html.fromHtml("Keyword searched: " + " " + " \"" +"<i>" + sSearchQuery.toUpperCase() + "</i>" + "\""));

            sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes);
            sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);
            mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter);




        }
        else{

            mKeywordSearchedString.setVisibility(View.GONE);
            mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.GONE);


            mSearchSuggestionsText.setVisibility(View.VISIBLE);
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





            mRandomSearchSuggstions = getRandomSearchSuggstions();
//            Log.i(TAG, "Search suggestions: " + mRandomSearchSuggstions);


            mSearchSuggestionOne.setText(mRandomSearchSuggstions.get(0));
            mSearchSuggestionTwo.setText(mRandomSearchSuggstions.get(1));
            mSearchSuggestionThree.setText(mRandomSearchSuggstions.get(2));
            mSearchSuggestionFour.setText(mRandomSearchSuggstions.get(3));
            mSearchSuggestionFive.setText(mRandomSearchSuggstions.get(4));
            mSearchSuggestionSix.setText(mRandomSearchSuggstions.get(5));
            mSearchSuggestionSeven.setText(mRandomSearchSuggstions.get(6));
            mSearchSuggestionEight.setText(mRandomSearchSuggstions.get(7));
            mSearchSuggestionNine.setText(mRandomSearchSuggstions.get(8));
            mSearchSuggestionTen.setText(mRandomSearchSuggstions.get(9));






            //TODO: Create layout for "Keyword search suggestions: "
        }




//        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++){
//
//            if (sSearchQuotesByKeywordQuotes.get(i) != null){
//                sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes);
//                sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);
//                mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter);
//
//            }
//        }


//        if (!sSearchQuotesByKeywordQuotes.isEmpty()){
//            sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes);
//            sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);
//            mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter);
//        }




//        sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes);
//        sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);
//        mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter);








        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Search Quotes");



//        for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {
//
//
//            if (sSearchQuotesByKeywordQuotes.get(i) == null){
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









    List<String> mRandomSearchSuggstions;




    public List<String> getRandomSearchSuggstions() {
        List<String> mSearchSuggestionsFullList = Arrays.asList("Dog", "Cat", "Power", "Strong", "Imaginations", "Strong",
                "Past", "Present", "Man", "Woman", "House", "Luck", "Weak", "Water", "Metal", "Fire", "Random", "Events",
                "Fast", "Slow", "Learn", "Teach", "Student", "Humanity", "Fortune", "Rich", "Food", "Famine", "Hot", "Cold",
                "Small", "Term", "Long", "Understand", "Wise", "Coward", "Forgive", "Happy", "Sad", "Emotion", "Ambition", "Rain",
                "Mental", "Artist", "Expert", "Universe", "Give", "More", "Movement", "Healthy", "Culture", "Heart", "Amazing",
                "Remarkable", "Interest", "Desire", "Drink", "Time", "Sun", "Infinity", "Human", "Robot", "Machine", "Society",
                "Cog", "Wall", "Divide", "Unity", "Wheel", "Invent", "Normal", "Strange", "Weird", "Adult", "Children", "Future",
                "Conquer", "Loss", "Success", "Defeat", "Win", "Lose", "Fly", "Run", "Walk", "Courage", "Brave", "King", "Queen",
                "Royal", "Equality", "Greed", "Commitment", "Spirituality", "War", "Peace", "Utopia", "Distopia", "Seek", "Friend");

        Collections.shuffle(mSearchSuggestionsFullList);

        int randomSeriesLength = 10;

        List<String> mRandomSearchSuggestions = mSearchSuggestionsFullList.subList(0, randomSeriesLength);

        return mRandomSearchSuggestions;
    }







//    GetSearchQuotesByKeywordAsyncTask mGetSearchQuotesByKeywordAsyncTask;
    List<GetSearchQuotesByKeywordAsyncTask> mGetSearchQuotesByKeywordAsyncTasksList = Arrays.asList(new GetSearchQuotesByKeywordAsyncTask[NUMBER_OF_QUOTES_TO_LOAD]);

    public static boolean shouldDisplaySearchItemsWhenFragmentIsReloaded;




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_search_quotes_by_keyword, menu);




        final MenuItem searchItem = menu.findItem(R.id.menu_item_search_quotes_by_keyword_fragment);


        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search by Keyword");


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

                sSearchQuery = searchQuery;


                searchView.onActionViewCollapsed();


                shouldDisplaySearchItemsWhenFragmentIsReloaded = true;



//                //Cancel any existing and running AsyncTasks that are fetching Quotes based on the keyword query
//                if (mGetSearchQuotesByKeywordAsyncTask != null){
//                    mGetSearchQuotesByKeywordAsyncTask.cancel(true);
//                }
                //Cancel all AsyncTasks (either running or not running) that are fetching Quotes based on the keyword query
                if (!mGetSearchQuotesByKeywordAsyncTasksList.isEmpty()) {

                    for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

                        try{
                            mGetSearchQuotesByKeywordAsyncTasksList.get(i).cancel(true);
                        }
                        catch (NullPointerException npe){
                            Log.e(TAG, "GetSearchQuotesByKeywordAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
                        }


                    }

                }






                mKeywordSearchedString.setVisibility(View.VISIBLE);
                mKeywordSearchedString.setText(Html.fromHtml("Keyword searched: " + " " + " \"" +"<i>" +  searchQuery.toUpperCase() + "</i>" + "\""));


                mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.VISIBLE);

                sSearchQuotesByKeywordQuotes = null;
                sSearchQuotesByKeywordQuotes = Arrays.asList(new Quote[NUMBER_OF_QUOTES_TO_LOAD]);
                sSearchQuotesByKeywordQuoteAdapter = new SearchQuotesByKeywordAdapter(sSearchQuotesByKeywordQuotes);
                sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);
                sSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged();
                mSearchQuotesByKeywordQuoteRecyclerView.setAdapter(sSearchQuotesByKeywordQuoteAdapter);


//                updateUI();






                for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {


                    Integer quotePosition = i;

//                    mGetSearchQuotesByKeywordAsyncTask = new GetSearchQuotesByKeywordAsyncTask(searchQuery);
//                    mGetSearchQuotesByKeywordAsyncTask.execute(quotePosition);
                    mGetSearchQuotesByKeywordAsyncTasksList.set(i, new GetSearchQuotesByKeywordAsyncTask(searchQuery));
                    mGetSearchQuotesByKeywordAsyncTasksList.get(i).execute(quotePosition);






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


            case (R.id.menu_item_clear_all_quotes_by_keyword_fragment):

                shouldDisplaySearchItemsWhenFragmentIsReloaded = false;

                mKeywordSearchedString.setVisibility(View.GONE);
                mSearchQuotesByKeywordQuoteRecyclerView.setVisibility(View.GONE);


                mSearchSuggestionsText.setVisibility(View.VISIBLE);
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



                mRandomSearchSuggstions = getRandomSearchSuggstions();
//            Log.i(TAG, "Search suggestions: " + mRandomSearchSuggstions);


                mSearchSuggestionOne.setText(mRandomSearchSuggstions.get(0));
                mSearchSuggestionTwo.setText(mRandomSearchSuggstions.get(1));
                mSearchSuggestionThree.setText(mRandomSearchSuggstions.get(2));
                mSearchSuggestionFour.setText(mRandomSearchSuggstions.get(3));
                mSearchSuggestionFive.setText(mRandomSearchSuggstions.get(4));
                mSearchSuggestionSix.setText(mRandomSearchSuggstions.get(5));
                mSearchSuggestionSeven.setText(mRandomSearchSuggstions.get(6));
                mSearchSuggestionEight.setText(mRandomSearchSuggstions.get(7));
                mSearchSuggestionNine.setText(mRandomSearchSuggstions.get(8));
                mSearchSuggestionTen.setText(mRandomSearchSuggstions.get(9));








                if (!mGetSearchQuotesByKeywordAsyncTasksList.isEmpty()) {

                    for (int i = 0; i < NUMBER_OF_QUOTES_TO_LOAD; i++) {

                        try{
                            mGetSearchQuotesByKeywordAsyncTasksList.get(i).cancel(true);
                        }
                        catch (NullPointerException npe){
                            Log.e(TAG, "GetSearchQuotesByKeywordAsyncTask AsyncTask is not running. Therefore, cannot be cancelled");
                        }


                    }

                }


                Toast.makeText(getActivity(), "Cleared Search Results", Toast.LENGTH_LONG).show();



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
            sSearchQuotesByKeywordQuotes.set(mQuotePosition[0], searchQuotesByKeywordQuote);


            sSearchQuotesByKeywordQuotes.get(mQuotePosition[0]).setSearchQuotesByKeywordQuotePosition(mQuotePosition[0] + 1);





//            try{
//
//                if (mQuotePosition[0] == NUMBER_OF_QUOTES_TO_LOAD-1){
//                    shouldEnableSearchMenuItem = true;
//                    getActivity().invalidateOptionsMenu();
//                }
//
//                //If the final Quote has NOT been created yet
//                else{
//                    shouldEnableSearchMenuItem = false;
//                    getActivity().invalidateOptionsMenu();
//                }
//
//            }
//            catch (NullPointerException npe){
//                Log.e(TAG, "invalideOptionsMenu() method calls null object - because SearchQuotesByKeywordFragment has been closed");
//            }





            updateUI();
        }


    }










    public void updateUI(){


        sSearchQuotesByKeywordQuoteAdapter.setSearchQuotesByKeywordQuotes(sSearchQuotesByKeywordQuotes);


        sSearchQuotesByKeywordQuoteAdapter.notifyDataSetChanged();

    }








    private class SearchQuotesByKeywordAdapter extends RecyclerView.Adapter<SearchQuotesByKeywordQuoteViewHolder>{


        public SearchQuotesByKeywordAdapter(List<Quote> searchQuotesByKewordQuotes){
            sSearchQuotesByKeywordQuotes = searchQuotesByKewordQuotes;
            Log.i(TAG, "ADAPTER - sSearchQuotesByKeywordQuotes: " + sSearchQuotesByKeywordQuotes);
        }


        @Override
        public int getItemCount(){
            return sSearchQuotesByKeywordQuotes.size();
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

            Quote searchQuotesByKeywordQuote = sSearchQuotesByKeywordQuotes.get(position);



            searchQuotesByKeywordQuoteViewHolder.bind(searchQuotesByKeywordQuote);

        }


        public void setSearchQuotesByKeywordQuotes(List<Quote> searchQuotesByKeywordQuotes){
            sSearchQuotesByKeywordQuotes = searchQuotesByKeywordQuotes;
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