package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

public class FavoritesFragment extends Fragment {


    //Log for Logcat
    private final String TAG = "FavoritesFragment";

    private RecyclerView mFavoriteQuotesRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private FavoriteQuotesAdapter mFavoriteQuotesAdapter;
    private FavoriteQuotesViewHolder mFavoriteQuotesViewHolder;



    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach() called");

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate() called");
    }



    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log in Logcat
        Log.i(TAG, "onCreateView(..) called");

        View view = layoutInflater.inflate(R.layout.favorite_quotes_recycler_view, viewGroup, false);



        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);




        mFavoriteQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_quotes_recycler_view);


        mFavoriteQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mFavoriteQuotesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));








        updateUI();


        getActivity().setTitle("Favorites");

        return view;
    }



    public void updateUI(){



        final List<Quote> mFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

        if (mFavoriteQuotes.size() == 0){

            Log.i(TAG, "No Favorite Quotes");
            //TODO: Add display "No Quotes have been favorited"
        }
        else{

            Log.i(TAG, "There are Favorite Quotes");

            //TODO: Remove view "No Quotes have been favorited"
        }




        mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);

        mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter);




//        if (mFavoriteQuotesAdapter == null){
//
//            Log.i(TAG, "No Adapter");
//
//            mFavoriteQuotesAdapter = new FavoriteQuotesAdapter(mFavoriteQuotes);
//
//            mFavoriteQuotesRecyclerView.setAdapter(mFavoriteQuotesAdapter);
//        }
//        else{
//
//            Log.i(TAG, "Adapter exists");
//
//            mFavoriteQuotesAdapter.setFavoriteQuotes(mFavoriteQuotes);
//            mFavoriteQuotesAdapter.notifyDataSetChanged();
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


        private Quote mFavoriteQuote;

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




            mFavoriteQuoteBubbleLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_round_edges));


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

                                //What to do AFTER the 300ms delay
                                @Override
                                public void run() {


                                    updateUI();







                                    Snackbar snackbar = Snackbar
                                            .make(mFavoriteQuotesRecyclerView, "Quote has been removed from Favorites", Snackbar.LENGTH_LONG)

                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {



                                                    Snackbar snackbar1 = Snackbar.make(view, "Quote has been re-added to Favorites!", Snackbar.LENGTH_LONG);
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

                                    snackbar.show();







                                }
                            }, 200);




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


            mFavoriteQuoteQuote.setText(mFavoriteQuote.getQuote());
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


        updateUI();
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