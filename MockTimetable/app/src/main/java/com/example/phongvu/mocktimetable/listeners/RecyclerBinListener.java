package com.example.phongvu.mocktimetable.listeners;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.activities.MainActivity;
import com.example.phongvu.mocktimetable.adapters.LessonAdapter;
import com.example.phongvu.mocktimetable.adapters.TableAdapter;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.models.Lesson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.phongvu.mocktimetable.activities.MainActivity.dragMode;
import static com.example.phongvu.mocktimetable.commons.Constants.LISTLESSON_DRAG;
import static com.example.phongvu.mocktimetable.commons.Constants.TIMETABLE_DRAG;

public class RecyclerBinListener implements View.OnDragListener {

    private Context mContext;
    private ImageView mRecyclerBin;
    private TableAdapter mTableAdapter;
    private LessonAdapter mLessonAdapter;

    public RecyclerBinListener(Context mContext,
                               ImageView mRecyclerBin,
                               TableAdapter mTableAdapter,
                               LessonAdapter lessonAdapter) {
        this.mContext = mContext;
        this.mRecyclerBin = mRecyclerBin;
        this.mTableAdapter = mTableAdapter;
        this.mLessonAdapter = lessonAdapter;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {

            case DragEvent.ACTION_DRAG_ENTERED:
                mRecyclerBin.startAnimation(AnimationUtils.loadAnimation(mContext,
                        R.anim.scale_recyclerbin));
                break;

            case DragEvent.ACTION_DROP:
                int startedPos = -1;
                Set<Map.Entry<Integer, CellData>> draggingCell = MainActivity.draggingCell.entrySet();
                CellData cellDelete = null;
                for (Map.Entry<Integer, CellData> entry : draggingCell) {
                    startedPos = entry.getKey();
                    cellDelete = entry.getValue();
                }

                if (dragMode == TIMETABLE_DRAG) {
                    mTableAdapter.getmListCellData().set(startedPos, new CellData(new Lesson("")));
                } else if (dragMode == LISTLESSON_DRAG) {
                    mLessonAdapter.getmListLesson().set(startedPos, new Lesson(""));
                    mLessonAdapter.notifyDataSetChanged();
                    for (CellData cellData : mTableAdapter.getmListCellData()) {
                        if (cellData.equals(cellDelete)) {
                            cellData.setLesson(new Lesson(""));
                        }
                    }
                }

                mLessonAdapter.notifyDataSetChanged();
                mTableAdapter.notifyDataSetChanged();

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                mRecyclerBin.startAnimation(AnimationUtils.loadAnimation(mContext,
                        R.anim.unscale_recyclerbin));
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                mRecyclerBin.startAnimation(AnimationUtils.loadAnimation(mContext,
                        R.anim.normalscale_recyclerbin));
                break;
        }
        return true;
    }
}
