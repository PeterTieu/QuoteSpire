package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.FavoriteQuotes;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureDetailActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureDetailFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.QuotePictureViewPagerActivity;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FavoriteQuotePictureDetailFragment extends Fragment{


    private final static String TAG = "FavQPicFragment";


    private QuotePicture mQuotePicture = new QuotePicture();

    private String mQuotePictureID;
    private int mViewPagerPosition;

    private Bitmap mQuotePictureBitmap;
    private Drawable mQuotePictureDrawable;

    private CheckBox mQuotePictureFavoriteIcon;
    private Button mQuotePictureShareIcon;
    private Button mQuotePictureDownloadIcon;
    private ImageView mQuotePictureImageView;



    private PhotoViewAttacher mPhotoViewAttacher; //Adds Zoom-Effect to ImageView



    public static FavoriteQuotePictureDetailFragment newInstance(String quotePictureID){

        //Create argument-bundle to pass data to QuotePictureDetailFragment
        Bundle argumentBundle = new Bundle();

//        argumentBundle.putString(QuotePictureViewPagerActivity.QUOTE_PICTURE_ID, quotePictureID); //Add Quote Picture ID (String) to the argument-bundle
//        argumentBundle.putByteArray(QuotePictureViewPagerActivity.QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureByteArray); //Add Quote Picture Byte Array (byte[]) to the argument-bundle
        argumentBundle.putString(QuotePictureViewPagerActivity.QUOTE_PICTURE_ID, quotePictureID); //Add Quote Picture ID (String) to the argument-bundle

        //Create QuotePictureDetailFragment
        FavoriteQuotePictureDetailFragment favoriteQuotePictureDetailFragment = new FavoriteQuotePictureDetailFragment();

        //Link the argument-bundle to the QuotePictureDetailFragment object
        favoriteQuotePictureDetailFragment.setArguments(argumentBundle);

        //Return the QuotePictureDetailFragment
        return favoriteQuotePictureDetailFragment;
    }










    //Override onAttach(..) fragment lifecycle callback method
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //Log to Logcat
        Log.i(TAG, "onAttach(..) called");


    }





    //Override onCreate(..) fragment lifecycle callback method
    @Override
    public void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);

        //Log to Logcat
        Log.i(TAG, "onCreate(..) called");

        setRetainInstance(true); //Retain data even when the view has changed (i.e. screen rotation)

        setHasOptionsMenu(true); //Declare that there is an opeionts menu


        //If arguments passed to CategoryChooserFragment from CategoryChooserActivity exists
        if (getArguments() != null){

//            Log.i(TAG, "ID: " + mQuotePicture.getId());


//            mQuotePictureID = getArguments().getString(QuotePictureViewPagerActivity.QUOTE_PICTURE_ID);
//            mQuotePictureByteArray = getArguments().getByteArray(QuotePictureViewPagerActivity.QUOTE_PICTURE_BYTE_ARRAY_KEY);
            mQuotePictureID = getArguments().getString(QuotePictureViewPagerActivity.QUOTE_PICTURE_ID);
//            mViewPagerPosition = getArguments().getInt(QuotePictureViewPagerActivity.VIEW_PAGER_POSITION);







            Log.i(TAG, "mQuotePicture.getQuotePictureBitmapByteArray(): " + mQuotePicture.getQuotePictureBitmapByteArray());


            //Set ID and Bitmap for the mQuotePicture
            mQuotePicture.setId(mQuotePictureID);


            Log.i(TAG, "mQuotePicture.getID(): " + mQuotePicture.getId());



//            QuotePictureViewPagerActivity.sViewPager.setCurrentItem(0);




//            QuotePictureViewPagerActivity.sViewPager.setCurrentItem(QuotePictureViewPagerActivity.sViewPager.getCurrentItem()+1);


//            Log.i(TAG, "QuotePictureViewPagerActivity.sViewPagerPositionSelected: " + QuotePictureViewPagerActivity.sViewPager.getCurrentItem());




        }


//        mCallbacks.scrollViewPagerForward();










        //If permission for location tracking HAS been granted at runtime (by user)
        if (hasWriteExternalStoragePermission() == false){
            //Request (user) for storage permissions - as they are 'dangerous' permissions (and therefore must be requested)
            requestPermissions(STORAGE_PERMISSIONS, REQUEST_CODE_FOR_STORAGE_PERMISSIONS);
        }



//        //Declare that this fragment participates in populating menus
//        setHasOptionsMenu(true);

        //Reset options menu
//        getActivity().invalidateOptionsMenu();

    }






    interface Callbacks{
        void scrollViewPagerBackward();
        void scrollViewPagerForward();
    }










    private static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_FOR_STORAGE_PERMISSIONS = 1; //Request code to WRITE to external storage




    //Check if STORAGE permissions have been granted at runtime (by user)
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

        //Log lifecycle callback
        Log.i(TAG, "onCreateView(..) called");


        //Log lifecycle callback
//        Log.i(TAG, "ID: " + mQuotePicture.getId());




        //Obtain View from the layout of the CategoryChooserFragment
        View view = layoutInflater.inflate(R.layout.fragment_quote_picture_detail, viewGroup, false);

        mQuotePictureFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_picture_detail_fragment_favorite_icon);
        mQuotePictureShareIcon = (Button) view.findViewById(R.id.quote_picture_detail_fragment_share_icon);
        mQuotePictureDownloadIcon = (Button) view.findViewById(R.id.quote_picture_detail_fragment_download_icon);


        mQuotePictureImageView = (ImageView) view.findViewById(R.id.quote_picture_image_view);


















        mQuotePictureDrawable = new BitmapDrawable(getResources(), mQuotePictureBitmap);
        mQuotePictureImageView.setImageDrawable(mQuotePictureDrawable);


        //Add zoom-effect to the ImageView
        mPhotoViewAttacher = new PhotoViewAttacher(mQuotePictureImageView);
        mPhotoViewAttacher.update();







//        mQuotePictureFavoriteIcon.setButtonDrawable(mQuoteOfTheDay.isFavorite() ? R.drawable.ic_imageview_favorite_on: R.drawable.ic_imageview_favorite_off);


        mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off);


        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) != null) {

//            mRandomQuote.setFavorite(true);
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_on);

        }
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null) {

//            mRandomQuote.setFavorite(false);
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off);

        }




        mQuotePictureFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {



//                mRandomQuote.setFavorite(isChecked);


                mQuotePictureFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_quote_picture_favorite_on: R.drawable.ic_imageview_quote_picture_favorite_off);


                if (isChecked == true){

                    if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null){

                        String quotePictureID = mQuotePicture.getId();
                        String quotePictureBitmapFilePath = saveToInternalStorage(mQuotePictureBitmap, quotePictureID);
                        mQuotePicture.setQuotePictureBitmapFilePath(quotePictureBitmapFilePath);
                        Log.i(TAG, "ID: " + mQuotePicture.getId());
                        Log.i(TAG, "Quote Picture Bitmap path: " + mQuotePicture.getQuotePictureBitmapFilePath());



                        FavoriteQuotePicturesManager.get(getActivity()).addFavoriteQuotePicture(mQuotePicture);
                        FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture);
                    }
                    else{
                        //Do nothing
                    }

                }

                if (isChecked == false){
                    FavoriteQuotePicturesManager.get(getActivity()).deleteFavoriteQuotePicture(mQuotePicture);
                    FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture);

                }


            }
        });








        //Set listener for Share Icon
        mQuotePictureShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                //Save Quote Picture Bitmap as cache data in the FileProvider
                Uri quotePictureBitmapURI = saveBitmap(mQuotePictureBitmap);

                //Share the Quote Picture Bitmap
                shareQuotePictureBitmap(quotePictureBitmapURI);

            }
        });






        mQuotePictureDownloadIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){


                //Set string for filename
                String fileName = "quote_picture";

                //Insert image to Gallery. Also, return a URL String - If the image failed to be saved in Gallery, the URL would be null
                String savedImageUrl = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mQuotePictureBitmap, fileName, "Quote Search");

                //If the image file failed to be saved in Gallery
                if (savedImageUrl == null) {
                    Toast.makeText(getActivity(), "Unable to save Picture.\nCheck Storage Permissions", Toast.LENGTH_LONG).show();
                }
                //If the image file is SUCCESSFULLY saved to Gallery
                else {
                    //Display toast for successful save
                    Toast.makeText(getActivity(), "Picture saved to internal storage", Toast.LENGTH_LONG).show();
                }




            }
        });












        setHasOptionsMenu(true);

        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Quote Picture");


        return view;
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
            savedBitmapURI = FileProvider.getUriForFile(getContext(), "com.petertieu.android.quotesearch.fileprovider", file);

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

        //Log to Logcat
        Log.i(TAG, "onStart() called");
    }




    //Override onResume() fragment lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();

        //Log to Logcat
        Log.i(TAG, "onResume() called");
    }









    //Override onPause() fragment lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause() called");
    }




    //Override onStop() fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop() called");
    }




    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i(TAG, "onDestroyView() called");
    }




    //Override onDestroy() fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
    }




    //Override onDetach() fragment lifecycle callback method
    @Override
    public void onDetach(){
        super.onDetach();

        //Log in Logcat
        Log.i(TAG, "onDetach() called");
    }
}
