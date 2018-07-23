package com.petertieu.android.quotesearch.ActivitiesAndFragments.Database;

import java.util.List;

//Database class #1:
    //Blueprint for the SQLite Database of Favorite Quotes
public class FavoriteQuotesDatabaseSchema {

    //STATIC inner clas for setting the database
    public static final class FavoriteQuotesTable{

        //Name of the table
        public static final String FAVORITE_QUOTES_TABLE_NAME = "favorte_quotes_table";

        //Inner-inner clas that sets names of the columns of the table
        public static final class Columns{
            public static final String ID = "id";
            public static final String QUOTE_STRING = "quote_string";
            public static final String AUTHOR = "author";
            public static final String IS_FAVORITE = "is_favorite";
        }
    }
}
