package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.Quotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
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

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.R;

import java.util.List;


//Fragment for displaying Favorited Quotes
public class FavoriteQuotesFragment extends Fragment {


    //================= INSTANCE VARIABLES ==============================================================
    private final String TAG = "FavoriteQuotesFragment"; //Log for Logcat

    //RecyclerView variables
    private RecyclerView mFavoriteQuotesRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FavoriteQuotesAdapter mFavoriteQuotesAdapter;
    private FavoriteQuotesViewHolder mFavoriteQuotesViewHolder;

    private TextView mNoFavoriteQuotesText; //TextView to indicate if no Quotes are favorited




    //================= METHODS ===========================================================================

    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.i(TAG, "onAttach() called"); //Log in Logcat
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called"); //Log in Logcat
        setHasOptionsMenu(true); //Declare that the fragme3nt has an options menu
    }




    //Override onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_favorite_quotes, viewGroup, false); //Assign View variable to layout resource file

        //Assign RecyclerView variables to associated Views
        mFavoriteQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_quotes_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true); //Reverse list order so that NEWER list items are at the TOP
        mLinearLayoutManager.setStackFromEnd(true); //Reverse list order so that NEWER list items are at the TOP
        mFavoriteQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNoFavoriteQuotesText = (TextView) view.findViewById(R.id.no_favorite_quotes_text); //Assign TextView variable


        getActivity().setTitle("Favorites"); //Set title of the (swipe tab) Fragment


        //If there ARE Quotes in the Favorite Quots SQLite Database
        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
            mNoFavoriteQuotesText.setVisibility(View.GONE); //Remove the TextView indicating that there are no Quotes Favorited
        }
        //If there are NO Quotes in the Favorite Quots SQLite Database
        else{
            mNoFavoriteQuotesText.setVisibility(View.VISIBLE); //Show the TextView indicating that there are no Quotes Favorited
        }

        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes(); //Obtain all Favorited Quotes from SQLiteDatabase of Favorite Quotes

        mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes); //Set RecyclerView-Adapter to the Favorite Quotes
        mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter); //Set the RecyclerView to the RecyclerView-Adapter

        return view;
    }




    //Helper method - called to update the UI - called every time a Favorite icon is pressed (to remove a Quote) OR when the "Remove All" options menu icon is pressed
    public void updateUI(){

        //Refresh the options menu every time a list item is added/removed, so we could re-evaluate whether the menu item "Remove all" is still appropriate
        getActivity().invalidateOptionsMenu();


        //If there ARE Quotes in the Favorite Quots SQLite Database
        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
            mNoFavoriteQuotesText.setVisibility(View.GONE); //Remove the TextView indicating that there are no Quotes Favorited
        }
        //If there are NO Quotes in the Favorite Quots SQLite Database
        else{
            mNoFavoriteQuotesText.setVisibility(View.VISIBLE); //Show the TextView indicating that there are no Quotes Favorited
        }

        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();  //Obtain all Favorited Quotes from SQLiteDatabase of Favorite Quotes

        mFavoriteQuotesAdapter.setFavoriteQuotes(mFavoriteQuotes); //Set RecyclerView-Adapter to the Favorite Quotes
        mFavoriteQuotesAdapter.notifyDataSetChanged(); //Set the RecyclerView to the RecyclerView-Adapter
    }




    //Adapter for RecyclerView (RecyclerView-Adapter)
    private class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuotesViewHolder>{

        private List<Quote> mFavoriteQuotes; //List of Favorite Quotes


        //Constructor
        public FavoriteQuotesAdapter(List<Quote> favoriteQuotes){
            mFavoriteQuotes = favoriteQuotes; //Stash the Favorite Quotes parameter to the instance variable
            Log.i(TAG, "ADAPTER - mFavoriteQuotes: " + mFavoriteQuotes); //Log to Logcat
        }


        //Override getItemCount() method
        @Override
        public int getItemCount(){
            return mFavoriteQuotes.size(); //Size of the List of Favorite Quotes
        }


        //Override onCreateViewHolder(..) method
        @Override
        public FavoriteQuotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Instantiate LayoutInflater
            View view = layoutInflater.inflate(R.layout.list_item_favorite_quote, viewGroup, false); //Inflate the Layout of the ViewHolder (i.e. list item)
            mFavoriteQuotesViewHolder = new FavoriteQuotesViewHolder(view); //Instantiate the ViewHolder object with its view

            return mFavoriteQuotesViewHolder; //Return ViewHolder
        }


        //Override onBindViewHolder(..) method
        @Override
        public void onBindViewHolder(FavoriteQuotesViewHolder favoriteQuotesViewHolder, int position){

            Quote favoriteQuote = mFavoriteQuotes.get(position); //Obtain Quote to be binded to ViewHolder
            favoriteQuotesViewHolder.bind(favoriteQuote); //Bind Quote to ViewHolder

            favoriteQuotesViewHolder.mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Set the ViewHolder Favorited icon to "on"

            Log.i(TAG, "ADAPTER - favoriteQuote: " + favoriteQuote.getQuote()); //Log Qutoe to Logcat
        }


        //Set the List of Favorite Quotes to the Adapter
        public void setFavoriteQuotes(List<Quote> favoriteQuotes){
            mFavoriteQuotes = favoriteQuotes; //Stash List parameter to instance variable
        }

    }



    //Snackbar ref. variables relating to removing Quotes from the SQLiteDatabase of Favorited Quotes
    private Snackbar snackbar;
    private Snackbar snackbar1;


    //ViewHolder for RecyclerView (RecyclerView-ViewHolder)
    private class FavoriteQuotesViewHolder extends RecyclerView.ViewHolder{

        public Quote mFavoriteQuote; //Quote (that is binded to the ViewHolder)

        //View instance variables
        private LinearLayout mFavoriteQuoteBubbleLayout; //Layout of ViewHolder
        private TextView mFavoriteQuoteQuote; //TextView to display Quote
        private TextView mFavoriteQuoteAuthorName; //TextView to display Author's name
        private CheckBox mFavoriteQuoteFavoriteIcon; //Favorite Quote icon
        private Button mFavoriteQuoteShareIcon; //Share icon
        private ProgressBar mFavoriteQuoteProgressBar; //Progress Bar



        //Constructor
        public FavoriteQuotesViewHolder(View view){
            super(view);

            //Assign View instance variables to associated resource IDs
            mFavoriteQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.favorite_quote_category_bubble_layout);
            mFavoriteQuoteAuthorName = (TextView) view.findViewById(R.id.favorite_quote_author_name);
            mFavoriteQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.favorite_quote_favorite_icon);
            mFavoriteQuoteShareIcon = (Button) view.findViewById(R.id.favorite_quote_share_icon);
            mFavoriteQuoteQuote = (TextView) view.findViewById(R.id.favorite_quote_quote);


            mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Set drawable Favorite Icon to "on"
            mFavoriteQuoteFavoriteIcon.setChecked(true); //Set Favorite Icon to 'checked'



            //Set listener for Favorite Icon
            mFavoriteQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                //Override onCheckedChanged(..) method
                @Override
                public void onCheckedChanged(final CompoundButton compoundButton, boolean isChecked) {

                    //Set drawable of the Favorite Icon based on whether it is in the SQLiteDatabase of Favorited Quotes
                    // NOTE: If it is a Favorited Quote, then IT must be "on" anyway. This line is redundant
                    compoundButton.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                    //If the Favorite Icon is 'unchecked' (... then remove the Quote from the Favorited Quotes SQLiteDatabase, and from the RecyclerView List)
                    if (isChecked == false){

                        final Quote favoriteQuote = mFavoriteQuote; //Assign instance variable to local variable


                        mFavoriteQuote.setFavorite(false); //Set the Favorited Quote's Favorite member variable to 'false'
                        FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mFavoriteQuote); //Remove the Favorited Quote from the Favorited Quotes SQLiteDatabase
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mFavoriteQuote); //Update the Favorited Quotes SQLiteDatabase


                        //If the Favorited Quote DOES NOT exist in the SQLiteDatabase of Favorited Quotes (as it has just been removed)
                        // ... then.. Display a Snackbar to allow user to "UNDO" the removal of this Favorited Quote (i.e. re-add it to SQLiteDatabase of Favorited Quotes)
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mFavoriteQuote.getId()) == null){

                            compoundButton.setButtonDrawable(R.drawable.ic_imageview_favorite_off); //Change the Favorite Icon drawable to "off"


                            final Handler handler = new Handler(); //Create a HandlerThread to display the Snackbar to allow the user to "UNDO" the removal of this Favorited Quote

                            //Run the HandlerThread
                            handler.postDelayed(new Runnable() {

                                //What to do AFTER the 100ms delay
                                @Override
                                public void run() {

                                    updateUI(); //Update the Fragment to take into account the fact that the Favorited Quote has been removed

                                    //Create a Snackar to allow the user to "UNDO" the removal of the Favorited Quote
                                    snackbar = Snackbar
                                            .make(mFavoriteQuotesRecyclerView, Html.fromHtml("<font color=\"#ffffff\">Quote has been removed from Favorites</font>"), Snackbar.LENGTH_LONG) //Make the Snackbar

                                            //Set the "UNDO" action button for the SnackBar
                                            .setAction("UNDO", new View.OnClickListener() {

                                                //Override onClick(..) method to set listener for "UNCO" action button
                                                @Override
                                                public void onClick(View view) {
                                                    snackbar1 = Snackbar.make(view, Html.fromHtml("<font color=\"#ffffff\">Quote has been re-added to Favorites!</font>"), Snackbar.LENGTH_LONG); //Make new Snackbar

                                                    //Configure View of the 'response' Snackbar
                                                    View snackBarActionView = snackbar1.getView();
                                                    snackBarActionView.setMinimumHeight(150);
                                                    snackBarActionView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_teal));

                                                    snackbar1.show(); //Show the 'response' Snackbar

                                                    mFavoriteQuote.setFavorite(true); //Set drawable of the  Favorite Icon to "on"
                                                    FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(favoriteQuote); //Add the Quote back to the SQliteDatabase of Favorited Quotes

                                                    compoundButton.setChecked(true); //Set the Favorite Icon to 'checked'
                                                }
                                            });

                                    //Configure View of the 'response' Snackbar
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setMinimumHeight(150);
                                    snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));
                                    snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.snackBarUndoAction));

                                    snackbar.show(); //Show the Snackbar
                                }
                            }, 100); //Set delay of the Handler to 100ms (i.e. the Handler ONLY RUNS 100ms AFTER the Favorite Icon having been 'unchecked')
                        }
                    }

                    //If the Favorite Icon is 'checked' (this block is redundant, since all of the Quotes in the RecyclerView already have their Favorite Icons 'checked')
                    if (isChecked == true){

                        mFavoriteQuote.setFavorite(true); //Set mFavorite member variable of the Quote to 'true'
                        compoundButton.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Set drawable of the Favorite Icon to "on"

                        updateUI(); //Update the RecyclerView List
                        compoundButton.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Set drawable of the Favorite Icon to "on"
                    }

                }
            });





            mFavoriteQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Favorite Quote");

                    shareIntent.putExtra(Intent.EXTRA_TEXT, getFavoriteQuoteShareString());

                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via");

                    startActivity(shareIntent);
                }

            });


        }






        //TODO: After releasing the app and upon releasing the next revision, add the URL string to the app on the Google Play store
        //TODO: ...at the bottom of the Share string!
        private String getFavoriteQuoteShareString(){
            String favoriteQuoteQuoteString = "\"" + mFavoriteQuote.getQuote() + "\"";

            String quoteOfTheDayQuoteAuthorString = " - " + mFavoriteQuote.getAuthor();

            return favoriteQuoteQuoteString + quoteOfTheDayQuoteAuthorString;
        }




        public void bind(Quote favoriteQuote){

            mFavoriteQuote = favoriteQuote;

            mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);


            Log.i(TAG, "VIEWHOLDER - mFavoriteQuote: " + mFavoriteQuote.getQuote());

//            if (mFavoriteQuote.getQuote() != null){
//            }

//            mFavoriteQuoteAuthorName.setText(mFavoriteQuote.getAuthor());

            if (mFavoriteQuote.getAuthor().length() == 0){
                mFavoriteQuoteAuthorName.setText("* No Author *");
                mFavoriteQuoteAuthorName.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mFavoriteQuoteAuthorName.setText(mFavoriteQuote.getAuthor());
                mFavoriteQuoteAuthorName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }





            mFavoriteQuoteFavoriteIcon.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

//            mFavoriteQuoteShareIcon


//            mFavoriteQuoteQuote.setText("\"" + mFavoriteQuote.getQuote() + "\"");


            if (mFavoriteQuote.getQuote().length() == 0){
                mFavoriteQuoteQuote.setText("* No Quote Text *");
                mFavoriteQuoteQuote.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mFavoriteQuoteQuote.setText("\"" + mFavoriteQuote.getQuote() + "\"");
                mFavoriteQuoteQuote.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }

        }





    }








    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);

        //Log lifecycle callback
        Log.i(TAG, "onCreateOptionsMenu(..) called");

        //If there are one or more FavoriteQuotes in the list (i.e. one or more list items)
        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0) {
            //Inflate a menu hierarchy from specified resource
            menuInflater.inflate(R.menu.fragment_favorite_quotes, menu);
        }
    }




    //Override onOptionsItemSelected(..) fragment lifecycle callback method
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        //Log lifecycle callback
        Log.i(TAG, "onOptionsItemsSelected(..) called");

        //Check through all menuItems
        switch(menuItem.getItemId()){

            //Check the "New Pix" menu item
            case (R.id.remove_all_favorite_quotes):


                removeAllFavoriteQuotesConfirmationDialog();

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }







    private void removeAllFavoriteQuotesConfirmationDialog(){



        View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_favorite_quotes_confirmation, null);

        TextView removeAllFavoriteQuotesConfirmationDialogFragmenMessage = (TextView) dialogFragmentView.findViewById(R.id.dialog_fragment_remove_all_favorite_quotes_confirmation_message);



        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nRemove All\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color

        int favoriteQuotesDatabaseSize = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size();


//        String dialogMessage = null;
//        if (favoriteQuotesDatabaseSize == 1){
//            dialogMessage = "Are you sure you want to remove this Quote from Favorites?";
//        }
//        else if (favoriteQuotesDatabaseSize == 2){
//            dialogMessage = "Are you sure you want to remove these 2 Quotes from Favorites?";
//        }
//        else if (favoriteQuotesDatabaseSize > 1){
//            dialogMessage = "Are you sure you want to remove all " + favoriteQuotesDatabaseSize + " Quotes from Favorites?";
//        }




        if (favoriteQuotesDatabaseSize == 1){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove this Quote from Favorites?");
        }
        else if (favoriteQuotesDatabaseSize == 2){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove these 2 Quotes from Favorites?");
        }
        else if (favoriteQuotesDatabaseSize > 1){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove all " + favoriteQuotesDatabaseSize + " Quotes from Favorites?");
        }


//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quotes, null);

        final AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(dialogFragmentView)
                .setCustomTitle(dialogTitle)
//                .setMessage(dialogMessage)
                .setNegativeButton(android.R.string.cancel, null)

                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAllFavoriteQuotes();
                            }
                        })
                .create();



        //Set colors of negative and positive buttons
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.dialogFragmentButton));
                alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.dialogFragmentButton));
            }
        });



        alertDialog.show();





    }







    private List<Quote> tempFavoriteQuotes;



    private void removeAllFavoriteQuotes(){


        int databaseIndex = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size();

//        tempFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

        while (databaseIndex > 0) {



            List<Quote> favoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

            Quote favoriteQuote = favoriteQuotes.get(0);

            favoriteQuote.setFavorite(false);
            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(favoriteQuote);

//            updateUI();


            databaseIndex--;



        }

        updateUI();





        Snackbar snackbar = Snackbar.make(mFavoriteQuotesRecyclerView, Html.fromHtml("<font color=\"#ffffff\">All Quotes removed from Favorites!</font>"), Snackbar.LENGTH_LONG);

//                .setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        int tempDatabaseIndex = tempFavoriteQuotes.size();
//
//
//                        while (tempDatabaseIndex > 0){
//
//                            Quote tempQuote = tempFavoriteQuotes.get(0);
//
//                            FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(tempQuote);
//
//                            tempDatabaseIndex--;
//                        }
//
//                        mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();
//
//                        updateUI();
//
//
//
//
//                        Snackbar snackbar1 = Snackbar.make(view, "All Favorited Quote re-added to Favorites!", Snackbar.LENGTH_SHORT);
//
//                        View snackBarActionView = snackbar1.getView();
//                        snackBarActionView.setMinimumHeight(150);
//                        snackBarActionView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_teal));
//
//                        snackbar1.show();
//
//                    }
//                });

        View snackBarView = snackbar.getView();
        snackBarView.setMinimumHeight(150);
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));


        snackbar.show();



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




//        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
//            mNoFavoriteQuotesText.setVisibility(View.GONE);
//        }
//        else{
//            mNoFavoriteQuotesText.setVisibility(View.VISIBLE);
//        }
//
//
//
//        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();
//        mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//        mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter);
    }







    @Override
    public void onPause(){
        super.onPause();

        try{

            snackbar.setAction(null, null);
        }
        catch (NullPointerException npe){
            Log.e(TAG, "Hmmm");
        }


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

        Log.i(TAG, "onDestroy() called");
    }



}