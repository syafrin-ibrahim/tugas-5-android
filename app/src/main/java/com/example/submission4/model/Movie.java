package com.example.submission4.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;




import static com.example.submission4.db.DBContract.MovieColumns.DESCRIB;
import static com.example.submission4.db.DBContract.MovieColumns.IMG;
import static com.example.submission4.db.DBContract.MovieColumns.MOV_ID;
import static com.example.submission4.db.DBContract.MovieColumns.TITLE;
import static com.example.submission4.db.DBContract.getColumnString;

public class Movie implements Parcelable {
    private String judul, desc;
    private String img_path;
    private String id;
   // private String mov_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public Movie(){

    }
    public Movie(int id, String jdl, String desc){
        this.id = String.valueOf(id);
        this.judul = jdl;
        this.desc = desc;
    }

  public Movie(Cursor cursor){
        this.id = getColumnString(cursor, BaseColumns._ID);
        this.judul = getColumnString(cursor, TITLE);
        this.img_path = getColumnString(cursor, IMG);
        this.desc = getColumnString(cursor, DESCRIB);
       // this.mov_id = getColumnString(cursor, MOV_ID);
    }
    protected Movie(Parcel in) {
        judul = in.readString();
        desc = in.readString();
        img_path = in.readString();
        id = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(desc);
        dest.writeString(img_path);
        dest.writeString(id);
    }



}
