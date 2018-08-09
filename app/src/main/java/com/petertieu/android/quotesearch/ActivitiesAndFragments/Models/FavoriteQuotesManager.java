package com.petertieu.android.quotesearch.ActivitiesAndFragments.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseCursorWrapper;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseHelper;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesManager {

    private final String TAG = "FavoriteQuotesManager";

    private static FavoriteQuotesManager sFavoriteQuotesManager;

    private static SQLiteDatabase sSQLiteDatabase;

    private Context mContext;





    public static FavoriteQuotesManager get(Context context){

        if (sFavoriteQuotesManager == null){
            return new FavoriteQuotesManager(context);
        }

        return sFavoriteQuotesManager;
    }




    private FavoriteQuotesManager(Context context){

        try{

//            if (context.getApplicationContext() != null){
//                Log.i(TAG, "context.getApplicationContext() exists");
//            }
//            else{
//                Log.i(TAG, "context.getApplicationContext() does NOT exist");
//            }

            mContext = context.getApplicationContext();

            sSQLiteDatabase = new FavoriteQuotesDatabaseHelper(mContext).getWritableDatabase();
        }


        catch (NullPointerException npe){
            Log.e(TAG, "Another fragment is loaded before QOD fragment completed AsyncTasks");
            Log.e(TAG, "NullPointException caught, since mContext refers to a null object");
            Log.e(TAG, "i.e. context.getApplicationContext() is null!");
        }

    }





    //===================== All the following methods are accessed like so: FavoriteQuotesManager.get(context).*method* ================================
    //===================== Their purposes are to QUERY, WRITE and REMOVE (favorite) Quote(s) to/from the SQLiteDatabase database ===============================

    public Quote getFavoriteQuote(String id){
        FavoriteQuotesDatabaseCursorWrapper favoriteQuotesDatabaseCursorWrapper = queryFavoriteQuotes(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ?", new String[]{id.toString()});

        try {
            if (favoriteQuotesDatabaseCursorWrapper.getCount() == 0) {

                return null;
            }


            favoriteQuotesDatabaseCursorWrapper.moveToFirst();


            return favoriteQuotesDatabaseCursorWrapper.getQuoteFromFavoriteQuotesDatabase();
        }

        finally {

            favoriteQuotesDatabaseCursorWrapper.close();
        }
    }






    public List<Quote> getFavoriteQuotes() {

        List<Quote> favoriteQuotes = new ArrayList<>();

        FavoriteQuotesDatabaseCursorWrapper favoriteQuotesDatabaseCursorWrapper = queryFavoriteQuotes(null, null);

        try {
            favoriteQuotesDatabaseCursorWrapper.moveToFirst();

            while (!favoriteQuotesDatabaseCursorWrapper.isAfterLast()) {
                favoriteQuotes.add(favoriteQuotesDatabaseCursorWrapper.getQuoteFromFavoriteQuotesDatabase());
                favoriteQuotesDatabaseCursorWrapper.moveToNext();
            }
        } finally {
            favoriteQuotesDatabaseCursorWrapper.close();
        }

        return favoriteQuotes;


    }





    public void addFavoriteQuote(Quote favoriteQuote){

        ContentValues contentValues = getContentValues(favoriteQuote);

        sSQLiteDatabase.insert(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME, null, contentValues);
    }





    public void deleteFavoriteQuote(Quote favoriteQuote){
        String favoriteQuoteId = favoriteQuote.getId();

        sSQLiteDatabase.delete(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME,
                                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ? " ,
                                new String[]{favoriteQuoteId});
    }




    public void updateFavoriteQuotesDatabase(Quote favoriteQuote){
        String favoriteQuotId = favoriteQuote.getId();

        ContentValues contentValues = getContentValues(favoriteQuote);

        sSQLiteDatabase.update(
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME,
                contentValues,
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ? ",
                new String[]{favoriteQuotId}
        );
    }




    //============= Define HELPER METHODS ==============================================================================================

    private FavoriteQuotesDatabaseCursorWrapper queryFavoriteQuotes(String whereClause, String[] whereArgs){

        Cursor favoriteQuotesDatatbaseCursor = sSQLiteDatabase.query(
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new FavoriteQuotesDatabaseCursorWrapper(favoriteQuotesDatatbaseCursor);
    }





    private static ContentValues getContentValues(Quote favoriteQuote){

        ContentValues contentValues = new ContentValues();


        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID, favoriteQuote.getId());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.QUOTE_STRING, favoriteQuote.getQuote());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.AUTHOR, favoriteQuote.getAuthor());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.IS_FAVORITE, favoriteQuote.isFavorite() ? 1:0);


        return contentValues;

    }




}
