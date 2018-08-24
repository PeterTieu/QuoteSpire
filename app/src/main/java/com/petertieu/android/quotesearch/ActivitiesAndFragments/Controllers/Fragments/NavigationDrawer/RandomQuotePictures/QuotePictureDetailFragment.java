package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.petertieu.android.quotesearch.R;

import java.util.Arrays;

import uk.co.senab.photoview.PhotoViewAttacher;

public class QuotePictureDetailFragment extends Fragment{

    private final static String TAG = "QPDFragment";

    private byte[] mQuotePictureByteArray;
    private Bitmap mQuotePictureBitmap;
    private Drawable mQuotePictureDrawable;

    private ImageView mQuotePictureImageView;



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

            //Get the String for the language chosen
            mQuotePictureByteArray = getArguments().getByteArray(QuotePictureDetailActivity.QUOTE_PICTURE_BYTE_ARRAY_KEY);


            mQuotePictureBitmap = BitmapFactory.decodeByteArray(mQuotePictureByteArray, 0, mQuotePictureByteArray.length);



        }


//        //Declare that this fragment participates in populating menus
//        setHasOptionsMenu(true);

        //Reset options menu
//        getActivity().invalidateOptionsMenu();

    }


    PhotoViewAttacher mPhotoViewAttacher;


    //Override the onCreateView(..) fragment lifecycle callback method
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);

        //Log lifecycle callback
        Log.i(TAG, "onCreateView(..) called");

        //Obtain View from the layout of the CategoryChooserFragment
        View view = layoutInflater.inflate(R.layout.fragment_quote_picture_detail, viewGroup, false);

        mQuotePictureImageView = (ImageView) view.findViewById(R.id.quote_picture_image_view);



        mQuotePictureDrawable = new BitmapDrawable(getResources(), mQuotePictureBitmap);
        mQuotePictureImageView.setImageDrawable(mQuotePictureDrawable);


        //Add zoom-effect to the ImageView
        mPhotoViewAttacher = new PhotoViewAttacher(mQuotePictureImageView);
        mPhotoViewAttacher.update();


        setHasOptionsMenu(true);

        getActivity().invalidateOptionsMenu();



        getActivity().setTitle("Quote Picture");


        return view;
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
