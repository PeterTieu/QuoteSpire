package com.tieutech.android.quotespire.ActivitiesAndFragments.Models;


//MODEL for Qutoe Pictures
//Appplicable to the following fragments:
    //1: RandomQuotePictureFragment
    //2: SearchQuotePictureByAuthorFragment
    //3: SearchQuotePictureByCategoryFragment
    //4: FavoriteQuotePicturesFragment
public class QuotePicture{

    //================= Declare INSTANCE VARIABLES ==============================================================

    //CORE instance variables
    String mId;                             //Unique identifier
    boolean mIsFavorite;                    //Favorited or not
    String mQuotePictureDownloadURI;        //URL to download a Quote Picture (obtained via networking)
    byte[] mQuotePictureBitmapByteArray;    //
    String mQuotePictureBitmapFilePath;     //

    //Conditional instance variables
    int mRandomQuotePicturePosition;    //Only used for RandomQuotePicturesFragment
    int mCategoryQuotePicturePosition;  //Only used for SearchQPByCategoryFragment
    int mAuthorQuotePicturePosition;    //Only used for SearchQPByAuthorFragment



    //================= Define METHODS ==========================================================================

    //Constructor #1
    public QuotePicture(){
    }


    //Constructor #2
    public QuotePicture(String id){
        mId = id;
    }


    //-------------- Getters and Setters ----------------------------------------

    //mId
    public String getId() {
        return mId;
    }
    public void setId(String id) {
        mId = id;
    }


    //mIsFavorite
    public boolean isFavorite() {
        return mIsFavorite;
    }
    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }


    //mQuotePictureDownloadURI
    public String getQuotePictureDownloadURI() {
        return mQuotePictureDownloadURI;
    }
    public void setQuotePictureDownloadURI(String quotePictureDownloadURI) {
        mQuotePictureDownloadURI = quotePictureDownloadURI;
    }


    //mRandomQuotePicturePosition
    public int getRandomQuotePicturePosition() {
        return mRandomQuotePicturePosition;
    }
    public void setRandomQuotePicturePosition(int randomQuotePicturePosition) {
        mRandomQuotePicturePosition = randomQuotePicturePosition;
    }


    //mCategoryQuotePicturePosition
    public int getCategoryQuotePicturePosition(){
        return mCategoryQuotePicturePosition;
    }
    public void setCategoryQuotePicturePosition(int categoryQuotePiturePosition){
        mCategoryQuotePicturePosition = categoryQuotePiturePosition;
    }


    //mAuthorQuotePicturePosition
    public int getAuthorQuotePicturePosition(){
        return mAuthorQuotePicturePosition;
    }
    public void setAuthorQuotePicturePosition(int authorQuotePiturePosition){
        mAuthorQuotePicturePosition = authorQuotePiturePosition;
    }


    //mQuotePictureBitmapByteArray
    public byte[] getQuotePictureBitmapByteArray(){
        return mQuotePictureBitmapByteArray;
    }
    public void setQuotePictureBitmapByteArray(byte[] quotePictureBitmapByteArray){
        mQuotePictureBitmapByteArray = quotePictureBitmapByteArray;
    }


    //mQuotePictureBitmapFilePath
    public String getQuotePictureBitmapFilePath() {
        return mQuotePictureBitmapFilePath;
    }
    public void setQuotePictureBitmapFilePath(String quotePictureBitmapFilePath) {
        mQuotePictureBitmapFilePath = quotePictureBitmapFilePath;
    }

}
