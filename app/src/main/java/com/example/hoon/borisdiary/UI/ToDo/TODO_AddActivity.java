package com.example.hoon.borisdiary.UI.ToDo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.hoon.borisdiary.DB.DBHelper;
import com.example.hoon.borisdiary.R;
import com.example.hoon.borisdiary.UI.DiaryApplication;

public class TODO_AddActivity extends Activity {
    private DBHelper mDB;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        //초기화
        mContext = getApplicationContext();
        mDB = DiaryApplication.getDB();
    }
}
