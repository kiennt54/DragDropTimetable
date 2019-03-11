package com.example.phongvu.mocktimetable.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Parcelable;
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
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.adapters.LessonAdapter;
import com.example.phongvu.mocktimetable.adapters.TableAdapter;
import com.example.phongvu.mocktimetable.commons.Constants;
import com.example.phongvu.mocktimetable.dao.ScheduleDao;
import com.example.phongvu.mocktimetable.listeners.ClickListener;
import com.example.phongvu.mocktimetable.listeners.RecyclerBinListener;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.models.Lesson;
import com.example.phongvu.mocktimetable.widgets.AppWidgetSchedule;
import com.example.phongvu.mocktimetable.widgets.ScheduleProvider;
import com.example.phongvu.mocktimetable.widgets.ScheduleService;

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

    private LinearLayout mDisableLayoutOverlay;

    private ScheduleDao mDao;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mDao = new ScheduleDao(this);
        mListCellData = (ArrayList<CellData>) Constants.createTestCells();
        mListLesson = (ArrayList<Lesson>) Constants.createTestLesson();
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
                    mDisableLayoutOverlay.setVisibility(View.VISIBLE);
                    enableDisableView(frame_timetable, false);
                    mLessonAdapter.setmIsItemTouchable(false);
                    mLessonAdapter.setClickListener(MainActivity.this);
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                } else {
                    btnEditLesson.setText("Edit Lesson Name");
                    mDisableLayoutOverlay.setVisibility(View.GONE);
                    FrameLayout frame_timetable = findViewById(R.id.frame_timetable);
                    enableDisableView(frame_timetable, true);
                    mLessonAdapter.setmIsItemTouchable(true);
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                }
            }
        });

        mRecyclerBin.setOnDragListener(new RecyclerBinListener(this, mRecyclerBin, mTableAdapter, mLessonAdapter));

        id = getIntent().getIntExtra(AppWidgetSchedule.ID_WIDGET, 0);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDao.deleteDataSubject();
                mDao.insertListSubject(mTableAdapter.getmListCellData());
                AppWidgetSchedule.sendRefreshBroadcast(MainActivity.this, id);
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
        mDisableLayoutOverlay = findViewById(R.id.overlay_view);
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
        mLessonAdapter.setClickListener(MainActivity.this);
        mRecyclerTimetabe.setAdapter(mTableAdapter);
        mRecyclerLesson.setAdapter(mLessonAdapter);
        mRecyclerLesson.setLayoutManager(mManagerLesson);
    }

    public List<Lesson> createTestLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("Toán"));
        lessons.add(new Lesson("IT"));
        lessons.add(new Lesson("Tiếng Anh"));
        lessons.add(new Lesson("Lý"));
        lessons.add(new Lesson("Hóa"));
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

    private List<CellData> createEmptyCells() {
        List<CellData> cellDatas = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            cellDatas.add(new CellData(new Lesson("")));
        }
        return cellDatas;
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
            startActivityForResult(intentEdit, REQUEST_CODE);
        }
    }


    private void addLesson(final int position) {
        AlertDialog.Builder dialogAdd = new AlertDialog.Builder(MainActivity.this);
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
                mListLesson.get(position).setName(mEdtAddLesson.getText().toString());
                mLessonAdapter.notifyDataSetChanged();
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
                        mListLesson.set(mPositionEdit,new Lesson(newName));
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
}
