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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities.IntroActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.List;

import javax.net.ssl.ManagerFactoryParameters;

public class QuoteOfTheDayFragment extends DynamicBroadcastReceiver{

    //Log for Logcat
    private final String TAG = "QuoteOfTheDayFragment";




    private Quote mQuoteOfTheDay = new Quote();

    private LinearLayout mQuoteOfTheDayQuoteBubbleLayout;
    private TextView mQuoteOfTheDayQuoteTitle;
    private CheckBox mQuoteOfTheDayQuoteFavoriteIcon;
    private Button mQuoteOfTheDayQuoteShareIcon;
    private TextView mQuoteOfTheDayQuoteQuote;
    private TextView mQuoteOfTheDayQuoteAuthor;
    private TextView mQuoteOfTheDayQuoteCategory;





    private Quote mQuoteOfTheDayAuthorQuote = new Quote();

    private LinearLayout mQuoteOfTheDayAuthorBubbleLayout;
    private TextView mQuoteOfTheDayAuthorQuoteTitle;
    private CheckBox mQuoteOfTheDayAuthorQuoteFavoriteIcon;
    private Button mQuoteOfTheDayAuthorQuoteShareIcon;
    private TextView mQuoteOfTheDayAuthorQuoteTitleAuthorName;
    private TextView mQuoteOfTheDayAuthorQuoteQuote;
    private TextView mQuoteOfTheDayAuthorQuoteAuthor;
    private TextView mQuoteOfTheDayAuthorQuoteCategory;
    private TextView mQutoeOfTheDayAuthorQuoteQuoteUnavailable;
    private boolean mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;





    private Quote mQuoteOfTheDayCategoryQuote = new Quote();

    private LinearLayout mQuoteOfTheDayCategoryBubbleLayout;
    private TextView mQuoteOfTheDayCategoryQuoteTitle;
    private CheckBox mQuoteOfTheDayCategoryQuoteFavoriteIcon;
    private Button mQuoteOfTheDayCategoryQuoteShareIcon;
    private TextView mQuoteOfTheDayCategoryQuoteTitleCategoryName;
    private TextView mQuoteOfTheDayCategoryQuoteQuote;
    private TextView mQuoteOfTheDayCategoryQuoteAuthor;
    private TextView mQuoteOfTheDayCategoryQuoteCategory;



    private ProgressBar mProgressBarQuoteOfTheDayQuoteQuote;
    private ProgressBar mProgressBarQuoteOfTheDayAuthorQuote;
    private ProgressBar mProgressBarQuoteOfTheDayCategoryQuote;





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


        mQuoteOfTheDayAuthorBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_author_bubble_layout);
        mQuoteOfTheDayAuthorQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title);
        mQuoteOfTheDayAuthorQuoteTitleAuthorName = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_title_author_name);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_author_quote_favorite_icon);
        mQuoteOfTheDayAuthorQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_author_quote_share_icon);
        mQuoteOfTheDayAuthorQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote);
        mQuoteOfTheDayAuthorQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_author);
        mQuoteOfTheDayAuthorQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_category);
        mQutoeOfTheDayAuthorQuoteQuoteUnavailable = (TextView) view.findViewById(R.id.quote_of_the_day_author_quote_quote_unavailable);



        mQuoteOfTheDayCategoryBubbleLayout = (LinearLayout) view.findViewById(R.id.quote_of_the_day_category_bubble_layout);
        mQuoteOfTheDayCategoryQuoteTitle = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title);
        mQuoteOfTheDayCategoryQuoteTitleCategoryName = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_title_category_name);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_of_the_day_category_quote_favorite_icon);
        mQuoteOfTheDayCategoryQuoteShareIcon = (Button) view.findViewById(R.id.quote_of_the_day_category_quote_share_icon);
        mQuoteOfTheDayCategoryQuoteQuote = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_quote);
        mQuoteOfTheDayCategoryQuoteAuthor = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_author);
        mQuoteOfTheDayCategoryQuoteCategory = (TextView) view.findViewById(R.id.quote_of_the_day_category_quote_category);


        mProgressBarQuoteOfTheDayQuoteQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_quote_quote);
        mProgressBarQuoteOfTheDayAuthorQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_author_quote);
        mProgressBarQuoteOfTheDayCategoryQuote = (ProgressBar) view.findViewById(R.id.progress_bar_quote_of_the_day_category_quote);





        mQuoteOfTheDayQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        mQuoteOfTheDayAuthorBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));
        mQuoteOfTheDayCategoryBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));



        mQuoteOfTheDayQuoteTitle.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.GONE);

        mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.GONE);
        mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);

        mQuoteOfTheDayQuoteShareIcon.setVisibility(View.GONE);
        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
        mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.GONE);











        //FAVORITES BUTTONS
        mQuoteOfTheDay.setFavorite(false);
        mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        mQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mQuoteOfTheDay.setFavorite(isChecked);

                compoundButton.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if (isChecked){
                    FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDay);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                }
                else{
                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDay);

                    //NOTE: Even if the below line is omitted, the favorites implementation still wouldn't be affected
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDay);
                }


            }
        });

        //If the static QOD Quote from IntroActivity EXISTS.
        // This only happens when sQuoteOfTheDay is created in IntroActivity BEFORE MainActivity begins
        if (IntroActivity.sQuoteOfTheDay != null){

            //If the static QOD Quote from IntroActivity is already in the FavoriteQuotes SQLiteDatabase
            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(IntroActivity.sQuoteOfTheDay.getId()) != null){
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
            }
            else{
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
            }
        }






        mQuoteOfTheDayAuthorQuote.setFavorite(false);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mQuoteOfTheDayAuthorQuote.setFavorite(isChecked);

                compoundButton.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if (isChecked){
                    FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayAuthorQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                }
                else{
                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayAuthorQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                }

            }
        });


        if (IntroActivity.sQuoteOfTheDayAuthorQuote != null){

            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(IntroActivity.sQuoteOfTheDayAuthorQuote.getId()) != null){
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
            }
            else{
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
            }
        }







        mQuoteOfTheDayCategoryQuote.setFavorite(false);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                mQuoteOfTheDayCategoryQuote.setFavorite(isChecked);

                compoundButton.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if (isChecked){
                    FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayCategoryQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                }
                else{
                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayCategoryQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                }

            }
        });


        if (IntroActivity.sQuoteOfTheDayCategoryQuote != null){

            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(IntroActivity.sQuoteOfTheDayCategoryQuote.getId()) != null){
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
            }
            else{
                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
            }
        }












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




        displayQuoteOfTheDay();

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

        if (isAdded()){

            Log.i(TAG, "called");







            mQuoteOfTheDay = IntroActivity.sQuoteOfTheDay;
            mQuoteOfTheDayAuthorQuote = IntroActivity.sQuoteOfTheDayAuthorQuote;
            mQuoteOfTheDayCategoryQuote = IntroActivity.sQuoteOfTheDayCategoryQuote;



            if (mQuoteOfTheDay == null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){
                new GetQuoteOfTheDayAsyncTask().execute();
            }



            //TODO: COMPARTMENTALISE THE BELOW "setText(..)" CALLS INTO METHODS

            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);

                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());

                //TODO: Remove "category" variables
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());

                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();

            }





            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote == null){

                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);

                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));

                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();
            }






            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote != null){


                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);


                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                //TODO: Remove "category" variables
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
                //TODO: Remove "category" variables
                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));

                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            }






            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote != null){


                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);



                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                //TODO: Remove "category" variables
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));


                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
                mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
                //TODO: Remove "category" variables
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));


                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
                mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
                //TODO: Remove "category" variables
                mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));

            }







        }

    }







    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){



//            mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);


            return new GetQuoteOfTheDay().getQuoteOfTheDay();
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDay){
            mQuoteOfTheDay = quoteOfTheDay;

            Log.i(TAG, "Quote of the day - Quote String: " + mQuoteOfTheDay.getQuote());
            Log.i(TAG, "Quote of the day - Category: " + mQuoteOfTheDay.getCategory());
            Log.i(TAG, "Quote of the day - Author: " + mQuoteOfTheDay.getAuthor());
            Log.i(TAG, "Quote of the day - ID: " + mQuoteOfTheDay.getId());



            if (mQuoteOfTheDayQuoteQuote != null){

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
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null){
                    mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
                }
                else{
                    mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
                }




            }




            new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
            new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


        }
    }








    private class GetQuoteOfTheDayAuthorQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        String quoteOfTheDayAuthor = mQuoteOfTheDay.getAuthor();

        //Build constructor
        public GetQuoteOfTheDayAuthorQuoteAsyncTask(){

        }


        @Override
        protected Quote doInBackground(Void... params){


//            mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayAuthorQuote().getQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthor);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayAuthorQuote){


            mQuoteOfTheDayAuthorQuote = quoteOfTheDayAuthorQuote;

            Log.i(TAG, "Quote of the day Author Quote - Quote String: " + mQuoteOfTheDayAuthorQuote.getQuote());
            Log.i(TAG, "Quote of the day Author Quote - Category: " + mQuoteOfTheDayAuthorQuote.getCategory());
            Log.i(TAG, "Quote of the day Author Quote - Author: " + mQuoteOfTheDayAuthorQuote.getAuthor());
            Log.i(TAG, "Quote of the day Author Quote - ID: " + mQuoteOfTheDayAuthorQuote.getId());





            if (mQuoteOfTheDayAuthorQuote.getQuote() == null){

                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);


                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());


                mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
            }




            else if (mQuoteOfTheDayAuthorQuote != null){




                if (! mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())){


                    mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;


                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);



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
                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) != null){
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(true);
                    }
                    else{
                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);
                    }

                }



                if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote()) && mQuoteOfTheDayAuthorQuoteAutoRefreshed == false){


                    mQuoteOfTheDayAuthorQuoteAutoRefreshed = true;

                    new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();


                }



                if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote()) && mQuoteOfTheDayAuthorQuoteAutoRefreshed == true){

                    mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;

                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);


                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());


                    mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
                }




            }









        }

    }















    private class GetQuoteOfTheDayCategoryQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{

        String quoteOfTheDayCategory = mQuoteOfTheDay.getCategory();

        //Build constructor
        public GetQuoteOfTheDayCategoryQuoteAsyncTask(){
        }


        @Override
        protected Quote doInBackground(Void... params){

//            mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);

            return new GetQuoteOfTheDayCategoryQuote().getQuoteOfTheDayCategoryQuote(quoteOfTheDayCategory);
        }


        @Override
        protected void onPostExecute(Quote quoteOfTheDayCategoryQuote){
            mQuoteOfTheDayCategoryQuote = quoteOfTheDayCategoryQuote;


            Log.i(TAG, "Quote of the day Category Quote - Quote String: " + mQuoteOfTheDayCategoryQuote.getQuote());
            Log.i(TAG, "Quote of the day Category Quote - Category: " + mQuoteOfTheDayCategoryQuote.getCategory());
            Log.i(TAG, "Quote of the day Category Quote - Author: " + mQuoteOfTheDayCategoryQuote.getAuthor());
            Log.i(TAG, "Quote of the day Category Quote - ID: " + mQuoteOfTheDayCategoryQuote.getId());



            if (mQuoteOfTheDayCategoryQuote != null){

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
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) != null){
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(true);
                }
                else{
                    mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);
                }
            }


        }


    }







    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){

        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.fragment_quote_of_the_day, menu);

        MenuItem refreshItem = menu.findItem(R.id.menu_item_refresh_quote_of_the_day_fragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case (R.id.menu_item_refresh_quote_of_the_day_fragment):




                mQuoteOfTheDayQuoteTitle.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayQuoteQuote.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayQuoteCategory.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayQuoteAuthor.setVisibility(View.INVISIBLE);


                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.INVISIBLE);
                mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


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
    }


    @Override
    public void onPause(){
        super.onPause();

        Log.i(TAG, "onPause() called");
    }







}