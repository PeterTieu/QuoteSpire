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


    //========= Flags for Enabling/Disabling the "Refresh" toolbar nenu button
    private boolean shouldEnebleRefreshButtonCheckpointOne = false; //Condition #1/2 to be met - when the AsyncTask to fetch the Author Quote is completed
    private boolean shouldEnableRefreshButtonCheckpointTwo = false; //Conidition #2/2 to be met - when the AsyncTask to fetch the Category Quote is completed




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

        View view = layoutInflater.inflate(R.layout.fragment_quote_of_the_day, viewGroup, false);
        sQuoteOfTheDayQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_quote_bubble_layout);
        sQuoteOfTheDayQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_quote_title);
        sQuoteOfTheDayQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_quote_favorite_icon);
        sQuoteOfTheDayQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_quote_share_icon);
        sQuoteOfTheDayQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_quote);
        sQuoteOfTheDayQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_quote_category);
        sQuoteOfTheDayQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_quote_author);
        sQuoteOfTheDayQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_quote_unavailable);


        sQuoteOfTheDayAuthorBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_author_bubble_layout);
        sQuoteOfTheDayAuthorQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title);
        sQuoteOfTheDayAuthorQuoteTitleAuthorName = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title_author_name);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_author_quote_favorite_icon);
        sQuoteOfTheDayAuthorQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_author_quote_share_icon);
        sQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        sQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        sQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);
        sQuoteOfTheDayAuthorQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote_unavailable);



        sQuoteOfTheDayCategoryBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_category_bubble_layout);
        sQuoteOfTheDayCategoryQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title);
        sQuoteOfTheDayCategoryQuoteTitleCategoryName = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title_category_name);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_category_quote_favorite_icon);
        sQuoteOfTheDayCategoryQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_category_quote_share_icon);
        sQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        sQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        sQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);
        sQuoteOfTheDayCategoryQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote_unavailable);


        mProgressBarQuoteOfTheDayQuoteQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_quote_quote);
        mProgressBarQuoteOfTheDayAuthorQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_author_quote);
        mProgressBarQuoteOfTheDayCategoryQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_category_quote);





        sQuoteOfTheDayQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        sQuoteOfTheDayAuthorBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        sQuoteOfTheDayCategoryBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));



        sQuoteOfTheDayQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);
        sQuoteOfTheDayQuoteUnavailable.setVisibility(View.GONE);

        sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);

        sQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);
        sQuoteOfTheDayCategoryQuoteQuoteUnavailable.setVisibility(View.GONE);











        sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        sQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        sQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
















        //SHARE BUTTONS
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
        String quoteOfTheDayQuoteQuoteString = "\"" + sQuoteOfTheDay.getQuote() + "\"";

        String quoteOfTheDayQuoteAuthorString = " - " + sQuoteOfTheDay.getAuthor();

        return quoteOfTheDayQuoteQuoteString + quoteOfTheDayQuoteAuthorString;
    }



    private String getQuoteOfTheDayAuthorQuoteShareString(){
        String quoteOfTheDayAuthorQuoteString = "\"" + sQuoteOfTheDayAuthorQuote.getQuote() + "\"";

        String quoteOfTheDayQuoteAuthorQuoteAuthorString = " - " + sQuoteOfTheDayAuthorQuote.getAuthor();

        return quoteOfTheDayAuthorQuoteString + quoteOfTheDayQuoteAuthorQuoteAuthorString;
    }



    private String getQuoteOfTheDayCategoryQuoteShareString(){
        String quoteOfTheDayCategoryQuoteString = "\"" + sQuoteOfTheDayCategoryQuote.getQuote() + "\"";

        String quoteOfTheDayQuoteCategoryQuoteAuthorString = " - " + sQuoteOfTheDayCategoryQuote.getAuthor();

        return quoteOfTheDayCategoryQuoteString + quoteOfTheDayQuoteCategoryQuoteAuthorString;
    }












    private void displayQuoteOfTheDay(){

        if (isAdded()) {


            Log.i(TAG, "displayQuoteOfTheDay() called");


            if (sQuoteOfTheDay.getQuote() != null) {

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










            if (sQuoteOfTheDayAuthorQuote.getQuote() != null) {



                if (! sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {


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


                if (sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

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















            if (sQuoteOfTheDayCategoryQuote.getQuote() != null) {



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




























            if (sQuoteOfTheDay.getQuote() == null || sQuoteOfTheDayAuthorQuote.getQuote() == null || sQuoteOfTheDayCategoryQuote.getQuote() == null) {





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




            sQuoteOfTheDay = quoteOfTheDay;







                if (sQuoteOfTheDay != null) {


                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                        Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN9 DATABASE");
                    } else {
                        Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT9 IN DATABASE");
                    }


                    mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);


                    sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
                    sQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);


//                boolean quoteOfTheDayIsInFavoriteQuotesDatabase;
//
//                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) != null){
//                    quoteOfTheDayIsInFavoriteQuotesDatabase = true;
//                }
//                else{
//                    quoteOfTheDayIsInFavoriteQuotesDatabase = false;
//                }
//
//                sQuoteOfTheDayQuoteFavoriteIcon.setChecked(quoteOfTheDayIsInFavoriteQuotesDatabase);


                    //Try risky task - sQuoteOfTheDay.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
                    // REMEMBER: sQuoteOfTheDay still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
                    // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
                    try {

                        sQuoteOfTheDayQuoteQuote.setText("\" " + sQuoteOfTheDay.getQuote() + " \"");
                        sQuoteOfTheDayQuoteAuthor.setText("- " + sQuoteOfTheDay.getAuthor());
                        sQuoteOfTheDayQuoteCategory.setText("Category: " + sQuoteOfTheDay.getCategory());
                        sQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", sQuoteOfTheDay.getCategories()));


                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()) != null) {
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is INNN DATABASE");
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() - Set checked - TRUE");
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(sQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() QUOTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()).getQuote());
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() QUOTEs: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(sQuoteOfTheDay.getId()).getId());
//                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
//                    sQuoteOfTheDay.setFavorite(true);
                            sQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);

                        } else {
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is NOTTT in DATABASE");
//                    Log.i(TAG, "QOD - Set checked - FALSE");
//                    Log.i(TAG, "sQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(sQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                    sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
//                    sQuoteOfTheDay.setFavorite(false);
                            sQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
                        }


//                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
//                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN10 DATABASE");
//                }
//                else{
//                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT10 IN DATABASE");
//                }


                    } catch (NullPointerException npe) {
                        Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask");

                        sQuoteOfTheDayQuoteUnavailable.setVisibility(View.VISIBLE);

                        sQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                        sQuoteOfTheDayQuoteAuthor.setVisibility(View.GONE);
                        sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
                        sQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
                        sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);
                        sQuoteOfTheDayQuoteCategory.setVisibility(View.GONE);

                    }
                }





            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


        }
    }













    public boolean isGetQuoteOfTheDayAuthorQuoteAsyncTaskCompleted = true;








    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        String quoteOfTheDayAuthor = sQuoteOfTheDay.getAuthor();

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


            sQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote;



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







            //Try risky task - sQuoteOfTheDayAuthorQuote.getAuthor()/getQuote()/isFavorite(), etc. would throw NullPointerException IF there is no internet connection.
            // REMEMBER: sQuoteOfTheDayAuthorQuote still exists even if there is no Internet, as it is created from the GetQuoteOfTheDayAuthorQuote class.
            // No internet connection just means that its member/isntance variables would be undeclared and therefore NULL
            try {


//                Log.i(TAG, "Quote of the day Author Quote - Quote String: " + sQuoteOfTheDayAuthorQuote.getQuote());
//                Log.i(TAG, "Quote of the day Author Quote - Category: " + sQuoteOfTheDayAuthorQuote.getCategory());
//                Log.i(TAG, "Quote of the day Author Quote - Author: " + sQuoteOfTheDayAuthorQuote.getAuthor());
//                Log.i(TAG, "Quote of the day Author Quote - ID: " + sQuoteOfTheDayAuthorQuote.getId());


//                if (sQuoteOfTheDay.getQuote() != null && sQuoteOfTheDayAuthorQuote.getQuote() == null) {
//
//                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
//                    sQuoteOfTheDayAuthorQuoteQuoteUnavailable.setText(R.string.no_internet_connection);
//
//
//                    sQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.GONE);
//                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
//                    sQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
//
//
//                }
                if (sQuoteOfTheDayAuthorQuote != null) {


                    if (! sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {


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


                    if (sQuoteOfTheDayAuthorQuote.getQuote().equals(sQuoteOfTheDay.getQuote())) {

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
            catch (NullPointerException npe){
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











    public boolean isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted = true;



    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = sQuoteOfTheDay.getCategory();

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

            sQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote;


            Log.i(TAG, "Quote of the day Category Quote - Quote String: " + sQuoteOfTheDayCategoryQuote.getQuote());
            Log.i(TAG, "Quote of the day Category Quote - Category: " + sQuoteOfTheDayCategoryQuote.getCategory());
            Log.i(TAG, "Quote of the day Category Quote - Author: " + sQuoteOfTheDayCategoryQuote.getAuthor());
            Log.i(TAG, "Quote of the day Category Quote - ID: " + sQuoteOfTheDayCategoryQuote.getId());






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








            if (sQuoteOfTheDayCategoryQuote != null) {




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


                } catch (NullPointerException npe) {
                    Log.e(TAG, "NO INTERNET CONNECTION - Caught in isGetQuoteOfTheDayCategoryQuoteAsyncTaskCompleted");

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

            sQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
            sQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);


            try{



                if (sQuoteOfTheDay.getQuote() != null){


                    if (! latestQuoteOfTheDay.getQuote().equals(sQuoteOfTheDay.getQuote())){
                        sQuoteOfTheDay = latestQuoteOfTheDay;

                        sQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(sQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                        Toast.makeText(getActivity(), "Quote Of The Day updated", Toast.LENGTH_LONG).show();

                        new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                        new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

                    }

                }





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
            catch (NullPointerException npe){
                Log.e(TAG, "NO INTERNET CONNECTION - Caught in GetQuoteOfTheDayAsyncTask");

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


//                if (shouldEnableRefreshButton == true){
//                    menuItem.setEnabled(true);
//                    menuItem.getIcon().setAlpha(255);
//                }
//                else{
//                    menuItem.setEnabled(false);
//                    menuItem.getIcon().setAlpha(130);
//                }







                if (sQuoteOfTheDay.getQuote() != null){

//                    sQuoteOfTheDayQuoteTitle.setVisibility(View.INVISIBLE);
//                sQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
//                sQuoteOfTheDayQuoteShareIcon.setVisibility(View.INVISIBLE);
//                sQuoteOfTheDayQuoteQuote.setVisibility(View.INVISIBLE);
//                sQuoteOfTheDayQuoteCategory.setVisibility(View.INVISIBLE);
//                sQuoteOfTheDayQuoteAuthor.setVisibility(View.INVISIBLE);


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