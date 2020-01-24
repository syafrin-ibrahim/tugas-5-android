package com.example.submission4.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.submission4.db.DBContract;

import static android.provider.UserDictionary.Words._ID;
import static com.example.submission4.db.DBContract.getColumnString;


public class Tvshow implements Parcelable {
    private String judul, desc, poster_path;
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Tvshow(){

    }



    protected Tvshow(Parcel in) {
        judul = in.readString();
        desc = in.readString();
        poster_path = in.readString();
        id = in.readString();
    }

    public static final Parcelable.Creator<Tvshow> CREATOR = new Parcelable.Creator<Tvshow>() {
        @Override
        public Tvshow createFromParcel(Parcel source) {
            return new Tvshow(source);
        }

        @Override
        public Tvshow[] newArray(int size) {
            return new Tvshow[size];
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
        dest.writeString(poster_path);
        dest.writeString(id);
    }

    public Tvshow(Cursor cursor){
        this.id = getColumnString(cursor, _ID);
        this.judul = getColumnString(cursor, DBContract.TVColumns.TITLEX);
        this.desc = getColumnString(cursor, DBContract.TVColumns.DESCRIBX);
        this.poster_path = getColumnString(cursor, DBContract.TVColumns.IMGX);


    }

}
