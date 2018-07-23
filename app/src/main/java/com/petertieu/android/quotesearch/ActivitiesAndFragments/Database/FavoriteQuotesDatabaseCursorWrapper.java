package com.petertieu.android.quotesearch.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;

import java.util.List;

public class FavoriteQuotesDatabaseCursorWrapper extends CursorWrapper{

    public FavoriteQuotesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    public Quote getQuoteFromFavoriteQuotesDatabase(){
        String id = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID));
        String quoteString = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.QUOTE_STRING));
        String author = getString(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.AUTHOR));
        int isFavorite = getInt(getColumnIndex(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.IS_FAVORITE));


        Quote quote = new Quote(id);
        quote.setQuote(quoteString);
        quote.setAuthor(author);
        quote.setFavorite(isFavorite != 0);


        return quote;

    }

}
