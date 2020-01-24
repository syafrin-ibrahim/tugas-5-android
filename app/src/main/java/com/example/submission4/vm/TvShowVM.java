package com.example.submission4.vm;

import android.util.Log;

import com.example.submission4.model.Tvshow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvShowVM extends ViewModel {
    private static final String API_KEY = "ISI SESUAI API_KEY ANDA";
    private MutableLiveData<ArrayList<Tvshow>> listTvShows = new MutableLiveData<>();


    public LiveData<ArrayList<Tvshow>> getListTvShows() {
        return listTvShows;
    }

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tvshow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=ad1fd499105277269da7e3a7deb12aa5&language=en-US";


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvs = list.getJSONObject(i);
                        Tvshow tv = new Tvshow();
                        String idtv = String.valueOf(tvs.getInt("id"));
                        tv.setId(idtv);
                        tv.setJudul(tvs.getString("name"));
                        tv.setDesc(tvs.getString("overview"));
                        tv.setPoster_path(tvs.getString("poster_path"));
                        listItems.add(tv);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }


        });
    }

    public LiveData<ArrayList<Tvshow>> getFilterTvShow(){
        return  listTvShows;
    }

    public void setFilter(final String title){
        AsyncHttpClient xp = new AsyncHttpClient();
        final ArrayList<Tvshow> listItemFilter = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=ad1fd499105277269da7e3a7deb12aa5&language=en-US&query="
                + title;
        xp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try{
                    String rsf = new String(responseBody);
                    JSONObject jobjx = new JSONObject(rsf);
                    JSONArray jarrayx = jobjx.getJSONArray("results");
                    for(int i = 0; i < jarrayx.length(); i++){
                        JSONObject mv = jarrayx.getJSONObject(i);
                        Tvshow mx = new Tvshow();
                        String idtv = String.valueOf(mv.getInt("id"));
                        mx.setId(idtv);
                        mx.setJudul(mv.getString("title"));
                        mx.setDesc(mv.getString("overview"));
                        mx.setPoster_path(mv.getString("poster_path"));
                        listItemFilter.add(mx);
                    }
                    listTvShows.postValue(listItemFilter);
                }catch (Exception e){
                    Log.d("exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("on failure", error.getMessage());
            }
        });
    }
}
