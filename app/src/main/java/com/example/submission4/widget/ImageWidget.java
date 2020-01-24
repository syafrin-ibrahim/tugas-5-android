package com.example.submission4.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.submission4.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "com.example.submission4.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.submission4.EXTRA_ITEM";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent i = new Intent(context, StackWidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);
        views.setRemoteAdapter(R.id.stack_view, i);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent t = new Intent(context, ImageWidget.class);
        t.setAction(ImageWidget.TOAST_ACTION);
        t.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent p = PendingIntent.getBroadcast(context, 0, t, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, p);
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null){
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Chosed layer " + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }

        super.onReceive(context, intent);
    }
}

