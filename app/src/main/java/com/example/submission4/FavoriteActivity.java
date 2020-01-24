package com.example.submission4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.submission4.adapter.FavoritePagerAdapter;
import com.example.submission4.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        FavoritePagerAdapter fp = new FavoritePagerAdapter(this, getSupportFragmentManager());
        ViewPager vp = findViewById(R.id.view_pagerx);
        vp.setAdapter(fp);
        TabLayout tb = findViewById(R.id.tabx);
        tb.setupWithViewPager(vp);
        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_favorite));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/
}
