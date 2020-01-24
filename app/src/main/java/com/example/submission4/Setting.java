package com.example.submission4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.submission4.extra.DailyRemind;
import com.example.submission4.extra.ReleaseRemind;

public class Setting extends AppCompatActivity {


    Switch radiodaily, radionewrelease;
    ReleaseRemind rr;
    DailyRemind dr;
    SharedPreferences sp = null;
    public static final String NAME = "key_name";
    public static final String KEY_DAILY = "key_daily";
    public static final String KEY_RELEASE = "key_release";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radiodaily = findViewById(R.id.swc_daily_reminder);
        radionewrelease = findViewById(R.id.swc_release_today_reminder);
        sp = getSharedPreferences(NAME, MODE_PRIVATE);
        rr = new ReleaseRemind();
        if(sp.getString(KEY_RELEASE, null)!= null){
            radionewrelease.setChecked(true);
        }else{
            radionewrelease.setChecked(false);
        }
        radionewrelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    rr.setReleaseReminder(Setting.this);
                    SharedPreferences.Editor e =sp.edit();
                    e.putString(KEY_RELEASE,"Release");
                    e.apply();
                }else{
                    rr.stopReleaseReminder(Setting.this);
                    SharedPreferences.Editor e = sp.edit();
                    e.remove(KEY_DAILY);
                    e.apply();
                }
            }
        });

        dr = new DailyRemind();
        if(sp.getString(KEY_DAILY, null)!= null){
            radiodaily.setChecked(true);
        }else{
            radiodaily.setChecked(false);
        }

        radiodaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    dr.setReminder(Setting.this);
                    SharedPreferences.Editor e = sp.edit();
                    e.putString(KEY_DAILY, "Daily");
                    e.apply();
                }else{
                    dr.stopReminder(Setting.this);
                    SharedPreferences.Editor e = sp.edit();
                    e.remove(KEY_DAILY);
                    e.apply();
                }
            }
        });
    }


}
