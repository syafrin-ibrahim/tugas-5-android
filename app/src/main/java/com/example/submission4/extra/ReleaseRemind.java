package com.example.submission4.extra;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReleaseRemind extends BroadcastReceiver {
    private static int r_id = 120;
    private String DISPATCHER_TAG = "DISPATCHER_TAG";
    FirebaseJobDispatcher fj;
    @Override
    public void onReceive(Context context, Intent intent) {
        fj = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        onJob();
    }

    private void onJob() {
        Job job =fj.newJobBuilder()
                    .setService(ReleaseService.class)
                    .setRecurring(false)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setTrigger(Trigger.executionWindow(0,1))
                    .setReplaceCurrent(true)
                    .setTag(DISPATCHER_TAG)
                    .build();
                    fj.mustSchedule(job);
    }

    public void setReleaseReminder(Context ctx){
        AlarmManager alm =(AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar c =Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
      //  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //final Date nowDate = Calendar.getInstance().getTime();

        Intent it =new Intent(ctx, ReleaseRemind.class);
        PendingIntent pd = PendingIntent.getBroadcast(ctx, r_id, it, 0);
        if(alm != null){
            //alm.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pd);
            alm.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pd);
        }
        Toast.makeText(ctx, "Reminder is Active", Toast.LENGTH_LONG).show();
    }


    public void stopReleaseReminder(Context ctx){
        AlarmManager alm =(AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent it =new Intent(ctx, ReleaseRemind.class);
        PendingIntent pd =PendingIntent.getBroadcast(ctx, r_id, it, 0);
        alm.cancel(pd);
        Toast.makeText(ctx, "Reminder Is Inactive", Toast.LENGTH_LONG).show();
    }


    public ReleaseRemind() {
    }


}
