package com.example.kiennt54.timetable;

import com.example.kiennt54.timetable.activities.MainActivity;
import com.example.kiennt54.timetable.widgets.AppWidgetSchedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AppWidgetScheduleTest {

    @Test
    public void testSendRefreshBroadcast(){
        MainActivity activity = Mockito.mock(MainActivity.class);
        AppWidgetSchedule appWidgetSchedule = Mockito.mock(AppWidgetSchedule.class);
        assertNull(appWidgetSchedule.updateWidgetLesson(activity,1));
    }
}
