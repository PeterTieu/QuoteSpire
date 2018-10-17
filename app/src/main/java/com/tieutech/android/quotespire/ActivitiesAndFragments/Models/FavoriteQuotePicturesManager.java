package com.tieutech.android.quotespire.ActivitiesAndFragments.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseCursorWrapper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseHelper;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseSchema;

import java.util.ArrayList;
import java.util.List;


//Database class #4:
    //Class for managing the Favorite Quote Pictures SQLiteDatabase
public class FavoriteQuotePicturesManager {

    //================= Declare INSTANCE VARIABLES ==============================================================

    private final String TAG = "FQPicturesManager"; //Tag for Logcat
    private static FavoriteQuotePicturesManager sFavoriteQuotePicturesManager;
    private static SQLiteDatabase sSQLiteDatabase; //SQLiteDatabase object
    private Context mContext; //Context




    //================= Define METHODS ==============================================================

    //'Main Constructor' method - creates the FavoriteQuotePicturesManager singleton object if it doesn't exist. Otherwise, if the object exists, just returns it
    public static FavoriteQuotePicturesManager get(Context context){

        if (sFavoriteQuotePicturesManager == null){
            return new FavoriteQuotePicturesManager(context);
        }

        return sFavoriteQuotePicturesManager; //Return the singleton FavoriteQuotesManager object IF it exists. NOTE: This object lasts the lifetime of the app, unless memory is cleared
    }




    //Contructor - helper method for 'Main Constructor' method
    private FavoriteQuotePicturesManager(Context context){

        try{
            mContext = context.getApplicationContext(); //Context that is tied to the LIFECYCLE of the ENTIRE application (instead of activity) for the purpose of retaining the SQLiteDatabase
            sSQLiteDatabase = new FavoriteQuotePicturesDatabaseHelper(mContext).getWritableDatabase();
        }


        //Create/Retrieve the SINGLETON database (of type SQLiteDatabase)
            //getWritableDatable will:
                //IF: An SQLiteDatabase does NOT exist..
                    //Call the overriden onCreate(SQLiteDatabase) from FavoriteQuotesDatabaseHelper to create the SQLiteDatabase
                //IF: An SQLiteDatabase EXISTS...
                    //Check the version number of the database and call the overriden onUpgrade(..) from FavoriteQuotesDatabaseHelper to upgrade if necessary
        catch (NullPointerException npe){
            Log.e(TAG, "Another fragment is loaded before QOD fragment completed AsyncTasks");
            Log.e(TAG, "NullPointException caught, since mContext refers to a null object");
            Log.e(TAG, "i.e. context.getApplicationContext() is null!");
        }

    }




    //===================== All the following methods are accessed like so: FavoriteQuotePicturesManager.get(context).*method* ================================
    //===================== Their purposes are to QUERY, WRITE and REMOVE (favorite) QuotePicture(s) to/from the SQLiteDatabase database ===============================

    public QuotePicture getFavoriteQuotePicture(String id){
        FavoriteQuotePicturesDatabaseCursorWrapper favoriteQuotePicturesDatabaseCursorWrapper = queryFavoriteQuotePictures(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ?", new String[]{id.toString()});

        try {
            if (favoriteQuotePicturesDatabaseCursorWrapper.getCount() == 0) {

                return null;
            }


            favoriteQuotePicturesDatabaseCursorWrapper.moveToFirst();



            return favoriteQuotePicturesDatabaseCursorWrapper.getQuotePictureFromFavoriteQuotePicturesDatabase();
        }

        finally {

            favoriteQuotePicturesDatabaseCursorWrapper.close();
        }
    }






    public List<QuotePicture> getFavoriteQuotePictures() {

        List<QuotePicture> favoriteQuotePictures = new ArrayList<>();

        FavoriteQuotePicturesDatabaseCursorWrapper favoriteQuotePicturesDatabaseCursorWrapper = queryFavoriteQuotePictures(null, null);

        try {
            favoriteQuotePicturesDatabaseCursorWrapper.moveToFirst();

            while (!favoriteQuotePicturesDatabaseCursorWrapper.isAfterLast()) {
                favoriteQuotePictures.add(favoriteQuotePicturesDatabaseCursorWrapper.getQuotePictureFromFavoriteQuotePicturesDatabase());
                favoriteQuotePicturesDatabaseCursorWrapper.moveToNext();
            }

//            favoriteQuotePicturesDatabaseCursorWrapper.close();

        } finally {
            favoriteQuotePicturesDatabaseCursorWrapper.close();
        }

        return favoriteQuotePictures;


    }





    public void addFavoriteQuotePicture(QuotePicture favoriteQuotePicture){

        ContentValues contentValues = getContentValues(favoriteQuotePicture);

        sSQLiteDatabase.insert(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME, null, contentValues);
    }





    public void deleteFavoriteQuotePicture(QuotePicture favoriteQuotePicture){
        String favoriteQuotePictureId = favoriteQuotePicture.getId();

        sSQLiteDatabase.delete(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME,
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ? " ,
                new String[]{favoriteQuotePictureId});
    }




    public void updateFavoriteQuotePicturesDatabase(QuotePicture favoriteQuotePictures){
        String favoriteQuotePictureId = favoriteQuotePictures.getId();

        ContentValues contentValues = getContentValues(favoriteQuotePictures);

        sSQLiteDatabase.update(
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME,
                contentValues,
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ? ",
                new String[]{favoriteQuotePictureId}
        );
    }




    //============= Define HELPER METHODS ==============================================================================================

    private FavoriteQuotePicturesDatabaseCursorWrapper queryFavoriteQuotePictures(String whereClause, String[] whereArgs){

        Cursor favoriteQuotePicturesDatatbaseCursor = sSQLiteDatabase.query(
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new FavoriteQuotePicturesDatabaseCursorWrapper(favoriteQuotePicturesDatatbaseCursor);
    }





    private static ContentValues getContentValues(QuotePicture favoriteQuotePicture){

        ContentValues contentValues = new ContentValues();


        contentValues.put(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID, favoriteQuotePicture.getId());
        contentValues.put(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.IS_FAVORITE, favoriteQuotePicture.isFavorite() ? 1:0);
        contentValues.put(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.BITMAP_FILE_PATH, favoriteQuotePicture.getQuotePictureBitmapFilePath());


        return contentValues;

    }











}
