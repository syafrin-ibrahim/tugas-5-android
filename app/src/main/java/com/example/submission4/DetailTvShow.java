package com.example.submission4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.submission4.db.MovieHelper;
import com.example.submission4.db.TvHelper;
import com.example.submission4.model.Movie;
import com.example.submission4.model.Tvshow;

import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;
import static com.example.submission4.db.DBContract.MovieColumns.DESCRIB;
import static com.example.submission4.db.DBContract.MovieColumns.IMG;
import static com.example.submission4.db.DBContract.MovieColumns.MOV_ID;
import static com.example.submission4.db.DBContract.MovieColumns.TITLE;
import static com.example.submission4.db.DBContract.TVColumns.TV_ID;

public class DetailTvShow extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TVSHOW = "EXTRA TVSHOW";
    public static String url = "https://image.tmdb.org/t/p/w342";
    private TextView jdl, desc;
    private ImageView img;
    private ProgressBar pb;
    private TvHelper tHelp;
    private Button add, del;
    Cursor c;
    Tvshow tvs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        Toolbar tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        getSupportActionBar().setElevation(0);
        add = findViewById(R.id.btnadd);
        pb = findViewById(R.id.pbar);
        pb.setVisibility(View.VISIBLE);
        final Handler handle = new Handler();
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
                        tvs = getIntent().getParcelableExtra(EXTRA_TVSHOW);
                        jdl = findViewById(R.id.text_title);
                        desc = findViewById(R.id.text_description);
                        img = findViewById(R.id.image);
                        jdl.setText(tvs.getJudul());
                        desc.setText(tvs.getDesc());
                        String idtv = "tv_" + tvs.getId();
                        tHelp = TvHelper.getInstance(getApplicationContext());
                        tHelp.open();
                        c = tHelp.queryById(idtv);

                        if(c.getCount() > 0){
                            add.setVisibility(View.INVISIBLE);

                        }else{
                            add.setVisibility(View.VISIBLE);

                        }

                        Glide.with(DetailTvShow.this)
                                .load(url + tvs.getPoster_path())
                                .placeholder(R.color.colorAccent)
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        pb.setVisibility(View.GONE);
                                        Toast.makeText(DetailTvShow.this, getResources().getString(R.string.failed_loadimage), Toast.LENGTH_LONG).show();
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

   private boolean addFavourite(Tvshow item) {
        tHelp.insert(item);
        return  true;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnadd :

                if(addFavourite(tvs)){
                    Toast.makeText(getApplicationContext(), "Success Add To favorite", Toast.LENGTH_LONG).show();
                    add.setVisibility(View.INVISIBLE);
                    
                }
                break;


        }
    }
}
