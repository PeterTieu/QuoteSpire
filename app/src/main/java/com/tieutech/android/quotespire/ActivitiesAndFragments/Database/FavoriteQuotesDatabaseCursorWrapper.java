package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;


//Database class #3:
    //Wrapper class for the SQLiteDatabase cursor
public class FavoriteQuotesDatabaseCursorWrapper extends CursorWrapper{


    //Constructor - called by FavoriteQuotesManager to obtain a CursorWrapper so as to point the cursor to the Quote in the Favorite Quotes database
    public FavoriteQuotesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    //Called by FavoriteQuotesManager to obtain the Quote that is pointed to by the CursorWrapper (FavoriteQuotesDatabaseCursorWrapper), in the Favorite Quotes database
    public Quote getQuoteFromFavoriteQuotesDatabase(){
        String id = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID)); //Obtain the ID (in the ID column)
        String quoteString = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.QUOTE_STRING)); //Obtain the Quote String (n the QUOTE_STRING column)
        String author = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.AUTHOR)); //Obtain the Quote author (in the AUTHOR column)
        int isFavorite = getInt(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.IS_FAVORITE)); //Obtain the favorite state (in the IS_FAVORITE column)

        Quote quote = new Quote(id); //Create Quote object
        quote.setQuote(quoteString); //Set Quote String to associated member variable of Quote
        quote.setAuthor(author); //Set Quote author to associated member variable of Quote
        quote.setFavorite(isFavorite != 0); //Set Quote favorite state to associated member variable of Quote

        return quote;
    }

}
