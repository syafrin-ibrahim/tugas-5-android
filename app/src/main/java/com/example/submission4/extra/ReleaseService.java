package com.example.submission4.extra;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.submission4.MainActivity;
import com.example.submission4.R;
import com.example.submission4.model.Movie;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;

import cz.msebera.android.httpclient.Header;

public class ReleaseService extends JobService {
    private static int r_id = 111;
    private int idnotif=1;
    String CHANEL_ID = " chanel_2";
    String CHANEL_NAME = "Release Remind";
    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    final ArrayList<Movie> listNotif = new ArrayList<>();
    private final static String GROUP_KEY_MOVIE = "group_key_movie";

    private void getMovie(final JobParameters jp){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date nowDate = Calendar.getInstance().getTime();
        String date = dateFormat.format(nowDate);
        String url="https://api.themoviedb.org/3/discover/movie?api_key=ad1fd499105277269da7e3a7deb12aa5&primary_release_date.gte="+ date +"&primary_release_date.lte="+ date;

        AsyncHttpClient hp = new AsyncHttpClient();
        hp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               try{
                   String rsf = new String(responseBody);
                   JSONObject jobjx = new JSONObject(rsf);

                   JSONArray jarrayx = jobjx.getJSONArray("results");
                  // int gh = (int) jobjx.get("total_results");



                   NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                   Notification n = null;
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                   PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), r_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                   NotificationCompat.Builder mb;

                   if(jarrayx.length() <= 1){
                                       mb = new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                                               .setContentTitle(jarrayx.getJSONObject(0).getString("title"))
                                               .setContentText(jarrayx.getJSONObject(0).getString("overview"))
                                               .setSmallIcon(R.drawable.baseline_notification_important_black_18dp)
                                               .setGroup(GROUP_KEY_MOVIE)
                                               .setAutoCancel(true);
                                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                           /* Create or update. */
                                           NotificationChannel channel = new NotificationChannel(CHANEL_ID,
                                                   CHANEL_NAME,
                                                   NotificationManager.IMPORTANCE_DEFAULT);
                                           channel.enableVibration(true);
                                           channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
                                           mb.setChannelId(CHANEL_ID);
                                           nm.createNotificationChannel(channel);
                                           mb.setChannelId(CHANEL_ID);
                                           if (nm != null) {
                                               nm.createNotificationChannel(channel);
                                           }

                                       }



                                       n = mb.build();
                                       if (nm != null) {
                                           nm.notify(idnotif,n);
                                       }


                   }else{

                                       mb = new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                                               .setSmallIcon(R.drawable.baseline_notification_important_black_18dp)
                                               .setGroup(GROUP_KEY_MOVIE)
                                               .setContentText("New Release Movie Today")
                                               .setGroupSummary(true)
                                               .setContentIntent(pendingIntent)
                                               .setVibrate(new long[]{1000, 1000, 1000, 1000, 100})
                                               .setSound(sound)
                                               .setAutoCancel(true);
                                            NotificationCompat.InboxStyle ib = new NotificationCompat.InboxStyle();
                                            ib.setBigContentTitle("List of Title relese Movie Today   : ");

                                               for(int i = 0; i < jarrayx.length(); i++){

                                                   String jdl = jarrayx.getJSONObject(i).getString("title");
                                                   //String mssg = jarrayx.getJSONObject(i).getString("overview");
                                                   //int id = jarrayx.getJSONObject(i).getInt("id");
                                                   ib.addLine("- "+jdl);

                                               }
                                          mb.setStyle(ib);

                                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                           NotificationChannel channel = new NotificationChannel(CHANEL_ID,
                                                   CHANEL_NAME,
                                                   NotificationManager.IMPORTANCE_DEFAULT);
                                           channel.enableVibration(true);
                                           channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
                                           mb.setChannelId(CHANEL_ID);
                                           nm.createNotificationChannel(channel);
                                           mb.setChannelId(CHANEL_ID);
                                           if (nm != null) {
                                               nm.createNotificationChannel(channel);
                                           }

                                       }
                                       n = mb.build();
                                       if (nm != null) {
                                           nm.notify(idnotif,n);
                                       }






                   }






                   jobFinished(jp, false);




               }catch(Exception e){
                   jobFinished(jp, true);
                   e.printStackTrace();
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    jobFinished(jp, true);
            }
        });


    }

    @Override
    public boolean onStartJob(JobParameters job) {
        getMovie(job);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
