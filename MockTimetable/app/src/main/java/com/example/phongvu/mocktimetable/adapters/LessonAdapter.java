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

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {

    private ArrayList<Lesson> mListLesson;

    private Context mContext;

    private ClickListener mClickListener;

    public boolean isTouch = true;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public LessonAdapter(ArrayList<Lesson> mListLesson, Context mContext) {
        this.mListLesson = mListLesson;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_cell_layout, viewGroup, false);
        LessonHolder lessonHolder = new LessonHolder(view);
        if (isTouch) {
            view.setOnTouchListener(new LessonTouchListener(this, lessonHolder, (RecyclerView) viewGroup));
        } else {
            view.setOnTouchListener(null);
        }


        return lessonHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder lessonHolder, final int i) {
        lessonHolder.textView.setText(mListLesson.get(i).getName());

        lessonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.clickPosition(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListLesson.size();
    }

    public static class LessonHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    public ArrayList<Lesson> getmListLesson() {
        return mListLesson;
    }

    public void setmListLesson(ArrayList<Lesson> mListLesson) {
        this.mListLesson = mListLesson;
    }

}
