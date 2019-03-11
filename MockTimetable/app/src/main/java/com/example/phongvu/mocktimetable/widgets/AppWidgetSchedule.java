package com.example.phongvu.mocktimetable.widgets;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.activities.MainActivity;
import com.example.phongvu.mocktimetable.commons.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidgetSchedule extends AppWidgetProvider {

    public static final String PUT_EXTRAS_POSITION = "position";

    public static final String ID_WIDGET = "id_widget";

    public static final String ID_FROM_MAINACTIVITY = "ID_fROM_MAIN";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {

            RemoteViews remoteViews = updateWidgetLesson(context, appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    private RemoteViews updateWidgetLesson(Context context, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_schedule);
        Intent svcIntent = new Intent(context, ScheduleService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.gv_time_table, svcIntent);
        views.setEmptyView(R.id.gv_time_table, R.id.txt_empty);

        Intent clickIntentTemplate = new Intent(context, MainActivity.class);
        clickIntentTemplate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.gv_time_table, clickPendingIntentTemplate);

        return views;
    }

    public static void sendRefreshBroadcast(Context context, int id) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(ID_FROM_MAINACTIVITY, id);
        intent.setComponent(new ComponentName(context, AppWidgetSchedule.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            int id = intent.getIntExtra(ID_FROM_MAINACTIVITY, 0);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetSchedule.class));
            for (int i = 0; i < appWidgetIds.length; i++) {
                // Log.d("kiemtra_mang",appWidgetIds[i]+"");
                if (id == appWidgetIds[i]) {
                    appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.gv_time_table);
                }

            }
        }
        super.onReceive(context, intent);
    }
}