package com.example.submission4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.submission4.db.MovieHelper;
import com.example.submission4.model.Movie;

import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;
import static com.example.submission4.db.DBContract.MovieColumns.DESCRIB;
import static com.example.submission4.db.DBContract.MovieColumns.IMG;
import static com.example.submission4.db.DBContract.MovieColumns.MOV_ID;
import static com.example.submission4.db.DBContract.MovieColumns.TITLE;

public class DetailMovie extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_MOVIE = "EXTRA MOVIE";
    public static String url = "https://image.tmdb.org/t/p/w342";
    private TextView jdl, desc;
    private ImageView img;
    Cursor c;
    private MovieHelper mHelp;
    private ProgressBar pb;

    private Button add;
    Movie mymovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        getSupportActionBar().setElevation(0);
        pb = findViewById(R.id.pbar);
        pb.setVisibility(View.VISIBLE);
        final Handler handle = new Handler();

        mymovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        add = findViewById(R.id.btnadd);
        jdl = findViewById(R.id.text_title);
        desc = findViewById(R.id.text_description);
        img = findViewById(R.id.image);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch(Exception e){

                }
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        jdl.setText(mymovie.getJudul());
                        desc.setText(mymovie.getDesc());
                        String idmovie = "movie_" + mymovie.getId();
                        mHelp = MovieHelper.getInstance(getApplicationContext());
                        mHelp.open();
                        c = mHelp.queryByIdProvider(idmovie);

                        if(c.getCount() > 0){
                            add.setVisibility(View.INVISIBLE);

                        }else{
                            add.setVisibility(View.VISIBLE);

                        }


                        Glide.with(DetailMovie.this)
                                .load(url + mymovie.getImg_path())
                                .placeholder(R.color.colorAccent)
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        pb.setVisibility(View.GONE);
                                        Toast.makeText(DetailMovie.this,getResources().getString(R.string.failed_loadimage), Toast.LENGTH_LONG).show();
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        pb.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .dontAnimate()
                                .into(img);




                    }
                });

            }
        }).start();



        add.setOnClickListener(this);




    }

 /*  private boolean isMovieFavorite(String idmovie){

       Cursor c = mHelp.queryProviderById(idmovie);
        if(c.moveToFirst()){
            c.close();
            return true;
        }
        c.close();
        return false;

    }*/

    private boolean addFavourite(Movie movieItem) {
        String id = movieItem.getId();
        String movid = "movie_" + id;
        String titleMovie = movieItem.getJudul();
        String overView = movieItem.getDesc();
        String img = movieItem.getImg_path();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, titleMovie);
        contentValues.put(DESCRIB, overView);
        contentValues.put(IMG, img);
        contentValues.put(MOV_ID, movid);
        this.getContentResolver().insert(CONTENT_URI,contentValues);
        return  true;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnadd :

                        if(addFavourite(mymovie)){
                           Toast.makeText(getApplicationContext(), "Success Add To favorite", Toast.LENGTH_LONG).show();
                           add.setVisibility(View.INVISIBLE);

                        }
                break;




        }
    }
}
