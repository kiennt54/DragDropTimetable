package com.example.kiennt54.timetable.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CellData implements Parcelable{

    Lesson lesson;

    public CellData(Lesson lesson) {
        this.lesson = lesson;
    }

    public CellData() {
    }

    protected CellData(Parcel in) {
        lesson = in.readParcelable(Lesson.class.getClassLoader());
    }

    public static final Creator<CellData> CREATOR = new Creator<CellData>() {
        @Override
        public CellData createFromParcel(Parcel in) {
            return new CellData(in);
        }

        @Override
        public CellData[] newArray(int size) {
            return new CellData[size];
        }
    };

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(lesson, flags);
    }
}