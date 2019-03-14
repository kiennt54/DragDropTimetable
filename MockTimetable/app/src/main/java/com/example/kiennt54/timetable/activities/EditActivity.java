package com.example.kiennt54.timetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiennt54.timetable.R;
import com.example.kiennt54.timetable.commons.Constants;
import com.example.kiennt54.timetable.models.Lesson;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private Button mBtnNameLessonEdit;

    private Button mBtnOkEdit;

    private Button mBtnCancelEdit;

    private EditText mEdtNewLesson;

    private Lesson mLesson;

    private List<Lesson> mLessonList;

    private String mOldLesson;

    private final View.OnClickListener mBtnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLesson.setName(mOldLesson);
            Intent intentEdit = new Intent();
            intentEdit.putExtra("KEY_INTENT_RESULT", mLesson);
            setResult(RESULT_OK, intentEdit);
            finish();
        }
    };

    private final View.OnClickListener mBtnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            String newLesson = mEdtNewLesson.getText().toString();
//
//            if (newLesson.equals("")) {
//                Toast.makeText(EditActivity.this, "PLease Input New Lesson", Toast.LENGTH_SHORT).show();
//            } else {
//                if (newLesson != null) {
//                    mLesson.setName(newLesson);
//                }
//                Intent intentEdit = new Intent();
//                intentEdit.putExtra("KEY_INTENT_RESULT", (Serializable) mLesson);
//                setResult(RESULT_OK, intentEdit);
//                finish();

//                if(Constants.checkExistedLesson(mLessonList, newLesson)){
//                    if (newLesson != null) {
//                        mLesson.setName(newLesson);
//                    }
//                    Intent intentEdit = new Intent();
//                    intentEdit.putExtra("KEY_INTENT_RESULT", (Serializable) mLesson);
//                    setResult(RESULT_OK, intentEdit);
//                    finish();
//                }
//            }

            String newLesson = mEdtNewLesson.getText().toString();

            if (newLesson.equals("")) {
                Toast.makeText(EditActivity.this, "PLease Input New Lesson", Toast.LENGTH_SHORT).show();
            } else {
                if (Constants.checkExistedLesson(mLessonList, newLesson)) {
                    if (newLesson != null) {
                        mLesson.setName(newLesson);
                    }
                    Intent intentEdit = new Intent();
                    intentEdit.putExtra("KEY_INTENT_RESULT", mLesson);
                    setResult(RESULT_OK, intentEdit);
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Lesson Existed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lesson_layout);

        initView();

        Intent intentReceived = getIntent();

        String nameLesson = null;

        if (intentReceived != null) {
            mLesson = intentReceived.getParcelableExtra("KEY_INTENT_EDIT");
            nameLesson = mLesson.getName();
            mLessonList = intentReceived.getParcelableArrayListExtra("KEY_LIST_LESSON");
            mOldLesson = nameLesson;
        }

        if (nameLesson != null) {
            mBtnNameLessonEdit.setText(nameLesson);
        }
    }

    private void initView() {
        mBtnNameLessonEdit = findViewById(R.id.btn_name_lesson_edit);
        mBtnCancelEdit = findViewById(R.id.btn_edit_cancel);
        mBtnOkEdit = findViewById(R.id.btn_edit_ok);
        mEdtNewLesson = findViewById(R.id.edt_new_name_lesson);
        mBtnCancelEdit.setOnClickListener(mBtnCancelClick);
        mBtnOkEdit.setOnClickListener(mBtnOkClick);
    }
}
