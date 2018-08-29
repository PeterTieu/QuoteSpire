package com.petertieu.android.quotesearch.ActivitiesAndFragments.Models;

import android.graphics.Bitmap;

import java.io.Serializable;


//Model for Qutoe Pictures
//Appplicable to the following fragments:
    //1: RandomQuotePictureFragment
    //2: SearchQuotePictureByAuthorFragment
    //3: SearchQuotePictureByCategoryFragment

//NOTE: QuotePicture implements the Serializable interface SO THAT a QuotePicture object could be sent as an Extra in an Intent (i.e. when it is put as an extra in the QuotePictureDetailActivity's class' intent)
public class QuotePicture{


    String mId;
    boolean mIsFavorite;
    String mQuotePictureDownloadURI;
    int mRandomQuotePicturePosition; //Only used for RandomQuotePicturesFragment
    byte[] mQuotePictureBitmapByteArray;

    String mQuotePictureBitmapFilePath;



    public QuotePicture(){
    }

    public QuotePicture(String id){
        mId = id;
    }


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public String getQuotePictureDownloadURI() {
        return mQuotePictureDownloadURI;
    }

    public void setQuotePictureDownloadURI(String quotePictureDownloadURI) {
        mQuotePictureDownloadURI = quotePictureDownloadURI;
    }

    public int getRandomQuotePicturePosition() {
        return mRandomQuotePicturePosition;
    }

    public void setRandomQuotePicturePosition(int randomQuotePicturePosition) {
        mRandomQuotePicturePosition = randomQuotePicturePosition;
    }



    public byte[] getQuotePictureBitmapByteArray(){
        return mQuotePictureBitmapByteArray;
    }


    public void setQuotePictureBitmapByteArray(byte[] quotePictureBitmapByteArray){
        mQuotePictureBitmapByteArray = quotePictureBitmapByteArray;
    }




    public String getQuotePictureBitmapFilePath() {
        return mQuotePictureBitmapFilePath;
    }

    public void setQuotePictureBitmapFilePath(String quotePictureBitmapFilePath) {
        mQuotePictureBitmapFilePath = quotePictureBitmapFilePath;
    }


}
