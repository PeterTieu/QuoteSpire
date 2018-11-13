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
        setHasOptionsMenu(true); //Declare that the fragment has an options menu
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


        //If there ARE Quotes in the Favorite Quotes SQLiteDatabase
        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
            mNoFavoriteQuotesText.setVisibility(View.GONE); //Remove the TextView indicating that there are no Quotes Favorited
        }
        //If there are NO Quotes in the Favorite Quotes SQLiteDatabase
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



            //Set listener for Share Icon
            mFavoriteQuoteShareIcon.setOnClickListener(new View.OnClickListener(){

                //Override onClick(..) method
                @Override
                public void onClick(View view){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create explicit Intent with SEND action
                    shareIntent.setType("text/plain"); //Set Intent type to "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Favorite Quote"); //Set Intent subject
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getFavoriteQuoteShareString()); //Set Intent text to the Quote and Author
                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Set Intent chooser title
                    startActivity(shareIntent); //Start Intent
                }
            });
        }



        //Helper method - the text (to be used by the Share Intent) to include the Quote and the Author
        private String getFavoriteQuoteShareString(){
            String favoriteQuoteQuoteString = "\"" + mFavoriteQuote.getQuote() + "\""; //Quote
            String quoteOfTheDayQuoteAuthorString = " - " + mFavoriteQuote.getAuthor(); //Author
            return favoriteQuoteQuoteString + quoteOfTheDayQuoteAuthorString; //Return String including Quote and Author
        }



        //Bind the Quote to the ViewHolder
        public void bind(Quote favoriteQuote){

            mFavoriteQuote = favoriteQuote; //Stash the parameter variable to the instance variable
            mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on); //Set drawable of the Favorite Icon to "on"

            Log.i(TAG, "VIEWHOLDER - mFavoriteQuote: " + mFavoriteQuote.getQuote()); //Log Quote to the Logcat

            //If the Author does NOT exist
            if (mFavoriteQuote.getAuthor().length() == 0){
                mFavoriteQuoteAuthorName.setText("* No Author *"); //Indicate that there is no Author in the Author TextView
                mFavoriteQuoteAuthorName.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            //If the Author EXISTS
            else{
                mFavoriteQuoteAuthorName.setText(mFavoriteQuote.getAuthor()); //Display the name of the Author in the Author TextView
                mFavoriteQuoteAuthorName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }


            mFavoriteQuoteFavoriteIcon.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off); //Set Favorite button drawable to whether it is Favorited or not


            //If the Quote does NOT exist
            if (mFavoriteQuote.getQuote().length() == 0){
                mFavoriteQuoteQuote.setText("* No Quote Text *"); //Indicate that there is no Quote in the Quote TextView
                mFavoriteQuoteQuote.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            //If the Quote EXISTS
            else{
                mFavoriteQuoteQuote.setText("\"" + mFavoriteQuote.getQuote() + "\""); //Display the Quote in the Quote TextView
                mFavoriteQuoteQuote.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }

        }

    }





    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);

        Log.i(TAG, "onCreateOptionsMenu(..) called"); //Log lifecycle callback

        //If there are one or more FavoriteQuotes in the list (i.e. one or more list items)
        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0) {
            menuInflater.inflate(R.menu.fragment_favorite_quotes, menu); //Inflate a menu hierarchy from specified resource
        }
    }




    //Override onOptionsItemSelected(..) fragment lifecycle callback method
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        Log.i(TAG, "onOptionsItemsSelected(..) called"); //Log lifecycle callback

        //Check through all menuItems
        switch(menuItem.getItemId()){

            //Check the "New Pix" menu item
            case (R.id.remove_all_favorite_quotes):
                removeAllFavoriteQuotesConfirmationDialog(); //Display Confirmation AlertDialog to remove all Favorite Quotes
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }




    //Helper method - Display Confirmation AlertDialog to remove all Favorite Quotes
    private void removeAllFavoriteQuotesConfirmationDialog(){

        View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_favorite_quotes_confirmation, null); //Inflate View for AlertDialog

        TextView removeAllFavoriteQuotesConfirmationDialogFragmenMessage = (TextView) dialogFragmentView.findViewById(R.id.dialog_fragment_remove_all_favorite_quotes_confirmation_message); //Assign TextView ref. variable to associated resource ID


        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nRemove All\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color


        int favoriteQuotesDatabaseSize = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size(); //Siz of the SQLiteDatabase of Favorited Quotes

        //If there is ONE Favorited Quote
        if (favoriteQuotesDatabaseSize == 1){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove this Quote from Favorites?");
        }
        //If there are TWO Favorited Quotes
        else if (favoriteQuotesDatabaseSize == 2){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove these 2 Quotes from Favorites?");
        }
        //If there are MORE THAN two Favorited Quotes
        else if (favoriteQuotesDatabaseSize > 1){
            removeAllFavoriteQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove all " + favoriteQuotesDatabaseSize + " Quotes from Favorites?");
        }


        //Display the AlertDialog
        final AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()) //Call Builder
                .setView(dialogFragmentView) //Set View
                .setCustomTitle(dialogTitle) //Set Title
                .setNegativeButton(android.R.string.cancel, null) //Set NEGATIVE button and listener
                .setPositiveButton(android.R.string.yes, //Set POSITIVE button and listener
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAllFavoriteQuotes(); //Remove all Favorite Quotes
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



        alertDialog.show(); //Show AlertDialog
    }





    private List<Quote> tempFavoriteQuotes;


    //Helper method - Remove all Favorite Quotes
    private void removeAllFavoriteQuotes(){


        int databaseIndex = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size(); //Int for indexing through the SQLiteDatabase of Favorited Quotes

        //Index through the SQLiteDatabase of Favorited Quotes and remove one Favorited Quote at a time
        while (databaseIndex > 0) {

            List<Quote> favoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes(); //Obtain List of Favorited Quotes from the SQLiteDatabase

            Quote favoriteQuote = favoriteQuotes.get(0); //Obtain the last Quote in the List

            favoriteQuote.setFavorite(false); //Set the Favorited Quote to 'false'
            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(favoriteQuote); //Remove the Favorited Quote from the SQLiteDatabse of Favorited Quotes

            databaseIndex--; //Decrement the index
        }

        updateUI(); //Update the Fragment to account for the removal of all Favorited Quotes


        //Display Nsackbar indicating all Quotes have been removed from Favorited Quotes
        Snackbar snackbar = Snackbar.make(mFavoriteQuotesRecyclerView, Html.fromHtml("<font color=\"#ffffff\">All Quotes removed from Favorites!</font>"), Snackbar.LENGTH_LONG);

        //Configure View for Snackbar
        View snackBarView = snackbar.getView();
        snackBarView.setMinimumHeight(150);
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));

        snackbar.show(); //Show the Snackbar
    }




    //Override onStart() fragment lifecycle callback method
    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart() called"); //Log to Logcat
    }




    //Override onResume() fragment lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume() called"); //Log to Logcat
    }




    //Override onPause() fragment lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();

        //Try risky task - SnackBar.setAction(..) could throw NullPointerException
        //IF the Snackbar that shows up when a Faovirted Quote is 'unfavorited', AND the user toggles away from the fragment... THEN... remove this Snackbar
        try{
            snackbar.setAction(null, null); //Remove the Snackbar
        }
        catch (NullPointerException npe){
            Log.e(TAG, "Hmmm");
        }

        Log.i(TAG, "onPause() called"); //Log to Logcat
    }




    //Override onStop() fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop() called");
    }




    //Override onDestroy() fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called"); //Log to Logcat
    }

}