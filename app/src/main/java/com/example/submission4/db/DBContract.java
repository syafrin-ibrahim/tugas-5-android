package com.example.submission4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
    public static String TABLE_TV ="tvshow";
    public static String TABLE_MOVIE ="movie";
    public static final String AUTHORITY ="com.example.submission4";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE ="title";
        public static String DESCRIB = "description";
        public static String IMG = "img";
        public static String MOV_ID = "idmovie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();

    }

    public static final class TVColumns implements BaseColumns {
        public static String TITLEX ="titlex";
        public static String DESCRIBX = "descriptionx";
        public static String IMGX = "imgx";
        public static String TV_ID ="idtvshow";
       /* public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();*/
    }



    public static String getColumnString(Cursor cursor, String columName){
        return cursor.getString(cursor.getColumnIndex(columName));
    }



}
