package com.example.submission4.db;

import com.example.submission4.model.Movie;
import com.example.submission4.model.Tvshow;

import java.util.ArrayList;

public interface TVcallback {
    void preExecute();
    void postExecute(ArrayList<Tvshow> tv);
}
