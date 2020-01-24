package com.example.submission4.db;

import com.example.submission4.model.Movie;

import java.util.ArrayList;

public interface MTcallback {
    void preExecute();
    void postExecute(ArrayList<Movie> mv);
}
