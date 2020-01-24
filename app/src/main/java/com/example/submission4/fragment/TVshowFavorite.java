package com.example.submission4.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

//import com.example.submission4.DetailMovie;
import com.example.submission4.DetailTvShow;
import com.example.submission4.R;

//import com.example.submission4.adapter.MFavoriteAdapter;
import com.example.submission4.adapter.TFavoriteAdapter;

//import com.example.submission4.db.MovieHelper;
import com.example.submission4.db.TVcallback;
import com.example.submission4.db.TvHelper;

//import com.example.submission4.model.Movie;
import com.example.submission4.model.Tvshow;


import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class TVshowFavorite extends Fragment implements TVcallback {
    private ProgressBar PBar;
    private TvHelper THelper;
    private TFavoriteAdapter tdb;
    private RecyclerView rV;
    private ArrayList<Tvshow> listTv = new ArrayList<>();
    public TVshowFavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
        PBar = view.findViewById(R.id.fbar);
        THelper = TvHelper.getInstance(getContext());
        THelper.open();
        tdb = new TFavoriteAdapter(THelper);
        tdb.notifyDataSetChanged();
        new TVshowFavorite.LoadTv(THelper, this).execute();
        rV = view.findViewById(R.id.frv_tvshow);
        rV.setLayoutManager(new LinearLayoutManager(getActivity()));
        rV.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rV.setAdapter(tdb);
        tdb.setOnItemClickCallBack(new TFavoriteAdapter.onItemClickCallBack() {
            @Override
            public void onItemClicked(Tvshow data) {
                Intent h = new Intent(getActivity(), DetailTvShow.class);
                h.putExtra(DetailTvShow.EXTRA_TVSHOW, data);
                startActivity(h);
            }
        });
        return view;
    }

    @Override
    public void preExecute() {
        PBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Tvshow> tv) {
        PBar.setVisibility(View.GONE);
        tdb.setData(tv);
        listTv.addAll(tv);
    }


    private class LoadTv extends AsyncTask<Void, Void, ArrayList<Tvshow>> {

        WeakReference<TvHelper> tWeakReference;
        WeakReference<TVcallback>lFWeakReference;


        public LoadTv(TvHelper tr, TVcallback context) {
            tWeakReference = new WeakReference<>(tr);
            lFWeakReference = new WeakReference<>(context);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lFWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Tvshow> tv) {
            super.onPostExecute(tv);
            lFWeakReference.get().postExecute(tv);
        }

        @Override
        protected ArrayList<Tvshow> doInBackground(Void... voids) {
            return tWeakReference.get().getAllData();
        }
    }


}

