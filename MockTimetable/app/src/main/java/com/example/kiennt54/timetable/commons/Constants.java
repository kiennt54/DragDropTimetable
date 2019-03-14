package com.example.kiennt54.timetable.commons;

import com.example.kiennt54.timetable.models.CellData;
import com.example.kiennt54.timetable.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int NUMCELL_TABLE_TIMETABLE = 49;

    public static final int NUMCELL_TABLE_LESSON = 15;

    public static final int TIMETABLE_DRAG = 1;

    public static final int LISTLESSON_DRAG = 2;

    public static final String SHARED_PREFERENCES_NAME = "SAVEIDWIDGET";

    public static final String NAME_ID = "NAMEID";


    public static final String NAME_ACTION="com.example.phongvu.MOC";

    public static final String POSITION="position";

    public static List<Lesson> createTestLesson() {

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("Math"));
        lessons.add(new Lesson("IT"));
        lessons.add(new Lesson("English"));
        lessons.add(new Lesson("Physic"));
        lessons.add(new Lesson("Chermistry"));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        return lessons;
    }

    public static List<CellData> createTestCells() {
        List<CellData> cellDatas = new ArrayList<>();
        for (int i = 0;i<49;i++){
            cellDatas.add(new CellData(new Lesson("")));
        }
        return cellDatas;
    }

    public static boolean checkExistedLesson(List<Lesson> lessonList, String nameLesson) {
        for (Lesson lesson : lessonList) {
            if (lesson.getName().equalsIgnoreCase(nameLesson)) {
                return false;
            }
        }
        return true;
    }
}
