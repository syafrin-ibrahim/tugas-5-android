package com.example.submission4.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.submission4.db.DBContract.AUTHORITY;
import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;
//import static com.example.submission4.db.DBContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int mov = 1;
    private static final int mov_id = 2;
    private static final int tv= 3;
    private static final int tv_id = 4;

    private MovieHelper mh;
    private TvHelper th;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

       sUriMatcher.addURI(AUTHORITY, DBContract.TABLE_MOVIE, mov);
       sUriMatcher.addURI(AUTHORITY,DBContract.TABLE_MOVIE+ "/#",mov_id);
        //sUriMatcher.addURI(AUTHORITY, DBContract.TABLE_TV, tv);
        //sUriMatcher.addURI(AUTHORITY,DBContract.TABLE_TV+ "/#",tv_id);
    }


    @Override
    public boolean onCreate() {
        th = TvHelper.getInstance(getContext());
        th.open();
        mh = MovieHelper.getInstance(getContext());
        mh.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor c;
        switch(sUriMatcher.match(uri)){
            case mov :
                c = mh.queryProvider();
                break;
            case mov_id :
                c = mh.queryProviderById(uri.getLastPathSegment());
               default :
                c = null;
                break;
        }
        if(c != null){c.setNotificationUri(getContext().getContentResolver(),uri);}
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch(sUriMatcher.match(uri)){
            case mov:
                added = mh.insertProvider(values);
                break;
            default :
                added = 0;
                break;
        }
        if(added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int removed;
        switch(sUriMatcher.match(uri)){
            case mov_id :
                removed = mh.deletePorvider(uri.getLastPathSegment());
                break;
            default :
                removed = 0;
                break;
        }

        if(removed > 0 ){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return removed;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
