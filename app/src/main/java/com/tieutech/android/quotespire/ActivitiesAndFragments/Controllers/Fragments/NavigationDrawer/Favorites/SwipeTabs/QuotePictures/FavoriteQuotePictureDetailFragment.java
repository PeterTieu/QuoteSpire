package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.SwipeTabs.QuotePictures;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import com.tieutech.android.quotespire.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;


//Fragment for displaying the detail view of a Quote Picture (from the Favorited Quote Pictures SQLiteDatabase)
    //Called by FavoriteQuotePictureViewPagerActivity, which pages between the FavoriteQuotePictureDetailFragment fragments
public class FavoriteQuotePictureDetailFragment extends Fragment{


    //================= INSTANCE VARIABLES ==============================================================

    private final static String TAG = "FavQPicFragment"; //Log to Logcat


    private QuotePicture mQuotePicture = new QuotePicture(); //Quote Picture to display

    private String mQuotePictureBitmapFilePath; //Path to the Bitmap of the QuotePicture
    private String mQuotePictureID; //Unique ID of the Quote Picture
    private int mViewPagerPosition; //Position of the Quote Picture

    private Bitmap mQuotePictureBitmap; //Bitmap obtained from the Bitmap File Path of the Quote Picture (sent from the FavoriteQuotePictureViewPagerActivity
    private Drawable mQuotePictureDrawable;

    //View variables
    private CheckBox mQuotePictureFavoriteIcon; //Favorite Icon
    private Button mQuotePictureShareIcon; //Share Icon
    private Button mQuotePictureDownloadIcon; //Download picture Icon
    private ImageView mQuotePictureImageView; //ImageView to display the Quote Picture
    private Button mQuotePicturePreviousButton; //Previous Button to toggle to FavoriteQuotePictureDetailFragment in the ViewPagerActivity containing the previous Quote Picture
    private Button mQuotePictureNextButton; //Next Button to toggle to FavoriteQuotePictureDetailFragment in the ViewPagerActivity containing the next Quote Picture

    //Snackbars
    private Snackbar snackbar;
    private Snackbar snackbar1;

    private PhotoViewAttacher mPhotoViewAttacher; //Adds Zoom-Effect to ImageView




    //================= METHODS ==============================================================

    //Encapsulating constructor - called by FavoriteQuotePictureDetailFragment
    public static FavoriteQuotePictureDetailFragment newInstance(String quotePictureBitmapFilePath, String quotePictureID){

        Bundle argumentBundle = new Bundle(); //Create argument-bundle to pass data to QuotePictureDetailFragment

        argumentBundle.putString(FavoriteQuotePictureViewPagerActivity.QUOTE_PICTURE_BITMAP_FILE_PATH, quotePictureBitmapFilePath); //Add File Path to the Bitmap of the Quote Picture
        argumentBundle.putString(FavoriteQuotePictureViewPagerActivity.QUOTE_PICTURE_ID, quotePictureID); //Add Quote Picture ID (String) to the argument-bundle

        FavoriteQuotePictureDetailFragment favoriteQuotePictureDetailFragment = new FavoriteQuotePictureDetailFragment(); //Create QuotePictureDetailFragment

        favoriteQuotePictureDetailFragment.setArguments(argumentBundle); //Link the argument-bundle to the QuotePictureDetailFragment object

        return favoriteQuotePictureDetailFragment; //Return the QuotePictureDetailFragment
    }




    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Log.i(TAG, "onAttach(..) called"); //Log to Logcat
    }




    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);

        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setRetainInstance(true); //Retain data even when the view has changed (i.e. screen rotation)
        setHasOptionsMenu(true); //Declare that there is an opeionts menu


        //If arguments passed to CategoryChooserFragment from CategoryChooserActivity exists
        if (getArguments() != null){

            mQuotePictureBitmapFilePath = getArguments().getString(FavoriteQuotePictureViewPagerActivity.QUOTE_PICTURE_BITMAP_FILE_PATH); //Obtain File Path to the Bitmap of the Quote Picture
            mQuotePictureID = getArguments().getString(FavoriteQuotePictureViewPagerActivity.QUOTE_PICTURE_ID); //Obtain the unique ID of the Quote Picture


            //Set ID and Bitmap for the mQuotePicture (redundant, as they were already done)
            mQuotePicture.setQuotePictureBitmapFilePath(mQuotePictureBitmapFilePath);
            mQuotePicture.setId(mQuotePictureID);

            Log.i(TAG, "mQuotePicture.getID(): " + mQuotePicture.getId());
        }


        //If permission for location tracking HAS NOT been granted at runtime (by user)
        if (hasWriteExternalStoragePermission() == false){
            requestPermissions(STORAGE_PERMISSIONS, REQUEST_CODE_FOR_STORAGE_PERMISSIONS); //Request (user) for storage permissions - as they are 'dangerous' permissions (and therefore must be requested)
        }

    }




    interface Callbacks{
        void scrollViewPagerBackward();
        void scrollViewPagerForward();
    }




    private static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_FOR_STORAGE_PERMISSIONS = 1; //Request code to WRITE to external storage




    //Helper method - Check if STORAGE permissions have been granted at runtime (by user)
    private boolean hasWriteExternalStoragePermission(){

        //If permission is granted, result = PackageManager.PERMISSION_GRANTED (else, PackageManager.PERMISSION_DENIED).
        //NOTE: Permissions WRITE_EXTERNAL_STORAGE and READ_EXTERNAL_STORAGE are in the same PERMISSION GROUP, called STORAGE.
        //If one permission is a permission group is granted/denied access, the same applies to all other permissions in that group.
        // Other groups include: CALENDAR, CAMERA, CONTACTS, MICROPHONE, PHONE, SENSORS, SMS, STORAGE.
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (result == PackageManager.PERMISSION_GRANTED);
    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log lifecycle callback


        //Obtain View from the layout of the CategoryChooserFragment
        View view = layoutInflater.inflate(R.layout.fragment_favorite_quote_picture_detail, viewGroup, false);

        //Assign View variables to the associated resource IDs
        mQuotePictureFavoriteIcon = (CheckBox) view.findViewById(R.id.favorite_quote_picture_detail_fragment_favorite_icon);
        mQuotePictureShareIcon = (Button) view.findViewById(R.id.favorite_quote_picture_detail_fragment_share_icon);
        mQuotePictureDownloadIcon = (Button) view.findViewById(R.id.favorite_quote_picture_detail_fragment_download_icon);
        mQuotePictureImageView = (ImageView) view.findViewById(R.id.favorite_quote_picture_detail_fragment_quote_image_view);
        mQuotePicturePreviousButton = (Button) view.findViewById(R.id.favorite_quote_picture_detail_fragment_previous_button);
        mQuotePictureNextButton = (Button) view.findViewById(R.id.favorite_quote_picture_detaill_fragment_next_button);

        //Use the File Path of the Bitmap of the Quote Picture to obtain the Bitmap, and then set the mQuotePictureImageView to this Bitmap
        loadImageFromStorage(mQuotePicture.getQuotePictureBitmapFilePath(), mQuotePicture.getId());

        //Add zoom-effect to the ImageView
        mPhotoViewAttacher = new PhotoViewAttacher(mQuotePictureImageView);
        mPhotoViewAttacher.update();


        //Set drawable for the Favorite Icon, based on whether or not the Quote Picture is in the Favorited Quote Pictures SQLiteDatabase
        mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off);

        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) != null) {
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_on);
        }
        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'inactive'
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null) {
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off);
        }


        //Set listener for Favorite Icon
        mQuotePictureFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                //Set Drawable based on 'checked' state of the Button
                mQuotePictureFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_quote_picture_favorite_on: R.drawable.ic_imageview_quote_picture_favorite_off);

                //If the Button is 'checked'
                if (isChecked == true){

                    //If the Quote Picture is in the database
                    if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null){

                        String quotePictureID = mQuotePicture.getId(); //Get the IDE of the Quote Picture
                        String quotePictureBitmapFilePath = saveToInternalStorage(mQuotePictureBitmap, quotePictureID); //Save the File Path of the Bitmap to the internal storage
                        mQuotePicture.setQuotePictureBitmapFilePath(quotePictureBitmapFilePath); //Set the File Path to the mQuotePictureBitmapFilePath member variable of the Quote Picture

                        Log.i(TAG, "ID: " + mQuotePicture.getId()); //Log to Logcat
                        Log.i(TAG, "Quote Picture Bitmap path: " + mQuotePicture.getQuotePictureBitmapFilePath()); //Log to Logcat

                        FavoriteQuotePicturesManager.get(getActivity()).addFavoriteQuotePicture(mQuotePicture); //Add the Quote Picture to the Favorited Quote Pictures SQLiteDatabase
                        FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture); //Update the database
                    }
                    else{
                        //Do nothing
                    }
                }

                //If the Button is 'unchecked'
                if (isChecked == false){
                    FavoriteQuotePicturesManager.get(getActivity()).deleteFavoriteQuotePicture(mQuotePicture); //Remove the Quote Picture from the Favorited Quote Pictures SQLiteDatabase
                    FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture); //Update the database


                    //Create Handler to perform concurrent thread - to display Snackbar indicating that the Quote Picture has been removed from the database
                    final Handler handler = new Handler();

                    //Perform Runnable after 100ms delay
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            //Creat 'parent' Snackbar to indicate removal of Quote Picture from the database
                            snackbar = Snackbar
                                    .make(mQuotePictureImageView, Html.fromHtml("<font color=\"#ffffff\">Quote Picture has been removed from Favorites</font>"), Snackbar.LENGTH_LONG)

                                    //Create "UNDO Snackbar and set a listener to it (i.e. Snackbar that is activated by pressing the "UNDO" button)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //Create Snackbar to "UNDO" the action of removing the Quote Picture from the database
                                            snackbar1 = Snackbar.make(view, Html.fromHtml("<font color=\"#ffffff\">Quote Picture has been re-added to Favorites!</font>"), Snackbar.LENGTH_LONG);

                                            //Configure Snackbar View
                                            View snackBarActionView = snackbar1.getView();
                                            snackBarActionView.setMinimumHeight(150);
                                            snackBarActionView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_teal));

                                            snackbar1.show(); //Show the 'action' Snackbar

                                            FavoriteQuotePicturesManager.get(getActivity()).addFavoriteQuotePicture(mQuotePicture); //Re-add the Quote Picture to the database

                                            mQuotePictureFavoriteIcon.setChecked(true); //Set the Favorite Icon to 'checked'
                                        }
                                    });

                            //Configure 'parent' Snackbar View
                            View snackBarView = snackbar.getView();
                            snackBarView.setMinimumHeight(150);
                            snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal));
                            snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.snackBarUndoAction));

                            snackbar.show(); //Show the 'parent' Snackbar
                        }

                    }, 100);
                }
            }
        });



        //Set listener for Share Icon
        mQuotePictureShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Uri quotePictureBitmapURI = saveBitmap(mQuotePictureBitmap); //Save Quote Picture Bitmap as cache data in the FileProvider
                shareQuotePictureBitmap(quotePictureBitmapURI); //Share the Quote Picture Bitmap
            }
        });



        //Set listener for Download Icon
        mQuotePictureDownloadIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String fileName = "quote_picture"; //Set string for filename

                //Save image to Gallery. Also, return a URL String - If the image failed to be saved in Gallery, the URL would be null
                String savedImageUrl = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mQuotePictureBitmap, fileName, "Quote Search");

                //If the image file failed to be saved in Gallery
                if (savedImageUrl == null) {
                    Toast.makeText(getActivity(), "Unable to save Picture.\nCheck Storage Permissions", Toast.LENGTH_LONG).show(); //Display toast for unsuccessful save
                }
                //If the image file is SUCCESSFULLY saved to Gallery
                else {
                    Toast.makeText(getActivity(), "Picture saved to internal storage", Toast.LENGTH_LONG).show(); //Display toast for successful save
                }
            }
        });



        // For scrolling to PREVIOUS item
        mQuotePicturePreviousButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mQuotePicturePreviousButton.setEnabled(true);
                FavoriteQuotePictureViewPagerActivity.sViewPager.setCurrentItem(getNextPossibleItemIndex(-1), true); //Scroll to the next fragment hosted by the ViewPagerActivity
            }
        });



        // For scrolling to NEXT item
        mQuotePictureNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FavoriteQuotePictureViewPagerActivity.sViewPager.setCurrentItem(getNextPossibleItemIndex(1), true); //Scroll to the next fragment hosted by the ViewPagerActivity
            }
        });


        setHasOptionsMenu(true); //Declare that the options menu will be available
        getActivity().invalidateOptionsMenu(); //Update the options menu
        getActivity().setTitle("Quote Picture"); //Set title of the fragment

        return view;
    }




    //Helper method - obtain the previous/next fragment hosted by the ViewPagerActivity
    private int getNextPossibleItemIndex (int change) {

        int currentIndex = FavoriteQuotePictureViewPagerActivity.sViewPager.getCurrentItem();
        int total = FavoriteQuotePictureViewPagerActivity.sViewPager.getAdapter().getCount();

        if (currentIndex + change < 0) {
            return total;
        }

        return Math.abs((currentIndex + change) % total) ;
    }




    //Helper method - use the File Path of the Bitmap of the Quote Picture to obtain the Bitmap
    private void loadImageFromStorage(String path, String quotePictureID) {

        try {
            File f = new File(path, quotePictureID + ".jpg");
            mQuotePictureBitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            mQuotePictureImageView.setImageBitmap(mQuotePictureBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }






    private String saveToInternalStorage(Bitmap bitmapImage, String quotePictureID){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,quotePictureID + ".jpg");

        Log.i(TAG, "FULL DIRECTORY FILE PATH: " + mypath);


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }










    //Helper method - save the File Path of the Bitmap to the internal storage
    /**
     * Saves the image as PNG to the app's cache directory.
     * @param quotePictureBitmap Bitmap to save.
     * @return Uri of the saved file or null
     */
    private Uri saveBitmap(Bitmap quotePictureBitmap) {
        //TODO - Should be processed in another thread
        File quotePictureBitmapSaveFolder = new File(getContext().getCacheDir(), "images");
        Uri savedBitmapURI = null;

        try {
            quotePictureBitmapSaveFolder.mkdirs();
            File file = new File(quotePictureBitmapSaveFolder, "shared_image.png");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            quotePictureBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            savedBitmapURI = FileProvider.getUriForFile(getContext(), "com.tieutech.android.quotespire.fileprovider", file);

            Log.i(TAG, savedBitmapURI.toString());

        } catch (IOException IOException) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + IOException.getMessage());
        }

        return savedBitmapURI;
    }




    /**
     * Shares the PNG image from Uri.
     * @param savedBitmapURI Uri of image to share.
     */
    private void shareQuotePictureBitmap(Uri savedBitmapURI){

        Intent shareBitmapIntent = new Intent(Intent.ACTION_SEND); //Create implicit Intent with send action
        shareBitmapIntent.setType("image/*"); //Set the type of the Intent to text
        shareBitmapIntent.putExtra(Intent.EXTRA_STREAM, savedBitmapURI); //Set text of Intent
        shareBitmapIntent = Intent.createChooser(shareBitmapIntent, "Share Quote Picture via"); //Set chooser title
        startActivity(shareBitmapIntent); //Start the Intent
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
        Log.i(TAG, "onPause() called"); //Log to Logcat
    }




    //Override onStop() fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop() called"); //Log to Logcat
    }




    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i(TAG, "onDestroyView() called"); //Log to Logcat
    }




    //Override onDestroy() fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called"); //Log to Logcat
    }




    //Override onDetach() fragment lifecycle callback method
    @Override
    public void onDetach(){
        super.onDetach();

        //Log in Logcat
        Log.i(TAG, "onDetach() called"); //Log to Logcat
    }

}