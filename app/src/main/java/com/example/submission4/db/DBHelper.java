package com.example.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.db.DBContract.MovieColumns.MOV_ID;
import static com.example.submission4.db.DBContract.TABLE_TV;
import static com.example.submission4.db.DBContract.TVColumns.DESCRIBX;
import static com.example.submission4.db.DBContract.TVColumns.IMGX;
import static com.example.submission4.db.DBContract.TVColumns.TITLEX;
import static com.example.submission4.db.DBContract.MovieColumns.DESCRIB;
import static com.example.submission4.db.DBContract.MovieColumns.IMG;
import static com.example.submission4.db.DBContract.MovieColumns.TITLE;
import static com.example.submission4.db.DBContract.TABLE_MOVIE;
import static com.example.submission4.db.DBContract.TVColumns.TV_ID;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbsubmiss";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_MOVIE = "create table " + TABLE_MOVIE +
            " (" + _ID + " integer primary key autoincrement, " +
            MOV_ID + " text not null," +
            TITLE + " text not null, " +
            DESCRIB + " text not null, " +
            IMG + " text not null);";

    private static String CREATE_TABLE_TV = "create table " + TABLE_TV +
            " (" + _ID + " integer primary key autoincrement, " +
            TV_ID + " text not null," +
            TITLEX + " text not null, " +
            DESCRIBX + " text not null, " +
            IMGX + " text not null);";

    public DBHelper(Context context ) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV);
        onCreate(db);
    }
}
