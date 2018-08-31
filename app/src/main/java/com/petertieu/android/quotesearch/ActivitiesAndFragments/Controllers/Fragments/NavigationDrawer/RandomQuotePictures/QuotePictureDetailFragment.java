package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.FavoriteQuotePicturesManager;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;
import com.petertieu.android.quotesearch.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;


//Fragment to display the QuotePicture RecyclerView list-item selected in:
//RandomQuotePicturesFragment, SearchQuotePicturesByCategoryFragment, or SearchQuotePicturesByAuthorFragment.
//NOTE: This Fragment is hosted by: QuotePictuerDetailActivity
public class QuotePictureDetailFragment extends Fragment{

    private final static String TAG = "QPDFragment"; //Tag for Logcat

    private QuotePicture mQuotePicture = new QuotePicture(); //QuotePicture retrieved from the intent passed from the hosting activity (QuotePictuerDetailActivity)

    private String mQuotePictureID; //Intent extra retrieved from the hosting activity: QuotePicture ID
    private byte[] mQuotePictureByteArray; //Intent extra retrieved from the hosting activity: Bitmap Byte Array (stores data of the Bitmap to be displayed)

    private Bitmap mQuotePictureBitmap; //Bitmap (converted from mQuotePictureByteArray - the Bitmap Byte Array)
    private Drawable mQuotePictureDrawable; //Drawable to be displayed in the ImageView (converted from mQuotePictureBitmap - the Bitmap)

    private CheckBox mQuotePictureFavoriteIcon; //View: Favorite icon
    private Button mQuotePictureShareIcon; //View: Share icon
    private Button mQuotePictureDownloadIcon; //View: Download icon
    private ImageView mQuotePictureImageView; //View: Displays the QuotePicture Picture

    private PhotoViewAttacher mPhotoViewAttacher; //Adds Zoom-Effect to mQuotePictureImageView

    private static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_FOR_STORAGE_PERMISSIONS = 1; //Request code to WRITE to external storage





    //Encapsulating method - called by: QuotePictureDetailActivity to create this Fragment (before hosting it)
    public static QuotePictureDetailFragment newInstance(String quotePictureID, byte[] quotePictureByteArray){

        Bundle argumentBundle = new Bundle(); //Create argument-bundle to pass data to QuotePictureDetailFragment

        argumentBundle.putString(QuotePictureDetailActivity.QUOTE_PICTURE_ID_KEY, quotePictureID); //Add Quote Picture ID (String) to the argument-bundle
        argumentBundle.putByteArray(QuotePictureDetailActivity.QUOTE_PICTURE_BYTE_ARRAY_KEY, quotePictureByteArray); //Add Quote Picture Byte Array (byte[]) to the argument-bundle

        QuotePictureDetailFragment quotePictureDetailFragment = new QuotePictureDetailFragment(); //Create QuotePictureDetailFragment

        quotePictureDetailFragment.setArguments(argumentBundle); //Link the argument-bundle to the QuotePictureDetailFragment object

        return quotePictureDetailFragment; //Return the QuotePictureDetailFragment
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

        Log.i(TAG, "onCreate(..) called"); //Log to Logcat

        setRetainInstance(true); //Retain data even when the view has changed (i.e. screen rotation)
        setHasOptionsMenu(true); //Declare that there is an opeionts menu

        //If arguments passed to CategoryChooserFragment from CategoryChooserActivity exists
        if (getArguments() != null){

            mQuotePictureID = getArguments().getString(QuotePictureDetailActivity.QUOTE_PICTURE_ID_KEY); //Retrieve data passed from activity: QuotePiture ID
            mQuotePictureByteArray = getArguments().getByteArray(QuotePictureDetailActivity.QUOTE_PICTURE_BYTE_ARRAY_KEY); //Retrieve data passed from activity: Bitmap Byte Array

            mQuotePicture.setId(mQuotePictureID); //Set ID and Bitmap to the QuotePicture variable
            mQuotePicture.setQuotePictureBitmapByteArray(mQuotePictureByteArray); //Set the Bitmap Byte Array to the QuotePicture variable

            mQuotePictureBitmap = BitmapFactory.decodeByteArray(mQuotePictureByteArray, 0, mQuotePictureByteArray.length); //Parese the Bitmap Byte Array to a Bitmap
        }

    }




    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        Log.i(TAG, "onCreateView(..) called"); //Log to Logcat

        getActivity().setTitle("Quote Picture"); //Set title for the Fragment


        View view = layoutInflater.inflate(R.layout.fragment_quote_picture_detail, viewGroup, false); //Obtain View from the layout of the fragment

        mQuotePictureFavoriteIcon = (CheckBox) view.findViewById(R.id.quote_picture_detail_fragment_favorite_icon); //Assign Favorite icon View
        mQuotePictureShareIcon = (Button) view.findViewById(R.id.quote_picture_detail_fragment_share_icon); //Assign Share icon View
        mQuotePictureDownloadIcon = (Button) view.findViewById(R.id.quote_picture_detail_fragment_download_icon); //Assign Download icon View
        mQuotePictureImageView = (ImageView) view.findViewById(R.id.quote_picture_detail_fragment_quote_image_view); //Assign ImageView View


        mQuotePictureDrawable = new BitmapDrawable(getResources(), mQuotePictureBitmap); //Parse the Bitmap to a Drawable (so that it could be displayed in the ImageView)
        mQuotePictureImageView.setImageDrawable(mQuotePictureDrawable);


        mPhotoViewAttacher = new PhotoViewAttacher(mQuotePictureImageView); //Add zoom-effect to the ImageView
        mPhotoViewAttacher.update(); //Update the PhotoViewAttacher to make this change effective



        //==================== PRE-CONFIGURE FAVORITE ICON DRAWABLE ===================================================================================================
        mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off); //Pre-set the Favorite icon drawable to "Favorited OFF"

        //If the Quote is in the FavoriteQuotes SQLite database, then display the favorite icon to 'active'
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) != null) {
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_on);

        }
        if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null) {
            mQuotePictureFavoriteIcon.setButtonDrawable(R.drawable.ic_imageview_quote_picture_favorite_off);
        }



        //==================== SET LISTENERS FOR ICONS/BUTTONS ===================================================================================================

        //FAVORITE ICON
        mQuotePictureFavoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                //Change the Favorite icon's drawable based on the state of the CheckBox (i.e. checked vs unchecked)
                mQuotePictureFavoriteIcon.setButtonDrawable(isChecked ? R.drawable.ic_imageview_quote_picture_favorite_on: R.drawable.ic_imageview_quote_picture_favorite_off);

                //If the Favorite icon is CHECKED...
                if (isChecked == true){
                    //If the QuotePicture EXISTS in the Favorite QuotePictures SQLite Database
                    if (FavoriteQuotePicturesManager.get(getActivity()).getFavoriteQuotePicture(mQuotePictureID) == null){

                        //Save the QuotePicture as a Bitmap to the internal storage SO THAT it could be retrieved and displayed in the "Favorites" navigation Icon
                            //Argument 1 (Bitmap): Bitmap to be saved
                            //Argument 2 (String): The QuotePicture ID - to be used as the (unique) title of the file
                        String quotePictureBitmapFilePath = saveToInternalStorage(mQuotePictureBitmap, mQuotePictureID);
                        mQuotePicture.setQuotePictureBitmapFilePath(quotePictureBitmapFilePath); //Set the Bitmap File Path to the QuotePicture, so that it could later be retried in FavoriteQuotePicturesFragment

                        FavoriteQuotePicturesManager.get(getActivity()).addFavoriteQuotePicture(mQuotePicture); //Add the QuotePicture to the Favorite QuotePictures SQLite Database
                        FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture); //Update the database
                    }
                    else{
                        //Do nothing
                    }
                }

                //If the Favorite icon is UNCHECKED...
                if (isChecked == false){
                    FavoriteQuotePicturesManager.get(getActivity()).deleteFavoriteQuotePicture(mQuotePicture); //Remove the QuotePicture from the Favorite QuotePictures SQLite Database
                    FavoriteQuotePicturesManager.get(getActivity()).updateFavoriteQuotePicturesDatabase(mQuotePicture); //Update the database
                }
            }
        });



        //SHARE ICON
        mQuotePictureShareIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Uri quotePictureBitmapURI = saveBitmap(mQuotePictureBitmap); //Save Quote Picture Bitmap as cache data in the FileProvider

                shareQuotePictureBitmap(quotePictureBitmapURI); //Share the Quote Picture Bitmap
            }
        });



        //DOWNLOAD ICON
        mQuotePictureDownloadIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                //If permission for writing to storage has NOT been granted at runtime (by user). NOTE: This permission is required for saving the QuotePicture to storage
                if (hasWriteExternalStoragePermission() == false){
                    requestPermissions(STORAGE_PERMISSIONS, REQUEST_CODE_FOR_STORAGE_PERMISSIONS); //Request (user) for storage permissions - as they are 'dangerous' permissions (and therefore must be requested)
                }

                String fileName = "quote_picture"; //Filename for the file to be saved to the internal storage

                String savedImageUrl = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mQuotePictureBitmap, fileName, "Quote Picture"); //Insert image to Gallery. Also, return a URL String; if the image failed to be saved in Gallery, the URL would be null

                //If the image file FAILED to be saved in Gallery
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

        return view;
    }




    //Helper method - Check if STORAGE permissions have been granted at runtime (by user)
    private boolean hasWriteExternalStoragePermission(){

        //If permission is granted, result = PackageManager.PERMISSION_GRANTED (else, PackageManager.PERMISSION_DENIED).
        //NOTE: Permissions WRITE_EXTERNAL_STORAGE and READ_EXTERNAL_STORAGE are in the same PERMISSION GROUP, called STORAGE.
        //If one permission is a permission group is granted/denied access, the same applies to all other permissions in that group.
        // Other groups include: CALENDAR, CAMERA, CONTACTS, MICROPHONE, PHONE, SENSORS, SMS, STORAGE.
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (result == PackageManager.PERMISSION_GRANTED);
    }




    //Helper method - Save the QuotePicture Picture to the internal storage so that it could be accessed and displayed in FavoriteQuotePicturesFragment
    private String saveToInternalStorage(Bitmap quotePictureBitmap, String quotePictureID){

        ContextWrapper contextWrapper = new ContextWrapper(getContext()); //Create ContextWrapper

        //Path to: /data/data/QuoteSearch/app_data/imageDir
        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory,quotePictureID + ".jpg"); // Create imageDir

        Log.i(TAG, "FULL DIRECTORY FILE PATH: " + file); //Log to Logcat

        FileOutputStream fileOutputStream = null; //Declare FileOutputStream

        //Try risky task - compress(..) may throw Exception
        try {
            fileOutputStream = new FileOutputStream(file); //Create FileOutputStream from the File created
            quotePictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream); // Use the compress method on the BitMap object to write image to the OutputStream
        }
        catch (Exception exception) {
            exception.printStackTrace(); //Print stack-trace
        }
        finally{
            try{
                fileOutputStream.close(); //Close the FileOutputStream
            }
            catch (IOException ioException) {
                ioException.printStackTrace(); //Print the stack-trace
            }
        }

        return directory.getAbsolutePath(); //Return the filepath of the Bitmap saved to the internal storage
    }




    //Helper method - Save the QuotePicture Picture to the app's cache directory so that it could be shared to other apps (via an implicit intent)
    private Uri saveBitmap(Bitmap quotePictureBitmap) {

        File quotePictureBitmapSaveFolder = new File(getContext().getCacheDir(), "images"); //Create folder to save the Bitmap cache
        Uri savedBitmapURI = null; //Declare URI to the Bitmap

        //Try risky task -
        try {
            quotePictureBitmapSaveFolder.mkdirs();
            File file = new File(quotePictureBitmapSaveFolder, "shared_image.png");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            quotePictureBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            savedBitmapURI = FileProvider.getUriForFile(getContext(), "com.petertieu.android.quotesearch.fileprovider", file);

            Log.i(TAG, savedBitmapURI.toString());

        }
        catch (IOException IOException) {
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
        Log.i(TAG, "onStart() called"); //Log in Logcat
    }




    //Override onResume() fragment lifecycle callback method
    @Override
    public void onResume(){
        super.onResume();

        Log.i(TAG, "onResume() called"); //Log in Logcat
    }





    //Override onPause() fragment lifecycle callback method
    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause() called"); //Log in Logcat
    }




    //Override onStop() fragment lifecycle callback method
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop() called"); //Log in Logcat
    }




    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i(TAG, "onDestroyView() called"); //Log in Logcat
    }




    //Override onDestroy() fragment lifecycle callback method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy() called"); //Log in Logcat
    }




    //Override onDetach() fragment lifecycle callback method
    @Override
    public void onDetach(){
        super.onDetach();
        Log.i(TAG, "onDetach() called"); //Log in Logcat
    }

}
