package com.example.submission4.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import com.example.submission4.DetailMovie;
import com.example.submission4.R;
import com.example.submission4.adapter.MFavoriteAdapter;

import com.example.submission4.db.MovieHelper;
import com.example.submission4.model.Movie;



//import static com.example.submission4.db.DBContract.CONTENT_URI;
import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavorite extends Fragment{
    private Cursor list;
    private ProgressBar PBar;
    private MovieHelper MHelper;
    private MFavoriteAdapter mdb;
    private RecyclerView rV;
   // private ArrayList<Movie> listMv = new ArrayList<>();

    public MovieFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        PBar = view.findViewById(R.id.fpbar);

        MHelper = MovieHelper.getInstance(getContext());
        MHelper.open();
        mdb = new MFavoriteAdapter(MHelper, getActivity());
        mdb.notifyDataSetChanged();
        new MovieFavorite.LoadMovie().execute();
        rV = view.findViewById(R.id.frv_movie);
        rV.setLayoutManager(new LinearLayoutManager(getActivity()));
        rV.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rV.setAdapter(mdb);
        mdb.setOnItemClickCallBack(new MFavoriteAdapter.onItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                Intent dm = new Intent(getActivity(), DetailMovie.class);
                dm.putExtra(DetailMovie.EXTRA_MOVIE, data);
                startActivity(dm);
            }
        });
        return view;
    }





    private class LoadMovie extends AsyncTask<Void, Void, Cursor>{
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
            super.onPostExecute(cursor);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null );
        }
    }



}
