package com.example.moviefavorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {

    public static String TABLE_MOVIE ="movie";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE ="title";
        public static String DESCRIB = "description";
        public static String IMG = "img";

    }
    public static final String AUTHORITY ="com.example.submission4";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();



    public static String getColumnString(Cursor cursor, String columName){
        return cursor.getString(cursor.getColumnIndex(columName));
    }




}
