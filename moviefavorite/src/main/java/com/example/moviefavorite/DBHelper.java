package com.example.moviefavorite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviefavorite.DBContract.MovieColumns.DESCRIB;
import static com.example.moviefavorite.DBContract.MovieColumns.IMG;
import static com.example.moviefavorite.DBContract.MovieColumns.TITLE;
import static com.example.moviefavorite.DBContract.TABLE_MOVIE;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbsubmiss";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_MOVIE = "create table " + TABLE_MOVIE +
            " (" + _ID + " integer primary key autoincrement, " +
            TITLE + " text not null, " +
            DESCRIB + " text not null, " +
            IMG + " text not null);";


    public DBHelper(Context context ) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);

        onCreate(db);
    }
}
