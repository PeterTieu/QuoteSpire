package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database class #2:
    //Create the Favorite Quote Pictures SQLite database by overriding methods from the SQLiteHelper class
public class FavoriteQuotePicturesDatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1; //Version of the SQLiteDatabase
    private static final String DATABASE_NAME = "favorite_quote_pictures_database.db"; //Name of the SQLiteDatabase

    //Constructor
    public FavoriteQuotePicturesDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Override onCraate(..) callback method - create the SQLiteDatabase
    @Override
    public void onCreate(SQLiteDatabase sqliteDb){
        //Define SQLiteDatabase and its columns (which correspond to the member variables of the Quote model)
        sqliteDb.execSQL(
                "create table " +
                        FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME +
                        "(" +
                        " _id integer primary key autoincrement, " +
                        FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + ", " +
                        FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.IS_FAVORITE + ", " +
                        FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.BITMAP_FILE_PATH +
                        ")"

        );
    }


    //Define what happens when the SQLiteDatabase is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        //Do nothing
    }

}