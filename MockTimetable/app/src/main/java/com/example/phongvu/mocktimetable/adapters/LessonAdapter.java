package com.example.phongvu.mocktimetable.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.listeners.ClickListener;
import com.example.phongvu.mocktimetable.listeners.LessonTouchListener;
import com.example.phongvu.mocktimetable.models.Lesson;

import java.util.ArrayList;

import static com.example.phongvu.mocktimetable.commons.Constants.NUMCELL_TABLE_LESSON;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder>{

    private ArrayList<Lesson> mListLesson;

    private Context mContext;

    private ClickListener mClickListener;

    private boolean mIsItemTouchable = true;

    private ViewGroup mParent;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public LessonAdapter(ArrayList<Lesson> mListLesson, Context mContext,boolean isItemTouchable) {
        this.mListLesson = mListLesson;
        this.mContext = mContext;
        this.mIsItemTouchable = isItemTouchable;
    }

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_cell_layout, viewGroup, false);
        LessonHolder lessonHolder = new LessonHolder(view);
        mParent = viewGroup;
        return lessonHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder lessonHolder, final int i) {
        lessonHolder.textView.setText(mListLesson.get(i).getName());
        if (mIsItemTouchable) {
            lessonHolder.itemView.setOnTouchListener(new LessonTouchListener(this, lessonHolder, (RecyclerView) mParent));
        } else {
            lessonHolder.itemView.setOnTouchListener(null);
        }
        lessonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.clickPosition(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return NUMCELL_TABLE_LESSON;
    }

    public static class LessonHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_subject);
        }
    }

    public ArrayList<Lesson> getmListLesson() {
        return mListLesson;
    }

    public void setmListLesson(ArrayList<Lesson> mListLesson) {
        this.mListLesson = mListLesson;
    }

    public void setmIsItemTouchable(boolean mIsItemTouchable) {
        this.mIsItemTouchable = mIsItemTouchable;
    }
}
