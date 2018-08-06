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



    String mId;
    String mQuote;
    String mAuthor;
    String mCategory;
    List<String> mCategories;
    boolean mIsFavorite;

    //Only used for RandomQuotesFragment
    int mRandomQuotePosition;

    //Only used for SearchQuotesByKeywordFragment
    int mSearchQuotesByKeywordQuotePosition;


    public Quote(){
    }

    public Quote(String id){
        mId = id;
    }


    public String getQuote(){
        return mQuote;
    }

    public void setQuote(String quote){
        mQuote = quote;
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

    public void setFavorite(boolean isFavorite){
        mIsFavorite = isFavorite;
    }

    public boolean isFavorite(){
        return mIsFavorite;
    }


    public int getRandomQuotePosition() {
        return mRandomQuotePosition;
    }

    public void setRandomQuotePosition(int randomQuotePosition){
        mRandomQuotePosition = randomQuotePosition;
    }

    public int getSearchQuotesByKeywordQuotePosition(){
        return mSearchQuotesByKeywordQuotePosition;
    }

    public void setSearchQuotesByKeywordQuotePosition(int searchQuotesByKeywordQuotePosition){
        mSearchQuotesByKeywordQuotePosition = searchQuotesByKeywordQuotePosition;
    }
}
