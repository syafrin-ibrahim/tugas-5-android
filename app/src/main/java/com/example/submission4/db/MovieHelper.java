package com.example.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

import static com.example.submission4.db.DBContract.MovieColumns.MOV_ID;
import static com.example.submission4.db.DBContract.TABLE_MOVIE;


public class MovieHelper  {
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

    /*public ArrayList<Movie> getAllData() { Cursor cursor = database.query(TABLE_MOVIE, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new Movie();
                movieModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIB)));
                movieModel.setImg_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));
                arrayList.add(movieModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie m){
        ContentValues cv = new ContentValues();
        cv.put(TITLE, m.getJudul());
        cv.put(DESCRIB, m.getDesc());
        cv.put(IMG, m.getImg_path());
        return database.insert(TABLE_MOVIE, null, cv);
    }
    public int deleteById(String id) {

        return database.delete(TABLE_MOVIE, _ID + " = ?", new String[]{id});
    } */

    public Cursor queryByIdProvider(String id){
        return database.query(TABLE_MOVIE, null,MOV_ID + "= ?"
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

    public Cursor queryProviderById(String mov_id){
        return database.query(TABLE_MOVIE,
                      null,
                      _ID + " = ?",
                       new String[]{mov_id},
                      null,
                      null,
                      null,
                        null);
    }

    public long insertProvider(ContentValues cv){
        return database.insert(TABLE_MOVIE,null, cv);
    }


    public int deletePorvider(String id){
        return database.delete(TABLE_MOVIE,_ID + " = ?", new String[]{id});
    }

}
