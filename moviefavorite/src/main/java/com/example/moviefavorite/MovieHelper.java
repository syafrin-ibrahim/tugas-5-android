package com.example.moviefavorite;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.moviefavorite.DBContract.TABLE_MOVIE;


public class MovieHelper {
    private DBHelper dataBaseHelper;
    private SQLiteDatabase database;
    private static MovieHelper INSTANCE;
    public MovieHelper(Context context) {
        dataBaseHelper = new DBHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }



    public Cursor queryByIdProvider(String id){
        return database.query(TABLE_MOVIE, null,_ID + "= ?"
                              ,new String[]{id}
                              ,null
                              ,null
                              ,null
                              ,null);
    }

    public Cursor queryProvider(){
        return database.query(TABLE_MOVIE
                              , null
                              ,null
                              ,null
                              ,null
                              ,null
                              , _ID + " DESC");
    }

   /* public long insertProvider(ContentValues cv){
        return database.insert(TABLE_MOVIE,null, cv);
    }*/

    /*public int updateProvider(String id, ContentValues cv){
        return database.update(TABLE_MOVIE, cv,_ID +" = ?", new String[]{});
    }*/

    public int deletePorvider(String id){
        return database.delete(TABLE_MOVIE,_ID + " = ?", new String[]{id});
    }

}
