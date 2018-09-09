package com.petertieu.android.quotesearch.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;

public class MyQuotesDatabaseCursorWrapper extends CursorWrapper {


    public MyQuotesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    public Quote getQuoteFromMyQuotesDatabase(){
        String id = getString(getColumnIndex(MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID));
        String quoteString = getString(getColumnIndex(MyQuotesDatabaseSchema.MyQuotesTable.Columns.QUOTE_STRING));
        String author = getString(getColumnIndex(MyQuotesDatabaseSchema.MyQuotesTable.Columns.AUTHOR));
        int isFavorite = getInt(getColumnIndex(MyQuotesDatabaseSchema.MyQuotesTable.Columns.IS_FAVORITE));


        Quote quote = new Quote(id);
        quote.setQuote(quoteString);
        quote.setAuthor(author);
        quote.setFavorite(isFavorite != 0);


        return quote;

    }

}
