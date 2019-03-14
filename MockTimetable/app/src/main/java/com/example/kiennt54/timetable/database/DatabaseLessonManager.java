package com.example.kiennt54.timetable.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLessonManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "LessonManager";

    public static final int VERSION = 1;

    public static final String TABLE_NAME = "lesson";

    public static final String DB_LESSON_ID = "id";

    public static final String DB_LESSON_NAME = "name";

    public DatabaseLessonManager(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbSchudule = "CREATE TABLE " + TABLE_NAME + " ( " + DB_LESSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_LESSON_NAME + " TEXT )";
        db.execSQL(dbSchudule);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
