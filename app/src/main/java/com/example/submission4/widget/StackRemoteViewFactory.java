package com.example.submission4.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.submission4.R;
import com.example.submission4.model.Movie;

import java.util.concurrent.ExecutionException;
//import static com.example.submission4.db.DBContract.CONTENT_URI;
import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;

//import static android.provider.ContactsContract.SyncState.CONTENT_URI;
public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    private Cursor list;


    public StackRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(list != null){
            list.close();
        }
        final long token = Binder.clearCallingIdentity();
      //  list = mContext.getContentResolver().query(CONTENT_URI, null,null,null,null);
        list = mContext.getContentResolver().query(CONTENT_URI, null,null,null,null);
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie m =  getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.image_item);
        RequestOptions req = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter();
        Bitmap b = null;
        String url_image = "https://image.tmdb.org/t/p/w185" + m.getImg_path();
        try{
            b = Glide.with(mContext)
                    .asBitmap()
                    .load(url_image)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();


        }catch(InterruptedException e){
                e.printStackTrace();
        }catch(ExecutionException ex){
                ex.printStackTrace();
        }
        rv.setImageViewBitmap(R.id.imageView, b);
        Bundle ext =new Bundle();
        ext.putInt(ImageWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(ext);
        rv.setOnClickFillInIntent(R.id.imageView, intent);
        return rv;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getItem(int position){
        if(!list.moveToPosition(position)){
            throw new IllegalStateException("position invalid");
        }


        return new Movie(list);
    }
}
