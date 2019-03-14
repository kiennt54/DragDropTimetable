package com.example.kiennt54.timetable.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseScheduleManager extends SQLiteOpenHelper {

    public static final String NAME_DB = "ScheduleManager";

    public static final int VERSION = 1;

    public static final String DB_SCHUDULE = "schudule";

    public static final String DB_SCHUDULE_ID = "idSchudule";

    public static final String DB_SCHUDULE_SUBJECT = "subject";

    public static final String DB_INITIALIZATION_SCHUDULE = "initializationSchudule";

    public static final String DB_INITIALIZATION_SCHUDULE_ID = "initializationSchuduleId";

    public static final String DB_INITIALIZATION_SCHUDULE_SUBJECT = "initializationSubject";

    public DatabaseScheduleManager(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dbSchudule = "CREATE TABLE " + DB_SCHUDULE + " ( " + DB_SCHUDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_SCHUDULE_SUBJECT + " TEXT )";
        String dbInitializationSchudule = "CREATE TABLE " + DB_INITIALIZATION_SCHUDULE + " ( " + DB_INITIALIZATION_SCHUDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_INITIALIZATION_SCHUDULE_SUBJECT + " TEXT )";

        db.execSQL(dbSchudule);
        db.execSQL(dbInitializationSchudule);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_SCHUDULE);
        db.execSQL("DROP TABLE IF EXISTS " + DB_INITIALIZATION_SCHUDULE);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}