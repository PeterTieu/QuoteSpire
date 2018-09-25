package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyQuotesDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "my_quotes_database.db";

    public MyQuotesDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqliteDb){
        sqliteDb.execSQL(
                "create table " +
                        MyQuotesDatabaseSchema.MyQuotesTable.MY_QUOTES_TABLE_NAME +
                        "(" +
                        " _id integer primary key autoincrement, " +
                        MyQuotesDatabaseSchema.MyQuotesTable.Columns.ID + ", " +
                        MyQuotesDatabaseSchema.MyQuotesTable.Columns.QUOTE_STRING + ", " +
                        MyQuotesDatabaseSchema.MyQuotesTable.Columns.AUTHOR + ", " +
                        MyQuotesDatabaseSchema.MyQuotesTable.Columns.IS_FAVORITE +
                        ")"

        );
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        //Do nothing
    }
}
