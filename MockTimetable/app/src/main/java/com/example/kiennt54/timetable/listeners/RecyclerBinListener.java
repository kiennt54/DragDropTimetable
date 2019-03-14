package com.example.kiennt54.timetable.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.kiennt54.timetable.R;
import com.example.kiennt54.timetable.activities.MainActivity;
import com.example.kiennt54.timetable.adapters.LessonAdapter;
import com.example.kiennt54.timetable.adapters.TableAdapter;
import com.example.kiennt54.timetable.dao.LessonDao;
import com.example.kiennt54.timetable.models.CellData;
import com.example.kiennt54.timetable.models.Lesson;

import java.util.Map;
import java.util.Set;

import static com.example.kiennt54.timetable.activities.MainActivity.dragMode;
import static com.example.kiennt54.timetable.commons.Constants.LISTLESSON_DRAG;
import static com.example.kiennt54.timetable.commons.Constants.TIMETABLE_DRAG;

public class RecyclerBinListener implements View.OnDragListener {

    private Context mContext;
    private ImageView mRecyclerBin;
    private TableAdapter mTableAdapter;
    private LessonAdapter mLessonAdapter;
    private LessonDao mLessonDao;
    public RecyclerBinListener(Context mContext,
                               ImageView mRecyclerBin,
                               TableAdapter mTableAdapter,
                               LessonAdapter lessonAdapter) {
        this.mContext = mContext;
        this.mRecyclerBin = mRecyclerBin;
        this.mTableAdapter = mTableAdapter;
        this.mLessonAdapter = lessonAdapter;
        mLessonDao = new LessonDao(mContext);
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

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setMessage("Are you sure you want to delete this lesson?");
                    final CellData finalCellDelete = cellDelete;
                    final int finalStartedPos = startedPos;
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mLessonAdapter.getmListLesson().set(finalStartedPos, new Lesson(""));
                            mLessonAdapter.notifyDataSetChanged();
                            mLessonDao.clearTableLesson();
                            mLessonDao.insertListLesson(mLessonAdapter.getmListLesson());
                            for (CellData cellData : mTableAdapter.getmListCellData()) {
                                if (cellData.getLesson().getName().equals(finalCellDelete.getLesson().getName())) {
                                    cellData.setLesson(new Lesson(""));
                                }
                            }
                            mLessonAdapter.notifyDataSetChanged();
                            mTableAdapter.notifyDataSetChanged();
                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
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
                        R.anim.normal_recyclerbin));
                break;
        }
        return true;
    }
}
