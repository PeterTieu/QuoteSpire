package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database class #2:
    //Create the Favorite Quotes SQLite database by overriding methods from the SQLiteHelper class
public class FavoriteQuotesDatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1; //Version of the SQLiteDatabase
    private static final String DATABASE_NAME = "favorite_quotes_database.db"; //Name of the SQLiteDatabase

    //Constructor
    public FavoriteQuotesDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Override onCraate(..) callback method - create the SQLiteDatabase
    @Override
    public void onCreate(SQLiteDatabase sqliteDb){
        //Define SQLiteDatabase and its columns (which correspond to the member variables of the Quote model)
        sqliteDb.execSQL(
                "create table " +
                        FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.FAVORITE_QUOTES_TABLE_NAME +
                        "(" +
                        " _id integer primary key autoincrement, " +
                        FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.ID + ", " +
                        FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.QUOTE_STRING + ", " +
                        FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.AUTHOR + ", " +
                        FavoriteQuotesDatabaseSchema.FavoriteQuotesTable.Columns.IS_FAVORITE +
                        ")"
        );
    }


    //Define what happens when the SQLiteDatabase is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        //Do nothing
    }

}
