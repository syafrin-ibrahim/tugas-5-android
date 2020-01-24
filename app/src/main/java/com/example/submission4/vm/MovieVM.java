package com.example.submission4.vm;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.example.submission4.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class MovieVM extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();


    public LiveData<ArrayList<Movie>> getListMovies(){
        return  listMovies;
    }
    public void setMovie(){
        AsyncHttpClient hp = new AsyncHttpClient();
        final ArrayList<Movie> listItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=ad1fd499105277269da7e3a7deb12aa5&language=en-US";
        hp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try{
                    String rs = new String(responseBody);
                    JSONObject jobj = new JSONObject(rs);
                    JSONArray jarray = jobj.getJSONArray("results");
                    for(int i = 0; i < jarray.length(); i++){
                        JSONObject mv = jarray.getJSONObject(i);
                        Movie m = new Movie();
                        String idmv = String.valueOf(mv.getInt("id"));
                        m.setId(idmv);
                        m.setJudul(mv.getString("title"));
                        m.setDesc(mv.getString("overview"));
                        m.setImg_path(mv.getString("poster_path"));
                        listItem.add(m);
                    }

                    listMovies.postValue(listItem);
                }catch(Exception e){
                    Log.d("exception", e.getMessage());

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }



    public LiveData<ArrayList<Movie>> getFilterMovies(){
        return  listMovies;
    }

    public void setFilter(final String title){
        AsyncHttpClient xp = new AsyncHttpClient();
        final ArrayList<Movie> listItemFilter = new ArrayList<>();

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
                        Movie mx = new Movie();
                        String idmv = String.valueOf(mv.getInt("id"));
                        mx.setId(idmv);
                        mx.setJudul(mv.getString("title"));
                        mx.setDesc(mv.getString("overview"));
                        mx.setImg_path(mv.getString("poster_path"));
                        listItemFilter.add(mx);
                    }
                    listMovies.postValue(listItemFilter);
                }catch (Exception e){
                    Log.d("exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("on failure", error.getMessage());
            }
        });

    }
}
