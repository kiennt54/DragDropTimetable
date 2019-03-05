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

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.CellHolder> {

    private ArrayList<CellData> mListCellData;

    private Context mContext;

    final int VIEW_TYPE_HEADERCOLUMN = 0;
    final int VIEW_TYPE_HEADERROW = 1;
    final int VIEW_TYPE_CELLDATA = 2;

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
        itemView.setOnTouchListener(new CellTouchListener(this,(RecyclerView) parent,cellHolder));
        itemView.setOnDragListener(new CellDragAndDropListener(this, (RecyclerView) parent));
        return cellHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CellHolder cellHolder, int position) {
        CellData cellData = mListCellData.get(position);
        cellHolder.textView.setText(cellData.getLesson().getName());
    }

    @Override
    public int getItemCount() {
        return Constants.NUM_CELL_TABLE;
    }


    public static class CellHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public CellHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    public ArrayList<CellData> getmListCellData() {
        return mListCellData;
    }

    public void setmListCellData(ArrayList<CellData> mListCellData) {
        this.mListCellData = mListCellData;
    }


}