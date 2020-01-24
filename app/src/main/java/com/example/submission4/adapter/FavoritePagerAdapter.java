package com.example.submission4.adapter;

import android.content.Context;

import com.example.submission4.R;
import com.example.submission4.fragment.F_Movie;
import com.example.submission4.fragment.F_Tvshow;
import com.example.submission4.fragment.MovieFavorite;
import com.example.submission4.fragment.TVshowFavorite;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FavoritePagerAdapter extends FragmentPagerAdapter {
    private final Context ctx;


    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    public FavoritePagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ctx = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fm = null;
        switch (position){
            case 0 :
                fm = new MovieFavorite();

                break;
            case 1 :
                fm = new TVshowFavorite();

                break;
        }
        return fm;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return ctx.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
