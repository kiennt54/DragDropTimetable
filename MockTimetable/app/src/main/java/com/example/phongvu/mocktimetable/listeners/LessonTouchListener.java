package com.example.phongvu.mocktimetable.listeners;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.phongvu.mocktimetable.activities.MainActivity;
import com.example.phongvu.mocktimetable.adapters.LessonAdapter;
import com.example.phongvu.mocktimetable.commons.Constants;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.models.Lesson;

import java.util.HashMap;

import static com.example.phongvu.mocktimetable.commons.Constants.LISTLESSON_DRAG;

public class LessonTouchListener implements View.OnTouchListener {

    private RecyclerView mRecyclerView;

    private LessonAdapter.LessonHolder mLessonHolder;

    private LessonAdapter mLessonAdapter;

    public LessonTouchListener(LessonAdapter mLessonAdapter, LessonAdapter.LessonHolder mLessonHolder, RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mLessonHolder = mLessonHolder;
        this.mLessonAdapter = mLessonAdapter;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            CellData cellData = new CellData(mLessonAdapter.getmListLesson().get(mLessonHolder.getAdapterPosition()));
            MainActivity.draggingCell = new HashMap<>();
            MainActivity.draggingCell.put(mRecyclerView.getChildAdapterPosition(view),cellData);
            MainActivity.dragMode = LISTLESSON_DRAG;
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, cellData, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, cellData, 0);
            } else {
                view.startDrag(data, shadowBuilder, cellData, 0);
            }
            return true;
        } else {
            return false;
        }
    }
}
