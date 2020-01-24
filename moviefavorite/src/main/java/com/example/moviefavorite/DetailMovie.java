package com.example.moviefavorite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

public class DetailMovie extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "EXTRA MOVIE";
    public static String url = "https://image.tmdb.org/t/p/w342";
    private TextView jdl, desc;
    private ImageView img;
    Cursor c;
    private MovieHelper mHelp;
    private ProgressBar pb;

    private Button add, del;
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







    }

}

