package com.example.kiennt54.timetable.listeners;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.example.kiennt54.timetable.activities.MainActivity;
import com.example.kiennt54.timetable.adapters.TableAdapter;
import com.example.kiennt54.timetable.models.CellData;
import java.util.HashMap;
import static com.example.kiennt54.timetable.commons.Constants.TIMETABLE_DRAG;
public class CellTouchListener implements View.OnTouchListener{

    private RecyclerView mRecyclerView;

    private TableAdapter.CellHolder mCellHolder;

    private TableAdapter mTableAdapter;

    public CellTouchListener(TableAdapter tableAdapter, RecyclerView recyclerView, TableAdapter.CellHolder cellHolder){
        this.mRecyclerView = recyclerView;
        this.mCellHolder = cellHolder;
        this.mTableAdapter = tableAdapter;
    }

    @SuppressLint({"UseSparseArrays", "ClickableViewAccessibility"})
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            CellData cellData = mTableAdapter.getmListCellData().get(mCellHolder.getAdapterPosition());
            MainActivity.draggingCell = new HashMap<>();
            MainActivity.draggingCell.put(mRecyclerView.getChildAdapterPosition(view),cellData);
            MainActivity.dragMode = TIMETABLE_DRAG;
            ClipData dragData = ClipData.newPlainText("","");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(dragData,dragShadowBuilder,cellData,0);
            }else{
                view.startDrag(dragData,dragShadowBuilder,cellData,0);
            }
            return true;
        }else{
            return false;
        }
    }
}
