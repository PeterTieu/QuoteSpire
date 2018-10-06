package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

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

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.PushNotification.DynamicBroadcastReceiver;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.R;

public class QuoteOfTheDayFragment extends DynamicBroadcastReceiver {

    //================= Declare INSTANCE VARIABLES ==============================================================

    private final String TAG = "QuoteOfTheDayFragment"; //Log for Logcat


    //QUOTE OF THE DAY
    private static Quote sQuoteOfTheDay = new Quote(); //Quote Of The Day (static so that it is not lost even when the app is closed)
        //Layout
    private LinearLayout sQuoteOfTheDayQuoteBubbleLayout;
    private TextView sQuoteOfTheDayQuoteTitle;
    private CheckBox sQuoteOfTheDayQuoteFavoriteIcon;
    private Button sQuoteOfTheDayQuoteShareIcon;
    private TextView sQuoteOfTheDayQuoteQuote;
    private TextView sQuoteOfTheDayQuoteAuthor;
    private TextView sQuoteOfTheDayQuoteCategory;
    private TextView sQuoteOfTheDayQuoteUnavailable;


    //ALSO FROM *AUTHOR*
    private static Quote sQuoteOfTheDayAuthorQuote = new Quote(); //Quote Of The Day's Author Quote (static so that it is not lost even when the app is closed)
    private LinearLayout sQuoteOfTheDayAuthorBubbleLayout;
    private TextView sQuoteOfTheDayAuthorQuoteTitle;
    private CheckBox sQuoteOfTheDayAuthorQuoteFavoriteIcon;
    private Button sQuoteOfTheDayAuthorQuoteShareIcon;
    private TextView sQuoteOfTheDayAuthorQuoteTitleAuthorName;
    private TextView sQuoteOfTheDayAuthorQuoteQuote;
    private TextView sQuoteOfTheDayAuthorQuoteAuthor;
    private TextView sQuoteOfTheDayAuthorQuoteCategory;
    private TextView sQuoteOfTheDayAuthorQuoteQuoteUnavailable;


    //ALSO ON (INSPIRE) *CATEGORY*
    private static Quote sQuoteOfTheDayCategoryQuote = new Quote(); //Quote Of The Day's Category Quote (static so that it is not lost even when the app is closed)

    private LinearLayout sQuoteOfTheDayCategoryBubbleLayout;
    private TextView sQuoteOfTheDayCategoryQuoteTitle;
    private CheckBox sQuoteOfTheDayCategoryQuoteFavoriteIcon;
    private Button sQuoteOfTheDayCategoryQuoteShareIcon;
    private TextView sQuoteOfTheDayCategoryQuoteTitleCategoryName;
    private TextView sQuoteOfTheDayCategoryQuoteQuote;
    private TextView sQuoteOfTheDayCategoryQuoteAuthor;
    private TextView sQuoteOfTheDayCategoryQuoteCategory;
    private TextView sQuoteOfTheDayCategoryQuoteQuoteUnavailable;


    //PROGRESS BARS
    private ProgressBar mProgressBarQuoteOfTheDayQuoteQuote;
    private ProgressBar mProgressBarQuoteOfTheDayAuthorQuote;
    private ProgressBar mProgressBarQuoteOfTheDayCategoryQuote;


    //========= Flags for Enabling/Disabling the "Refresh" toolbar nenu button ==============
    private boolean shouldEnebleRefreshButtonCheckpointOne = false; //Condition #1/2 to be met - when the AsyncTask to fetch the Author Quote is completed
    private boolean shouldEnableRefreshButtonCheckpointTwo = false; //Conidition #2/2 to be met - when the AsyncTask to fetch the Category Quote is completed


    //========= Flags for whether the AsyncTasks are completed =============
    public boolean isGetQuoteOfTheDayAsyncTaskCompleted = true;
    public boolean isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = true;
    public boolean isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = true;




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(..) called"); //Log for Logcat

        setRetainInstance(true); //Retain fragment instance upon configuration changes (redundant)
    }


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat


        //--------- Assign View reference variables to associated views -------------
            //Quote of The Day
        View view = layoutInflater.inflate(R.layout.fragment_quote_of_the_day, viewGroup, false);
        sQuoteOfTheDayQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_quote_bubble_layout);
        sQuoteOfTheDayQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_quote_title);
        sQuoteOfTheDayQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_quote_favorite_icon);
        sQuoteOfTheDayQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_quote_share_icon);
        sQuoteOfTheDayQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_quote);
        sQuoteOfTheDayQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_quote_category);
        sQuoteOfTheDayQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_quote_author);
        sQuoteOfTheDayQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_quote_unavailable);

            //Quote of The Day AUTHOR
        sQuoteOfTheDayAuthorBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_author_bubble_layout);
        sQuoteOfTheDayAuthorQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title);
        sQuoteOfTheDayAuthorQuoteTitleAuthorName = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title_author_name);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_author_quote_favorite_icon);
        sQuoteOfTheDayAuthorQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_author_quote_share_icon);
        sQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        sQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        sQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);
        sQuoteOfTheDayAuthorQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote_unavailable);

        //Quote of The Day CATEGORY (i.e. "inspire")
        sQuoteOfTheDayCategoryBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_category_bubble_layout);
        sQuoteOfTheDayCategoryQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title);
        sQuoteOfTheDayCategoryQuoteTitleCategoryName = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title_category_name);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_category_quote_favorite_icon);
        sQuoteOfTheDayCategoryQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_category_quote_share_icon);
        sQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        sQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        sQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);
        sQuoteOfTheDayCategoryQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote_unavailable);

            //Progress bars
        mProgressBarQuoteOfTheDayQuoteQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_quote_quote);
        mProgressBarQuoteOfTheDayAuthorQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_author_quote);
        mProgressBarQuoteOfTheDayCategoryQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_category_quote);


        //--------- Set drawables to the Bubble Layout Views -------------
        sQuoteOfTheDayQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        sQuoteOfTheDayAuthorBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        sQuoteOfTheDayCategoryBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));


        //--------- Initialise visibility of certain Views -------------
            //Quote of The Day
        sQuoteOfTheDayQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayQuoteUnavailable.setVisibility(View.GONE);

        //Quote of The Day AUTHOR
        sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);

        //Quote of The Day CATEGORY (i.e. "inspire")
        sQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);


        //--------- Set drawables to Favorite buttons of each Bubble -------------
        sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);





        //--------- Set listeners to Share buttons of each Bubble -------------
            //Quote of The Day
        sQuoteOfTheDayQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

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

            //Quote of The Day AUTHOR
        sQuoteOfTheDayAuthorQuoteShareIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getQuoteOfTheDayAuthorQuoteShareString());
                shareIntent = Intent.createChooser(shareIntent, "Share" + " " + sQuoteOfTheDayAuthorQuote.getAuthor() + "'s" + " " + "quote via");
                startActivity(shareIntent);
            }
        });

            //Quote of The Day CATEGORY
        sQuoteOfTheDayCategoryQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getQuoteOfTheDayCategoryQuoteShareString());
                shareIntent = Intent.createChooser(shareIntent, "Share" + " " + sQuoteOfTheDayCategoryQuote.getAuthor() + "'s" + " " + "quote via");
                startActivity(shareIntent);
            }
        });



        displayQuoteOfTheDay();


        setHasOptionsMenu(true); //Let the FragmentManager know that it will receive a menu item callback

        getActivity().setTitle(R.string.dashboard); //Set title to the Fragment

        return view;
    }




    //Helper method - get the SHARE STRING of: Quote of The Day
    private String getQuoteOfTheDayQuoteShareString(){
        String quoteOfTheDayQuoteQuoteString = "\"" + sQuoteOfTheDay.getQuote() + "\""; //Quote
        String quoteOfTheDayQuoteAuthorString = " - " + sQuoteOfTheDay.getAuthor(); //Author
        return quoteOfTheDayQuoteQuoteString + quoteOfTheDayQuoteAuthorString; //Complete String
    }




    //Helper method - get the SHARE STRING of: Quote of The Day AUTHOR
    private String getQuoteOfTheDayAuthorQuoteShareString(){
        String quoteOfTheDayAuthorQuoteString = "\"" + sQuoteOfTheDayAuthorQuote.getQuote() + "\""; //Quote
        String quoteOfTheDayQuoteAuthorQuoteAuthorString = " - " + sQuoteOfTheDayAuthorQuote.getAuthor(); //Author
        return quoteOfTheDayAuthorQuoteString + quoteOfTheDayQuoteAuthorQuoteAuthorString; //Complete String
    }




    //Helper method - get the SHARE STRING of: Quote of The Day CATEGORY
    private String getQuoteOfTheDayCategoryQuoteShareString(){
        String quoteOfTheDayCategoryQuoteString = "\"" + sQuoteOfTheDayCategoryQuote.getQuote() + "\""; //Quote
        String quoteOfTheDayQuoteCategoryQuoteAuthorString = " - " + sQuoteOfTheDayCategoryQuote.getAuthor(); //Author
        return quoteOfTheDayCategoryQuoteString + quoteOfTheDayQuoteCategoryQuoteAuthorString; //Complete String
    }




    //CONFIGURE each of the Views (QOD, QOD AUTHOR, QOC CATEGORY)
    private void displayQuoteOfTheDay(){

        //If the fragment is added to the activity
        if (isAdded()) {

            Log.i(TAG, "displayQuoteOfTheDay() called"); //Log to Logcat



            //If QOD variable EXISTS
            if (sQuoteOfTheDay.getQuote() != null) {

                //Set visibilities of each QOD View
                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteQuote.setText("\" " + sQuoteOfTheDay.getQuote() + " \"");
                sQuoteOfTheDayQuoteAuthor.setText("- " + sQuoteOfTheDay.getAuthor());
                sQuoteOfTheDayQuoteCategory.setText("Category: " + sQuoteOfTheDay.getCategory());
                sQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", sQuoteOfTheDay.getCategories()));

                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) != null) {
                    sQuoteOfTheDay.setFavorite(true);
                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) == null){
                    sQuoteOfTheDay.setFavorite(false);
                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                }
            }



            //If QOD AUTHOR variable EXISTS
            if (sQuoteOfTheDayAuthorQuote.getQuote() != null) {

                //If the QOD AUTHOR is DIFFERENT to the QOD
                if (! sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

                    //Set visibilities of each QOD AUTHOR View - display the QOD AUTHOR
                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(sQuoteOfTheDay.getAuthor());
                    sQuoteOfTheDayAuthorQuoteQuote.setText("\" " + sQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                    sQuoteOfTheDayAuthorQuoteAuthor.setText("- " + sQuoteOfTheDayAuthorQuote.getAuthor());
                    sQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + sQuoteOfTheDayAuthorQuote.getCategory());
                    sQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", sQuoteOfTheDayAuthorQuote.getCategories()));

                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayAuthorQuote.getId()) != null) {
                        sQuoteOfTheDayAuthorQuote.setFavorite(true);
                        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                    } else {
                        sQuoteOfTheDayAuthorQuote.setFavorite(false);
                        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                    }
                }

                //If the QOD AUTHOR is the SAME as the QOD
                if (sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

                    //Set visibilities of each QOD AUTHOR View - DO NOT display the QOD AUTHOR
                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_quotes_from_author);
                    sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(sQuoteOfTheDay.getAuthor());
                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
                }
            }



            //If QOD CATEGORY variable EXISTS
            if (sQuoteOfTheDayCategoryQuote.getQuote() != null) {

                //Set visibilities of each QOD CATEGORY View - display the QOD CATEGORY
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
                sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(sQuoteOfTheDay.getCategory());
                sQuoteOfTheDayCategoryQuoteQuote.setText("\" " + sQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                sQuoteOfTheDayCategoryQuoteAuthor.setText("- " + sQuoteOfTheDayCategoryQuote.getAuthor());
                sQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + sQuoteOfTheDayCategoryQuote.getCategory());
                sQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", sQuoteOfTheDayCategoryQuote.getCategories()));


                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayCategoryQuote.getId()) != null) {
                    sQuoteOfTheDayCategoryQuote.setFavorite(true);
                    sQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayCategoryQuote.getId()) == null){
                    sQuoteOfTheDayCategoryQuote.setFavorite(false);
                    sQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                }
            }



            //If QOD or QOD AUTHOR or QOD CATEGORY - DO NOT exist
            if (sQuoteOfTheDay.getQuote() == null || sQuoteOfTheDayAuthorQuote.getQuote() == null || sQuoteOfTheDayCategoryQuote.getQuote() == null) {


                //Set listner for QOD Favorites Button
                sQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        sQuoteOfTheDay.setFavorite(isChecked); //Set Favorite for QOD based on whether the Favorite is checked or not
                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off); //Set Favorite button drawable


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayAsyncTaskCompleted == true){

                            //If the QOD does NOT exist in the Favorite Quotes SQLiteDatabase
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) == null){
                                sQuoteOfTheDay.setFavorite(true); //Set Favorite for QOD to true
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDay); //Add QOD to the Favorite Quotes SQLiteDatabase
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDay); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                            }
                            else{
                                //Do nothing
                            }
                        }
                        if (isChecked == false){
                            sQuoteOfTheDay.setFavorite(false); //Set Favorite for QOD to false
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDay); //Remove QOD to the Favorite Quotes SQLiteDatabase
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDay); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                        }
                    }
                });



                //Set listner for QOD AUTHOR Favorites Button
                sQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        sQuoteOfTheDayAuthorQuote.setFavorite(isChecked); //Set Favorite for QOD AUTHOR based on whether the Favorite is checked or not
                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off); //Set Favorite button drawable


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted == true){

                            //If the QOD AUTHOR does NOT exist in the Favorite Quotes SQLiteDatabase
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayAuthorQuote.getId()) == null){
                                sQuoteOfTheDayAuthorQuote.setFavorite(true); //Set Favorite for QOD AUTHOR to true
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDayAuthorQuote); //Add QOD AUTHOR to the Favorite Quotes SQLiteDatabase
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayAuthorQuote); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                            }
                            else{
                                //Do nothing
                            }
                        }
                        if (isChecked == false){
                            sQuoteOfTheDayAuthorQuote.setFavorite(false);  //Set Favorite for QOD AUTHOR to false
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDayAuthorQuote); //Remove QOD AUTHOR to the Favorite Quotes SQLiteDatabase
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayAuthorQuote); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                        }


                    }
                });



                //Set listner for QOD CATEGORY Favorites Button
                sQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        sQuoteOfTheDayCategoryQuote.setFavorite(isChecked); //Set Favorite for QOD AUTHOR based on whether the Favorite is checked or not
                        compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off); //Set Favorite button drawable


                        //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                        //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                        // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                        // Putting isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted in the conditional statement blocks this from happening
                        if ( isChecked == true && isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted == true){

                            //If the QOD CATEGORY does NOT exist in the Favorite Quotes SQLiteDatabase
                            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayCategoryQuote.getId()) == null){
                                sQuoteOfTheDayCategoryQuote.setFavorite(true); //Set Favorite for QOD AUTHOR to true
                                FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDayCategoryQuote); //Add QOD CATEGORY to the Favorite Quotes SQLiteDatabase
                                FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayCategoryQuote); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                            }
                            else{
                                //Do nothing
                            }
                        }
                        if (isChecked == false){
                            sQuoteOfTheDayCategoryQuote.setFavorite(false); //Set Favorite for QOD CATEGORY to false
                            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDayCategoryQuote); //Add QOD CATEGORY to the Favorite Quotes SQLiteDatabase
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayCategoryQuote); //NOTE: Even if this line is omitted, the favorites implementation still wouldn't be affected
                        }
                    }
                });



                //Start AsyncTask to fetch the QOD (which would in turn call the AsyncTasks to fetch the QOD AUTHOR and QOD CATEGORY
                new GetQuoteOfTheDayAsyncTask().execute();
            }

        }

    }




    //AsyncTask - Fetches the QOD Quote, Author and Categories via JSON networking, then stashes them to the sQuoteOfTheDay static instance variable
    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {


        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }


        //Perform main AsyncTask operation
        @Override
        protected Quote doInBackground(Void... params){
            isGetQuoteOfTheDayAsyncTaskCompleted = false; //Flag to indicate AsyncTask is incomplete
            Quote quoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay(); //Obtain QOD Quote object via networking
            return quoteOfTheDay;
        }


        //When main AsyncTask operation is completed
        @Override
        protected void onPostExecute(Quote quoteOfTheDay){
            isGetQuoteOfTheDayAsyncTaskCompleted = true; //Flag to indicate AsyncTask is completed
            sQuoteOfTheDay = quoteOfTheDay; //Refer sQuoteOfTheDay to the Quote object referred to by quoteOfTheDay

            //If sQuoteOfTheDay EXISTS
            if (sQuoteOfTheDay != null) {
                //Congfigure view visibilities
                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);

                //Try risky task - sQuoteOfTheDay.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
                // REMEMBER: sQuoteOfTheDay still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
                // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
                try {
                    //Configure view visibilities
                    sQuoteOfTheDayQuoteQuote.setText("\" " + sQuoteOfTheDay.getQuote() + " \"");
                    sQuoteOfTheDayQuoteAuthor.setText("- " + sQuoteOfTheDay.getAuthor());
                    sQuoteOfTheDayQuoteCategory.setText("Category: " + sQuoteOfTheDay.getCategory());
                    sQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", sQuoteOfTheDay.getCategories()));

                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) != null) {
                        sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true); //
                    } else{
                        sQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
                    }
                }
                catch (NullPointerException npe) {
                    Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask"); //Log to Logcat

                    //Configure view visibilities
                    sQuoteOfTheDayQuoteUnavailable.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayQuoteAuthor.setVisibility(View.GONE);
                    sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                    sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                    }
                }

            //Once the QOD is loaded... begin AsyncTasks to obtain QOD AUTHOR and QOD CATEGORY
            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


        }
    }




    //AsyncTask - Fetches the QOD AUTHOR Quote, Author and Categories via JSON networking, then stashes them to the sQuoteOfTheDayAuthorQuote static instance variable
    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayAuthor = sQuoteOfTheDay.getAuthor(); //Instance variable referringt to the QOD AUTHOR


        //Build constructor
        public GetQuoteOfTheDayAuthorQuoteAsyncTask(){
        }


        //Perform main AsyncTask operation
        @Override
        protected Quote doInBackground(Void... params){
            isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = false; //Flag to indicate AsyncTask is incomplete
            return new GetQuoteOfTheDayAuthorQuote().getQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthor); //Obtain QOD AUTHOR Quote object via networking, and return it as a parameter in onPostExecute(..)
        }


        //When main AsyncTask operation is completed
        @Override
        protected void onPostExecute(Quote quoteOfTheDayAuthorQuote){
            isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = true; //Flag to indicate AsyncTask is completed
            sQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote; //Refer sQuoteOfTheDayAuthorQuote to the Quote object referred to by quoteOfTheDayAuthorQuote

            shouldEnebleRefreshButtonCheckpointOne = true; //Condition #1/2 to enable the "Refresh" menu button has been met - as the AsyncTask to fetch the Author Quote has been completed

            //Try risky task - as getActivity().invalidateOptionsMenu() throws a NullPointerException if another navigation drawer list item is opened
            try{
                //If both conditions to enable the "Refresh" menu button is passed
                if (shouldEnebleRefreshButtonCheckpointOne == true && shouldEnableRefreshButtonCheckpointTwo == true){
                    getActivity().invalidateOptionsMenu(); //Refresh the options menu (i.e. call onCreateOptionsMenu(..))
                }
            }
            catch (NullPointerException npe) {
                Log.e(TAG, "invalideOptionsMenu() method calls null object - because QuoteOfTheDayFragment has been closed");
            }



            //Try risky task - sQuoteOfTheDayAuthorQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
            // REMEMBER: sQuoteOfTheDayAuthorQuote still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
            // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
            try {
                //If sQuoteOfTheDayAuthorQuote aexists
                if (sQuoteOfTheDayAuthorQuote != null) {

                    //If the Quotes of sQuoteOfTheDayAuthorQuote AND sQuoteOfTheDayare DIFFERENT
                    if (! sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

                        //Configure view visibilities
                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.VISIBLE);

                        sQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(sQuoteOfTheDay.getAuthor());
                        sQuoteOfTheDayAuthorQuoteQuote.setText("\" " + sQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                        sQuoteOfTheDayAuthorQuoteAuthor.setText("- " + sQuoteOfTheDayAuthorQuote.getAuthor());
                        sQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + sQuoteOfTheDayAuthorQuote.getCategory());
                        sQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", sQuoteOfTheDayAuthorQuote.getCategories()));


                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayAuthorQuote.getId()) != null) {
                            sQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(true);
                        } else {
                            sQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);
                        }
                    }


                    //If the Quotes of sQuoteOfTheDayAuthorQuote AND sQuoteOfTheDayare the SAME
                    if (sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

                        //Configure view visibilities
                        sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_quotes_from_author);
                        sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                        sQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(sQuoteOfTheDay.getAuthor());

                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                        sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
                    }
                }
            }
            //Catch NullPointerException thrown by sQuoteOfTheDayAuthorQuote.getAuthor()/getQuote()/isFavorite() when there is no internet connection to obtain these values
            catch (NullPointerException npe){

                //Configure view visibilities
                Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAuthorQuoteAsyncTask");
                sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_internet_connection);

                sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
            }

        }
    }




    //AsyncTask - Fetches the QOD CATEGORY Quote, Author and Categories via JSON networking, then stashes them to the sQuoteOfTheDayCategoryQuote static instance variable
    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = sQuoteOfTheDay.getCategory(); //Instance variable referringt to the QOD CATEGORY

        //Build constructor
        public GetQuoteOfTheDayCategoryQuoteAsyncTask(){
        }


        //Perform main AsyncTask operation
        @Override
        protected Quote doInBackground(Void... params){
            isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = false; //Flag to indicate AsyncTask is incomplete
            return new GetQuoteOfTheDayCategoryQuote().getQuoteOfTheDayCategoryQuote(quoteOfTheDayCategory); //Obtain QOD CATEGORY Quote object via networking, and return it as a parameter in onPostExecute(..)
        }


        //When main AsyncTask operation is completed
        @Override
        protected void onPostExecute(Quote quoteOfTheDayCategoryQuote) {
            isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = true; //Flag to indicate AsyncTask is completed
            sQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote; //Refer sQuoteOfTheDayCategoryQuote to the Quote object referred to by quoteOfTheDayCategoryQuote

            shouldEnableRefreshButtonCheckpointTwo = true; //Condition #2/2 to enable the "Refresh" menu button has been met - as the AsyncTask to fetch the Category Quote has been completed

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


            //Try risky task - sQuoteOfTheDayCategoryQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
            // REMEMBER: sQuoteOfTheDayAuthorQuote still exists even if there is no Internet, as it is created from the sQuoteOfTheDayCategoryQuote class.
            // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
            if (sQuoteOfTheDayCategoryQuote != null) {

                //Configure view visibilities
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
                sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.VISIBLE);
                sQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.VISIBLE);


                //Try risky task - sQuoteOfTheDayCategoryQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
                // REMEMBER: sQuoteOfTheDayCategoryQuote still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
                // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
                try {
                    //Configure view visibilities
                    sQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(sQuoteOfTheDay.getCategory());
                    sQuoteOfTheDayCategoryQuoteQuote.setText("\" " + sQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                    sQuoteOfTheDayCategoryQuoteAuthor.setText("- " + sQuoteOfTheDayCategoryQuote.getAuthor());
                    sQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + sQuoteOfTheDayCategoryQuote.getCategory());
                    sQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", sQuoteOfTheDayCategoryQuote.getCategories()));

                    //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayCategoryQuote.getId()) != null) {
                        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(true);
                    } else {
                        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);
                    }
                }
                //Catch NullPointerException thrown by sQuoteOfTheDayCategoryQuote.getAuthor()/getQuote()/isFavorite() when there is no internet connection to obtain these values
                catch (NullPointerException npe) {
                    Log.e(TAG, "NO INTERNET CONNECTION - Caught in isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted");

                    //Configure view visibilities
                    sQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.GONE);
                    mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.GONE);
                }
            }

        }

    }




    //AsyncTask - Fetches the LATEST QOD Quote, Author and Categories via JSON networking, then stashes them to the sQuoteOfTheDay static instance variable.
    //NOTE: This class is called in onResume(..), i.e. whenever the "Dashboard/QOD" navigation drawer is opened
    private class GetLatestQuoteOfTheDayQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        //Build constructor
        public GetLatestQuoteOfTheDayQuoteAsyncTask(){
        }


        //Perform main AsyncTask operation
        @Override
        protected Quote doInBackground(Void... params){
            return new GetQuoteOfTheDay().getQuoteOfTheDay(); //Obtain LATEST QOD Quote object via networking
        }


        //When main AsyncTask operation is completed
        @Override
        protected void onPostExecute(Quote latestQuoteOfTheDay) {

            //Configure view visibilities
            mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
            sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);


            //Try risky task - sQuoteOfTheDay.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
            // REMEMBER: sQuoteOfTheDay still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
            // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
            try{

                //If the sQuoteOfTheDay Quote EXISTS
                if (sQuoteOfTheDay.getQuote() != null){

                    //If the Quote of the LATEST QOD and QOD are DIFFERENT (i.e. this means QOD needs to be updated to be the same as the LATEST QOD)
                    if (! latestQuoteOfTheDay.getQuote().equals(sQuoteOfTheDay.getQuote())){
                        sQuoteOfTheDay = latestQuoteOfTheDay; //Update sQuoteOfTheDay
                        sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off); //Show whether the LATEST QOD is favorited or not
                        Toast.makeText(getActivity(), "Quote Of The Day updated", Toast.LENGTH_LONG).show(); //Notify the user via Toast that the QOD Quote has been changed

                        new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute(); //Call GetQuoteOfTheDayAuthorQuoteAsyncTask
                        new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute(); //Call GetQuoteOfTheDayCategoryQuoteAsyncTask
                    }

                }

                //Update the views based on the new/updated QOD
                sQuoteOfTheDayQuoteQuote.setText("\" " + sQuoteOfTheDay.getQuote() + " \"");
                sQuoteOfTheDayQuoteAuthor.setText("- " + sQuoteOfTheDay.getAuthor());
                sQuoteOfTheDayQuoteCategory.setText("Category: " + sQuoteOfTheDay.getCategory());
                sQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", sQuoteOfTheDay.getCategories()));


                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) != null) {
                    sQuoteOfTheDay.setFavorite(true);
                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) == null){
                    sQuoteOfTheDay.setFavorite(false);
                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                }
            }
            //Catch NullPointerException thrown by sQuoteOfTheDay.getAuthor()/getQuote()/isFavorite() if there is no internet connection
            catch (NullPointerException npe){
                Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask"); //Log to Logcat

                //Configure view visibilties
                sQuoteOfTheDayQuoteUnavailable.setVisibility(View.VISIBLE);
                sQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteAuthor.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
            }

        }

    }




    //
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

        Log.i(TAG, "onOptionsItemsSelected(..) called"); //Log lifecycle callback

        switch (menuItem.getItemId()){
            case (R.id.menu_item_refresh_quote_of_the_day_fragment):


                shouldEnebleRefreshButtonCheckpointOne = false; //Reset the Condition #1/2 that is used for enabling the "Refresh" menu item button
                shouldEnableRefreshButtonCheckpointTwo = false; //Reset the Condition #2/3 that is used for enabling the "Refresh" menu item button
                menuItem.setEnabled(false); //Disable the "Refresh" menu item button
                menuItem.getIcon().setAlpha(130); //Set the "Refresh" menu item button to 'disabled' color (i.e. grey)







                if (sQuoteOfTheDay.getQuote() != null){




                    sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


                    sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);






//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);
                    mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);





//                new GetQuoteOfTheDayAsyncTask().execute();

                    new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                    new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

                }




                else{


                    sQuoteOfTheDayQuoteUnavailable.setVisibility(View.GONE);
                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);
                    sQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);



//                    sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);


                    sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.INVISIBLE);


                    sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.INVISIBLE);
                    sQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.INVISIBLE);





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







//        sQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);

        sQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                sQuoteOfTheDay.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayAsyncTaskCompleted == true){
//                    if ( isChecked == true){
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) == null){

                        sQuoteOfTheDay.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDay);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDay);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    sQuoteOfTheDay.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDay);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDay);
                }
            }
        });









//        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);

        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                sQuoteOfTheDayAuthorQuote.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted == true){

                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayAuthorQuote.getId()) == null){



                        sQuoteOfTheDayAuthorQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDayAuthorQuote);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayAuthorQuote);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    sQuoteOfTheDayAuthorQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDayAuthorQuote);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayAuthorQuote);
                }


            }
        });













//        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);

        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                sQuoteOfTheDayCategoryQuote.setFavorite(isChecked);


                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //isGetQuoteOfTheDayAsyncTaskCompleted is a boolean marker to identify whether the GetQuoteOfTheDayAsyncTask's doInBackground() method is completed.
                //This is added because for some weird & mysterious reason, sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true) is called everytime after
                // GetQuoteOfTheDay().getQuoteOfTheDay() is completed in doInBackground() of the isGetQuoteOfTheDayAsyncTask class.
                // Putting isGetQuoteOfTheDayAsyncTaskCompleted in the conditional statement blocks this from happening
                if ( isChecked == true && isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted == true){
//                    if ( isChecked == true){
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDayCategoryQuote.getId()) == null){

                        sQuoteOfTheDayCategoryQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(sQuoteOfTheDayCategoryQuote);

                        //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayCategoryQuote);
                    }
                    else{
                        //Do nothing
                    }

                }
                if (isChecked == false){

                    sQuoteOfTheDayCategoryQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(sQuoteOfTheDayCategoryQuote);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(sQuoteOfTheDayCategoryQuote);
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