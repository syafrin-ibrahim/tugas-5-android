package com.example.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.submission4.model.Tvshow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.db.DBContract.TABLE_TV;
import static com.example.submission4.db.DBContract.TVColumns.DESCRIBX;
import static com.example.submission4.db.DBContract.TVColumns.IMGX;
import static com.example.submission4.db.DBContract.TVColumns.TITLEX;
import static com.example.submission4.db.DBContract.TVColumns.TV_ID;


public class TvHelper  {
    private DBHelper dataBaseHelper;
    private SQLiteDatabase database;
    private static TvHelper INSTANCE;

    public TvHelper(Context context) {
        dataBaseHelper = new DBHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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

    public ArrayList<Tvshow> getAllData() {
        Cursor cursor = database.query(TABLE_TV, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Tvshow> arrayList = new ArrayList<>();
        Tvshow tvModel;
        if (cursor.getCount() > 0) {
            do {
                tvModel = new Tvshow();
                tvModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                tvModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLEX)));
                tvModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIBX)));
                tvModel.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMGX)));
                arrayList.add(tvModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Tvshow tv){
        ContentValues cv = new ContentValues();
        String tvid = "tv_"+tv.getId();
        cv.put(TV_ID, tvid);
        cv.put(TITLEX, tv.getJudul());
        cv.put(DESCRIBX, tv.getDesc());
        cv.put(IMGX, tv.getPoster_path());
        return database.insert(TABLE_TV, null, cv);
    }



  public int deleteById(String id) {

        return database.delete(TABLE_TV, _ID + " = ?", new String[]{id});
    }

    public Cursor queryById(String id){
        return database.query(TABLE_TV, null,TV_ID + "= ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(TABLE_TV
                , null
                ,null
                ,null
                ,null
                ,null
                , _ID + " DESC");
    }

  /*  public long insertProvider(ContentValues cv){
        return database.insert(TABLE_TV,null, cv);
    }*/


    public int deleteTvId(String id){
        return database.delete(TABLE_TV,TV_ID + " = ?", new String[]{id});
    }
}
