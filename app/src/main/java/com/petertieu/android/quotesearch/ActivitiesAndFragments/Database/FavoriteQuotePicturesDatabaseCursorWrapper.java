package com.petertieu.android.quotesearch.ActivitiesAndFragments.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;

public class FavoriteQuotePicturesDatabaseCursorWrapper extends CursorWrapper {

    public FavoriteQuotePicturesDatabaseCursorWrapper(Cursor cursor){
        super(cursor);
    }


    public QuotePicture getQuotePictureFromFavoriteQuotePicturesDatabase(){
        String id = getString(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.ID));
        int isFavorite = getInt(getColumnIndex(FavoriteQuotePicturesDatabaseSchema.FavoriteQuotePicturesTable.Columns.IS_FAVORITE));


        QuotePicture quotePicture = new QuotePicture(id);
        quotePicture.setFavorite(isFavorite != 0);


        return quotePicture;

    }

}

