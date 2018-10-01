package com.tieutech.android.quotespire.ActivitiesAndFragments.Models;

import android.graphics.Bitmap;

import java.util.List;


//MODEL for Quotes - Blueprint of all Quotes
    //Appplicable to the following fragments:
        //1: RandomQuotesFragment
        //2: SearchQuotesByKeywordFragment
        //3: SearchQuotesByCategoryFragment
        //4: SearchQuotesByAuthorFragment
        //5: SearchQuotesByAdvancedFragment
        //6: FavoriteQuotesFragment
public class Quote {

    //================= Declare INSTANCE VARIABLES ==============================================================

    //CORE instance variables
    String mId;                 //Unique identifier
    String mQuote;              //Quote Text
    String mAuthor;             //Author
    String mCategory;           //Category
    List<String> mCategories;   //List of Categories
    boolean mIsFavorite;        //Favorited or not


    //Conditional instance variables
    int mRandomQuotePosition;                   //Only used for RandomQuotesFragment
    int mSearchQuotesByKeywordQuotePosition;    //Only used for SearchQuotesByKeywordFragment
    int mSearchQuotesByCategoryQuotePosition;   //Only used for SearchQuotesByCategoryFragment
    int mSearchQuotesByAuthorQuotePosition;     //Only used for SearchQuotesByAuthorFragment
    int mSearchQuotesByAdvancedQuotePosition;   //Only used for SearchQuotesByAdvancedFragment


    //================= Define METHODS ==========================================================================

    //Constructor #1
    public Quote(){
    }

    //Constructor #2
    public Quote(String id){
        mId = id;
    }

    //Getter for mQuote
    public String getQuote(){
        return mQuote;
    }

    //-------------- Getters and Setters ----------------------------------------

    //mQuote
    public void setQuote(String quote){
        mQuote = quote;
    }


    //mCatetgory
    public String getCategory(){
        return mCategory;
    }
    public void setCategory(String category){
        mCategory = category;
    }


    //mCategories
    public List<String> getCategories(){
        return mCategories;
    }
    public void setCategories(List<String> categories){
        mCategories = categories;
    }


    //mAuthor
    public String getAuthor(){
        return mAuthor;
    }
    public void setAuthor(String author){
        mAuthor = author;
    }


    //mId
    public String getId(){
        return mId;
    }
    public void setId(String id){
        mId = id;
    }


    //mFavorite
    public void setFavorite(boolean isFavorite){
        mIsFavorite = isFavorite;
    }
    public boolean isFavorite(){
        return mIsFavorite;
    }


    //mRandomQuotePosition
    public int getRandomQuotePosition() {
        return mRandomQuotePosition;
    }
    public void setRandomQuotePosition(int randomQuotePosition){
        mRandomQuotePosition = randomQuotePosition;
    }


    //mSearchQuotesByKeywordQuotePosition
    public int getSearchQuotesByKeywordQuotePosition(){
        return mSearchQuotesByKeywordQuotePosition;
    }
    public void setSearchQuotesByKeywordQuotePosition(int searchQuotesByKeywordQuotePosition){
        mSearchQuotesByKeywordQuotePosition = searchQuotesByKeywordQuotePosition;
    }


    //mSearchQuotesByCategoryQuotePosition
    public int getSearchQuotesByCategoryQuotePosition(){
        return mSearchQuotesByCategoryQuotePosition;
    }
    public void setSearchQuotesByCategoryQuotePosition(int searchQuotesByCategoryQuotePosition){
        mSearchQuotesByCategoryQuotePosition = searchQuotesByCategoryQuotePosition;
    }


    //mSearchQuotesByAuthorQuotePosition
    public int getSearchQuotesByAuthorQuotePosition(){
        return mSearchQuotesByAuthorQuotePosition;
    }
    public void setSearchQuotesByAuthorQuotePosition(int searchQuotesByAuthorQuotePosition){
        mSearchQuotesByAuthorQuotePosition = searchQuotesByAuthorQuotePosition;
    }


    //mSearchQuotesByAdvancedQuotePosition
    public int getSearchQuotesByAdvancedQuotePosition(){
        return mSearchQuotesByAdvancedQuotePosition;
    }
    public void setSearchQuotesByAdvancedQuotePosition(int searchQuotesByAdvancedQuotePosition){
        mSearchQuotesByAdvancedQuotePosition = searchQuotesByAdvancedQuotePosition;
    }

}