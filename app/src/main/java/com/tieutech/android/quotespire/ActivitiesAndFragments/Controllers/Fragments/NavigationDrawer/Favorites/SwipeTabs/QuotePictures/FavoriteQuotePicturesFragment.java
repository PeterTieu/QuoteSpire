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
        private Drawable mFavoriteQuotePictureDrawable; //Drawable of Quote Picture
        private ImageView mFavoriteQuotePictureImageView; //ImageView of Quote Picture
        private byte[] mFavoriteQuotePictureByteArray; //Byte Array to store
        private Bitmap mFavoriteQuotePictureBitmap; //Bitmap display Quote Picture



        //Constructor
        public FavoriteQuotePictureViewHolder(View view) {
            super(view);

            mListItemView = (LinearLayout) view.findViewById(R.id.list_item_favorite_quote_picture_view);
            mFavoriteQuotePictureImageView = (ImageView) view.findViewById(R.id.favorite_quote_picture_detail_fragment_quote_image_view);
        }





        public void bind(QuotePicture favoriteQuotePicture, final int position){

            mFavoriteQuotePicture = favoriteQuotePicture;



            mFavoriteQuotePictureByteArray = mFavoriteQuotePicture.getQuotePictureBitmapByteArray();


            if (mFavoriteQuotePictureByteArray != null){

                Log.i(TAG, "mFavoriteQuotePictureByteArray: " + mFavoriteQuotePictureByteArray);


                mFavoriteQuotePictureBitmap = BitmapFactory.decodeByteArray(mFavoriteQuotePictureByteArray, 0, mFavoriteQuotePictureByteArray.length);
//            mFavoriteQuotePictureBitmap = mFavoriteQuotePicture.getQuotePictureBitmap();
                mFavoriteQuotePictureDrawable = new BitmapDrawable(getResources(), mFavoriteQuotePictureBitmap);
                mFavoriteQuotePictureImageView.setImageDrawable(mFavoriteQuotePictureDrawable);
            }





            loadImageFromStorage(mFavoriteQuotePicture.getQuotePictureBitmapFilePath(), mFavoriteQuotePicture.getId());





            Log.i(TAG, "ID: " + mFavoriteQuotePicture.getId());
            Log.i(TAG, "Quote Picture Bitmap path: " + mFavoriteQuotePicture.getQuotePictureBitmapFilePath());




            for(int i=0; i<FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size(); i++){
                Log.i(TAG, "IDs: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(i).getId());
            }



            Log.i(TAG, "CURRENT SIZE: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size());
            Log.i(TAG, "CURRENT ID: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getId());
            Log.i(TAG, "CURRENT BITMAP BYTE ARRAY: " + FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getQuotePictureBitmapByteArray());


            final int positionOfFavoriteQuotePicture = position;


            mListItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent favoriteQuotePictureViewPagerActivityIntent = FavoriteQuotePictureViewPagerActivity.newIntent(
                            getContext(),
                            FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().size(),
                            mFavoriteQuotePicture.getQuotePictureBitmapFilePath(),
                            FavoriteQuotePicturesManager.get(getContext()).getFavoriteQuotePictures().get(position).getId());

                    startActivity(favoriteQuotePictureViewPagerActivityIntent);


                }

            });


        }


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

        //Log lifecycle callback
        Log.i(TAG, "onCreateOptionsMenu(..) called");

        //If there are one or more FavoriteQuotes in the list (i.e. one or more list items)
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0) {
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


        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nRemove All\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color



        View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_favorite_quote_pictures_confirmation, null);

        TextView removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage = (TextView) dialogFragmentView.findViewById(R.id.dialog_fragment_remove_all_favorite_quote_pictures_confirmation_message);




        int favoriteQuotePicturesDatabaseSize = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size();


//        String dialogMessage = null;
//        if (favoriteQuotePicturesDatabaseSize == 1){
//            dialogMessage = "Are you sure you want to remove this Quote Picture from Favorites?";
//        }
//        else if (favoriteQuotePicturesDatabaseSize == 2){
//            dialogMessage = "Are you sure you want to remove these 2 Quote Pictures from Favorites?";
//        }
//        else if (favoriteQuotePicturesDatabaseSize > 1){
//            dialogMessage = "Are you sure you want to remove all " + favoriteQuotePicturesDatabaseSize + " Quote Pictures from Favorites?";
//        }




        if (favoriteQuotePicturesDatabaseSize == 1){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove this Quote Picture from Favorites?");
        }
        else if (favoriteQuotePicturesDatabaseSize == 2){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove these 2 Quote Pictures from Favorites?");
        }
        else if (favoriteQuotePicturesDatabaseSize > 1){
            removeAllFavoriteQuotePicturesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove all " + favoriteQuotePicturesDatabaseSize + " Quote Pictures from Favorites?");
        }



        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_remove_all_favorite_quote_pictures, null);

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
                                removeAllFavoriteQuotePictures();
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










    private void removeAllFavoriteQuotePictures(){


        int databaseIndex = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size();
        Log.i(TAG, "databaseIndex: " + databaseIndex);

//        tempFavoriteQuotes = FavoriteQuotesManager.get(getActivity()).getFavoriteQuotes();

        while (databaseIndex > 0) {



            List<QuotePicture> favoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures();

            QuotePicture favoriteQuotePicture = favoriteQuotePictures.get(0);




            //Make sure that the picture files of all the Favorited Quote Pictures are also deleted. NOTE: Deleting them from the SQLiteDatabase just isn't enough.
            //We need to delete from internal storage as well, so as to clear space for the app
            File file = new File(favoriteQuotePicture.getQuotePictureBitmapFilePath(), favoriteQuotePicture.getId() + ".jpg");
            boolean deleteFile = file.delete();
            Log.i(TAG, "Deleted file: " + favoriteQuotePicture.getQuotePictureBitmapFilePath() + favoriteQuotePicture.getId() + ".. State: " + deleteFile);






            favoriteQuotePicture.setFavorite(false);
            FavoriteQuotePicturesManager.get(getActivity()).deleteFavoriteQuotePicture(favoriteQuotePicture);

//            updateUI();


            databaseIndex--;
        }

        updateUI();




        Snackbar snackbar = Snackbar
                .make(mFavoriteQuotePicturesRecyclerView, Html.fromHtml("<font color=\"#ffffff\">All Quote Pictures removed from Favorites\"</font>"), Snackbar.LENGTH_LONG);


        View snackBarView = snackbar.getView();
        snackBarView.setMinimumHeight(150);
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));

        snackbar.show();



    }









    @Override
    public void onStart(){
        super.onStart();

        updateUI(); //Update the UI in case a Quote Picture has been "un-favorited" in the FavoriteqQuotePictureDetailFragment, and then the user returns to this fragment with a back button

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
