package com.example.phongvu.mocktimetable.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Lesson implements Parcelable {

    private String name;

    public Lesson() {
    }

    public Lesson(String name) {
        this.name = name;
    }

    protected Lesson(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(name, lesson.name);
    }

}
