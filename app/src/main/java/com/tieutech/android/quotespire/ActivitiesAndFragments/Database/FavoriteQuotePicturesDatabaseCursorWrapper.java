package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;

//Database class #3:
    //Wrapper class for the SQLiteDatabase cursor
public class FavoriteQuotePicturesDatabaseCursorWrapper extends CursorWrapper {

    //Constructor - called by FavoriteQuotesManager to obtain a CursorWrapper so as to point the cursor to the QuotePicture in the Favorite Quote Pictures database
    public FavoriteQuotePicturesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    //Called by FavoriteQuotesManager to obtain the QuotePicture that is pointed to by the CursorWrapper (FavoriteQuotePicturesDatabaseCursorWrapper), in the Favorite Quote Pictures database
    public QuotePicture getQuotePictureFromFavoriteQuotePicturesDatabase(){

        String id = getString(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID)); //Obtain the ID (in the ID column)
        int isFavorite = getInt(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.IS_FAVORITE)); //Obtain the favorite state (in the IS_FAVORITE column)
        String bitmapFilePath = getString(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.BITMAP_FILE_PATH)); //Obtain the file path of the Bitmap

        QuotePicture quotePicture = new QuotePicture(id); //Create QuotePicture object
        quotePicture.setFavorite(isFavorite != 0); //Set QuotePicture favorite state to associated member variable of QuotePicture
        quotePicture.setQuotePictureBitmapFilePath(bitmapFilePath); //Set the file path of the Bitmap to associated member viarble of QuotePicture

        return quotePicture; //Return the QuotePicture
    }

}

