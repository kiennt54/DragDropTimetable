package com.example.kiennt54.timetable.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ScheduleService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ScheduleProvider(this.getApplicationContext(), intent));
    }

}