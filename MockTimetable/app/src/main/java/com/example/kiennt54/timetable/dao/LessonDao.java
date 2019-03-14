package com.example.kiennt54.timetable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiennt54.timetable.database.DatabaseLessonManager;
import com.example.kiennt54.timetable.models.Lesson;

import java.util.ArrayList;
import java.util.List;

import static com.example.kiennt54.timetable.database.DatabaseLessonManager.TABLE_NAME;

public class LessonDao {

    private SQLiteDatabase mDatabase;

    private DatabaseLessonManager lessonManager;

    public LessonDao(Context context) {
        this.lessonManager = new DatabaseLessonManager(context);
        mDatabase = lessonManager.open();
    }

    public List<Lesson> getListLesson(){
        List<Lesson> lessons = new ArrayList<Lesson>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setName(cursor.getString(1));
                lessons.add(lesson);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessons;
    }

    public long addLesson(Lesson lesson){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseLessonManager.DB_LESSON_NAME,lesson.getName());
        long row = mDatabase.insert(TABLE_NAME,null,contentValues);
        return row;
    }

    public long deleteLesson(Lesson lesson){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseLessonManager.DB_LESSON_NAME,"");
        int row = mDatabase.update(TABLE_NAME,contentValues,
                DatabaseLessonManager.DB_LESSON_NAME + " =?",
                new String[]{lesson.getName()});
        return row;
    }

    public long updateLesson(Lesson oldLesson,Lesson newLesson){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseLessonManager.DB_LESSON_NAME,newLesson.getName());
        int row = mDatabase.update(TABLE_NAME,contentValues,
                DatabaseLessonManager.DB_LESSON_NAME + " =?",
                new String[]{oldLesson.getName()});
        return row;
    }

    public void clearTableLesson(){
        mDatabase.delete(TABLE_NAME,null,null);
    }

    public long insertListLesson(List<Lesson> lessons) {
        long row = 0;
        for (int i = 0; i < lessons.size(); i++) {
            row += addLesson(lessons.get(i));
        }
        return row;
    }
}
