package com.example.moviefavorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.UserDictionary.Words._ID;

import static com.example.moviefavorite.DBContract.getColumnString;

public class Movie implements Parcelable {
    private String judul, desc;
    private String img_path;
    private String id;

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
    protected Movie(Parcel in) {
        judul = in.readString();
        desc = in.readString();
        img_path = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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

    public Movie(Cursor cursor){
        this.id = getColumnString(cursor, _ID);
        this.judul = getColumnString(cursor, DBContract.MovieColumns.TITLE);
        this.desc = getColumnString(cursor, DBContract.MovieColumns.DESCRIB);
        this.img_path = getColumnString(cursor, DBContract.MovieColumns.IMG);
    }
}
