package com.example.kiennt54.timetable;

import com.example.kiennt54.timetable.commons.Constants;
import com.example.kiennt54.timetable.models.Lesson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ConstantsTest {

    @Test
    public void testLessonTableSize(){
        List<Lesson> lessonList = Constants.createTestLesson();
        assertEquals(15,lessonList.size());
    }


}
