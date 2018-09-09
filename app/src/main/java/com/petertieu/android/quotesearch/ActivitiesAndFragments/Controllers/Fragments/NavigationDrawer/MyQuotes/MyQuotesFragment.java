package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.MyQuotesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.R;

import java.util.ArrayList;
import java.util.List;

public class MyQuotesFragment extends Fragment{


    private final String TAG = "MyQuotesFragment"; //Log for Logcat

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mMyQuotesRecyclerView;
    private TextView mNoMyQuotesTextView;

    private MyQuotesAdapter mMyQuotesAdapter;
    private MyQuoteViewHolder mMyQuoteViewHolder;




//    private List<Quote> mMyQuotesList = new ArrayList<>();
//    private List<Quote> mMyQuotesList = MyQuotesManager.get(getContext()).


    private static final int REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT = 1;   //Request code for receiving results from dialog fragment to delete Pix
    private static final String IDENTIFIER_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT = "AddNewQuoteDialogFragment";                                                 //Identifier of dialog fragment of DatePicker





    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach() called");
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate() called");

        setHasOptionsMenu(true);
    }







    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat


        View view = layoutInflater.inflate(R.layout.fragment_my_quotes, viewGroup, false);



        mMyQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.my_quotes_recycler_view);
        mNoMyQuotesTextView = (TextView) view.findViewById(R.id.no_my_quotes_text);



        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mMyQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);








//        mMyQuotesAdapter = new MyQuotesAdapter(mMyQuotesList);
        mMyQuotesAdapter = new MyQuotesAdapter(MyQuotesManager.get(getActivity()).getMyQuotes());
        mMyQuotesRecyclerView.setAdapter(mMyQuotesAdapter);





//        if (mMyQuotesList.size() == 0){
//            mNoMyQuotesTextView.setVisibility(View.VISIBLE);
//            mMyQuotesRecyclerView.setVisibility(View.GONE);
//        }
//        else{
//            mNoMyQuotesTextView.setVisibility(View.GONE);
//            mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
//        }


        if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
            mNoMyQuotesTextView.setVisibility(View.VISIBLE);
            mMyQuotesRecyclerView.setVisibility(View.GONE);
        }
        else{
            mNoMyQuotesTextView.setVisibility(View.GONE);
            mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
        }



        getActivity().setTitle("My Quotes");








        return view;
    }









    private class MyQuotesAdapter extends RecyclerView.Adapter<MyQuoteViewHolder> {


        private List<Quote> mMyQuotesList;


        public MyQuotesAdapter(List<Quote> quotesList){
            mMyQuotesList = quotesList;

        }


        public void setMyQuotesList(List<Quote> quotesList){
            mMyQuotesList = quotesList;
        }



        @Override
        public int getItemCount(){
            return mMyQuotesList.size();

        }


        @Override
        public MyQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_my_quote, viewGroup, false);

            mMyQuoteViewHolder = new MyQuoteViewHolder(view);

            return mMyQuoteViewHolder;
        }


        @Override
        public void onBindViewHolder(MyQuoteViewHolder myQuoteViewHolder, int position){

            Quote myQuote = mMyQuotesList.get(position);


            String ID = myQuote.getId();
            String author = myQuote.getAuthor();
            String quote = myQuote.getQuote();


            myQuoteViewHolder.bindToQuote(myQuote);



        }

    }











    private class MyQuoteViewHolder extends RecyclerView.ViewHolder{


        TextView mAuthorTextView;
        CheckBox mFavoriteIcon;
        Button mShareIcon;
        Button mEditIcon;
        Button mDeleteIcon;

        TextView mQuoteTextView;


        Quote mMyQuote;




        public MyQuoteViewHolder(View view){
            super(view);


            mAuthorTextView = (TextView) view.findViewById(R.id.my_quote_author_name);
            mFavoriteIcon = (CheckBox) view.findViewById(R.id.my_quote_favorite_icon);
            mShareIcon = (Button) view.findViewById(R.id.my_quote_share_icon);
            mQuoteTextView = (TextView) view.findViewById(R.id.my_quote_quote);




        }


        public void bindToQuote(Quote myQuote){

            mMyQuote = myQuote;


            //============ mAuthorTextView ===========================
            if (mMyQuote.getAuthor().length() == 0){
                mAuthorTextView.setText("* No Author *");
                mAuthorTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mAuthorTextView.setText(mMyQuote.getAuthor());
                mAuthorTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }






            //============ mFavoriteIcon ===========================

            try {
                //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) != null) {

                    mFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);

                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) == null) {

                    mFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);

                }


            }
            catch (NullPointerException npe){

                mAuthorTextView.setVisibility(View.GONE);
                mFavoriteIcon.setVisibility(View.GONE);
                mShareIcon.setVisibility(View.GONE);
                mQuoteTextView.setVisibility(View.GONE);


                mNoMyQuotesTextView.setVisibility(View.VISIBLE);

            }






            mFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                    mFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                    if (isChecked == true){

                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) == null){
                            FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mMyQuote);
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mMyQuote);
                        }
                        else{
                            //Do nothing
                        }

                    }

                    if (isChecked == false){
                        FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mMyQuote);
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mMyQuote);

                    }
                }
            });





            //============ mShareIcon ===========================

            mShareIcon.setOnClickListener(new View.OnClickListener(){

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













            //============ mQuoteTextView ===========================
            if (mMyQuote.getQuote().length() == 0){
                mQuoteTextView.setText("* No Quote Text *");
                mQuoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mQuoteTextView.setText("\"" + myQuote.getQuote() + "\"");
                mQuoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }





        }







    private String getFavoriteQuoteShareString(){
        String favoriteQuoteQuoteString = "\"" + mMyQuote.getQuote() + "\"";

        String quoteOfTheDayQuoteAuthorString = " - " + mMyQuote.getAuthor();

        return favoriteQuoteQuoteString + quoteOfTheDayQuoteAuthorString;
    }

















    }









    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);

        //Log lifecycle callback
        Log.i(TAG, "onCreateOptionsMenu(..) called");


        menuInflater.inflate(R.menu.fragment_my_quotes, menu);


        MenuItem addNewQuoteItem = menu.findItem(R.id.menu_item_add_new_quote);



//        refreshItem.setEnabled(true); //Enable the "Refresh" menu item button
        addNewQuoteItem.getIcon().setAlpha(255); //Set the "Refresh" menu item button to 'full color' (i.e. white)

    }




    //Override onOptionsItemSelected(..) fragment lifecycle callback method
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        Log.i(TAG, "onOptionsItemsSelected(..) called"); //Log lifecycle callback

        //Check through all menuItems
        switch(menuItem.getItemId()){

            //Check the "New Pix" menu item
            case (R.id.menu_item_add_new_quote):


                addQuotesDialogFragment();

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }





    private void addQuotesDialogFragment() {

        FragmentManager fragmentManager = getFragmentManager();

        AddNewQuoteDialogFragment addNewQuoteDialogFragment = AddNewQuoteDialogFragment.newInstance();

        addNewQuoteDialogFragment.setTargetFragment(MyQuotesFragment.this, REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT);


        addNewQuoteDialogFragment.show(fragmentManager, IDENTIFIER_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT);


    }













    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        Log.i(TAG, "onActivityResult(..) called"); //Log in Logcat


        if (resultCode != Activity.RESULT_OK){
            return;
        }




        if (requestCode == REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT){

            String ID = intent.getStringExtra(AddNewQuoteDialogFragment.EXTRA_ID);
            String author = intent.getStringExtra(AddNewQuoteDialogFragment.EXTRA_AUTHOR);
            String quote = intent.getStringExtra(AddNewQuoteDialogFragment.EXTRA_QUOTE);

            Log.i(TAG, "onActivityResult(..) ID: " + ID);
            Log.i(TAG, "onActivityResult(..) AUTHOR: " + author);
            Log.i(TAG, "onActivityResult(..) QUOTE: " + quote);


            Quote myQuote = new Quote(ID);
            myQuote.setAuthor(author);
            myQuote.setQuote(quote);





//            mMyQuotesAdapter = new MyQuotesAdapter(mMyQuotesList);
//            mMyQuotesRecyclerView.setAdapter(mMyQuotesAdapter);




//            mMyQuotesList.add(myQuote);
            MyQuotesManager.get(getActivity()).addMyQuote(myQuote);









//            mMyQuotesAdapter.setMyQuotesList(mMyQuotesList);
//            mMyQuotesAdapter.notifyDataSetChanged();
            mMyQuotesAdapter.setMyQuotesList(MyQuotesManager.get(getActivity()).getMyQuotes());
            mMyQuotesAdapter.notifyDataSetChanged();









//            if (mMyQuotesList == null){
//                Log.i(TAG, "NULLLL");
//            }
//            else{
//                Log.i(TAG, "EXISTSSS");
//
//
//                for (Quote quotee : mMyQuotesList){
//
//                    Log.i(TAG, "mMyQuotesList ID: " + quotee.getId());
//                    Log.i(TAG, "mMyQuotesList Author: " + quotee.getAuthor());
//                    Log.i(TAG, "mMyQuotesList Quote: " + quotee.getQuote());
//
//                }
//
//
//            }





















            Toast toast = Toast.makeText(getContext(), "Added new Quote", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();



//            if (mMyQuotesList.size() == 0){
//                mNoMyQuotesTextView.setVisibility(View.VISIBLE);
//                mMyQuotesRecyclerView.setVisibility(View.GONE);
//            }
//            else{
//                mNoMyQuotesTextView.setVisibility(View.GONE);
//                mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
//            }


            if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
                mNoMyQuotesTextView.setVisibility(View.VISIBLE);
                mMyQuotesRecyclerView.setVisibility(View.GONE);
            }
            else{
                mNoMyQuotesTextView.setVisibility(View.GONE);
                mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
            }









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

        Log.i(TAG, "onDestroy() called");
    }
}
