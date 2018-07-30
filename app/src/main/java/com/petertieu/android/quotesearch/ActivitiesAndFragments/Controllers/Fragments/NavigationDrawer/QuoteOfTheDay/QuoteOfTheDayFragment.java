package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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




    private static Quote mQuoteOfTheDay = new Quote();

    private LinearLayout mQuoteOfTheDayQuoteBubbleLayout;
    private TextView mQuoteOfTheDayQuoteTitle;
    private CheckBox mQuoteOfTheDayQuoteFavoriteIcon;
    private Button mQuoteOfTheDayQuoteShareIcon;
    private TextView mQuoteOfTheDayQuoteQuote;
    private TextView mQuoteOfTheDayQuoteAuthor;
    private TextView mQuoteOfTheDayQuoteCategory;





    private static Quote mQuoteOfTheDayAuthorQuote = new Quote();

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





    private static Quote mQuoteOfTheDayCategoryQuote = new Quote();

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
//        mQuoteOfTheDay.setFavorite(false);
        Log.i(TAG, "QUOTE OF THE DAY QUOTE - " + mQuoteOfTheDay.getQuote());
        Log.i(TAG, "QUOTE OF THE DAY QUOTE - " + mQuoteOfTheDay.isFavorite());



        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN DATABASE");
        }
        else{
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT IN DATABASE");
        }







        if (mQuoteOfTheDay == null){
            Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - mQuoteOfTheDay does NOT EXIST");
        }
        else{
            Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - mQuoteOfTheDay EXISTS");
        }









//        boolean quoteOfTheDayIsFavorite = FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).isFavorite();
//        mQuoteOfTheDay.setFavorite(quoteOfTheDayIsFavorite);


        mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


//        mQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(null);
//        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(mQuoteOfTheDay.isFavorite());


//        mQuoteOfTheDay.setFavorite(false);
        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);

        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - ONCREATEVIEW(): " + mQuoteOfTheDayQuoteFavoriteIcon.isChecked());







        mQuoteOfTheDayQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                Log.i(TAG, "Listener called");

                if (mQuoteOfTheDay == null){
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - INSIDE ON CHECKED CHANGED - mQuoteOfTheDay does NOT EXIST");
                }
                else{
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - INSIDE ON CHECKED CHANGED - mQuoteOfTheDay EXISTS");
                }




//                if (mQuoteOfTheDay != null){
//
//                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()) != null){
//                        isChecked = true;
//                    }
//                    else{
//                        isChecked = false;
//                    }
//                }




                Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked - INSIDE LISTENER: " + isChecked);


                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF CHECKBOX IS PRESSED: " +  mQuoteOfTheDayQuoteFavoriteIcon.isPressed());



                mQuoteOfTheDay.setFavorite(isChecked);



//                compoundButton.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

                compoundButton.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on : R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if ( (isChecked == true && stepFive == true && stepSix == true) || (mQuoteOfTheDayQuoteFavoriteIcon.isPressed())){

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


        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);

        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - ONCREATEVIEW(): " + mQuoteOfTheDayQuoteFavoriteIcon.isChecked());



        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN1 DATABASE");
        }
        else{
            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT1 IN DATABASE");
        }












//        mQuoteOfTheDayAuthorQuote.setFavorite(false);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(null);
        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(mQuoteOfTheDayAuthorQuote.isFavorite());

        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

//                boolean author = isChecked;

//                mQuoteOfTheDayAuthorQuote.setFavorite(isChecked);

                compoundButton.setButtonDrawable(mQuoteOfTheDayAuthorQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if (isChecked){

                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) == null){

//                        mQuoteOfTheDayAuthorQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayAuthorQuote);
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);

                    }
                    else{
                        //Do nothing
                    }

                }
                else{

//                    mQuoteOfTheDayAuthorQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayAuthorQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayAuthorQuote);
                }

            }
        });


//        if (IntroActivity.sQuoteOfTheDayAuthorQuote != null){
//
//            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(IntroActivity.sQuoteOfTheDayAuthorQuote.getId()) != null){
//                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
//            }
//            else{
//                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
//            }
//        }







//        mQuoteOfTheDayCategoryQuote.setFavorite(false);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(null);
        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(mQuoteOfTheDayCategoryQuote.isFavorite());

        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Override onCheckedChanged(..) from CompoundButton.OnCheckedChangedListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

//                boolean category = isChecked;

                mQuoteOfTheDayCategoryQuote.setFavorite(isChecked);

                compoundButton.setButtonDrawable(mQuoteOfTheDayCategoryQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                //Save Quote to FavoriteQuotes SQLite database
                if (isChecked){

                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) == null){

//                        mQuoteOfTheDayCategoryQuote.setFavorite(true);

                        FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mQuoteOfTheDayCategoryQuote);
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);

                    }

                }
                else{

//                    mQuoteOfTheDayCategoryQuote.setFavorite(false);

                    FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mQuoteOfTheDayCategoryQuote);
                    FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mQuoteOfTheDayCategoryQuote);
                }

            }
        });


//        if (IntroActivity.sQuoteOfTheDayCategoryQuote != null){
//
//            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(IntroActivity.sQuoteOfTheDayCategoryQuote.getId()) != null){
//                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
//            }
//            else{
//                mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
//            }
//        }












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

        if (isAdded()){

            Log.i(TAG, "displayQuoteOfTheDay() called");



            if (mQuoteOfTheDay.getQuote() == null || mQuoteOfTheDayAuthorQuote.getQuote() == null || mQuoteOfTheDayCategoryQuote.getQuote() == null){



                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN4 DATABASE");
                }
                else{
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT4 IN DATABASE");
                }


                new GetQuoteOfTheDayAsyncTask().execute();
            }



            else{
                new GetQuoteOfTheDayAsyncTask().execute();


//                if (mQuoteOfTheDayQuoteQuote != null) {
//
//                    Log.i(TAG, "CALLLLLLED");
//
//                    mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
//
//
//                    mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayQuoteCategory.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayQuoteAuthor.setVisibility(View.VISIBLE);
//
//
//
//
//                    mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
//                    mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
//                    mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
//                    mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));
//
//
//
//
//                    if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is INN DATABASE");
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - Set checked - TRUE");
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " +  Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() QUOTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).getId());
//                        mQuoteOfTheDay.setFavorite(true);
//                        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);
//                    }
//                    else{
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is NOTTT in DATABASE");
//                        Log.i(TAG, "QOD - Set checked - FALSE");
//                        Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " +  Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
//                        mQuoteOfTheDay.setFavorite(false);
//                        mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
//                    }
//
//
//
//
//
//                }
//
//
//
//
//
//
//
//
//
//                if (mQuoteOfTheDayAuthorQuote.getQuote() == null){
//
//                    mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
//                    mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
//
//
//                    mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
//                    mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
//
//
//                    mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
//                }
//
//
//
//
//                else if (mQuoteOfTheDayAuthorQuote != null){
//
//
//
//
//                    if (! mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote())){
//
//
//                        mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;
//
//
//                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                        mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);
//
//
//
//                        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.VISIBLE);
//
//
//
//
//
//                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
//                        mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
//                        mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
//                        mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
//                        mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));
//
//
//
//                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
//                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayAuthorQuote.getId()) != null){
//                            mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(true);
//                        }
//                        else{
//                            mQuoteOfTheDayAuthorQuoteFavoriteIcon.setChecked(false);
//                        }
//
//                    }
//
//
//
//                    if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote()) && mQuoteOfTheDayAuthorQuoteAutoRefreshed == false){
//
//
//                        mQuoteOfTheDayAuthorQuoteAutoRefreshed = true;
//
//                        new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();
//
//
//                    }
//
//
//
//                    if (mQuoteOfTheDayAuthorQuote.getQuote().equals(mQuoteOfTheDay.getQuote()) && mQuoteOfTheDayAuthorQuoteAutoRefreshed == true){
//
//                        mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;
//
//                        mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                        mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.GONE);
//                        mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.GONE);
//                        mQuoteOfTheDayAuthorQuoteQuote.setVisibility(View.GONE);
//                        mQuoteOfTheDayAuthorQuoteAuthor.setVisibility(View.GONE);
//                        mQuoteOfTheDayAuthorQuoteCategory.setVisibility(View.GONE);
//
//
//                        mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
//
//
//                        mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.VISIBLE);
//                    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                    if (mQuoteOfTheDayCategoryQuote != null){
//
//                        mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
//
//
//
//
//                        mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.VISIBLE);
//                        mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.VISIBLE);
//
//
//
//
//
//
//
//                        mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
//                        mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
//                        mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
//                        mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
//                        mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));
//
//
//
//                        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
//                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDayCategoryQuote.getId()) != null){
//                            mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(true);
//                        }
//                        else{
//                            mQuoteOfTheDayCategoryQuoteFavoriteIcon.setChecked(false);
//                        }
//                    }
//
//
//                }


















            }








//            mQuoteOfTheDay = IntroActivity.sQuoteOfTheDay;
//            mQuoteOfTheDayAuthorQuote = IntroActivity.sQuoteOfTheDayAuthorQuote;
//            mQuoteOfTheDayCategoryQuote = IntroActivity.sQuoteOfTheDayCategoryQuote;







//            if (mQuoteOfTheDay == null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){
//                new GetQuoteOfTheDayAsyncTask().execute();
//            }
//
//
//
//            //TODO: COMPARTMENTALISE THE BELOW "setText(..)" CALLS INTO METHODS
//
//            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote == null){
//
//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
//
//                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
//                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
//
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
//
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));
//
//
//                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
//                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();
//
//            }
//
//
//
//
//
//            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote == null){
//
//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
//                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//
//                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
//                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));
//
//
//                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
//                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
//                mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
//                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
//                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));
//
//                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();
//            }
//
//
//
//
//
//
//            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote == null && mQuoteOfTheDayCategoryQuote != null){
//
//
//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
//                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
//
//
//                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
//                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));
//
//
//                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
//                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
//                mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
//                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));
//
//                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
//            }
//
//
//
//
//
//
//            if (mQuoteOfTheDay != null && mQuoteOfTheDayAuthorQuote != null && mQuoteOfTheDayCategoryQuote != null){
//
//
//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.GONE);
//                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.GONE);
//                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.GONE);
//
//
//
//                mQuoteOfTheDayQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
//                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
//                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));
//
//
//                mQuoteOfTheDayAuthorQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteTitleAuthorName.setText(mQuoteOfTheDay.getAuthor());
//                mQuoteOfTheDayAuthorQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayAuthorQuoteQuote.setText("\" " + mQuoteOfTheDayAuthorQuote.getQuote() + " \"");
//                mQuoteOfTheDayAuthorQuoteAuthor.setText("- " + mQuoteOfTheDayAuthorQuote.getAuthor());
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + mQuoteOfTheDayAuthorQuote.getCategory());
//                mQuoteOfTheDayAuthorQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayAuthorQuote.getCategories()));
//
//
//                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setText(mQuoteOfTheDay.getCategory());
//                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.VISIBLE);
//                mQuoteOfTheDayCategoryQuoteQuote.setText("\" " + mQuoteOfTheDayCategoryQuote.getQuote() + " \"");
//                mQuoteOfTheDayCategoryQuoteAuthor.setText("- " + mQuoteOfTheDayCategoryQuote.getAuthor());
//                //TODO: Remove "category" variables
//                mQuoteOfTheDayCategoryQuoteCategory.setText("Other Categories: " + mQuoteOfTheDayCategoryQuote.getCategory());
//                mQuoteOfTheDayCategoryQuoteCategory.setText("Categories: " + TextUtils.join(", ", mQuoteOfTheDayCategoryQuote.getCategories()));
//
//            }







        }

    }




    public boolean stepFive = false;
    public boolean stepSix = false;




    private class GetQuoteOfTheDayAsyncTask extends AsyncTask<Void, Void, Quote> {




        //Build constructor
        public GetQuoteOfTheDayAsyncTask(){
        }



        @Override
        protected Quote doInBackground(Void... params){



//            mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);


            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN5 DATABASE");

                stepFive = true;


            }
            else{
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT5 IN DATABASE");

                stepFive = false;
            }


























            Log.i(TAG, "Reached here");








            Quote quoteOfTheDay = new GetQuoteOfTheDay().getQuoteOfTheDay();

















































//            Quote quoteOfTheDay = new Quote();
//            quoteOfTheDay.setFavorite(false);
//            quoteOfTheDay.setQuote("hello");
//            quoteOfTheDay.setCategories(null);
//            quoteOfTheDay.setAuthor("peter");
//            quoteOfTheDay.setId("lfj2lj");


            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF - FROM ASYNCTASK: " + quoteOfTheDay.getId());




            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN6 DATABASE");

                stepSix = true;



            }
            else{
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT6 IN DATABASE");

                stepSix = false;
            }

































































            return quoteOfTheDay;

        }



        @Override
        protected void onPostExecute(Quote quoteOfTheDay){































            Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF - ON POST EXECUTTTE: COMPLETED");


            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {

                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN7 DATABASE");

                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF").getQuote() != null){
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN7 DATABASE QUOOOTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF").getQuote());
                }


                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN7 DATABASE FAVORITTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF").isFavorite());



            }
            else{
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT7 IN DATABASE");
            }




            mQuoteOfTheDay = quoteOfTheDay;

            Log.i(TAG, "Quote of the day - Quote String: " + mQuoteOfTheDay.getQuote());
            Log.i(TAG, "Quote of the day - Category: " + mQuoteOfTheDay.getCategory());
            Log.i(TAG, "Quote of the day - Author: " + mQuoteOfTheDay.getAuthor());
            Log.i(TAG, "Quote of the day - ID: " + mQuoteOfTheDay.getId());



            if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN8 DATABASE");
            }
            else{
                Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT8 IN DATABASE");
            }


            if (mQuoteOfTheDayQuoteQuote != null){


                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN9 DATABASE");
                }
                else{
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


                mQuoteOfTheDayQuoteQuote.setText("\" " + mQuoteOfTheDay.getQuote() + " \"");
                mQuoteOfTheDayQuoteAuthor.setText("- " + mQuoteOfTheDay.getAuthor());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + mQuoteOfTheDay.getCategory());
                mQuoteOfTheDayQuoteCategory.setText("Category: " + TextUtils.join(", ", mQuoteOfTheDay.getCategories()));





                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is INNN DATABASE");
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - Set checked - TRUE");
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() QUOTE: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).getQuote());
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() QUOTEs: " + FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mQuoteOfTheDay.getId()).getId());
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                    mQuoteOfTheDay.setFavorite(true);
                    mQuoteOfTheDayQuoteFavoriteIcon.setChecked(true);

                } else {
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() - 4idFuc8pVqrSmebcJSlRzQeF is NOTTT in DATABASE");
                    Log.i(TAG, "QOD - Set checked - FALSE");
                    Log.i(TAG, "mQuoteOfTheDayFavoriteIcon.isChecked() = : " + Boolean.toString(mQuoteOfTheDayQuoteFavoriteIcon.isChecked()));
                    mQuoteOfTheDayQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                    mQuoteOfTheDay.setFavorite(false);
                    mQuoteOfTheDayQuoteFavoriteIcon.setChecked(false);
                }




                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote("4idFuc8pVqrSmebcJSlRzQeF") != null) {
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS IN10 DATABASE");
                }
                else{
                    Log.i(TAG, "QUOTE OF THE DAY - 4idFuc8pVqrSmebcJSlRzQeF IS NOTT10 IN DATABASE");
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


//                    mQuoteOfTheDayAuthorQuoteAutoRefreshed = false;


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

//

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




//                mQuoteOfTheDayQuoteTitle.setVisibility(View.INVISIBLE);
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
                mQutoeOfTheDayAuthorQuoteQuoteUnavailable.setVisibility(View.GONE);


                mQuoteOfTheDayCategoryQuoteTitle.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteTitleCategoryName.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteFavoriteIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteShareIcon.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteQuote.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteAuthor.setVisibility(View.INVISIBLE);
                mQuoteOfTheDayCategoryQuoteCategory.setVisibility(View.INVISIBLE);






//                mProgressBarQuoteOfTheDayQuoteQuote.setVisibility(View.VISIBLE);
                mProgressBarQuoteOfTheDayAuthorQuote.setVisibility(View.VISIBLE);
                mProgressBarQuoteOfTheDayCategoryQuote.setVisibility(View.VISIBLE);





//                new GetQuoteOfTheDayAsyncTask().execute();

                new GetQuoteOfTheDayAuthorQuoteAsyncTask().execute();
                new GetQuoteOfTheDayCategoryQuoteAsyncTask().execute();



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