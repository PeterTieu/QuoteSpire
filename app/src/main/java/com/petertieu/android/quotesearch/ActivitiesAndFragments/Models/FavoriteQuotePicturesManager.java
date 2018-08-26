package com.petertieu.android.quotesearch.ActivitiesAndFragments.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseCursorWrapper;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseHelper;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Database.FavoriteQuotePicturesDatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotePicturesManager {

    private final String TAG = "FQPicturesManager";

    private static FavoriteQuotePicturesManager sFavoriteQuotePicturesManager;

    private static SQLiteDatabase sSQLiteDatabase;

    private Context mContext;



    public static FavoriteQuotePicturesManager get(Context context){

        if (sFavoriteQuotePicturesManager == null){
            return new FavoriteQuotePicturesManager(context);
        }

        return sFavoriteQuotePicturesManager;
    }






    private FavoriteQuotePicturesManager(Context context){

        try{

//            if (context.getApplicationContext() != null){
//                Log.i(TAG, "context.getApplicationContext() exists");
//            }
//            else{
//                Log.i(TAG, "context.getApplicationContext() does NOT exist");
//            }

            mContext = context.getApplicationContext();

            sSQLiteDatabase = new FavoriteQuotePicturesDatabaseHelper(mContext).getWritableDatabase();
        }


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
