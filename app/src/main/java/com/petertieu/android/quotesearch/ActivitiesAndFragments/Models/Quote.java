package com.petertieu.android.quotesearch.ActivitiesAndFragments.Models;

import java.util.List;

public class Quote {
    //States (i.e. instance variables):
        //isFavorite (boolean)
        //Quote URL (String)
        //Picture URL - if exists (String)
        //QuoteText (String)
        //Category (String)
        //Author (String)


    String mQuote;
    int mLenght;
    String mCategory;
    List<String> mCategories;
    String mAuthor;
    String mId;


    public String getQuote(){
        return mQuote;
    }

    public void setQuote(String quote){
        mQuote = quote;
    }

    public int getLength(){
        return mLenght;
    }

    public String getCategory(){
        return mCategory;
    }

    public void setCategory(String category){
        mCategory = category;
    }


    public List<String> getCategories(){
        return mCategories;
    }

    public void setCategories(List<String> categories){
        mCategories = categories;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public void setAuthor(String author){
        mAuthor = author;
    }

    public String getId(){
        return mId;
    }

    public void setId(String id){
        mId = id;
    }

}
