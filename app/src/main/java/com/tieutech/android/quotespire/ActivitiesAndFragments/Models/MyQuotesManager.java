package com.tieutech.android.quotespire.ActivitiesAndFragments.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.MyQuotesDatabaseCursorWrapper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.MyQuotesDatabaseHelper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.MyQuotesDatabaseSchema;

import java.util.ArrayList;
import java.util.List;


//Database class #4:
    //Class for managing the My Quotes SQLiteDatabase
public class MyQuotesManager {

    //================= Declare INSTANCE VARIABLES ==============================================================

    private final String TAG = "MyQuotesManager"; //Tag for Logcat
    private static MyQuotesManager sMyQuotesManager; //DB Manager object. Singleton - only created once and lasts the lifetime of the app, unless memory is cleared
    private static SQLiteDatabase sSQLiteDatabase; //SQLiteDatabase object
    private Context mContext; //Context



    //================= Define METHODS ==============================================================

    //'Main Constructor' method - creates the MyQuotesManager singleton object if it doesn't exist. Otherwise, if the object exists, just returns it
    public static MyQuotesManager get(Context context){

        if (sMyQuotesManager == null){
            return new MyQuotesManager(context);
        }

        return sMyQuotesManager; //Return the singleton MyQuotesManager object IF it exists. NOTE: This object lasts the lifetime of the app, unless memory is cleared
    }




    //Contructor - helper method for 'Main Constructor' method
    private MyQuotesManager(Context context){


        try{
            mContext = context.getApplicationContext(); //Context that is tied to the LIFECYCLE of the ENTIRE application (instead of activity) for the purpose of retaining the SQLiteDatabase

            //Create/Retrieve the SINGLETON database (of type SQLiteDatabase)
            //getWritableDatable will:
            //IF: An SQLiteDatabase does NOT exist..
            //Call the overriden onCreate(SQLiteDatabase) from FavoriteQuotesDatabaseHelper to create the SQLiteDatabase
            //IF: An SQLiteDatabase EXISTS...
            //Check the version number of the database and call the overriden onUpgrade(..) from FavoriteQuotesDatabaseHelper to upgrade if necessary
            sSQLiteDatabase = new MyQuotesDatabaseHelper(mContext).getWritableDatabase();
        }


        catch (NullPointerException npe){
            Log.e(TAG, "Another fragment is loaded before QOD fragment completed AsyncTasks");
            Log.e(TAG, "NullPointException caught, since mContext refers to a null object");
            Log.e(TAG, "i.e. context.getApplicationContext() is null!");
        }

    }





    //===================== All the following methods are accessed like so: MyQuotesManager.get(context).*method* ================================
    //===================== Their purposes are to QUERY, WRITE and REMOVE (my) Quote(s) to/from the SQLiteDatabase database ===============================

    public Quote getMyQuote(String id){
        MyQuotesDatabaseCursorWrapper myQuotesDatabaseCursorWrapper = queryMyQuotes(MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ?", new String[]{id.toString()});

        try {
            if (myQuotesDatabaseCursorWrapper.getCount() == 0) {

                return null;
            }


            myQuotesDatabaseCursorWrapper.moveToFirst();


            return myQuotesDatabaseCursorWrapper.getQuoteFromMyQuotesDatabase();
        }

        finally {

            myQuotesDatabaseCursorWrapper.close();
        }
    }






    public List<Quote> getMyQuotes() {

        List<Quote> myQuotes = new ArrayList<>();

        MyQuotesDatabaseCursorWrapper myQuotesDatabaseCursorWrapper = queryMyQuotes(null, null);

        try {
            myQuotesDatabaseCursorWrapper.moveToFirst();

            while (!myQuotesDatabaseCursorWrapper.isAfterLast()) {
                myQuotes.add(myQuotesDatabaseCursorWrapper.getQuoteFromMyQuotesDatabase());
                myQuotesDatabaseCursorWrapper.moveToNext();
            }
        } finally {
            myQuotesDatabaseCursorWrapper.close();
        }

        return myQuotes;


    }





    public void addMyQuote(Quote myQuote){

        ContentValues contentValues = getContentValues(myQuote);

        sSQLiteDatabase.insert(MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME, null, contentValues);
    }





    public void deleteMyQuote(Quote myQuote){
        String myQuoteId = myQuote.getId();

        sSQLiteDatabase.delete(MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME,
                MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ? " ,
                new String[]{myQuoteId});
    }




    public void updateMyQuotesDatabase(Quote myQuote){
        String myQuotId = myQuote.getId();

        ContentValues contentValues = getContentValues(myQuote);

        sSQLiteDatabase.update(
                MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME,
                contentValues,
                MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ? ",
                new String[]{myQuotId}
        );
    }




    //============= Define HELPER METHODS ==============================================================================================

    private MyQuotesDatabaseCursorWrapper queryMyQuotes(String whereClause, String[] whereArgs){

        Cursor myQuotesDatatbaseCursor = sSQLiteDatabase.query(
                MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new MyQuotesDatabaseCursorWrapper(myQuotesDatatbaseCursor);
    }





    private static ContentValues getContentValues(Quote myQuote){

        ContentValues contentValues = new ContentValues();


        contentValues.put(MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID, myQuote.getId());
        contentValues.put(MyQuotesDatabaseSchema.MyQuotesTable.Columns.QUOTE_STRING, myQuote.getQuote());
        contentValues.put(MyQuotesDatabaseSchema.MyQuotesTable.Columns.AUTHOR, myQuote.getAuthor());
        contentValues.put(MyQuotesDatabaseSchema.MyQuotesTable.Columns.IS_FAVORITE, myQuote.isFavorite() ? 1:0);


        return contentValues;

    }

}
