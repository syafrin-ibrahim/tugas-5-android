package com.example.submission4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.submission4.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionPagerAdapter sp = new SectionPagerAdapter(this, getSupportFragmentManager());
        ViewPager vp = findViewById(R.id.view_pager);
        vp.setAdapter(sp);
        TabLayout tb = findViewById(R.id.tabs);
        tb.setupWithViewPager(vp);
        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        getSupportActionBar().setElevation(0);

    }


}
