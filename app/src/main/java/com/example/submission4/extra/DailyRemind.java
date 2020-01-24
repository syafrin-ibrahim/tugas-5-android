package com.example.submission4.extra;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.example.submission4.MainActivity;
import com.example.submission4.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class DailyRemind extends BroadcastReceiver {
    private static int notif_id = 110;


     @Override
    public void onReceive(Context context, Intent intent) {
            getNotif(context, "Notifikasi..!",
                    "Cek Ke Aplikasi mungkin ada film favorit anda ", notif_id);

     }

    private void getNotif(Context ctx, String title, String msg, int id) {
        String CHANEL_ID = " chanel_1";
        String CHANEL_NAME = "Daily Remind";
        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(ctx, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.baseline_notification_important_black_18dp)
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.baseline_notification_important_black_18dp))
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pi)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 100})
                .setSound(sound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            nc.enableVibration(true);
            nc.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANEL_ID);
            nm.createNotificationChannel(nc);
        }

        Notification nf = builder.build();
        nm.notify(id, nf);
    }

    public void setReminder(Context ctx){
        AlarmManager alm =(AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar c =Calendar.getInstance();
     //   SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 7);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        String waktu = hour.format(c.getTime());
        Intent it =new Intent(ctx, DailyRemind.class);
        PendingIntent pd =PendingIntent.getBroadcast(ctx, notif_id, it, 0);
        if(alm != null){
                alm.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pd);
                //alm.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pd);

        }
        Toast.makeText(ctx, "Alarm Is Active on" + waktu, Toast.LENGTH_LONG).show();
    }

    public void stopReminder(Context ctx){
        AlarmManager alm =(AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent it =new Intent(ctx, DailyRemind.class);
        PendingIntent pd =PendingIntent.getBroadcast(ctx, notif_id, it, 0);
        alm.cancel(pd);
        Toast.makeText(ctx, "Alarm Is Inactive", Toast.LENGTH_LONG).show();
   }
}
