package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.QuotePictures;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import com.tieutech.android.quotespire.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;


//Fragment for displaying Favorited Quote Pictures
public class FavoriteQuotePicturesFragment extends Fragment{



    //================= INSTANCE VARIABLES ==============================================================
    private final String TAG = "FQPicturesFragment"; //Log for Logcat

    //RecyclerView variables
    private static RecyclerView mFavoriteQuotePicturesRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private FavoriteQuotePicturesAdapter mFavoriteQuotePicturesAdapter;
    private FavoriteQuotePictureViewHolder mFavoriteQuotePictureViewHolder;

    private TextView mNoFavoriteQuotePicturesTextView; //TextView to indicate if no Quotes are favorited




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





    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat

        View view = layoutInflater.inflate(R.layout.fragment_favorite_quote_pictures, viewGroup, false); //Assign View variable to layout resource file

        //Assign RecyclerView variables to associated Views
        mFavoriteQuotePicturesRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_quote_pictures_recycler_view);
        mNoFavoriteQuotePicturesTextView = (TextView) view.findViewById(R.id.no_favorite_quote_pictures_text_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFavoriteQuotePicturesRecyclerView.setLayoutManager(mGridLayoutManager);


        final List<QuotePicture> mFavoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures(); //Obtain all Favorited Quote Pictures from SQLiteDatabase of Favorite Quote Pictures
        Collections.reverse(mFavoriteQuotePictures); //Reverse the order of the List of QuotePicture objects SO THAT recently favorited QuotePicture object is displayed at the top of the RecyclerView


        mFavoriteQuotePicturesAdapter = new FavoriteQuotePicturesAdapter(mFavoriteQuotePictures);
        mFavoriteQuotePicturesRecyclerView.setAdapter(mFavoriteQuotePicturesAdapter);


        //If there ARE Quotes in the Favorite Quote Pictures SQLiteDatabase
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0){
            mNoFavoriteQuotePicturesTextView.setVisibility(View.GONE); //Remove the TextView indicating that there are no Quote Pictures Favorited
        }
        //If there are NO Quotes in the Favorite Quote Pictures SQLiteDatabase
        else{
            mNoFavoriteQuotePicturesTextView.setVisibility(View.VISIBLE); //Show the TextView indicating that there are no Quote Pictures Favorited
        }


        getActivity().setTitle("Favorites"); //Set title of the (swipe tab) Fragment

        return view;
    }




    //Helper method - called to update the UI - called every time a Favorite icon is pressed (to remove a Quote Picture) OR when the "Remove All" options menu icon is pressed
    public void updateUI(){

        getActivity().invalidateOptionsMenu(); //Refresh the options menu every time a list item is added/removed, so we could re-evaluate whether the menu item "Remove all" is still appropriate


        final List<QuotePicture> mFavoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures(); //Obtain all Favorited Quote Pictures from SQLiteDatabase of Favorite Quote Pictures
        Collections.reverse(mFavoriteQuotePictures); //Reverse the order of the List of QuotePicture objects SO THAT recently favorited QuotePicture object is displayed at the top of the RecyclerView
        mFavoriteQuotePicturesAdapter = new FavoriteQuotePicturesAdapter(mFavoriteQuotePictures);
        mFavoriteQuotePicturesRecyclerView.setAdapter(mFavoriteQuotePicturesAdapter);


        //If there ARE Quotes in the Favorite Quote Pictures SQLiteDatabase
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0){
            mNoFavoriteQuotePicturesTextView.setVisibility(View.GONE); //Remove the TextView indicating that there are no Quote Pictures Favorited
        }
        //If there are NO Quotes in the Favorite Quote Pictures SQLiteDatabase
        else{
            mNoFavoriteQuotePicturesTextView.setVisibility(View.VISIBLE); //Show the TextView indicating that there are no Quote Pictures Favorited
        }
    }




    //Adapter for RecyclerView (RecyclerView-Adapter)
    private class FavoriteQuotePicturesAdapter extends RecyclerView.Adapter<FavoriteQuotePictureViewHolder>{

        private List<QuotePicture> mFavoriteQuotePictures; //List of Favorite Quote Pictures


        //Constructor
        public FavoriteQuotePicturesAdapter(List<QuotePicture> favoriteQuotePictures){
            mFavoriteQuotePictures = favoriteQuotePictures; //Stash the Favorite Quotes parameter to the instance variable
            Log.i(TAG, "ADAPTER - mFavoriteQuotes: " + mFavoriteQuotePictures); //Log to Logcat

        }


        //Override getItemCount() method
        @Override
        public int getItemCount(){
            return mFavoriteQuotePictures.size(); //Size of the List of Favorite Quote Pictures
        }


        //Override onCreateViewHolder(..) method
        @Override
        public FavoriteQuotePictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //Instantiate LayoutInflater
            View view = layoutInflater.inflate(R.layout.list_item_favorite_quote_picture, viewGroup, false); //Inflate the Layout of the ViewHolder (i.e. list item)
            mFavoriteQuotePictureViewHolder = new FavoriteQuotePictureViewHolder(view); //Instantiate the ViewHolder object with its view

            return mFavoriteQuotePictureViewHolder; //Return ViewHolder
        }


        //Override onBindViewHolder(..) method
        @Override
        public void onBindViewHolder(FavoriteQuotePictureViewHolder favoriteQuotePictureViewHolder, int position){
            QuotePicture favoriteQuotePicture = mFavoriteQuotePictures.get(position); //Obtain Quote Picture to be binded to ViewHolder
            favoriteQuotePictureViewHolder.bind(favoriteQuotePicture, position); //Bind Quote to ViewHolder
        }


        //Set the List of Favorite Quote Pictures to the Adapter
        public void setFavoriteQuotePictures(List<QuotePicture> favoriteQuotePictures){
            mFavoriteQuotePictures = favoriteQuotePictures; //Stash List parameter to instance variable
        }

    }




    //Snackbar ref. variables relating to removing Quotes from the SQLiteDatabase of Favorited Quote Pictures
    private Snackbar snackbar;
    private Snackbar snackbar1;


    //ViewHolder for RecyclerView (RecyclerView-ViewHolder)
    private class FavoriteQuotePictureViewHolder extends RecyclerView.ViewHolder{

        public QuotePicture mFavoriteQuotePicture; //Quote Picture (that is binded to the ViewHolder)

        private LinearLayout mListItemView; //Layout of ViewHolder

        //Quote Picture IMAGE PROCESSING reference variables
        private byte[] mFavoriteQuotePictureByteArray; //Byte Array to store the Bytes data of the picture - and stored in the SQLiteDatabase of Quote Pictues (raw variable)
        private Bitmap mFavoriteQuotePictureBitmap; //Bitmap of the Quote Picture that is converted from the ByteArray (intermediate variable #1)
        private Drawable mFavoriteQuotePictureDrawable; //Drawable of Quote Picture that is converted from the Bitmap (intermediate variable #2)
        private ImageView mFavoriteQuotePictureImageView; //ImageView of Quote Picture (converted from Drawable) - to be displayed in the UI (final variable)



        //Constructor
        public FavoriteQuotePictureViewHolder(View view) {
            super(view);

            //Assign View instance reference variables to associated resource IDs
            mListItemView = (LinearLayout) view.findViewById(R.id.list_item_favorite_quote_picture_view); //List item View - contains the ImageView (below)
            mFavoriteQuotePictureImageView = (ImageView) view.findViewById(R.id.favorite_quote_picture_detail_fragment_quote_image_view); //ImageView of the Quote Picture
        }



        //Bind the Qutoe Picture to the ViewHolder
        public void bind(QuotePicture favoriteQuotePicture, final int position){

            mFavoriteQuotePicture = favoriteQuotePicture; //Stash the parameter variable to the instance variable

            mFavoriteQuotePictureByteArray = mFavoriteQuotePicture.getQuotePictureBitmapByteArray(); //Obtain ByteArray from the QuotePicture

            //If the ByteArray variable EXISTS
            if (mFavoriteQuotePictureByteArray != null){

                Log.i(TAG, "mFavoriteQuotePictureByteArray: " + mFavoriteQuotePictureByteArray); //Log to Logcat

                mFavoriteQuotePictureBitmap = BitmapFactory.decodeByteArray(mFavoriteQuotePictureByteArray, 0, mFavoriteQuotePictureByteArray.length); //Convert ByteArray to Bitmap
                mFavoriteQuotePictureDrawable = new BitmapDrawable(getResources(), mFavoriteQuotePictureBitmap); //Convert Bitmap to Drawable
                mFavoriteQuotePictureImageView.setImageDrawable(mFavoriteQuotePictureDrawable); //Convert Drawable to ImageView
            }


            loadImageFromStorage(mFavoriteQuotePicture.getQuotePictureBitmapFilePath(), mFavoriteQuotePicture.getId()); //Load the File Path to the Bimap of the Quote Picture


            //Log to Logcat
            Log.i(TAG, "ID: " + mFavoriteQuotePicture.getId());
            Log.i(TAG, "Quote Picture Bitmap path: " + mFavoriteQuotePicture.getQuotePictureBitmapFilePath());


            //Check for the IDs of each of the Quote Pictures in the Faovorited Quote Pictues SQLiteDatabase
            for(int i=0; i<FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size(); i++){
                Log.i(TAG, "IDs: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(i).getId());
            }


            //Log to Logcat
            Log.i(TAG, "CURRENT SIZE: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size());
            Log.i(TAG, "CURRENT ID: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getId());
            Log.i(TAG, "CURRENT BITMAP BYTE ARRAY: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getQuotePictureBitmapByteArray());


            final int positionOfFavoriteQuotePicture = position;


            //Set listener for List Item View
            mListItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    //Start FavoriteQuotePictureViewpagerActivity activity for viewing detailed version of the Quote Picture
                    Intent favoriteQuotePictureViewPagerActivityIntent = FavoriteQuotePictureViewPagerActivity.newIntent(
                            getContext(),
                            FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size(), //Size of the SQLiteDatabase of Quote Pictures (for ViewPager purposes)
                            mFavoriteQuotePicture.getQuotePictureBitmapFilePath(), //File path to Bitmap Array (so that the Bitmap array could be unpacked and displayed in the UI as ImageView)
                            FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getId()); //Position of the Quote Picture in the SQLiteDatabase of Quote Pictures

                    startActivity(favoriteQuotePictureViewPagerActivityIntent); //Start the activity
                }
            });
        }




        //Helper method - load the File Path to the Bimap of the Quote Picture
        private void loadImageFromStorage(String path, String quotePictureID) {

            try {
                File f = new File(path, quotePictureID + ".jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                mFavoriteQuotePictureImageView.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }




    //Override onCreateOptionsMenu(..) fragment lifecycle callback method
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);

        Log.i(TAG, "onCreateOptionsMenu(..) called"); //Log lifecycle callback

        //If there are one or more FavoriteQuotes in the list (i.e. one or more list items)
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0) {
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
                removeAllFavoriteQuotesConfirmationDialog(); //Start Confirmation AlertDialog to remove all Favorited Quote Pictures from the SQLiteDatabase of Favorited Quote Pictures
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }




    //Helper method - start Confirmation AlertDialog to remove all Favorited Quote Pictures from the SQLiteDatabase of Favorited Quote Pictures
    private void removeAllFavoriteQuotesConfirmationDialog(){

        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nRemove All\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color


        View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_favorite_quote_pictures_confirmation, null); //View of DialogFragment
        TextView removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage = (TextView) dialogFragmentView.findViewById(R.id.dialog_fragment_remove_all_favorite_quote_pictures_confirmation_message); //TextView of DialogFragment


        int favoriteQuotePicturesDatabaseSize = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size(); //Number of QuotePictures in the SQLiteDatabase of Favorited Quote Pictures


        //If there is ONE Quote Picture in the SQLiteDatabase
        if (favoriteQuotePicturesDatabaseSize == 1){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove this Quote Picture from Favorites?");
        }
        //If there are TWO Quote Picture in the SQLiteDatabase
        else if (favoriteQuotePicturesDatabaseSize == 2){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove these 2 Quote Pictures from Favorites?");
        }
        //If there are MORE THAN ONE Quote Picture in the SQLiteDatabase
        else if (favoriteQuotePicturesDatabaseSize > 1){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove all " + favoriteQuotePicturesDatabaseSize + " Quote Pictures from Favorites?");
        }


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quote_pictures, null);


        //Set up parameters AlertDialog
        final AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(dialogFragmentView)
                .setCustomTitle(dialogTitle)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAllFavoriteQuotePictures(); //Remove all of the Quote Pictures in the SQLiteDatabase of Favorited Quote Pictures
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




    //Helper method - remove all of the Quote Pictures in the SQLiteDatabase of Favorited Quote Pictures
    private void removeAllFavoriteQuotePictures(){

        int databaseIndex = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size(); //Index for database - equals to size of the database
        Log.i(TAG, "databaseIndex: " + databaseIndex); //Log to Logcat


        //Index through the entire database
        while (databaseIndex > 0) {

            List<QuotePicture> favoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures(); //Obtain List of all Favorited uote Pictures
            QuotePicture favoriteQuotePicture = favoriteQuotePictures.get(0); //Obtain first Quote Picture in the list


            //Make sure that the picture files of all the Favorited Quote Pictures are also deleted. NOTE: Deleting them from the SQLiteDatabase just isn't enough.
            //We need to delete from internal storage as well, so as to clear space for the app
            File file = new File(favoriteQuotePicture.getQuotePictureBitmapFilePath(), favoriteQuotePicture.getId() + ".jpg");
            boolean deleteFile = file.delete();
            Log.i(TAG, "Deleted file: " + favoriteQuotePicture.getQuotePictureBitmapFilePath() + favoriteQuotePicture.getId() + ".. State: " + deleteFile);


            favoriteQuotePicture.setFavorite(false); //Set the Quote Picture mFaorite field to 'false'
            FavoriteQuotePicturesManager.get(getActivity()).deleteFavoriteQuotePicture(favoriteQuotePicture); //Delete the Favorited Quote Picture form the database


            databaseIndex--; //Decrement database index
        }

        updateUI(); //Update the RecyclerView variables



        //Display Snackbar to indicate that all Favorited Quote Pictures have been removed
        Snackbar snackbar = Snackbar
                .make(mFavoriteQuotePicturesRecyclerView, Html.fromHtml("<font color=\"#ffffff\">All Quote Pictures removed from Favorites\"</font>"), Snackbar.LENGTH_LONG);

        View snackBarView = snackbar.getView();
        snackBarView.setMinimumHeight(150);
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));

        snackbar.show();
    }




    //Override onStart() fragment lifecycle callback method
    @Override
    public void onStart(){
        super.onStart();
        updateUI(); //Update the UI in case a Quote Picture has been "un-favorited" in the FavoriteqQuotePictureDetailFragment, and then the user returns to this fragment with a back button
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
            snackbar.setAction(null, null);
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
        Log.i(TAG, "onStop() called"); //Log to Logcat
    }




    //Override onDestroy() fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called"); //Log to Logcat
    }

}
