package com.example.kiennt54.timetable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiennt54.timetable.database.DatabaseScheduleManager;
import com.example.kiennt54.timetable.models.CellData;
import com.example.kiennt54.timetable.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {

    private SQLiteDatabase mDatabase;

    private DatabaseScheduleManager mScheduleManager;

    public ScheduleDao(Context context) {
        mScheduleManager = new DatabaseScheduleManager(context);
        mDatabase = mScheduleManager.open();
    }


    public long insertListSubject(List<CellData> mCellDatas) {

        long check = 0;
        for (int i = 0; i < mCellDatas.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseScheduleManager.DB_SCHUDULE_SUBJECT, mCellDatas.get(i).getLesson().getName());
            check = mDatabase.insert(DatabaseScheduleManager.DB_SCHUDULE, null, contentValues);
        }

        if (check != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public ArrayList<CellData> getSubjectFromCellData() {

        ArrayList<CellData> mCellDatas = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseScheduleManager.DB_SCHUDULE;

        Cursor cursor = mDatabase.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            CellData cellData = new CellData();
            Lesson lesson = new Lesson();
            lesson.setName(cursor.getString(cursor.getColumnIndex(DatabaseScheduleManager.DB_SCHUDULE_SUBJECT)));
            cellData.setLesson(lesson);

            mCellDatas.add(cellData);

            cursor.moveToNext();
        }

        return mCellDatas;

    }


    public void deleteDataSubject() {
        mDatabase.delete(DatabaseScheduleManager.DB_SCHUDULE, null, null);
    }


}
