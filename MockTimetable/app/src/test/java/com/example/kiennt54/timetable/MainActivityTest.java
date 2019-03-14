package com.example.kiennt54.timetable;

import com.example.kiennt54.timetable.activities.MainActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Test
    public void testAddLesson_ReturnFalse(){
        MainActivity activity = Mockito.mock(MainActivity.class);
        boolean res = activity.addLesson(1,activity);
        Assert.assertEquals(false, res);
    }
}
