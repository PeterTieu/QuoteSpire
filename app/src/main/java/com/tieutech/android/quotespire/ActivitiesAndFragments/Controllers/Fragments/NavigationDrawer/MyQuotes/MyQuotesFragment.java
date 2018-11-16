package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.MyQuotesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.R;

import java.util.List;


//Fragment for displaying all the Quotes created by the user - stored in the MyQuotes SQLiteDatabase
public class MyQuotesFragment extends Fragment{


    //================= INSTANCE VARIABLES ==============================================================
    private final String TAG = "MyQuotesFragment"; //Log for Logcat

    //View variables
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mMyQuotesRecyclerView;
    private TextView mNoMyQuotesTextView;
    private MyQuotesAdapter mMyQuotesAdapter;
    private MyQuoteViewHolder mMyQuoteViewHolder;


    //Request code and identifier variables
    private static final int REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT = 1;   //Request code for receiving results from dialog fragment to ADD MyQuote
    private static final String IDENTIFIER_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT = "AddNewMyQuoteDialogFragment"; //Identifier of AddMyQuoteDialogFragment

    private static final int REQUEST_CODE_REMOVE_ALL_MY_QUOTES_CONFIRMATION_DIALOG_FRAGMENT = 2; //Request code for receiving results from dialog on whether user confirmed REMOVE ALL of MyQuotes
    private static final String  IDENTIFIER_REMOVE_ALL_MY_QUOTES_CONFIRMATION_DIALOG_FRAGMENT = "RemoveAllMyQuotesConfirmationDialogFragment"; //Identifier of RemoveAllMyQuotesConfirmationDialogFragment

    private static final int REQUEST_CODE_EDIT_MY_QUOTE_DIALOG_FRAGMENT = 3; //Request code for receiving results from dialog fragment to EDIT MyQuote
    private static final String IDENTIFIER_EDIT_MY_QUOTE_DIALOG_FRAGMENT = "EditMyQuoteDialogFragment"; //Identifier of EditMyQuoteDialogFragment

    private static final int REQUEST_CODE_DELETE_MY_QUOTE_CONFIRMATION_DIALOG_FRAGMENT = 4; //Request code for receiving boolean result on whether user confirmed DELETION of the Quote
    private static final String IDENTIFIER_DELETE_MY_QUOTE_CONFIRMATION_DIALOG_FRAGMENT = "DeleteMyQuoteConfrmationDialogFragment"; //Identifier of dialog fragment of RemoveMyQuoteConfirmationDialogFragment



    //================= METHODS ==============================================================

    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i(TAG, "onAttach() called"); //Log to Logcat
    }


    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called"); //Log to Logcat

        setHasOptionsMenu(true); //Declare that the fragment has an option menu
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_my_quotes, viewGroup, false); //Instantiate fragment view

        //Assign View variables to associated resource IDs
        mMyQuotesRecyclerView = (RecyclerView) view.findViewById(R.id.my_quotes_recycler_view);
        mNoMyQuotesTextView = (TextView) view.findViewById(R.id.no_my_quotes_text);

        //Configure View variables
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mMyQuotesRecyclerView.setLayoutManager(mLinearLayoutManager);

        mMyQuotesAdapter = new MyQuotesAdapter(MyQuotesManager.get(getActivity()).getMyQuotes()); //Obtain the SQLiteDatabase of My Quotes and link it to the Adapter
        mMyQuotesRecyclerView.setAdapter(mMyQuotesAdapter); //Link to the RecyclerView to the Adapter


        //Display the message indicating that there is no Quote objects IF the size of the SQLiteDatabase of My Quotes is zero
        if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
            mNoMyQuotesTextView.setVisibility(View.VISIBLE);
            mMyQuotesRecyclerView.setVisibility(View.GONE);
        }
        else{
            mNoMyQuotesTextView.setVisibility(View.GONE);
            mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
        }

        getActivity().setTitle("My Quotes"); //Set title for the fragment

        return view;
    }




    //RecyclerView Adapter
    private class MyQuotesAdapter extends RecyclerView.Adapter<MyQuoteViewHolder> {

        private List<Quote> mMyQuotesList; //


        //Constructor
        public MyQuotesAdapter(List<Quote> quotesList){
            mMyQuotesList = quotesList; //Assign the List instance variable to the same object referred to by the local variable
        }


        //Set any new Lists to the Adapter
        public void setMyQuotesList(List<Quote> quotesList){
            mMyQuotesList = quotesList;
        }


        //Override getItemCount() method
        @Override
        public int getItemCount(){
            return mMyQuotesList.size(); //Size of the List
        }


        //Override onCreateViewHolder(..) method
        @Override
        public MyQuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Obtain LayoutInflater
            View view = layoutInflater.inflate(R.layout.list_item_my_quote, viewGroup, false); //Obtain layout of the ViewHolder
            mMyQuoteViewHolder = new MyQuoteViewHolder(view); //Instantiate ViewHolder based on the layout

            return mMyQuoteViewHolder; //Return the ViewHolder
        }


        //Override onBindViewHolder(..) method
        @Override
        public void onBindViewHolder(MyQuoteViewHolder myQuoteViewHolder, int position){

            Quote myQuote = mMyQuotesList.get(position); //Quote in the List of My Quote objects

            String ID = myQuote.getId(); //ID of the Quote
            String author = myQuote.getAuthor(); //Author of the Quote
            String quote = myQuote.getQuote(); //Quote of the Quote

            myQuoteViewHolder.bindToQuote(myQuote, position); //Bind the Quote and position of the Quote to the ViewHolder
        }
    }



    boolean mConfirmDelete = false;




    //RecyclerView ViewHolder
    private class MyQuoteViewHolder extends RecyclerView.ViewHolder{

        //List item View variables
        TextView mAuthorTextView;
        TextView mQuoteTextView;
        CheckBox mFavoriteIcon;
        Button mShareIcon;
        Button mEditIcon;
        Button mDeleteIcon;

        Quote mMyQuote; //My Quote
        int mPosition; //Position of the Quote in the RecyclerView view


        //Constructor
        public MyQuoteViewHolder(View view){
            super(view);

            //Assign View variables to the associated resource IDs
            mAuthorTextView = (TextView) view.findViewById(R.id.my_quote_author_name);
            mQuoteTextView = (TextView) view.findViewById(R.id.my_quote_quote);
            mFavoriteIcon = (CheckBox) view.findViewById(R.id.my_quote_favorite_icon);
            mShareIcon = (Button) view.findViewById(R.id.my_quote_share_icon);
            mEditIcon = (Button) view.findViewById(R.id.my_quote_edit_icon);
            mDeleteIcon = (Button) view.findViewById(R.id.my_quote_delete_icon);
        }


        //Bind method - binds the ViewHolder (i.e. list item) to the data (to be displayed)
        public void bindToQuote(Quote myQuote, int position){

            //Assign instance variables to objects pointed to by the parameters
            mMyQuote = myQuote;
            mPosition = position;


            //============ mAuthorTextView ===========================

            //Display the Quote Author based on whether or not it exists
            if (mMyQuote.getAuthor().length() == 0){
                mAuthorTextView.setText("* No Author *");
                mAuthorTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mAuthorTextView.setText(mMyQuote.getAuthor());
                mAuthorTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }



            //============ mQuoteTextView ===========================

            //Display the Quote text based on whether or not it exists
            if (mMyQuote.getQuote().length() == 0){
                mQuoteTextView.setText("* No Quote Text *");
                mQuoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            }
            else{
                mQuoteTextView.setText("\"" + myQuote.getQuote() + "\"");
                mQuoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }



            //============ mFavoriteIcon ===========================

            //Display the relevant 'on'/'off' Favorite drawable based on whether the Quote exists in the SQLiteDatabase of My Quotes
            //NOTE: mMyQuote could throw NullPointerException if it does not exist
            //NOTE: Since this fragment displays only Quotes in the My Quotes database, then all Favorite drawables are already going to be 'on' by default
            try {
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) != null) {
                    mFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
                }
                if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) == null) {
                    mFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_off);
                }
            }
            //If mMyQuote does NOT exist. IOW, there are no Quote objects in the SQLiteDatabase of Quote objects
            catch (NullPointerException npe){
                mAuthorTextView.setVisibility(View.GONE);
                mFavoriteIcon.setVisibility(View.GONE);
                mShareIcon.setVisibility(View.GONE);
                mQuoteTextView.setVisibility(View.GONE);
                mNoMyQuotesTextView.setVisibility(View.VISIBLE);
            }


            //Set listener for Favorite Icon
            mFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    //Set Favorite Icon drawable based on the 'check' state of the CheckBox
                    mFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


                    //If the Favorite Icon is 'checked'
                    if (isChecked == true){
                        //If the Quote is NOT in the SQLiteDatabase of My Quote objects
                        if (FavoriteQuotesManager.get(getActivity()).getFavoriteQuote(mMyQuote.getId()) == null){
                            FavoriteQuotesManager.get(getActivity()).addFavoriteQuote(mMyQuote); //Add the Quote to the My Quotes database
                            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mMyQuote); //Update the My Quotes database
                        }
                        else{
                            //Do nothing
                        }

                    }
                    //If the Favorite Icon is 'unchecked'
                    if (isChecked == false){
                        FavoriteQuotesManager.get(getActivity()).deleteFavoriteQuote(mMyQuote); //Remove the Qutoe from the My Quotes database
                        FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(mMyQuote); //Update the My Quotes database
                    }
                }
            });



            //============ mShareIcon ===========================

            //Set listener for Share Icon
            mShareIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    Intent shareIntent = new Intent(Intent.ACTION_SEND); //Create implicit intent with SEND action
                    shareIntent.setType("text/plain"); //Set intent type to "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Favorite Quote"); //Add Extra: subject of the intent
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getFavoriteQuoteShareString()); //Add Extra: text of the intent
                    shareIntent = Intent.createChooser(shareIntent, "Share Quote via"); //Create chooser and title
                    startActivity(shareIntent); //Start implicit intent to share
                }
            });





            //============ mEditIcon ===========================

            mEditIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){


                    editMyQuoteDialogFragment(mMyQuote.getAuthor(), mMyQuote.getQuote(), mMyQuote.getId(), mPosition);

                }

            });






            //============ mDeleteIcon ===========================
            mDeleteIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){


                    deleteMyQuoteConfirmationDialogFragment(mMyQuote.getAuthor(), mMyQuote.getQuote(), mPosition); //Open DialogFragment to confirm Quote deletion


                }

            });




        }







        private String getFavoriteQuoteShareString() {
            String favoriteQuoteQuoteString = "\"" + mMyQuote.getQuote() + "\"";

            String quoteOfTheDayQuoteAuthorString = " - " + mMyQuote.getAuthor();

            return favoriteQuoteQuoteString + quoteOfTheDayQuoteAuthorString;
        }






        private void editMyQuoteDialogFragment(String author, String quote, String ID, int position){
            FragmentManager fragmentManager = getFragmentManager();

            EditMyQuoteDialogFragment editMyQuoteDialogFragment = EditMyQuoteDialogFragment.newInstance(author, quote, ID);

            editMyQuoteDialogFragment.setTargetFragment(MyQuotesFragment.this, REQUEST_CODE_EDIT_MY_QUOTE_DIALOG_FRAGMENT);

            editMyQuoteDialogFragment.show(fragmentManager, IDENTIFIER_EDIT_MY_QUOTE_DIALOG_FRAGMENT);
        }





        //Helper method - open DialogFramgent to confirm Quote deletion
        private void deleteMyQuoteConfirmationDialogFragment(String author, String quote, int position){
            FragmentManager fragmentManager = getFragmentManager();

            RemoveMyQuoteConfirmationDialogFragment removeMyQuoteConfirmationDialogFragment = RemoveMyQuoteConfirmationDialogFragment.newInstance(author, quote, position);

            removeMyQuoteConfirmationDialogFragment.setTargetFragment(MyQuotesFragment.this, REQUEST_CODE_DELETE_MY_QUOTE_CONFIRMATION_DIALOG_FRAGMENT);

            removeMyQuoteConfirmationDialogFragment.show(fragmentManager, IDENTIFIER_DELETE_MY_QUOTE_CONFIRMATION_DIALOG_FRAGMENT);
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


        MenuItem removeAllMyQuotes = menu.findItem(R.id.menu_item_remove_all_my_quotes);



        if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
            removeAllMyQuotes.setVisible(false);
        }
        else{
            removeAllMyQuotes.setVisible(true);
        }


    }




    //Override onOptionsItemSelected(..) fragment lifecycle callback method
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){


        Log.i(TAG, "onOptionsItemsSelected(..) called"); //Log lifecycle callback

        //Check through all menuItems
        switch(menuItem.getItemId()){

            //Check the "New Pix" menu item
            case (R.id.menu_item_add_new_quote):



                addMyQuoteDialogFragment();

                return true;


            case (R.id.menu_item_remove_all_my_quotes):


                removeAllMyQuotesConfirmationDialogFragment();
//                removeAllMyQuotes();

                return true;


            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }





    private void addMyQuoteDialogFragment() {

        FragmentManager fragmentManager = getFragmentManager();

        AddNewMyQuoteDialogFragment addNewMyQuoteDialogFragment = AddNewMyQuoteDialogFragment.newInstance();

        addNewMyQuoteDialogFragment.setTargetFragment(MyQuotesFragment.this, REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT);


        addNewMyQuoteDialogFragment.show(fragmentManager, IDENTIFIER_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT);


    }





    public void removeAllMyQuotesConfirmationDialogFragment(){

        FragmentManager fragmentManager = getFragmentManager();

        int myQuotesListSize = MyQuotesManager.get(getActivity()).getMyQuotes().size();

        RemoveAllMyQuotesConfirmationDialogFragment removeAllMyQuotesConfirmationDialogFragment = RemoveAllMyQuotesConfirmationDialogFragment.newInstance(myQuotesListSize);

        removeAllMyQuotesConfirmationDialogFragment.setTargetFragment(MyQuotesFragment.this, REQUEST_CODE_REMOVE_ALL_MY_QUOTES_CONFIRMATION_DIALOG_FRAGMENT);

        removeAllMyQuotesConfirmationDialogFragment.show(fragmentManager, IDENTIFIER_REMOVE_ALL_MY_QUOTES_CONFIRMATION_DIALOG_FRAGMENT);
    }








    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        Log.i(TAG, "onActivityResult(..) called"); //Log in Logcat


        if (resultCode != Activity.RESULT_OK){
            return;
        }




        if (requestCode == REQUEST_CODE_ADD_NEW_MY_QUOTE_DIALOG_FRAGMENT){

            String ID = intent.getStringExtra(AddNewMyQuoteDialogFragment.EXTRA_ID);
            String author = intent.getStringExtra(AddNewMyQuoteDialogFragment.EXTRA_AUTHOR);
            String quote = intent.getStringExtra(AddNewMyQuoteDialogFragment.EXTRA_QUOTE);

            Log.i(TAG, "onActivityResult(..) ID: " + ID);
            Log.i(TAG, "onActivityResult(..) AUTHOR: " + author);
            Log.i(TAG, "onActivityResult(..) QUOTE: " + quote);


            Quote myQuote = new Quote(ID);
            myQuote.setAuthor(author);
            myQuote.setQuote(quote);



            MyQuotesManager.get(getActivity()).addMyQuote(myQuote);



            mMyQuotesAdapter.setMyQuotesList(MyQuotesManager.get(getActivity()).getMyQuotes());
            mMyQuotesAdapter.notifyDataSetChanged();




            Toast toast = Toast.makeText(getContext(), "Added new Quote", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();




            if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
                mNoMyQuotesTextView.setVisibility(View.VISIBLE);
                mMyQuotesRecyclerView.setVisibility(View.GONE);
            }
            else{
                mNoMyQuotesTextView.setVisibility(View.GONE);
                mMyQuotesRecyclerView.setVisibility(View.VISIBLE);
            }


            getActivity().invalidateOptionsMenu();

        }



        if (requestCode == REQUEST_CODE_REMOVE_ALL_MY_QUOTES_CONFIRMATION_DIALOG_FRAGMENT){
            boolean shouldRemoveAllMyQuotes = intent.getBooleanExtra(RemoveAllMyQuotesConfirmationDialogFragment.EXTRA_SHOULD_REMOVE_ALL_MY_QUOTES, false);

            if (shouldRemoveAllMyQuotes){
                removeAllMyQuotes();
            }
        }






        if (requestCode == REQUEST_CODE_EDIT_MY_QUOTE_DIALOG_FRAGMENT) {

            String author = intent.getStringExtra(EditMyQuoteDialogFragment.EXTRA_AUTHOR);
            String quote = intent.getStringExtra(EditMyQuoteDialogFragment.EXTRA_QUOTE);
            String ID = intent.getStringExtra(EditMyQuoteDialogFragment.EXTRA_ID);


            Quote editedQuote = new Quote(ID);
            editedQuote.setAuthor(author);
            editedQuote.setQuote(quote);


            MyQuotesManager.get(getActivity()).updateMyQuotesDatabase(editedQuote);
            FavoriteQuotesManager.get(getActivity()).updateFavoriteQuotesDatabase(editedQuote);


            mMyQuotesAdapter.setMyQuotesList(MyQuotesManager.get(getActivity()).getMyQuotes());
            mMyQuotesAdapter.notifyDataSetChanged();
        }







        if (requestCode == REQUEST_CODE_DELETE_MY_QUOTE_CONFIRMATION_DIALOG_FRAGMENT){

            boolean confirmDelete = intent.getBooleanExtra(RemoveMyQuoteConfirmationDialogFragment.EXTRA_SHOULD_DELETE_MY_QUOTE_ID, false);
            int position = intent.getIntExtra(RemoveMyQuoteConfirmationDialogFragment.EXTRA_POSITION_ID, 0);




            if (confirmDelete){

                //=========== Delete the Quote =====================================================
                List<Quote> myQuotesList = MyQuotesManager.get(getActivity()).getMyQuotes(); //Get full list of Quotes
                MyQuotesManager.get(getActivity()).deleteMyQuote(myQuotesList.get(position)); //Delete the Quote based on its position in the list


                //=========== Update RecyclerView Adapter =====================================================
                mMyQuotesAdapter.setMyQuotesList(MyQuotesManager.get(getActivity()).getMyQuotes()); //
                mMyQuotesAdapter.notifyDataSetChanged();


                //=========== Configure View =====================================================
                //If NO Quotes are present, show the Text saying "There are no Quotes..."
                if (MyQuotesManager.get(getActivity()).getMyQuotes().size() == 0){
                    mNoMyQuotesTextView.setVisibility(View.VISIBLE);
                    mMyQuotesRecyclerView.setVisibility(View.GONE);
                }


                ///=========== Show Toast =====================================================
                Toast quoteDeleteToast = Toast.makeText(getActivity(), "Quote Removed", Toast.LENGTH_LONG);
                quoteDeleteToast.show();
            }

        }




    }





    private void removeAllMyQuotes() {

        List<Quote> myQuotesList = MyQuotesManager.get(getActivity()).getMyQuotes();

        for (Quote myQuote : myQuotesList){
            MyQuotesManager.get(getActivity()).deleteMyQuote(myQuote);
        }


        mMyQuotesAdapter.setMyQuotesList(myQuotesList);
        mMyQuotesAdapter.notifyDataSetChanged();


        mNoMyQuotesTextView.setVisibility(View.VISIBLE);
        mMyQuotesRecyclerView.setVisibility(View.GONE);


        getActivity().invalidateOptionsMenu();


        Snackbar snackbar = Snackbar.make(mMyQuotesRecyclerView, "All Quotes removed from My Quotes", Snackbar.LENGTH_LONG);

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
