package com.example.hoon.borisdiary.UI.ToDo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoon.borisdiary.Bean.TODO;
import com.example.hoon.borisdiary.DB.DBHelper;
import com.example.hoon.borisdiary.R;
import com.example.hoon.borisdiary.UI.DiaryApplication;
import com.example.hoon.borisdiary.UI.Main.MainActivity;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

public class TODO_AddActivity extends Activity {
    private DBHelper mDB;
    private EditText mEt_ToDo;
    private LinearLayout mBtnSave;
    private LinearLayout mLayout_color;
    private ImageButton mBtnBack;
    private ArrayList<Integer> mColorList;
    private int mClicked_Count=0;
    private int mClicked_Color=0xffffff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ((TextView)findViewById(R.id.app_header_title)).setText("추가하기");

        //초기화
        mDB = DiaryApplication.getDB();
        mColorList = getColorList();

        //뷰 바인딩
        mEt_ToDo = findViewById(R.id.todo_contents);
        mBtnSave = findViewById(R.id.btnSave);
        mLayout_color = findViewById(R.id.todo_color);
        mBtnBack = findViewById(R.id.back_key);

        //이벤트처리
        AddVeiw_ColorList(mColorList);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TODO_AddActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        mEt_ToDo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //글자 수에 따른 이벤트 처리
                if(s.length()>0){
                    mBtnSave.setBackgroundResource(R.color.color_btn_enabled);
                    if(s.length()==100)
                        DiaryApplication.DiaryDialog(TODO_AddActivity.this,"100자까지만 가능합니다.");
                }else{
                    mBtnSave.setBackgroundResource(R.color.color_btn_unable);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAuthBtn();
            }
        });
        getColorList();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TODO_AddActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }


    public  ArrayList<Integer> getColorList(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        Cursor color = mDB.selectAllColor();

        if (color.moveToFirst()) {
            do {
                int value = color.getInt(color.getColumnIndex(DBHelper.COL_COLOR));
                list.add(value);
            } while (color.moveToNext());
        }

        return list;
    }

    private void AddVeiw_ColorList(ArrayList<Integer> list){
        LinearLayout linearLayout = null;
        for(int i=0;i<list.size();i++){
            if(i%3==0){
                // 레이아웃 생성
                linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams linearLayoutParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(linearLayoutParams);
                mLayout_color.addView(linearLayout);
            }
            int value = list.get(i);
            final FrameLayout colorview= (FrameLayout) View.inflate(TODO_AddActivity.this,R.layout.item_color_settting,null);
            colorview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView color = v.findViewById(R.id.item_color);
                    ImageView clicked = v.findViewById(R.id.item_color_clicked);
                    CheckAuthColorBtn(color,clicked);

                }
            });
            ImageView imageView = colorview.findViewById(R.id.item_color);
            imageView.setBackgroundColor(value);
            imageView.setTag(value);

            linearLayout.addView(colorview);

        }
    }
//
//    private LinearLayout CreateColorView(int color){
//
//        return layout;
//    }

    //버튼 클릭시 조건 확인
    private void CheckAuthBtn() {
        if (mEt_ToDo.getText().toString().isEmpty()) {
            DiaryApplication.DiaryDialog(TODO_AddActivity.this,"할 일을 입력해주세요.");
        } else {
            TODO todo = new TODO();
            todo.setTodo_color(mClicked_Color);
            todo.setTodo(mEt_ToDo.getText().toString());
            todo.setState(DBHelper.TODO_STATE_CREATE);

            mDB.insertTODO(todo);
            Intent intent = new Intent(TODO_AddActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private void CheckAuthColorBtn(ImageView color, ImageView clicked) {
        if ((mClicked_Count<1) && (clicked.getVisibility()==View.GONE)) {
            clicked.setVisibility(View.VISIBLE);
            mClicked_Color = (int)color.getTag();
            mClicked_Count++;
        }
        else if((mClicked_Count>=1) && (clicked.getVisibility()==View.VISIBLE)){
            clicked.setVisibility(View.GONE);
            mClicked_Color = 0xffffff;
            mClicked_Count--;
        }
    }
}
