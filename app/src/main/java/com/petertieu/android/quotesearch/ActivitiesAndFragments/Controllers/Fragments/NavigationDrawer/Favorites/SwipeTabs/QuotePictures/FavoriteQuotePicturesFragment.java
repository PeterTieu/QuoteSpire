package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.QuotePictures;

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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class FavoriteQuotePicturesFragment extends Fragment{



    //Log for Logcat
    private final String TAG = "FQPicturesFragment";

    private static RecyclerView mFavoriteQuotePicturesRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    private FavoriteQuotePicturesAdapter mFavoriteQuotePicturesAdapter;
    private FavoriteQuotePictureViewHolder mFavoriteQuotePictureViewHolder;


    private TextView mNoFavoriteQuotePicturesTextView;




    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        Log.i(TAG, "onAttach() called");

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate() called"); //Log in Logcat

        setHasOptionsMenu(true);
    }





    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log in Logcat


        View view = layoutInflater.inflate(R.layout.fragment_favorite_quote_pictures, viewGroup, false);


        mFavoriteQuotePicturesRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_quote_pictures_recycler_view);
        mNoFavoriteQuotePicturesTextView = (TextView) view.findViewById(R.id.no_favorite_quote_pictures_text_view);



        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFavoriteQuotePicturesRecyclerView.setLayoutManager(mGridLayoutManager);





        final List<QuotePicture> mFavoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures();
        Collections.reverse(mFavoriteQuotePictures); //Reverse the order of the List of QuotePicture objects SO THAT recently favorited QuotePicture object is displayed at the top of the RecyclerView


        mFavoriteQuotePicturesAdapter = new FavoriteQuotePicturesAdapter(mFavoriteQuotePictures);

        mFavoriteQuotePicturesRecyclerView.setAdapter(mFavoriteQuotePicturesAdapter);



        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0){
            mNoFavoriteQuotePicturesTextView.setVisibility(View.GONE);
        }
        else{
            mNoFavoriteQuotePicturesTextView.setVisibility(View.VISIBLE);
        }





//        updateUI();





        getActivity().setTitle("Favorites");
        return view;
    }




    public void updateUI(){

        getActivity().invalidateOptionsMenu(); //Refresh the options menu every time a list item is added/removed, so we could re-evaluate whether the menu item "Remove all" is still appropriate



        final List<QuotePicture> mFavoriteQuotePictures = FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures();
        Collections.reverse(mFavoriteQuotePictures); //Reverse the order of the List of QuotePicture objects SO THAT recently favorited QuotePicture object is displayed at the top of the RecyclerView
        mFavoriteQuotePicturesAdapter = new FavoriteQuotePicturesAdapter(mFavoriteQuotePictures);
        mFavoriteQuotePicturesRecyclerView.setAdapter(mFavoriteQuotePicturesAdapter);



        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePictures().size() > 0){
            mNoFavoriteQuotePicturesTextView.setVisibility(View.GONE);
        }
        else{
            mNoFavoriteQuotePicturesTextView.setVisibility(View.VISIBLE);
        }
    }





    private class FavoriteQuotePicturesAdapter extends RecyclerView.Adapter<FavoriteQuotePictureViewHolder>{

        private List<QuotePicture> mFavoriteQuotePictures;



        public FavoriteQuotePicturesAdapter(List<QuotePicture> favoriteQuotePictures){
            mFavoriteQuotePictures = favoriteQuotePictures;

            Log.i(TAG, "ADAPTER - mFavoriteQuotes: " + mFavoriteQuotePictures);

        }

        @Override
        public int getItemCount(){
            return mFavoriteQuotePictures.size();
        }


        @Override
        public FavoriteQuotePictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_favorite_quote_picture, viewGroup, false);


            mFavoriteQuotePictureViewHolder = new FavoriteQuotePictureViewHolder(view);


            return mFavoriteQuotePictureViewHolder;
        }




        @Override
        public void onBindViewHolder(FavoriteQuotePictureViewHolder favoriteQuotePictureViewHolder, int position){
            QuotePicture favoriteQuotePicture = mFavoriteQuotePictures.get(position);

            favoriteQuotePictureViewHolder.bind(favoriteQuotePicture, position);

//            favoriteQuotePictureViewHolder.mFavoriteQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_favorite_on);
        }



        public void setFavoriteQuotePictures(List<QuotePicture> favoriteQuotePictures){
            mFavoriteQuotePictures = favoriteQuotePictures;
        }

    }





    private Snackbar snackbar;
    private Snackbar snackbar1;









    private class FavoriteQuotePictureViewHolder extends RecyclerView.ViewHolder{


        private LinearLayout mListItemView;

        public QuotePicture mFavoriteQuotePicture;

        private Drawable mFavoriteQuotePictureDrawable;
        private ImageView mFavoriteQuotePictureImageView;

        private byte[] mFavoriteQuotePictureByteArray;
        private Bitmap mFavoriteQuotePictureBitmap;



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
                .make(mFavoriteQuotePicturesRecyclerView, "All Quote Pictures removed from Favorites", Snackbar.LENGTH_LONG);


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
