package com.example.kiennt54.timetable.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;
import com.example.kiennt54.timetable.activities.MainActivity;
import com.example.kiennt54.timetable.adapters.TableAdapter;
import com.example.kiennt54.timetable.models.CellData;
import com.example.kiennt54.timetable.models.Lesson;
import java.util.Map;
import java.util.Set;
import static android.view.DragEvent.ACTION_DROP;
import static com.example.kiennt54.timetable.commons.Constants.TIMETABLE_DRAG;

public class CellDragAndDropListener implements View.OnDragListener{

    private RecyclerView mRecyclerView;

    private TableAdapter mTableAdapter;

    public CellDragAndDropListener(TableAdapter tableAdapter, RecyclerView recyclerView){
        this.mRecyclerView = recyclerView;
        this.mTableAdapter = tableAdapter;
    }

    @Override
    public boolean onDrag(View dragView, DragEvent event) {
        switch (event.getAction()){

            case ACTION_DROP:
                int startedPos = -1;
                int dropPos = mRecyclerView.getChildAdapterPosition(dragView);
                CellData cellData = null;
                Set< Map.Entry< Integer,CellData> > draggingCell = MainActivity.draggingCell.entrySet();
                for(Map.Entry<Integer,CellData> entry :draggingCell){
                        cellData = entry.getValue();
                        startedPos = entry.getKey();
                }

                if(mTableAdapter.getmListCellData().get(dropPos).getLesson().getName().equals("")){
                    if(MainActivity.dragMode == TIMETABLE_DRAG && startedPos >= -1){
                        mTableAdapter.getmListCellData().set(dropPos,cellData);
                        mTableAdapter.getmListCellData().set(startedPos,
                                new CellData(new Lesson("")));
                    }else{
                        mTableAdapter.getmListCellData().set(dropPos,cellData);
                    }
                }else{
                    Toast.makeText(mRecyclerView.getContext(),"Đã có môn đăng ký",Toast.LENGTH_SHORT).show();
                }

                mTableAdapter.notifyDataSetChanged();
                break;


        }

        return true;
    }
}
