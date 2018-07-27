package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;



import java.util.List;

public class FavoriteQuotesFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "FavoriteQuotesFragment";

    private RecyclerView mFavoriteQuotesRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private FavoriteQuotesAdapter mFavoriteQuotesAdapter;
    private FavoriteQuotesViewHolder mFavoriteQuotesViewHolder;


    private TextView mNoFavoriteQuotesText;




    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach() called");

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



//        if (savedInstanceState != null){
//
//            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
//        }




        Log.i(TAG, "onCreate() called");


        setHasOptionsMenu(true);
    }







//    // Save state
//    private Parcelable recyclerViewState;
//
//    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";
//
//
//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstaceState){
//
//
//        savedInstaceState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState);
//
//
//        super.onSaveInstanceState(savedInstaceState);
//
//
//
//
//
//
//
//    }







    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");


        View view = layoutInflater.inflate(R.layout.fragment_favorite_quotes, viewGroup, false);







        mFavoriteQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_quotes_recycler_view);


        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);


        mFavoriteQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mFavoriteQuotesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));






        mNoFavoriteQuotesText = (TextView) view.findViewById(R.id.no_favorite_quotes_text);



        getActivity().setTitle("Favorites");





        //        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
//            mNoFavoriteQuotesText.setVisibility(View.GONE);
//        }
//        else{
//            mNoFavoriteQuotesText.setVisibility(View.VISIBLE);
//        }

//        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();
//        mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//        mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter);




//        updateUI();





        return view;
    }









    public void updateUI(){



        //Refresh the options menu every time a list item is added/removed, so we could re-evaluate whether the menu item "Remove all" is still appropriate
        getActivity().invalidateOptionsMenu();



        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size() > 0){
            mNoFavoriteQuotesText.setVisibility(View.GONE);
        }
        else{
            mNoFavoriteQuotesText.setVisibility(View.VISIBLE);
        }


        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();
//
        mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//
        mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter);










//        if (mFavoriteQuotesAdapter == null){
//
//            Log.i(TAG, "No Adapter");
//
//            mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//
//            mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter); //Resets the RecyclerView
//        }
//        else{
//
//
//            Log.i(TAG, "Adapter exists");
//
//            mFavoriteQuotesAdapter.setFavoriteQuotes(mFavoriteQuotes);
////            mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//
//
//            Log.i(TAG, "Adapter exists:  " + mFavoriteQuotes.get(0).getQuote());
//
//            mFavoriteQuotesAdapter.notifyDataSetChanged();
//
//        }


    }





    private class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuotesViewHolder>{


        private List<Quote> mFavoriteQuotes;



        public FavoriteQuotesAdapter(List<Quote> favoriteQuotes){
            mFavoriteQuotes = favoriteQuotes;

            Log.i(TAG, "ADAPTER - mFavoriteQuotes: " + mFavoriteQuotes);

        }

        @Override
        public int getItemCount(){
            return mFavoriteQuotes.size();
        }


        @Override
        public FavoriteQuotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_favorite_quote, viewGroup, false);


            mFavoriteQuotesViewHolder = new FavoriteQuotesViewHolder(view);


            return mFavoriteQuotesViewHolder;
        }


        @Override
        public void onBindViewHolder(FavoriteQuotesViewHolder favoriteQuotesViewHolder, int position){
            Quote favoriteQuote = mFavoriteQuotes.get(position);
            favoriteQuotesViewHolder.bind(favoriteQuote);

            Log.i(TAG, "ADAPTER - favoriteQuote: " + favoriteQuote.getQuote());
        }



        public void setFavoriteQuotes(List<Quote> favoriteQuotes){
            mFavoriteQuotes = favoriteQuotes;
        }

    }










    private class FavoriteQuotesViewHolder extends RecyclerView.ViewHolder{


        public Quote mFavoriteQuote;

        private LinearLayout mFavoriteQuoteBubbleLayout;

        private TextView mFavoriteQuoteAuthorName;

        private CheckBox mFavoriteQuoteFavoriteIcon;

        private Button mFavoriteQuoteShareIcon;

        private ProgressBar mFavoriteQuoteProgressBar;

        private TextView mFavoriteQuoteQuote;



        public FavoriteQuotesViewHolder(View view){
            super(view);

            mFavoriteQuoteBubbleLayout = (LinearLayout) view.findViewById(R.id.favorite_quote_category_bubble_layout);

            mFavoriteQuoteAuthorName = (TextView) view.findViewById(R.id.favorite_quote_author_name);

            mFavoriteQuoteFavoriteIcon = (CheckBox) view.findViewById(R.id.favorite_quote_favorite_icon);

            mFavoriteQuoteShareIcon = (Button) view.findViewById(R.id.favorite_quote_share_icon);

            mFavoriteQuoteQuote = (TextView) view.findViewById(R.id.favorite_quote_quote);




//            mFavoriteQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));


//            if (mFavoriteQuote.isFavorite() == true){
//                mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
//            }
//            else{
//                mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
//            }



            mFavoriteQuoteFavoriteIcon.setChecked(true);


            mFavoriteQuoteFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                    mFavoriteQuote.setFavorite(false);

//                    mFavoriteQuote.setFavorite(isChecked);

                    compoundButton.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);





                    if (isChecked == false){

                        FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mFavoriteQuote);
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mFavoriteQuote);


                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mFavoriteQuote.getId()) == null){


                            compoundButton.setButtonDrawable(R.drawable.ic_imageview_favorite_off);





                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                //What to do AFTER the 100ms delay
                                @Override
                                public void run() {


                                    updateUI();









                                    Snackbar snackbar = Snackbar
                                            .make(mFavoriteQuotesRecyclerView, "Quote removed from Favorites", Snackbar.LENGTH_LONG)

                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {




                                                    Snackbar snackbar1 = Snackbar.make(view, "Quote re-added to Favorites!", Snackbar.LENGTH_SHORT);

                                                    View snackBarActionView = snackbar1.getView();
                                                    snackBarActionView.setMinimumHeight(150);
                                                    snackBarActionView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_teal));

                                                    snackbar1.show();



//                                                    mFavoriteQuoteFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                                                    FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mFavoriteQuote);
//                                                    mFavoriteQuote.setFavorite(true);
                                                    mFavoriteQuoteFavoriteIcon.setChecked(true);

//                                                    Log.i(TAG, "UNDO called");
//                                                    bind(mFavoriteQuote);
//                                                    updateUI();
                                                }
                                            });

                                    View snackBarView = snackbar.getView();
                                    snackBarView.setMinimumHeight(150);
                                    snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));

                                    snackbar.show();







                                }
                            }, 100);




                        }



                    }






                    if (isChecked == true){

//                        bind(mFavoriteQuote);

                        mFavoriteQuote.setFavorite(true);

//                        compoundButton.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);
//                        Log.i(TAG, "HELOOOOOOOOOOOOOO " + Boolean.toString(mFavoriteQuote.isFavorite()));

                        updateUI();

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


            Log.i(TAG, "VIEWHOLDER - mFavoriteQuote: " + mFavoriteQuote.getQuote());

//            if (mFavoriteQuote.getQuote() != null){
//            }

            mFavoriteQuoteAuthorName.setText(mFavoriteQuote.getAuthor());


            mFavoriteQuoteFavoriteIcon.setButtonDrawable(mFavoriteQuote.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);

//            mFavoriteQuoteShareIcon


            mFavoriteQuoteQuote.setText("\"" + mFavoriteQuote.getQuote() + "\"");
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


        TextView dialogTitle = new TextView(getActivity());
        dialogTitle.setText("Remove all");
        dialogTitle.setTextSize(22);
        dialogTitle.setGravity(Gravity.CENTER);
        dialogTitle.setTypeface(null, Typeface.BOLD);
        dialogTitle.setTextColor(getResources().getColor(R.color.orange));
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey));

        int favoriteQuotesDatabaseSize = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size();


        String dialogMessage = null;
        if (favoriteQuotesDatabaseSize == 1){
            dialogMessage = "Are you sure you want to remove this quote from Favorites?";
        }
        else if (favoriteQuotesDatabaseSize == 2){
            dialogMessage = "Are you sure you want to remove these 2 quotes from Favorites?";
        }
        else if (favoriteQuotesDatabaseSize > 1){
            dialogMessage = "Are you sure you want to remove all " + favoriteQuotesDatabaseSize + " quotes from Favorites?";
        }


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quotes, null);

        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setNegativeButton(android.R.string.cancel, null)

                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAllFavoriteQuotes();
                            }
                        })
                .create();


        alertDialog.show();

    }







    private List<Quote> tempFavoriteQuotes;



    private void removeAllFavoriteQuotes(){


        int databaseIndex = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes().size();

//        tempFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

        while (databaseIndex > 0) {



            List<Quote> favoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

            Quote favoriteQuote = favoriteQuotes.get(0);

            FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(favoriteQuote);

//            updateUI();


            databaseIndex--;



        }

        updateUI();





        Snackbar snackbar = Snackbar
                .make(mFavoriteQuotesRecyclerView, "All Quotes removed from Favorites", Snackbar.LENGTH_LONG);

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



        updateUI();
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

        Log.i(TAG, "onDestroy() called");
    }



}