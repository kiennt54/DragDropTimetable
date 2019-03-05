package com.example.phongvu.mocktimetable.listeners;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.example.phongvu.mocktimetable.activities.MainActivity;
import com.example.phongvu.mocktimetable.adapters.TableAdapter;
import com.example.phongvu.mocktimetable.models.CellData;

import java.util.HashMap;

import static com.example.phongvu.mocktimetable.commons.Constants.TIME_TABLE_DRAG;

public class CellTouchListener implements View.OnTouchListener{

    RecyclerView mRecyclerView;

    TableAdapter.CellHolder mCellHolder;

    TableAdapter mTableAdapter;

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
            MainActivity.dragMode = TIME_TABLE_DRAG;
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
