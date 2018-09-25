package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database class #2:
//Create the Favorite Quotes SQLite database by overriding methods from the SQLiteHelper class
public class FavoriteQuotePicturesDatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "favorite_quote_pictures_database.db";

    public FavoriteQuotePicturesDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqliteDb){
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



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        //Do nothing
    }








}