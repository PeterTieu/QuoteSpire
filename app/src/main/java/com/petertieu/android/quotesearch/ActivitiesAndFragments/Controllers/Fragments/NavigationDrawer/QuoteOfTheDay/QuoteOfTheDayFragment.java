package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

public class QuoteOfTheDayFragment extends DynamicBroadcastReceiver{

    //Log for Logcat
    private final String TAG = "QuoteOfTheDayFragment";




    private static Quote mQuoteOfTheDay = new Quote();

    private LinearLayout mQuoteOfTheDayQuoteBubbleLayout;
    private TextView mQuoteOfTheDayQuoteTitle;
    private CheckBox mQuoteOfTheDayQuoteFavoriteIcon;
    private Button mQuoteOfTheDayQuoteShareIcon;
    private TextView mQuoteOfTheDayQuoteQuote;
    private TextView mQuoteOfTheDayQuoteAuthor;
    private TextView mQuoteOfTheDayQuoteCategory;
    private TextView mQuoteOfTheDayQuoteUnavailable;





    private static Quote mQuoteOfTheDayAuthorQuote = new Quote();

    private LinearLayout mQuoteOfTheDayAuthorBubbleLayout;
    private TextView mQuoteOfTheDayAuthorQuoteTitle;
    private CheckBox mQuoteOfTheDayAuthorQuoteFavoriteIcon;
    private Button mQuoteOfTheDayAuthorQuoteShareIcon;
    private TextView mQuoteOfTheDayAuthorQuoteTitleAuthorName;
    private TextView mQuoteOfTheDayAuthorQuoteQuote;
    private TextView mQuoteOfTheDayAuthorQuoteAuthor;
    private TextView mQuoteOfTheDayAuthorQuoteCategory;
    private TextView mQuoteOfTheDayAuthorQuoteQuoteUnavailable;
    private boolean mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;





    private static Quote mQuoteOfTheDayCategoryQuote = new Quote();

    private LinearLayout mQuoteOfTheDayCategoryBubbleLayout;
    private TextView mQuoteOfTheDayCategoryQuoteTitle;
    private CheckBox mQuoteOfTheDayCategoryQuoteFavoriteIcon;
    private Button mQuoteOfTheDayCategoryQuoteShareIcon;
    private TextView mQuoteOfTheDayCategoryQuoteTitleCategoryName;
    private TextView mQuoteOfTheDayCategoryQuoteQuote;
    private TextView mQuoteOfTheDayCategoryQuoteAuthor;
    private TextView mQuoteOfTheDayCategoryQuoteCategory;
    private TextView mQuoteOfTheDayCategoryQuoteQuoteUnavailable;



    private ProgressBar mProgressBarQuoteOfTheDayQuoteQuote;
    private ProgressBar mProgressBarQuoteOfTheDayAuthorQuote;
    private ProgressBar mProgressBarQuoteOfTheDayCategoryQuote;



    //========= Flags for Enabling/Disabling the "Refresh" toolbar nenu button
    private boolean shouldEnebleRefreshButtonCheckpointOne = false; //Condition #1/2 to be met - when the AsyncTask to fetch the Author Quote is completed
    private boolean shouldEnableRefreshButtonCheckpointTwo = false; //Conidition #2/2 to be met - when the AsyncTask to fetch the Category Quote is completed






    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called");

        setRetainInstance(true);




//        new GetQuoteOfTheDayAsyncTask().execute();
    }


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called");

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.fragment_quote_of_the_day, viewGroup, false);
        mQuoteOfTheDayQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_quote_bubble_layout);
        mQuoteOfTheDayQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_quote_title);
        mQuoteOfTheDayQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_quote_favorite_icon);
        mQuoteOfTheDayQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_quote_share_icon);
        mQuoteOfTheDayQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_quote);
        mQuoteOfTheDayQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_quote_category);
        mQuoteOfTheDayQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_quote_author);
        mQuoteOfTheDayQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_quote_unavailable);


        mQuoteOfTheDayAuthorBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_author_bubble_layout);
        mQuoteOfTheDayAuthorQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title);
        mQuoteOfTheDayAuthorQuoteTitleAuthorName = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title_author_name);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_author_quote_favorite_icon);
        mQuoteOfTheDayAuthorQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_author_quote_share_icon);
        mQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        mQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        mQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);
        mQuoteOfTheDayAuthorQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote_unavailable);



        mQuoteOfTheDayCategoryBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_category_bubble_layout);
        mQuoteOfTheDayCategoryQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title);
        mQuoteOfTheDayCategoryQuoteTitleCategoryName = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title_category_name);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_category_quote_favorite_icon);
        mQuoteOfTheDayCategoryQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_category_quote_share_icon);
        mQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        mQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        mQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);
        mQuoteOfTheDayCategoryQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote_unavailable);


        mProgressBarQuoteOfTheDayQuoteQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_quote_quote);
        mProgressBarQuoteOfTheDayAuthorQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_author_quote);
        mProgressBarQuoteOfTheDayCategoryQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_category_quote);





        mQuoteOfTheDayQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        mQuoteOfTheDayAuthorBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        mQuoteOfTheDayCategoryBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));



        mQuoteOfTheDayQuoteTitle.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);
        mQuoteOfTheDayQuoteUnavailable.setVisibility(View.GONE);

        mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);

        mQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);











        mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);










//        mQuoteOfTheDayAuthorQuote.setFavorite(false);
//        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
//
//
//        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(null);
//        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(mQuoteOfTheDayAuthorQuote.isFavorite());
//
//        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
////                boolean author = isChecked;
//
//                mQuoteOfTheDayAuthorQuote.setFavorite(isChecked);
//
//                compoundButton.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
//
//
//                //Save Quote to FavoriteQuotes SQLite database
//                if (isChecked && isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted == true){
//
//                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) == null){
//
////                        mQuoteOfTheDayAuthorQuote.setFavorite(true);
//
//                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayAuthorQuote);
//                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
//
//                    }
//                    else{
//                        //Do nothing
//                    }
//
//                }
//                if (isChecked == false){
//
////                    mQuoteOfTheDayAuthorQuote.setFavorite(false);
//
//                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayAuthorQuote);
//                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
//                }
//
//            }
//        });









////        mQuoteOfTheDayCategoryQuote.setFavorite(false);
//        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
//
//        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(null);
//        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(mQuoteOfTheDayCategoryQuote.isFavorite());
//
//        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
////                boolean category = isChecked;
//
//                mQuoteOfTheDayCategoryQuote.setFavorite(isChecked);
//
//                compoundButton.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
//
//
//                //Save Quote to FavoriteQuotes SQLite database
//                if (isChecked == true && isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted == true){
//
//                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) == null){
//
////                        mQuoteOfTheDayCategoryQuote.setFavorite(true);
//
//                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayCategoryQuote);
//                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
//
//                    }
//
//                }
//                if (isChecked == false){
//
////                    mQuoteOfTheDayCategoryQuote.setFavorite(false);
//
//                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayCategoryQuote);
//                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
//                }
//
//            }
//        });














        //SHARE BUTTONS
        mQuoteOfTheDayQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");

                shareIntent.putExtra(Intent.EXTRA_TEXT, getQuoteOfTheDayQuoteShareString());

                shareIntent = Intent.createChooser(shareIntent, "Share Quote of the Day via");

                startActivity(shareIntent);
            }
        });


        mQuoteOfTheDayAuthorQuoteShareIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");

                shareIntent.putExtra(Intent.EXTRA_TEXT, getQuoteOfTheDayAuthorQuoteShareString());

                shareIntent = Intent.createChooser(shareIntent, "Share" + " " + mQuoteOfTheDayAuthorQuote.getAuthor() + "'s" + " " + "quote via");

                startActivity(shareIntent);
            }
        });







        mQuoteOfTheDayCategoryQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");

                shareIntent.putExtra(Intent.EXTRA_TEXT, getQuoteOfTheDayCategoryQuoteShareString());

                shareIntent = Intent.createChooser(shareIntent, "Share" + " " + mQuoteOfTheDayCategoryQuote.getAuthor() + "'s" + " " + "quote via");

                startActivity(shareIntent);
            }
        });



        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN2 DATABASE");
        }
        else{
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT2 IN DATABASE");
        }








        displayQuoteOfTheDay();


//        new GetLatestQuoteOfTheDayQuoteAsyncTask().execute();
















        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN3 DATABASE");
        }
        else{
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT3 IN DATABASE");
        }


        //Let the FragmentManager know that it will receive a menu item callback
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.dashboard);




        return view;
    }
















    //TODO: After releasing the app and upon releasing the next revision, add the URL string to the app on the Google Play store
    //TODO: ...at the bottom of the Share string!
    private String getQuoteOfTheDayQuoteShareString(){
        String quoteOfTheDayQuoteQuoteString = "\"" + mQuoteOfTheDay.getQuote() + "\"";

        String quoteOfTheDayQuoteAuthorString = " - " + mQuoteOfTheDay.getAuthor();

        return quoteOfTheDayQuoteQuoteString + quoteOfTheDayQuoteAuthorString;
    }



    private String getQuoteOfTheDayAuthorQuoteShareString(){
        String quoteOfTheDayAuthorQuoteString = "\"" + mQuoteOfTheDayAuthorQuote.getQuote() + "\"";

        String quoteOfTheDayQuoteAuthorQuoteAuthorString = " - " + mQuoteOfTheDayAuthorQuote.getAuthor();

        return quoteOfTheDayAuthorQuoteString + quoteOfTheDayQuoteAuthorQuoteAuthorString;
    }



    private String getQuoteOfTheDayCategoryQuoteShareString(){
        String quoteOfTheDayCategoryQuoteString = "\"" + mQuoteOfTheDayCategoryQuote.getQuote() + "\"";

        String quoteOfTheDayQuoteCategoryQuoteAuthorString = " - " + mQuoteOfTheDayCategoryQuote.getAuthor();

        return quoteOfTheDayCategoryQuoteString + quoteOfTheDayQuoteCategoryQuoteAuthorString;
    }












    private void displayQuoteOfTheDay(){

        if (isAdded()) {


            Log.i(TAG, "displayQuoteOfTheDay() called");


            if (mQuoteOfTheDay.getQuote() != null) {

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);


                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);

                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));




                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null) {

                    mQuoteOfTheDay.setFavorite(true);
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) == null){

                    mQuoteOfTheDay.setFavorite(false);
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                }


            }










            if (mQuoteOfTheDayAuthorQuote.getQuote() != null) {



                if (! mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())) {


                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.VISIBLE);


                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
                    mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                    mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
                    mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
                    mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));


                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) != null) {

                        mQuoteOfTheDayAuthorQuote.setFavorite(true);
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                    } else {
                        mQuoteOfTheDayAuthorQuote.setFavorite(false);
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                    }

                }


                if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())) {

                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_quotes_from_author);


                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());


                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
                }













            }















            if (mQuoteOfTheDayCategoryQuote.getQuote() != null) {



                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);


                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.VISIBLE);


                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));



                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) != null) {

                    mQuoteOfTheDayCategoryQuote.setFavorite(true);
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) == null){

                    mQuoteOfTheDayCategoryQuote.setFavorite(false);
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                }
            }




























            if (mQuoteOfTheDay.getQuote() == null || mQuoteOfTheDayAuthorQuote.getQuote() == null || mQuoteOfTheDayCategoryQuote.getQuote() == null) {





                mQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        mQuoteOfTheDay.setFavorite(isChecked);


                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayAsyncTaskCompleted == true){

                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) == null){



                                mQuoteOfTheDay.setFavorite(true);

                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDay);

                                //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                            }
                            else{
                                //Do nothing
                            }

                        }
                        if (isChecked == false){

                            mQuoteOfTheDay.setFavorite(false);

                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDay);

                            //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                        }


                    }
                });









                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        mQuoteOfTheDayAuthorQuote.setFavorite(isChecked);


                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted == true){

                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) == null){



                                mQuoteOfTheDayAuthorQuote.setFavorite(true);

                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayAuthorQuote);

                                //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                            }
                            else{
                                //Do nothing
                            }

                        }
                        if (isChecked == false){

                            mQuoteOfTheDayAuthorQuote.setFavorite(false);

                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayAuthorQuote);

                            //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                        }


                    }
                });













                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                        mQuoteOfTheDayCategoryQuote.setFavorite(isChecked);


                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted == true){
//                    if ( isChecked == true){
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) == null){

                                mQuoteOfTheDayCategoryQuote.setFavorite(true);

                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayCategoryQuote);

                                //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                            }
                            else{
                                //Do nothing
                            }

                        }
                        if (isChecked == false){

                            mQuoteOfTheDayCategoryQuote.setFavorite(false);

                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayCategoryQuote);

                            //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                        }
                    }
                });
























                new GetQuoteOfTheDayAsyncTask().execute();
//                }
//                catch (NullPointerException npe){
//                    Log.e(TAG, "No Internet Connection!");
//                }

            }

//            else {
//
//
//                try {
//
//                    new GetQuoteOfTheDayAsyncTask().execute();
//                } catch (NullPointerException npe) {
//                    Log.e(TAG, "No Internet Connection!");
//                }
//
//
//            }









        }

    }



































    public boolean stepFive = false;
    public boolean stepSix = false;

    public boolean isGetQuoteOfTheDayAsyncTaskCompleted = true;




    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){


            isGetQuoteOfTheDayAsyncTaskCompleted = false;



            Log.i(TAG, "Reached here");


            Quote quoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay();




            return quoteOfTheDay;

        }



        @Override
        protected void onPostExecute(Quote quoteOfTheDay){


            isGetQuoteOfTheDayAsyncTaskCompleted = true;




            mQuoteOfTheDay = quoteOfTheDay;







                if (mQuoteOfTheDay != null) {


                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                        Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN9 DATABASE");
                    } else {
                        Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT9 IN DATABASE");
                    }


                    mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);


                    mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);


//                boolean quoteOfTheDayIsInFavoriteQuotesDatabase;
//
//                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null){
//                    quoteOfTheDayIsInFavoriteQuotesDatabase = true;
//                }
//                else{
//                    quoteOfTheDayIsInFavoriteQuotesDatabase = false;
//                }
//
//                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(quoteOfTheDayIsInFavoriteQuotesDatabase);


                    //Try risky task - mQuoteOfTheDay.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
                    // REMEMBER: mQuoteOfTheDay still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
                    // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
                    try {

                        mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                        mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                        mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                        mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null) {
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is INNN DATABASE");
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - Set checked - TRUE");
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() QUOTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).getQuote());
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() QUOTEs: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).getId());
//                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
//                    mQuoteOfTheDay.setFavorite(true);
                            mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);

                        } else {
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is NOTTT in DATABASE");
//                    Log.i(TAG, "QOD - Set checked - FALSE");
//                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
//                    mQuoteOfTheDay.setFavorite(false);
                            mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
                        }


//                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
//                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN10 DATABASE");
//                }
//                else{
//                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT10 IN DATABASE");
//                }


                    } catch (NullPointerException npe) {
                        Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask");

                        mQuoteOfTheDayQuoteUnavailable.setVisibility(View.VISIBLE);

                        mQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                        mQuoteOfTheDayQuoteAuthor.setVisibility(View.GONE);
                        mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
                        mQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
                        mQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                        mQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);

                    }
                }





            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


        }
    }













    public boolean isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = true;








    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        String quoteOfTheDayAuthor = mQuoteOfTheDay.getAuthor();

        //Build constructor
        public GetQuoteOfTheDayAuthorQuoteAsyncTask(){

        }


        @Override
        protected Quote doInBackground(Void... params){



            isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = false;


//            mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayAuthorQuote().getQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthor);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayAuthorQuote){


            isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = true;


            mQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote;



            shouldEnebleRefreshButtonCheckpointOne = true; //Condition #1/2 to enable the "Refresh" menu button has been met - as the AsyncTask to fetch the Author Quote has been completed
            //Try risky task - as getActivity().invalidateOptionsMenu() throws a NullPointerException if another navigation drawer is opened
            try{
                //If both conditions to enable the "Refresh" menu button is passed
                if (shouldEnebleRefreshButtonCheckpointOne == true && shouldEnableRefreshButtonCheckpointTwo == true){
                    getActivity().invalidateOptionsMenu(); //Refresh the options menu (i.e. call onCreateOptionsMenu(..))

                }
            }
            catch (NullPointerException npe) {
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because QuoteOfTheDayFragment has been closed");
            }







            //Try risky task - mQuoteOfTheDayAuthorQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
            // REMEMBER: mQuoteOfTheDayAuthorQuote still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
            // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
            try {


//                Log.i(TAG, "Quote of the day Author Quote - Quote String: " + mQuoteOfTheDayAuthorQuote.getQuote());
//                Log.i(TAG, "Quote of the day Author Quote - Category: " + mQuoteOfTheDayAuthorQuote.getCategory());
//                Log.i(TAG, "Quote of the day Author Quote - Author: " + mQuoteOfTheDayAuthorQuote.getAuthor());
//                Log.i(TAG, "Quote of the day Author Quote - ID: " + mQuoteOfTheDayAuthorQuote.getId());


//                if (mQuoteOfTheDay.getQuote() != null && mQuoteOfTheDayAuthorQuote.getQuote() == null) {
//
//                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_internet_connection);
//
//
//                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.GONE);
//                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
//
//
//                }
                if (mQuoteOfTheDayAuthorQuote != null) {


                    if (! mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())) {


                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


                        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.VISIBLE);


                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
                        mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                        mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
                        mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
                        mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));


                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) != null) {
                            mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(true);
                        } else {
                            mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);
                        }

                    }


                    if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())) {

                        mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_quotes_from_author);


                        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());


                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                        mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
                    }


                }





            }
            catch (NullPointerException npe){
                Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAuthorQuoteAsyncTask");
                mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_internet_connection);


                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);




            }









        }

    }











    public boolean isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = true;



    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = mQuoteOfTheDay.getCategory();

        //Build constructor
        public GetQuoteOfTheDayCategoryQuoteAsyncTask(){
        }


        @Override
        protected Quote doInBackground(Void... params){


            isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = false;




//            mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayCategoryQuote().getQuoteOfTheDayCategoryQuote(quoteOfTheDayCategory);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayCategoryQuote) {

            isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = true;

            mQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote;


            Log.i(TAG, "Quote of the day Category Quote - Quote String: " + mQuoteOfTheDayCategoryQuote.getQuote());
            Log.i(TAG, "Quote of the day Category Quote - Category: " + mQuoteOfTheDayCategoryQuote.getCategory());
            Log.i(TAG, "Quote of the day Category Quote - Author: " + mQuoteOfTheDayCategoryQuote.getAuthor());
            Log.i(TAG, "Quote of the day Category Quote - ID: " + mQuoteOfTheDayCategoryQuote.getId());






            shouldEnableRefreshButtonCheckpointTwo = true; //Condition #1/2 to enable the "Refresh" menu button has been met - as the AsyncTask to fetch the Category Quote has been completed
            //Try risky task - as getActivity().invalidateOptionsMenu() throws a NullPointerException if another navigation drawer is opened
            try{
                //If both conditions to enable the "Refresh" menu button is passed
                if (shouldEnebleRefreshButtonCheckpointOne == true && shouldEnableRefreshButtonCheckpointTwo == true){
                    getActivity().invalidateOptionsMenu(); //Refresh the options menu (i.e. call onCreateOptionsMenu(..))
                }
            }
            catch (NullPointerException npe){
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because QuoteOfTheDayFragment has been closed");
            }








            if (mQuoteOfTheDayCategoryQuote != null) {




                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);


                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.VISIBLE);


                //Try risky task - mQuoteOfTheDayCategoryQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
                // REMEMBER: mQuoteOfTheDayCategoryQuote still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
                // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
                try {

                    mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                    mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                    mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
                    mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
                    mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));


                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) != null) {
                        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(true);
                    } else {
                        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);
                    }


                } catch (NullPointerException npe) {
                    Log.e(TAG, "NO INTERNET CONNECTION - Caught in isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted");

                    mQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.VISIBLE);


                    mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.GONE);
                    mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.GONE);

                }
            }


        }



    }


















    private class GetLatestQuoteOfTheDayQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        //Build constructor
        public GetLatestQuoteOfTheDayQuoteAsyncTask(){
        }


        @Override
        protected Quote doInBackground(Void... params){


            return new GetQuoteOfTheDay().getQuoteOfTheDay();
        }


        @Override
        protected void onPostExecute(Quote latestQuoteOfTheDay) {



            mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);

            mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
            mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
            mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
            mQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
            mQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
            mQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);


            try{



                if (mQuoteOfTheDay.getQuote() != null){


                    if (! latestQuoteOfTheDay.getQuote().equals(mQuoteOfTheDay.getQuote())){
                        mQuoteOfTheDay = latestQuoteOfTheDay;

                        mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        Toast.makeText(getActivity(), "Quote Of The Day updated", Toast.LENGTH_LONG).show();

                        new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                        new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

                    }

                }





                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null) {

                    mQuoteOfTheDay.setFavorite(true);
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) == null){

                    mQuoteOfTheDay.setFavorite(false);
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                }



            }
            catch (NullPointerException npe){
                Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask");

                mQuoteOfTheDayQuoteUnavailable.setVisibility(View.VISIBLE);

                mQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mQuoteOfTheDayQuoteAuthor.setVisibility(View.GONE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
                mQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                mQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
            }



        }



    }













    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){

        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_quote_of_the_day, menu);

        MenuItem refreshItem = menu.findItem(R.id.menu_item_refresh_quote_of_the_day_fragment);



//        refreshItem.setEnabled(true); //Enable the "Refresh" menu item button
        refreshItem.getIcon().setAlpha(255); //Set the "Refresh" menu item button to 'full color' (i.e. white)



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case (R.id.menu_item_refresh_quote_of_the_day_fragment):


                shouldEnebleRefreshButtonCheckpointOne = false; //Reset the Condition #1/2 that is used for enabling the "Refresh" menu item button
                shouldEnableRefreshButtonCheckpointTwo = false; //Reset the Condition #2/3 that is used for enabling the "Refresh" menu item button
                menuItem.setEnabled(false); //Disable the "Refresh" menu item button
                menuItem.getIcon().setAlpha(130); //Set the "Refresh" menu item button to 'disabled' color (i.e. grey)


//                if (shouldEnableRefreshButton == true){
//                    menuItem.setEnabled(true);
//                    menuItem.getIcon().setAlpha(255);
//                }
//                else{
//                    menuItem.setEnabled(false);
//                    menuItem.getIcon().setAlpha(130);
//                }







                if (mQuoteOfTheDay.getQuote() != null){

//                    mQuoteOfTheDayQuoteTitle.setVisibility(View.INVISIBLE);
//                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
//                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.INVISIBLE);
//                mQuoteOfTheDayQuoteQuote.setVisibility(View.INVISIBLE);
//                mQuoteOfTheDayQuoteCategory.setVisibility(View.INVISIBLE);
//                mQuoteOfTheDayQuoteAuthor.setVisibility(View.INVISIBLE);


                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


                    mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);






//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);





//                new GetQuoteOfTheDayAsyncTask().execute();

                    new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                    new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

                }




                else{


                    mQuoteOfTheDayQuoteUnavailable.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);
                    mQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);



//                    mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);


                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.INVISIBLE);


                    mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.INVISIBLE);
                    mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.INVISIBLE);





                    mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);



                    new GetQuoteOfTheDayAsyncTask().execute();
                }

//


                return true;


            default:
                return super.onOptionsItemSelected(menuItem);

        }




    }







    @Override
    public void onStart(){
        super.onStart();



        Log.i(TAG, "onStart() called");
    }




    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume() called");







//        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);

        mQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                mQuoteOfTheDay.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayAsyncTaskCompleted == true){
//                    if ( isChecked == true){
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) == null){

                        mQuoteOfTheDay.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDay);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    mQuoteOfTheDay.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDay);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                }
            }
        });









//        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);

        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                mQuoteOfTheDayAuthorQuote.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted == true){

                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) == null){



                        mQuoteOfTheDayAuthorQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayAuthorQuote);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    mQuoteOfTheDayAuthorQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayAuthorQuote);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                }


            }
        });













//        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);

        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                mQuoteOfTheDayCategoryQuote.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted == true){
//                    if ( isChecked == true){
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) == null){

                        mQuoteOfTheDayCategoryQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayCategoryQuote);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    mQuoteOfTheDayCategoryQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayCategoryQuote);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                }
            }
        });







        new GetLatestQuoteOfTheDayQuoteAsyncTask().execute();








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
    public void onDestroy(){
        super.onDestroy();

        Log.i(TAG, "onDestoroy() called");
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();

        Log.i(TAG, "onDestroyView() called");
    }


    @Override
    public void onDetach(){
        super.onDetach();

        Log.i(TAG, "onDetach() called");
    }







}