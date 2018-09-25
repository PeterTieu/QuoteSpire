package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;

public class FavoriteQuotePicturesDatabaseCursorWrapper extends CursorWrapper {

    public FavoriteQuotePicturesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    public QuotePicture getQuotePictureFromFavoriteQuotePicturesDatabase(){
        String id = getString(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID));
        int isFavorite = getInt(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.IS_FAVORITE));
        String bitmapFilePath = getString(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.BITMAP_FILE_PATH));


        QuotePicture quotePicture = new QuotePicture(id);
        quotePicture.setFavorite(isFavorite != 0);
        quotePicture.setQuotePictureBitmapFilePath(bitmapFilePath);


        return quotePicture;

    }

}

