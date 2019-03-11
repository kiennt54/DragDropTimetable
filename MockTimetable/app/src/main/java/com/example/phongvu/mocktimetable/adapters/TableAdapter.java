package com.example.phongvu.mocktimetable.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.listeners.CellDragAndDropListener;
import com.example.phongvu.mocktimetable.listeners.CellTouchListener;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.commons.Constants;

import java.util.ArrayList;

import static com.example.phongvu.mocktimetable.commons.Constants.NUMCELL_TABLE_TIMETABLE;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.CellHolder> {

    private ArrayList<CellData> mListCellData;

    private Context mContext;

    private ViewGroup mViewGroup;
    private String[] mWeek = {"", "Monday", "Tuesday", "Wednesday", "Thurday", "Friday", "Saturnday"};
    private String[] mLesson = {"Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5", "Lesson 6"};

    public TableAdapter(ArrayList<CellData> listCellData, Context context) {
        this.mListCellData = listCellData;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public CellHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.single_cell_layout, parent, false);
        CellHolder cellHolder = new CellHolder(itemView);
        this.mViewGroup = parent;
        return cellHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CellHolder cellHolder, int position) {
        CellData cellData = mListCellData.get(position);
        cellHolder.textView.setText(cellData.getLesson().getName());

        if (checkHeader(position)) {
            cellHolder.itemView.setOnTouchListener(null);
            cellHolder.itemView.setOnDragListener(null);
        } else {
            cellHolder.itemView.setOnTouchListener(new CellTouchListener(this, (RecyclerView) mViewGroup, cellHolder));
            cellHolder.itemView.setOnDragListener(new CellDragAndDropListener(this, (RecyclerView) mViewGroup));
        }

        if (position > 0 && position < 7) {
            cellHolder.textView.setText(mWeek[position]);
        }

        if ((position % 7 == 0) && (position < 43) && (position > 0)) {
            cellHolder.textView.setText(mLesson[(position / 7) - 1]);
        }

    }

    @Override
    public int getItemCount() {
        return NUMCELL_TABLE_TIMETABLE;
    }


    public static class CellHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public CellHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_subject);
        }
    }

    public ArrayList<CellData> getmListCellData() {
        return mListCellData;
    }

    public void setmListCellData(ArrayList<CellData> mListCellData) {
        this.mListCellData = mListCellData;
    }

    public boolean checkHeader(int i) {
        if (i > 0 && i < 7) {
            return true;
        }
        if (i % 7 == 0 && i < 43) {
            return true;
        }
        return false;
    }


}