package com.example.phongvu.mocktimetable.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ScheduleService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ScheduleProvider(this.getApplicationContext(), intent));
    }

}