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
            //Call the overriden onCreate(SQLiteDatabase) from MyQuotesDatabaseHelper to create the SQLiteDatabase
            //IF: An SQLiteDatabase EXISTS...
            //Check the version number of the database and call the overriden onUpgrade(..) from MyQuotesDatabaseHelper to upgrade if necessary
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

    //Obtain the My Quote via its ID - this method is called to check if a Quote EXISTS in the My Quotes database
    public Quote getMyQuote(String id){

        //OBTAIN the CursorWrapper (if the My Quote EXIST in the database)
        MyQuotesDatabaseCursorWrapper myQuotesDatabaseCursorWrapper = queryMyQuotes(MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ?", new String[]{id.toString()});

        //Try risky task - myQuotesDatabaseCursorWrapper.getCount() could return RuntimeException IF the My Quotes database doesn't exist
        try {
            if (myQuotesDatabaseCursorWrapper.getCount() == 0) {
                return null;
            }

            //At this point, the Quote exists in the My Quotes database
            myQuotesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to the row containing the Quote (since this row contains the matching ID)

            return myQuotesDatabaseCursorWrapper.getQuoteFromMyQuotesDatabase(); //Return the Quote
        }

        finally {
            myQuotesDatabaseCursorWrapper.close(); //Close the Cursor
        }
    }




    //OBTAIN List of ALL the Quote objects in the My Quotes database
    public List<Quote> getMyQuotes() {

        List<Quote> myQuotes = new ArrayList<>(); //Create List object

        MyQuotesDatabaseCursorWrapper myQuotesDatabaseCursorWrapper = queryMyQuotes(null, null); //Obtain the CursorWrapper, which could to ANY column/row in the database!

        //Try risky task - MyQuotesDatabaseCursorWrapper.moveToFirst() could return RuntimeException IF the My Quoes database doesn't exist
        try {
            myQuotesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to FIRST ROW (whch contains the FIRST Quote in the database)

            //While the Cursor DOES NOT point to the position after the last row (IOW, while the cursor is still pointing to an EXISTING row)
            while (!myQuotesDatabaseCursorWrapper.isAfterLast()) {
                myQuotes.add(myQuotesDatabaseCursorWrapper.getQuoteFromMyQuotesDatabase()); //Obtain the Quote pointed to by the Cursor, then add it to the List of Quotes
                myQuotesDatabaseCursorWrapper.moveToNext(); //Move the Cursor to the next row
            }
        }
        finally {
            myQuotesDatabaseCursorWrapper.close(); //Close the Cursor
        }

        return myQuotes; //Return the List of My Quotes
    }




    //ADD a Quote to the My Quotes database
    public void addMyQuote(Quote myQuote){

        ContentValues contentValues = getContentValues(myQuote); //Create a ContentValues object, storing the Quote in it
        sSQLiteDatabase.insert(MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME, null, contentValues); //Insert the ContentValues object (containing the Quote) into the database
    }




    //DELETE a Quote from the My Quotes database
    public void deleteMyQuote(Quote myQuote){
        String myQuoteId = myQuote.getId(); //Obtain the unique ID of the Quote

        //Delete the Quote from the database
        sSQLiteDatabase.delete(MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME, //Database name
                MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ? " , //Column name (ID)
                new String[]{myQuoteId}); //Row value (identified by the String value of the ID)
    }




    //UPDATE the My Quotes database - to accomodate the newly added Quote
    public void updateMyQuotesDatabase(Quote myQuote){

        String myQuotId = myQuote.getId(); //Obtain the unique ID from the Quote
        ContentValues contentValues = getContentValues(myQuote); //Create a ContentValue, storing the Quote in it

        //Update the Quote (stored in the ContentValues object) from the database
        sSQLiteDatabase.update(
                MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME, //Database name
                contentValues, //ContentValuea object (containing the Quote)
                MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + " = ? ", //Column name (ID)
                new String[]{myQuotId} //Row value (identified by the String value of the ID)
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
