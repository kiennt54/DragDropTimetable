package com.example.phongvu.mocktimetable.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.phongvu.mocktimetable.R;
import com.example.phongvu.mocktimetable.adapters.LessonAdapter;
import com.example.phongvu.mocktimetable.adapters.TableAdapter;
import com.example.phongvu.mocktimetable.listeners.ClickListener;
import com.example.phongvu.mocktimetable.listeners.LessonTouchListener;
import com.example.phongvu.mocktimetable.models.CellData;
import com.example.phongvu.mocktimetable.models.Lesson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.example.phongvu.mocktimetable.commons.Constants.TIME_TABLE_DRAG;

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

    private Button mBtnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTestCells();

        createTestLessons();

        InitView();
        InitAction();

        mRecyclerBin.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (dragMode == TIME_TABLE_DRAG) {
                            mRecyclerBin.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                                    R.anim.scale_recyclerbin));
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        int startedPos = -1;
                        Set<Map.Entry<Integer, CellData>> draggingCell = MainActivity.draggingCell.entrySet();
                        for (Map.Entry<Integer, CellData> entry : draggingCell) {
                            startedPos = entry.getKey();
                        }
                        if (dragMode == TIME_TABLE_DRAG) {
                            mTableAdapter.getmListCellData().set(startedPos, new CellData(new Lesson("")));
                        }

                        mTableAdapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
    }

    private void InitAction() {
        btnEditLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEditMode = !mEditMode;

                if (mEditMode) {
                    btnEditLesson.setText("Cancel editting");
                    LinearLayout linear_timetable = findViewById(R.id.overlay_view);
                    FrameLayout frame_timetable = findViewById(R.id.frame_timetable);
                    linear_timetable.setVisibility(View.VISIBLE);
                    enableDisableView(frame_timetable, false);
                    mLessonAdapter = new LessonAdapter(mListLesson, MainActivity.this);
                    mLessonAdapter.isTouch = false;
                    mLessonAdapter.setClickListener(MainActivity.this);
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                } else {
                    btnEditLesson.setText("Edit Lesson Name");
                    LinearLayout linear_timetable = findViewById(R.id.overlay_view);
                    linear_timetable.setVisibility(View.GONE);
                    FrameLayout frame_timetable = findViewById(R.id.frame_timetable);
                    enableDisableView(frame_timetable, true);
                    mLessonAdapter = new LessonAdapter(mListLesson, MainActivity.this);
                    mLessonAdapter.isTouch = true;
                    mRecyclerLesson.setAdapter(mLessonAdapter);
                }
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

    private void InitView() {
        btnOK = findViewById(R.id.btn_OK);
        btnCancel = findViewById(R.id.btn_Cancel);
        btnEditLesson = findViewById(R.id.btn_EditLesson);
        mRecyclerTimetabe = findViewById(R.id.recyclerTimetabe);
        mRecyclerLesson = findViewById(R.id.recyclerLesson);
        mRecyclerBin = findViewById(R.id.recyclerBin);
        mManagerTimetable = new GridLayoutManager(this, 6);
        mManagerLesson = new GridLayoutManager(this, 3);
        mRecyclerTimetabe.setLayoutManager(mManagerTimetable);
        mTableAdapter = new TableAdapter(mListCellData, this);
        mLessonAdapter = new LessonAdapter(mListLesson, this);
        mLessonAdapter.setClickListener(MainActivity.this);
        mRecyclerTimetabe.setAdapter(mTableAdapter);
        mRecyclerLesson.setAdapter(mLessonAdapter);
        mRecyclerLesson.setLayoutManager(mManagerLesson);
    }

    private void createTestLessons() {
        mListLesson = new ArrayList<>();
        mListLesson.add(new Lesson("Math"));
        mListLesson.add(new Lesson("IT"));
        mListLesson.add(new Lesson("English"));
        mListLesson.add(new Lesson("Physic"));
        mListLesson.add(new Lesson("Chermistry"));
        mListLesson.add(new Lesson(""));
        mListLesson.add(new Lesson(""));
        mListLesson.add(new Lesson(""));
        mListLesson.add(new Lesson(""));
    }

    private void createTestCells() {
        mListCellData = new ArrayList<>();
        mListCellData.add(new CellData(new Lesson("Math")));
        mListCellData.add(new CellData(new Lesson("IT")));
        mListCellData.add(new CellData(new Lesson("English")));
        mListCellData.add(new CellData(new Lesson("Physic")));
        mListCellData.add(new CellData(new Lesson("Chermistry")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("IT")));
        mListCellData.add(new CellData(new Lesson("English")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Chermistry")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Math")));
        mListCellData.add(new CellData(new Lesson("IT")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Physic")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Math")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Physic")));
        mListCellData.add(new CellData(new Lesson("Chermistry")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Math")));
        mListCellData.add(new CellData(new Lesson("IT")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Physic")));
        mListCellData.add(new CellData(new Lesson("Chermistry")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Math")));
        mListCellData.add(new CellData(new Lesson("IT")));
        mListCellData.add(new CellData(new Lesson("English")));
        mListCellData.add(new CellData(new Lesson("")));
        mListCellData.add(new CellData(new Lesson("Chermistry")));
        mListCellData.add(new CellData(new Lesson("")));
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
                        mListLesson.get(mPositionEdit).setName(newName);
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
