package com.example.kiennt54.timetable.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kiennt54.timetable.R;
import com.example.kiennt54.timetable.adapters.LessonAdapter;
import com.example.kiennt54.timetable.adapters.TableAdapter;
import com.example.kiennt54.timetable.commons.Constants;
import com.example.kiennt54.timetable.dao.LessonDao;
import com.example.kiennt54.timetable.dao.ScheduleDao;
import com.example.kiennt54.timetable.listeners.ClickListener;
import com.example.kiennt54.timetable.listeners.RecyclerBinListener;
import com.example.kiennt54.timetable.models.CellData;
import com.example.kiennt54.timetable.models.Lesson;
import com.example.kiennt54.timetable.widgets.AppWidgetSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ClickListener {

    private static final int REQUEST_CODE = 1000;

    private static final int REQUEST_CODE_VOICE = 1001;

    private RecyclerView mRecyclerTimetabe, mRecyclerLesson;

    private RecyclerView.LayoutManager mManagerTimetable, mManagerLesson;

    private ArrayList<CellData> mListCellData;

    private ArrayList<Lesson> mListLesson;

    public static Map<Integer, CellData> draggingCell = null;

    private ImageView mRecyclerBin;

    private TableAdapter mTableAdapter;

    private LessonAdapter mLessonAdapter;

    public static int dragMode = 0;

    private Button btnEditLesson;

    private Button btnOK, btnCancel;

    public boolean mEditMode = false;

    private EditText mEdtAddLesson;

    private ImageView mImgVoice;

    private int mPositionEdit;

    private LinearLayout mDisableTimetableOverlay,mDisableButtonOverlay;

    private ScheduleDao mScheduleDao;

    private LessonDao mLessonDao;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mScheduleDao = new ScheduleDao(this);
        mLessonDao = new LessonDao(this);
        mListCellData = new ArrayList<>();
        if(mLessonDao.getListLesson().isEmpty()){
            mListLesson = (ArrayList<Lesson>) Constants.createTestLesson();
        }else{
            mListLesson = (ArrayList<Lesson>) mLessonDao.getListLesson();
        }
        Intent intentPut = getIntent();

        Bundle arg = intentPut.getBundleExtra("bundleCells");
        mListCellData = (ArrayList<CellData>) arg.getSerializable("listCells");
        id = intentPut.getIntExtra(AppWidgetSchedule.ID_WIDGET, 0);
        initView();
        initAction();
    }

    private void initAction() {
        btnEditLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEditMode = !mEditMode;
                if (mEditMode) {
                    btnEditLesson.setText("Cancel editting");
                    FrameLayout frame_timetable = findViewById(R.id.frame_timetable);
                    FrameLayout frame_lesson = findViewById(R.id.frame_lesson);
                    frame_lesson.setBackgroundColor(Color.parseColor("#c0000000"));
                    mDisableTimetableOverlay.setVisibility(View.VISIBLE);
                    mDisableButtonOverlay.setVisibility(View.VISIBLE);
                    enableDisableView(frame_timetable, false);
                    btnOK.setEnabled(false);
                    btnCancel.setEnabled(false);
                    mLessonAdapter.setmIsItemTouchable(false);
                    mLessonAdapter.setClickListener(MainActivity.this);
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                } else {
                    btnEditLesson.setText("Edit Lesson Name");
                    mDisableTimetableOverlay.setVisibility(View.GONE);
                    mDisableButtonOverlay.setVisibility(View.GONE);
                    FrameLayout frame_timetable = findViewById(R.id.frame_timetable);
                    FrameLayout frame_lesson = findViewById(R.id.frame_lesson);
                    frame_lesson.setBackgroundColor(Color.TRANSPARENT);
                    enableDisableView(frame_timetable, true);
                    btnOK.setEnabled(true);
                    btnCancel.setEnabled(true);
                    mLessonAdapter.setmIsItemTouchable(true);
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                }
            }
        });

        mRecyclerBin.setOnDragListener(new RecyclerBinListener(this, mRecyclerBin, mTableAdapter, mLessonAdapter));

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScheduleDao.deleteDataSubject();
                mScheduleDao.insertListSubject(mTableAdapter.getmListCellData());
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                AppWidgetSchedule.sendRefreshBroadcast(MainActivity.this, id);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    private void initView() {
        mDisableTimetableOverlay = findViewById(R.id.overlay_timetable);
        mDisableButtonOverlay = findViewById(R.id.overlay_disablebutton);
        btnOK = findViewById(R.id.btn_OK);
        btnCancel = findViewById(R.id.btn_Cancel);
        btnEditLesson = findViewById(R.id.btn_EditLesson);
        mRecyclerTimetabe = findViewById(R.id.recyclerTimetabe);
        mRecyclerLesson = findViewById(R.id.recyclerLesson);
        mRecyclerBin = findViewById(R.id.recyclerBin);
        mManagerTimetable = new GridLayoutManager(this, 7);
        mManagerLesson = new GridLayoutManager(this, 3);
        mRecyclerTimetabe.setLayoutManager(mManagerTimetable);
        mTableAdapter = new TableAdapter(mListCellData, this);
        mLessonAdapter = new LessonAdapter(mListLesson, this, true);
        mLessonAdapter.setClickListener(this);
        mRecyclerTimetabe.setAdapter(mTableAdapter);
        mRecyclerLesson.setAdapter(mLessonAdapter);
        mRecyclerLesson.setLayoutManager(mManagerLesson);
    }

    public List<Lesson> createTestLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        lessons.add(new Lesson(""));
        return lessons;
    }

    @Override
    public void clickPosition(int position) {
        if (mListLesson.get(position).getName().equals("")) {
            addLesson(position);
        } else {
            mPositionEdit = position;
            Lesson lesson = mListLesson.get(position);
            Intent intentEdit = new Intent(MainActivity.this, EditActivity.class);
            intentEdit.putExtra("KEY_INTENT_EDIT", lesson);
            intentEdit.putParcelableArrayListExtra("KEY_LIST_LESSON", mListLesson);
            startActivityForResult(intentEdit, REQUEST_CODE);
        }
    }


    public void addLesson(final int position) {
        AlertDialog.Builder dialogAdd = new AlertDialog.Builder(this);
        dialogAdd.setTitle("ADD LESSON");
        dialogAdd.setMessage("Please enter name lesson");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View addView = layoutInflater.inflate(R.layout.add_lesson_layout, null);
        mEdtAddLesson = addView.findViewById(R.id.edt_lesson_add);
        mImgVoice = addView.findViewById(R.id.img_voice);

        mImgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE_VOICE);
                } else {
                    Toast.makeText(MainActivity.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogAdd.setView(addView);
        dialogAdd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Constants.checkExistedLesson(mListLesson, mEdtAddLesson.getText().toString())){
                    mListLesson.get(position).setName(mEdtAddLesson.getText().toString().trim());
                    mLessonAdapter.notifyDataSetChanged();
                    synchronziedDBLesson();
                }else {
                    Toast.makeText(MainActivity.this, "Lesson Existed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogAdd.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogAdd.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Lesson lesson = data.getParcelableExtra("KEY_INTENT_RESULT");
                    if (lesson != null) {
                        String newName = lesson.getName();
                        String oldName = mLessonAdapter.getmListLesson().get(mPositionEdit).getName();
                        mListLesson.set(mPositionEdit, new Lesson(newName));
                        for(CellData cellData : mListCellData){
                            if(cellData.getLesson().getName().equals(oldName)){
                                cellData.setLesson(new Lesson(newName));
                            }
                        }
                        synchronziedDBLesson();
                        mTableAdapter.notifyDataSetChanged();
                        mLessonAdapter.notifyDataSetChanged();
                    }
                }
                break;

            case REQUEST_CODE_VOICE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEdtAddLesson.setText(result.get(0));
                }
                break;
        }
    }

    private void synchronziedDBLesson(){
        mLessonDao.clearTableLesson();
        mLessonDao.insertListLesson(mListLesson);
    }
}
