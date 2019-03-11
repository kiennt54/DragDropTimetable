package com.example.phongvu.mocktimetable.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.commons.Constants;
import com.example.phongvu.mocktimetable.dao.ScheduleDao;
import com.example.phongvu.mocktimetable.models.CellData;

import java.util.List;

public class ScheduleProvider implements RemoteViewsService.RemoteViewsFactory {

    private List<CellData> mCellDatas;

    private Context mContext;

    private ScheduleDao mDao;

    private int idWidget;

    private String[] mWeek = {"", "Monday", "Tuesday", "Wednesday", "Thurday", "Friday", "Saturnday"};
    private String[] mLesson = {"Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5", "Lesson 6"};

    public ScheduleProvider(Context context, Intent intent) {
        mContext = context;
        idWidget = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        mDao = new ScheduleDao(context);
        mCellDatas = mDao.getSubjectFromCellData();
        
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        mCellDatas = mDao.getSubjectFromCellData();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mCellDatas.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.single_cell_layout);

        CellData cellData = mCellDatas.get(position);
        views.setTextViewText(R.id.txt_subject, cellData.getLesson().getName());
        Intent fillIntent = new Intent();
        fillIntent.putExtra(AppWidgetSchedule.ID_WIDGET, idWidget);
        views.setOnClickFillInIntent(R.id.txt_subject, fillIntent);

        if (position > 0 && position < 7) {
            views.setTextViewText(R.id.txt_subject, mWeek[position]);
        }

        if ((position % 7 == 0) && (position < 43) && (position > 0)) {
            views.setTextViewText(R.id.txt_subject, mLesson[(position / 7) - 1]);
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
