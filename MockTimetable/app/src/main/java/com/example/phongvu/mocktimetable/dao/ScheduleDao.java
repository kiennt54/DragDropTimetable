package com.example.phongvu.mocktimetable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phongvu.mocktimetable.database.DatabaseScheduleManager;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {

    private SQLiteDatabase mDatabase;

    private DatabaseScheduleManager mScheduleManager;

    public ScheduleDao(Context context) {

        mScheduleManager = new DatabaseScheduleManager(context);
        mDatabase = mScheduleManager.open();
    }


    public void insertListSubject(List<CellData> mCellDatas) {

        for (int i = 0; i < mCellDatas.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseScheduleManager.DB_SCHUDULE_SUBJECT, mCellDatas.get(i).getLesson().getName());
            mDatabase.insert(DatabaseScheduleManager.DB_SCHUDULE, null, contentValues);
        }

    }

    public List<CellData> getSubjectFromCellData() {
        List<CellData> mCellDatas = new ArrayList<>();
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
