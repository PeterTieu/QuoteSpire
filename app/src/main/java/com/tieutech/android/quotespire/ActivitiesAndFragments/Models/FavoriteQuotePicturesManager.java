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

    //Obtain the Favorite Quote Picture via its ID - this method is called to check if a Quote Picture EXISTS in the Favorite Quote Pictures database
    public QuotePicture getFavoriteQuotePicture(String id){

        //OBTAIN the CursorWrapper (if the Favorite Quote Picture EXISTS in the database)
        FavoriteQuotePicturesDatabaseCursorWrapper favoriteQuotePicturesDatabaseCursorWrapper = queryFavoriteQuotePictures(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ?", new String[]{id.toString()});

        //Try risky task - favoriteQuotePicturesDatabaseCursorWrapper.getCount() could return RuntimeException IF the Favorite Quote Pictures database doesn't exist
        try {
            if (favoriteQuotePicturesDatabaseCursorWrapper.getCount() == 0) {
                return null;
            }

            //At this point, the Quote exists in the Favorite Quote Pictures database
            favoriteQuotePicturesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to the row containing the Quote Picture (since this row contains the matching ID)

            return favoriteQuotePicturesDatabaseCursorWrapper.getQuotePictureFromFavoriteQuotePicturesDatabase();  //Return the Quote Picture
        }

        finally {
            favoriteQuotePicturesDatabaseCursorWrapper.close(); //Close the Cursor
        }
    }




    //OBTAIN List of ALL the Quote objects in the Favorite Quotes database
    public List<QuotePicture> getFavoriteQuotePictures() {

        List<QuotePicture> favoriteQuotePictures = new ArrayList<>(); //Create List object

        FavoriteQuotePicturesDatabaseCursorWrapper favoriteQuotePicturesDatabaseCursorWrapper = queryFavoriteQuotePictures(null, null); //Obtain the CursorWrapper, which could to ANY column/row in the database!

        //Try risky task - favoriteQuotesDatabaseCursorWrapper.moveToFirst() could return RuntimeException IF the Favorite Quoes database doesn't exist
        try {
            favoriteQuotePicturesDatabaseCursorWrapper.moveToFirst(); //Point the CursorWrapper to FIRST ROW (whch contains the FIRST Quote in the database)

            //While the Cursor DOES NOT point to the position after the last row (IOW, while the cursor is still pointing to an EXISTING row)
            while (!favoriteQuotePicturesDatabaseCursorWrapper.isAfterLast()) {
                favoriteQuotePictures.add(favoriteQuotePicturesDatabaseCursorWrapper.getQuotePictureFromFavoriteQuotePicturesDatabase()); //Obtain the Quote pointed to by the Cursor, then add it to the List of Quotes
                favoriteQuotePicturesDatabaseCursorWrapper.moveToNext(); //Move the Cursor to the next row
            }
        }
        finally {
            favoriteQuotePicturesDatabaseCursorWrapper.close(); //Close the Cursor
        }

        return favoriteQuotePictures; //Return the List of Favorite Quotes
    }




    //ADD a Quote to the Favorite Quote Pictures database
    public void addFavoriteQuotePicture(QuotePicture favoriteQuotePicture){

        ContentValues contentValues = getContentValues(favoriteQuotePicture); //Create a ContentValues object, storing the Quote Picture in it
        sSQLiteDatabase.insert(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME, null, contentValues); //Insert the ContentValues object (containing the Quote) into the database
    }



    //DELETE a Quote Picture from the Favorite Quote Pictures database
    public void deleteFavoriteQuotePicture(QuotePicture favoriteQuotePicture){
        String favoriteQuotePictureId = favoriteQuotePicture.getId(); //Obtain the unique ID of the Quote

        //Delete the Quote Pictures from the database
        sSQLiteDatabase.delete(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME, //Database name
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ? " , //Column name (ID)
                new String[]{favoriteQuotePictureId}); //Row value (identified by the String value of the ID)
    }




    //UPDATE the Favorite Quote Pictures database - to accomodate the newly added Quote Picture
    public void updateFavoriteQuotePicturesDatabase(QuotePicture favoriteQuotePictures){

        String favoriteQuotePictureId = favoriteQuotePictures.getId(); //Obtain the unique ID from the Quote Picture
        ContentValues contentValues = getContentValues(favoriteQuotePictures); //Create a ContentValue, storing the Quote Picture in it

        //Update the Quote (stored in the ContentValues object) from the database
        sSQLiteDatabase.update(
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.FAVORITE_QUOTE_PICTURES_TABLE_NAME, //Database name
                contentValues, //ContentValuea object (containing the Quote Picture)
                FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID + " = ? ", //Column name (ID)
                new String[]{favoriteQuotePictureId} //Row value (identified by the String value of the ID)
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
