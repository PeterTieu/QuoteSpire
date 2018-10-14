package com.tieutech.android.quotespire.ActivitiesAndFragments.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseCursorWrapper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseHelper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotesDatabaseSchema;

import java.util.ArrayList;
import java.util.List;


//Database class #4:
    //Class for managing the Favorite Quotes SQLiteDatabase
public class FavoriteQuotesManager {

    //================= Declare INSTANCE VARIABLES ==============================================================

    private final String TAG = "FavoriteQuotesManager"; //Tag for Logcat
    private static FavoriteQuotesManager sFavoriteQuotesManager; //DB Manager object. Singleton - only created once and lasts the lifetime of the app, unless memory is cleared
    private static SQLiteDatabase sSQLiteDatabase; //SQLiteDatabase object
    private Context mContext; //Context




    //================= Define METHODS ==============================================================

    //'Main Constructor' method - creates the FavoriteQuotesManager singleton object if it doesn't exist. Otherwise, if the object exists, just returns it
    public static FavoriteQuotesManager get(Context context){

        if (sFavoriteQuotesManager == null){
            return new FavoriteQuotesManager(context);
        }

        return sFavoriteQuotesManager; //Return the singleton FavoriteQuotesManager object IF it exists. NOTE: This object lasts the lifetime of the app, unless memory is cleared
    }




    //Contructor - helper method for 'Main Constructor' method
    private FavoriteQuotesManager(Context context){

        try{
            mContext = context.getApplicationContext(); //Context that is tied to the LIFECYCLE of the ENTIRE application (instead of activity) for the purpose of retaining the SQLiteDatabase

            //Create/Retrieve the SINGLETON database (of type SQLiteDatabase)
                //getWritableDatable will:
                    //IF: An SQLiteDatabase does NOT exist..
                        //Call the overriden onCreate(SQLiteDatabase) from FavoriteQuotesDatabaseHelper to create the SQLiteDatabase
                    //IF: An SQLiteDatabase EXISTS...
                        //Check the version number of the database and call the overriden onUpgrade(..) from FavoriteQuotesDatabaseHelper to upgrade if necessary
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

    //Obtain the Favorite Quote via its ID - this method is called to check if a Quote EXISTS in the Favorite Quotes database
    public Quote getFavoriteQuote(String id){

        //OBTAIN the CursorWrapper (if the Favorite Quote EXIST in the database)
        FavoriteQuotesDatabaseCursorWrapper favoriteQuotesDatabaseCursorWrapper = queryFavoriteQuotes(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ?", new String[]{id.toString()});

        //Try risky task - favoriteQuotesDatabaseCursorWrapper.getCount() could return RuntimeException IF the Favorite Quoes database doesn't exist
        try {
            if (favoriteQuotesDatabaseCursorWrapper.getCount() == 0) {
                return null;
            }

            //At this point, the Quote exists in the Favorite Quotes database
            favoriteQuotesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to the row containing the Quote (since this row contains the matching ID)

            return favoriteQuotesDatabaseCursorWrapper.getQuoteFromFavoriteQuotesDatabase(); //Return the Quote
        }

        finally {
            favoriteQuotesDatabaseCursorWrapper.close(); //Close the Cursor
        }
    }




    //OBTAIN List of ALL the Quote objects in the Favorite Quotes database
    public List<Quote> getFavoriteQuotes() {

        List<Quote> favoriteQuotes = new ArrayList<>(); //Create List object

        FavoriteQuotesDatabaseCursorWrapper favoriteQuotesDatabaseCursorWrapper = queryFavoriteQuotes(null, null); //Obtain the CursorWrapper, which could to ANY column/row in the database!

        //Try risky task - favoriteQuotesDatabaseCursorWrapper.moveToFirst() could return RuntimeException IF the Favorite Quoes database doesn't exist
        try {
            favoriteQuotesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to FIRST ROW (whch contains the FIRST Quote in the database)

            //While the Cursor DOES NOT point to the position after the last row (IOW, while the cursor is still pointing to an EXISTING row)
            while (!favoriteQuotesDatabaseCursorWrapper.isAfterLast()) {
                favoriteQuotes.add(favoriteQuotesDatabaseCursorWrapper.getQuoteFromFavoriteQuotesDatabase()); //Obtain the Quote pointed to by the Cursor, then add it to the List of Quotes
                favoriteQuotesDatabaseCursorWrapper.moveToNext(); //Move the Cursor to the next row
            }
        }
        finally {
            favoriteQuotesDatabaseCursorWrapper.close(); //Close the Cursor
        }

        return favoriteQuotes; //Return the List of Favorite Quotes
    }




    //ADD a Quote to the Favorite Quotes database
    public void addFavoriteQuote(Quote favoriteQuote){
        ContentValues contentValues = getContentValues(favoriteQuote); //Create a ContentValues object, storing the Quote in it
        sSQLiteDatabase.insert(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME, null, contentValues); //Insert the ContentValues object (containing the Quote) into the database
    }




    //DELETE a Quote from the Favorite Quotes database
    public void deleteFavoriteQuote(Quote favoriteQuote){
        String favoriteQuoteId = favoriteQuote.getId(); //Obtain the unique ID of the Quote

        //Delete the Quote from the database
        sSQLiteDatabase.delete(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME, //Database name
                                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ? " , //Column name (ID)
                                new String[]{favoriteQuoteId}); //Row value (identified by the String value of the ID)
    }




    //UPDATE the Favorite Quotes database - to accomodate the newly added Quote
    public void updateFavoriteQuotesDatabase(Quote favoriteQuote){
        String favoriteQuotId = favoriteQuote.getId(); //Obtain the unique ID from the Quote
        ContentValues contentValues = getContentValues(favoriteQuote); //Create a ContentValue, storing the Quote in it

        //Update the Quote (stored in the ContentValues object) from the database
        sSQLiteDatabase.update(
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME, //Database name
                contentValues, //ContentValuea object (containing the Quote)
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + " = ? ", //Column name (ID)
                new String[]{favoriteQuotId} //Row value (identified by the String value of the ID)
        );
    }




    //============= Define HELPER METHODS ==============================================================================================

    //Helper method - OBTAIN the CursorWrapper (if the Favorite Quote EXIST in the database), and point it to the column (whereClause) and row (whereArgs)
    private FavoriteQuotesDatabaseCursorWrapper queryFavoriteQuotes(String whereClause, String[] whereArgs){

        //Obtain the Cursor
        Cursor favoriteQuotesDatatbaseCursor = sSQLiteDatabase.query(
                FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME, //Database name
                null, //Which columns to return (String[])
                whereClause, //Column (String)
                whereArgs, //Row (String[])
                null, //How to group the rows (String). null means rows are not grouped
                null, //Which rows to group (String). null means all row groups are included
                null //How to order rows (String). null means use default order (i.e. unsorted)
        );

        return new FavoriteQuotesDatabaseCursorWrapper(favoriteQuotesDatatbaseCursor); //Return the CursorWrapper (Cursor)
    }




    //Helper method - Create a ContentValues object and store a Quote in it.
    //NOTE: A ContentValues object is necessary for the SQLiteDatabase to access
    private static ContentValues getContentValues(Quote favoriteQuote){

        ContentValues contentValues = new ContentValues(); //Create the ContentValues object

        //Add member variables of the Quote to the ContentValues object (using key-value pairs)
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID, favoriteQuote.getId());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.QUOTE_STRING, favoriteQuote.getQuote());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.AUTHOR, favoriteQuote.getAuthor());
        contentValues.put(FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.IS_FAVORITE, favoriteQuote.isFavorite() ? 1:0);

        return contentValues; //Retrun the ContentValues object
    }

}
