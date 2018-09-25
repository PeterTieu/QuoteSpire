package com.tieutech.android.quotespire.ActivitiesAndFragments.Database;


//Database class #1:
//Blueprint for the SQLite Database of Favorite Quotes
public class FavoriteQuotePicturesDatabaseSchema {

    //STATIC inner clas for setting the database
    public static final class FavoriteQuotePicturesTable{

        //Name of the table
        public static final String FAVORITE_QUOTE_PICTURES_TABLE_NAME = "favorite_quote_pictures_table";

        //Inner-inner clas that sets names of the columns of the table
        public static final class Columns{
            public static final String ID = "id";
            public static final String IS_FAVORITE = "is_favorite";
            public static final String BITMAP_FILE_PATH = "bitmap_file_path";
        }
    }
}