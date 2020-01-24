package com.example.moviefavorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import static com.example.moviefavorite.DBContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private Cursor list;
    private ProgressBar PBar;
    private MovieHelper MHelper;
    private MFavoriteAdapter mdb;
    private RecyclerView rV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PBar = findViewById(R.id.pbar);
        Toolbar tbar =findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        getSupportActionBar().setElevation(0);
        MHelper = MovieHelper.getInstance(this);
        MHelper.open();
        mdb = new MFavoriteAdapter(MHelper,getApplicationContext());
        mdb.notifyDataSetChanged();
        new MainActivity.LoadMovie().execute();
        rV = findViewById(R.id.rv_movie);
        rV.setLayoutManager(new LinearLayoutManager(this));
        rV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rV.setAdapter(mdb);

    }

    private class LoadMovie extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            PBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            PBar.setVisibility(View.GONE);
            list = cursor;
            mdb.setListMovie(list);
            mdb.notifyDataSetChanged();
            mdb.setOnItemClickCallBack(new MFavoriteAdapter.onItemClickCallBack() {
                @Override
                public void onItemClicked(Movie data) {
                    Intent i = new Intent(MainActivity.this, DetailMovie.class);
                    i.putExtra(DetailMovie.EXTRA_MOVIE, data);
                    startActivity(i);
                }
            });
            super.onPostExecute(cursor);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null, null );
        }
    }

}
